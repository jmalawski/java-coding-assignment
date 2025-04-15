package wordssorter.domain;

import java.util.Set;

class Abbreviations {
    private static final Set<String> ABBREVIATIONS = Set.of(
            "Mr.", "Mrs.", "Ms.", "Dr.", "Prof."
    );

    public static boolean isAbbreviation(String word) {
        return ABBREVIATIONS.contains(word);
    }
}