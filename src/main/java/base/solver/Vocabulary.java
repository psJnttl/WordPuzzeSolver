package base.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import base.solver.symbol.Symbol;

public class Vocabulary {

    private Map<String, List<String>> wordHash;
    private int wordCount = 0;
    private List<String> activeVocabulary;
    private int lastWordIndex = 0;
    private int wordIndex = 0;
    
    public void setVocabulary(List<String> words) {
        wordHash = new HashMap<>();
        for (String word: words) {
            String key = word.substring(0, 1);
            List<String> list = wordHash.getOrDefault(key, new ArrayList<String>());
            list.add(word);
            wordHash.put(key, list);
            wordCount++;
        }
    }

    public int getWordCount() {
        return wordCount;
    }
    
    public void useSubVocabulary(Symbol symbol) {
        String firstLetter = symbol.toString().substring(0, 1);
        if (wordHash.containsKey(firstLetter)) {
            activeVocabulary = wordHash.get(firstLetter);
            lastWordIndex = activeVocabulary.size();
            wordIndex = 0;
        }
    }
    
    public Optional<String> getNextWord() {
        if (this.wordIndex < this.lastWordIndex) {
            String word = activeVocabulary.get(wordIndex++);
            return Optional.of(word);
        }
        return Optional.empty();
    }
}
