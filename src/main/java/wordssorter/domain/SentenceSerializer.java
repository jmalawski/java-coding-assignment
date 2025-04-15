package wordssorter.domain;

public interface SentenceSerializer {

    void startDocument();

    void write(Sentence sentence);

    void endDocument();
}
