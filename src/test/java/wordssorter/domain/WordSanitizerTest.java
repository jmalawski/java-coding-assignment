package wordssorter.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WordSanitizerTest {

    @ParameterizedTest
    @ValueSource(strings = {":a:", "(a)", ",a,", "'a'"})
    void shouldTrimNonAlphanumericCharacters(String dirty) {
        // given
        WordSanitizer sanitizer = new WordSanitizer();

        // when
        String sanitized = sanitizer.sanitize(dirty);

        // then
        Assertions.assertEquals("a", sanitized);
    }

    @Test
    void shouldLeaveNonAlphanumericCharactersInTheMiddle() {
        // given
        WordSanitizer sanitizer = new WordSanitizer();

        // expect
        Assertions.assertEquals("a'b", sanitizer.sanitize("a'b"));
        Assertions.assertEquals("a/b", sanitizer.sanitize("a/b"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Mr.", "Ms.", "Dr."})
    void shouldNotSanitizeAbbreviations(String abbreviation) {
        // given
        WordSanitizer sanitizer = new WordSanitizer();

        // when
        String sanitized = sanitizer.sanitize(abbreviation);

        // then
        Assertions.assertEquals(abbreviation, sanitized);
    }

    @Test
    void shouldReplaceUnicodeQuotes() {
        // given
        WordSanitizer sanitizer = new WordSanitizer();

        // expect
        Assertions.assertEquals("you'd", sanitizer.sanitize("you’d"));
    }
}