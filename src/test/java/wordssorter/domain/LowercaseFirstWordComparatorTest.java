package wordssorter.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LowercaseFirstWordComparatorTest {

    @Test
    void shouldSortIgnoringCaseButWhenEqualLowercaseFirst() {
        // given
        List<String> words = List.of("in", "In", "in");
        LowercaseFirstWordComparator comparator = new LowercaseFirstWordComparator();

        // when
        List<String> sortedWords = words.stream()
                .sorted(comparator)
                .toList();

        // then
        Assertions.assertEquals(List.of("in", "in", "In"), sortedWords);
    }

}