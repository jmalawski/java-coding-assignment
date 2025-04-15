package wordssorter.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public final class SentenceBuilder {

    public void buildSentences(Iterator<String> wordsIterator, WordSanitizer wordSanitizer,
                               Consumer<Sentence> sentenceConsumer) {
        List<String> wordsBuffer = new ArrayList<>();

        while (wordsIterator.hasNext()) {
            String word = wordsIterator.next();
            if (isEndingCharacter(word)) {
                buildSentence(wordsBuffer, sentenceConsumer);
                continue;
            }
            String sanitizedWord = wordSanitizer.sanitize(word);
            if (sanitizedWord.isEmpty()) {
                continue;
            }
            wordsBuffer.add(sanitizedWord);
            if (isLastWord(word)) {
                buildSentence(wordsBuffer, sentenceConsumer);
            }
        }
        if (!wordsBuffer.isEmpty()) {
            log.error("Sentence was started, but not finished {}", wordsBuffer);
        }
        wordsBuffer.clear();
    }

    private boolean isEndingCharacter(String word) {
        return ".".equals(word) || "!".equals(word) || "?".equals(word);
    }

    private void buildSentence(List<String> wordsBuffer, Consumer<Sentence> sentenceConsumer) {
        Sentence sentence = new Sentence(wordsBuffer);
        log.debug("Built {}", sentence);
        sentenceConsumer.accept(sentence);
        wordsBuffer.clear();
    }

    private boolean isLastWord(String word) {
        if (Abbreviations.isAbbreviation(word)) {
            return false;
        }
        return hasEndingCharacter(word);
    }

    private boolean hasEndingCharacter(String word) {
        return word.endsWith(".") || word.endsWith("!") || word.endsWith("?");
    }
}
