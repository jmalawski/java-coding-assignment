package wordssorter.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wordssorter.domain.Sentence;
import wordssorter.domain.SentenceSerializer;

import java.io.*;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
class CSVSentenceSerializer implements SentenceSerializer {

    private final PrintStream outputStream;
    private final Comparator<String> wordsComparator;

    private File tempFile;
    private PrintWriter tempFileWriter;
    private int maxColumnsCount;
    private long writtenSentencesCount;

    @Override
    public void startDocument() {
        try {
            tempFile = File.createTempFile("sentences-", ".csv");
            tempFileWriter = new PrintWriter(new FileWriter(tempFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(Sentence sentence) {
        List<String> words = sentence.getSortedWords(wordsComparator);
        updateMaxColumnsCount(words);

        tempFileWriter.println();
        tempFileWriter.print("Sentence " + ++writtenSentencesCount);
        words.forEach(word -> {
            tempFileWriter.print(", ");
            tempFileWriter.print(word);
        });
    }

    private void updateMaxColumnsCount(List<String> words) {
        if (maxColumnsCount < words.size()) {
            maxColumnsCount = words.size();
        }
    }

    @Override
    public void endDocument() {
        tempFileWriter.close();

        printHeaders();
        redirectTempFileToTargetOutputStream();

        if (!tempFile.delete()) {
            log.error("Unable to delete {}", tempFile.getAbsolutePath());
        }
    }

    private void printHeaders() {
        for (int i = 1; i <= maxColumnsCount; i++) {
            outputStream.print(", Word ");
            outputStream.print(i);
        }
    }

    private void redirectTempFileToTargetOutputStream() {
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputStream.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
