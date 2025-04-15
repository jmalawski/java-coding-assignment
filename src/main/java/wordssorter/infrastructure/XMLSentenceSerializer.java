package wordssorter.infrastructure;

import com.ctc.wstx.api.WstxOutputProperties;
import com.ctc.wstx.sw.BaseNsStreamWriter;
import org.apache.commons.text.StringEscapeUtils;
import wordssorter.domain.Sentence;
import wordssorter.domain.SentenceSerializer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

class XMLSentenceSerializer implements SentenceSerializer {

    private final Comparator<String> wordsComparator;
    private final BaseNsStreamWriter writer;

    XMLSentenceSerializer(PrintStream outputStream, Comparator<String> wordsComparator) {
        this.wordsComparator = wordsComparator;
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        factory.setProperty(WstxOutputProperties.P_USE_DOUBLE_QUOTES_IN_XML_DECL, true);
        try {
            writer = (BaseNsStreamWriter) factory.createXMLStreamWriter(outputStream);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startDocument() {
        try {
            writer.writeStartDocument("1.0", StandardCharsets.UTF_8.name(), true);
            writer.writeStartElement("text");
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(Sentence sentence) {
        try {
            writer.writeStartElement("sentence");
            sentence.getSortedWords(wordsComparator)
                    .forEach(this::writeWord);
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeWord(String word) {
        try {
            writer.writeStartElement("word");
            writer.writeRaw(StringEscapeUtils.escapeXml10(word));
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endDocument() {
        try {
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}
