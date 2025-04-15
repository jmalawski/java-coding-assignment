package wordssorter.infrastructure;

import org.junit.jupiter.api.Test;
import wordssorter.domain.Sentence;
import wordssorter.domain.SentenceSerializer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XMLSentenceSerializerTest {

    @Test
    void shouldSerializeSentences() {
        // given
        Sentence firstSentence = new Sentence(List.of("a", "b"));
        Sentence secondSentence = new Sentence(List.of("c"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteArrayOutputStream);
        SentenceSerializer serializer = new XMLSentenceSerializer(outputStream, String::compareTo);

        // when
        serializer.startDocument();
        serializer.write(firstSentence);
        serializer.write(secondSentence);
        serializer.endDocument();

        outputStream.close();
        String xml = byteArrayOutputStream.toString();

        //then
        assertEquals("""
                        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                        <text>
                        <sentence><word>a</word><word>b</word></sentence>
                        <sentence><word>c</word></sentence>
                        </text>
                        """.replaceAll("\n", ""),
                xml);
    }

    @Test
    void shouldEscapeSingleQuotes() {
        // given
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteArrayOutputStream);
        SentenceSerializer serializer = new XMLSentenceSerializer(outputStream, String::compareTo);

        // when
        serializer.startDocument();
        serializer.write(new Sentence(List.of("isn't")));
        serializer.endDocument();

        outputStream.close();
        String xml = byteArrayOutputStream.toString();

        //then
        assertTrue(xml.contains("<word>isn&apos;t</word>"));
    }

}