package wordssorter.domain;

import java.util.regex.Pattern;

public class WordSanitizer {

    private static final Pattern UNICODE_QUOTE_PATTERN = Pattern.compile("’");

    public String sanitize(String word) {
        if (Abbreviations.isAbbreviation(word)) {
            return word;
        }
        word = replaceUnicodeQuoteWithAscii(word);
        return trimNonAlphanumeric(word);
    }

    private static String replaceUnicodeQuoteWithAscii(String word) {
        return UNICODE_QUOTE_PATTERN.matcher(word)
                .replaceAll("'");
    }

    private String trimNonAlphanumeric(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        int start = 0;
        int end = input.length() - 1;

        // Move start forward to first alphanumeric character
        while (start <= end && !Character.isLetterOrDigit(input.charAt(start))) {
            start++;
        }

        // Move end backward to last alphanumeric character
        while (end >= start && !Character.isLetterOrDigit(input.charAt(end))) {
            end--;
        }

        // Substring the trimmed result
        return input.substring(start, end + 1);
    }
}
