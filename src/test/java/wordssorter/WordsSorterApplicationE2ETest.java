package wordssorter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

class WordsSorterApplicationE2ETest {

    @Test
    void shouldSerializeSmallInputToCSV() throws IOException, URISyntaxException {
        // when
        String captured = runCapturingStdOut(() ->
                runRedirectingFileToStdIn(() -> WordsSorterApplication.main(new String[]{"csv"}))
        );
        String expected = readExpectedOutput("sample.csv");

        // then
        Assertions.assertEquals(expected, captured);
    }

    @Test
    void shouldSerializeSmallInputToXML() throws IOException, URISyntaxException {
        // when
        String captured = runCapturingStdOut(() ->
                runRedirectingFileToStdIn(() -> WordsSorterApplication.main(new String[]{"xml"}))
        );

        String expected = readExpectedOutput("sample.xml")
                .replaceAll("\n", "");

        // then
        Assertions.assertEquals(expected, captured);
    }

    private void runRedirectingFileToStdIn(Runnable runnable) {
        InputStream originalIn = System.in;
        System.setIn(getClass().getClassLoader().getResourceAsStream("sample.in"));
        try {
            runnable.run();
        } finally {
            System.setIn(originalIn);
        }
    }

    private String runCapturingStdOut(Runnable runnable) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream capturingOut = new PrintStream(outputStream);
        System.setOut(capturingOut);
        try {
            runnable.run();
        } finally {
            System.setOut(originalOut);
        }
        capturingOut.close();
        return outputStream.toString();
    }

    private String readExpectedOutput(String name) throws URISyntaxException, IOException {
        Path path = Path.of(getClass().getClassLoader().getResource(name).toURI());
        return Files.readString(path);
    }
}