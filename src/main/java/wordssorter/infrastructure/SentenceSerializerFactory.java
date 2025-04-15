package wordssorter.infrastructure;

import wordssorter.domain.SentenceSerializer;

import java.io.PrintStream;
import java.util.Comparator;

public class SentenceSerializerFactory {
    public SentenceSerializer createFor(String outputType, PrintStream outputStream, Comparator<String> wordsComparator) {
        return switch (outputType.toLowerCase()) {
            case "csv" -> new CSVSentenceSerializer(outputStream, wordsComparator);
            case "xml" -> new XMLSentenceSerializer(outputStream, wordsComparator);
            default -> throw new IllegalArgumentException(String.format("Unknown serializer '%s'", outputType));
        };
    }
}
