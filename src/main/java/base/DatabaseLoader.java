package base;

import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import base.domain.Symbol;
import base.domain.Word;
import base.repository.SymbolRepository;
import base.repository.WordRepository;

/**
 *
 * @author Pasi
 */
@Profile("test")
@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private WordRepository wordRepository;
    
    @Autowired
    private SymbolRepository symbolRepository;

    @Override
    public void run(String... strings) throws InvalidKeyException {
        final String [] words = {"abandon","babies","cabbage","daily","eager","fabric","gadgets","habanero","iceberg",
                "jacket","karat","label","machete","nab","oaring","polled","quadrant","rabbi","sacred","table",
                "ubiquitous","vacation","wages","xray","yahoo","zealot"};
        final Object[] symbolScores = { "a", 2, "at", 6, "b", 5, "c", 3, "d", 3, "e", 1, "ed", 7, "-est", 12,
                "f", 5, "g", 4, "h", 4, "i", 2, "i/m", 20, "in-", 12, "j", 10, "k", 6, "l", 3, "ll", 8, "m", 4, "n", 2, "o",
                2, "p", 4, "q", 10, "r", 2, "s", 2, "t", 2, "u", 4, "u/f", 20, "v", 6, "w", 6, "x", 9, "y", 5, "z", 8 };

        insertWords(Arrays.asList(words));
        insertSymbolScores(symbolScores);
    }

    private void insertWords(List<String> items) {
        for (String item: items) {
            Word word = new Word(item);
            wordRepository.saveAndFlush(word);
        }
    }
    
    private void insertSymbolScores(Object [] symbolScores) throws InvalidKeyException {
        String value = null;
        for (int i = 0; i < symbolScores.length; i++) {
            if (i % 2 == 0) {
                value = (String) symbolScores[i];
            }
            else {
                Integer score = (Integer) symbolScores[i];
                if (null == value || value.isEmpty()) {
                    throw new InvalidKeyException("Key missing, can't insert new symbolScore");
                }
                Symbol symbol = new Symbol(value, score);
                symbol = symbolRepository.saveAndFlush(symbol);
            }
        }
        
    }
}
