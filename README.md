# java-coding-assignment

## Build
    ./mvnw clean package

## Usage
    java -jar ./target/words-sorter-1.0-SNAPSHOT.jar csv
    or
    java -jar ./target/words-sorter-1.0-SNAPSHOT.jar xml


### Test using large input
    cat ./path/to/large.in | java -Xmx32m -jar ./target/words-sorter-1.0-SNAPSHOT.jar xml | less

### Disclaimer
Only parsing cases found in small.in or the docx were implemented.