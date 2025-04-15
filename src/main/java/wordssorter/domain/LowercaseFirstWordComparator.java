package wordssorter.domain;

import java.util.Comparator;

public class LowercaseFirstWordComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        int result = o1.compareToIgnoreCase(o2);
        if (result != 0) {
            return result;
        }
        return o2.compareTo(o1);
    }
}
