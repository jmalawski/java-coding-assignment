package wordssorter.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wordssorter.PersistentConsumer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class SentenceBuilderTest {

    @ParameterizedTest
    @ValueSource(strings = {"b.", "b!", "b?"})
    void shouldEndSentenceOnCommonEndingCharacters(String sentenceEnd) {
        // given
        SentenceBuilder builder = new SentenceBuilder();
        PersistentConsumer<Sentence> sentenceConsumer = new PersistentConsumer<>();

        // when
        builder.buildSentences(ofWords("a", sentenceEnd, "c"), new WordSanitizer(), sentenceConsumer);

        // then
        Assertions.assertEquals(new Sentence(List.of("a", "b")), sentenceConsumer.getLastConsumed());
    }

    @Test
    void shouldProduceTwoSentencesIfWordsForBothAreSupplied() {
        // given
        SentenceBuilder builder = new SentenceBuilder();
        PersistentConsumer<Sentence> sentenceConsumer = new PersistentConsumer<>();

        // when
        builder.buildSentences(ofWords("a", "b.", "c", "d.", "ignored"), new WordSanitizer(), sentenceConsumer);
        List<Sentence> results = sentenceConsumer.getConsumed();

        // then
        Assertions.assertTrue(results.contains(new Sentence(List.of("a", "b"))));
        Assertions.assertTrue(results.contains(new Sentence(List.of("c", "d"))));
    }

    @Test
    void shouldNotFinishSentenceOnAbbreviation() {
        // given
        SentenceBuilder builder = new SentenceBuilder();
        PersistentConsumer<Sentence> sentenceConsumer = new PersistentConsumer<>();

        // when
        builder.buildSentences(ofWords("a", "Mr.", "b."), new WordSanitizer(), sentenceConsumer);
        List<Sentence> results = sentenceConsumer.getConsumed();

        // then
        Assertions.assertEquals(new Sentence(List.of("a", "Mr.", "b")), sentenceConsumer.getLastConsumed());
    }

    private static Iterator<String> ofWords(String... words) {
        return Arrays.stream(words).iterator();
    }

}