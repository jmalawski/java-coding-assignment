package wordssorter.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@EqualsAndHashCode
@ToString
public class Sentence {
    private final List<String> words;

    public Sentence(List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public List<String> getSortedWords(Comparator<String> comparator) {
        return words.stream()
                .sorted(comparator)
                .toList();
    }
}
