package wordssorter.infrastructure;

import org.junit.jupiter.api.Test;
import wordssorter.domain.Sentence;
import wordssorter.domain.SentenceSerializer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVSentenceSerializerTest {

    @Test
    void shouldSerializeSentences() {
        // given
        Sentence firstSentence = new Sentence(List.of("a", "b"));
        Sentence secondSentence = new Sentence(List.of("c"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteArrayOutputStream);
        SentenceSerializer serializer = new CSVSentenceSerializer(outputStream, String::compareTo);

        // when
        serializer.startDocument();
        serializer.write(firstSentence);
        serializer.write(secondSentence);
        serializer.endDocument();

        outputStream.close();
        String csv = byteArrayOutputStream.toString();

        //then
        assertEquals("""
                        , Word 1, Word 2
                        Sentence 1, a, b
                        Sentence 2, c
                        """,
                csv);
    }

}