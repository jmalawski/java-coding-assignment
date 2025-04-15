package wordssorter;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class PersistentConsumer<T> implements Consumer<T> {

    private final List<T> consumed = new ArrayList<>();

    @Override
    public void accept(T t) {
        consumed.add(t);
    }

    public T getLastConsumed() {
        return consumed.getLast();
    }
}
