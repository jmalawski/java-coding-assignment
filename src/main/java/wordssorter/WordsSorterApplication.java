package wordssorter;


import lombok.extern.slf4j.Slf4j;
import wordssorter.domain.SentenceBuilder;
import wordssorter.domain.SentenceSerializer;
import wordssorter.domain.WordSanitizer;
import wordssorter.domain.LowercaseFirstWordComparator;
import wordssorter.infrastructure.SentenceSerializerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class WordsSorterApplication {

    public static void main(String[] args) {
        Instant startTime = Instant.now();

        String outputType = parseOutputType(args);
        log.info("Chosen output: {}", outputType);

        SentenceSerializerFactory serializerFactory = new SentenceSerializerFactory();
        SentenceSerializer sentenceSerializer = serializerFactory.createFor(outputType, System.out,
                new LowercaseFirstWordComparator());
        processInput(sentenceSerializer);

        log.info("Finished: took {}", Duration.between(startTime, Instant.now()));
    }

    private static void processInput(SentenceSerializer sentenceSerializer) {
        log.info("Processing");
        SentenceBuilder sentenceBuilder = new SentenceBuilder();

        sentenceSerializer.startDocument();
        try (Scanner wordsScanner = createScanner()) {
            sentenceBuilder.buildSentences(
                    wordsScanner,
                    new WordSanitizer(),
                    sentenceSerializer::write
            );
        }
        sentenceSerializer.endDocument();
    }

    private static String parseOutputType(String[] args) {
        return Arrays.stream(args)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing output type argument"));
    }

    private static Scanner createScanner() {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\p{javaWhitespace}|,");
        return scanner;
    }
}
