package base.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Contains a list of Words found during searchPath.
 * @author Pasi
 *
 */
public class FoundWords {
    private List<Word> wordList = new ArrayList<>();

    public void insertWord(Word word) {
        Word newWord = word.copyOf();
        boolean duplicate = false;
        for (Word aWord : wordList) {
            if (aWord.toString().equals(newWord.toString())) {
                duplicate = true; 
                if (newWord.getPoints() > aWord.getPoints()) {
                    removeWord(aWord); // can happen with digram, either/or:
                    wordList.add(newWord); // same word yields more points
                    break;
                }
            }
        }
        if (!duplicate) {
            wordList.add(newWord);
        }
    }
    
    public void removeWord(Word aWord) {
        Optional<Word> tbr = wordList.stream().filter(w -> w.toString().equals(aWord.toString())).findFirst();
        if (tbr.isPresent()) {
            Word toBeRemoved = tbr.get();
            wordList.remove(toBeRemoved);
        }
    }
    
    public void printWordList() {
        Collections.sort(wordList);
        Collections.reverse(wordList);
        for (Word aWord : wordList) {
            System.out.println(aWord.getPoints() + ": " + aWord.toString() + ", " + aWord.getPath());
        }
    }

    public List<Word> getResults() {
        Collections.sort(wordList);
        Collections.reverse(wordList);
        return wordList;
    }
}
