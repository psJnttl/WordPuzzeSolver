package base.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vocabulary {

    private Map<String, List<String>> wordHash = new HashMap<>();
    private int wordCount = 0;
    
    public void setVocabulary(List<String> words) {
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
}
