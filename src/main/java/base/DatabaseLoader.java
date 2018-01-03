package base;

import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import base.domain.Symbol;
import base.domain.TileColor;
import base.domain.Word;
import base.repository.SymbolRepository;
import base.repository.TileColorRepository;
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
    
    @Autowired
    private TileColorRepository tileColorRepository;

    @Override
    public void run(String... strings) throws InvalidKeyException {
        final String [] words = {"abandon","babies","cabbage","daily","eager","fabric","greatest","habanero","inbox",
                "jacket","karat","label","machete","nab","oaring","polled","quadrant","responsibilities","sacred","table",
                "ubiquitous","vacation","wages","xray","yahoo","zealot"};
        final Object[] symbolScores = { "a", 2, "at", 6, "b", 5, "c", 3, "d", 3, "e", 1, "ed", 7, "-est", 12,
                "f", 5, "g", 4, "h", 4, "i", 2, "i/m", 20, "in-", 12, "j", 10, "k", 6, "l", 3, "ll", 8, "m", 4, "n", 2, "o",
                2, "p", 4, "q", 10, "r", 2, "s", 2, "t", 2, "u", 4, "u/f", 20, "v", 6, "w", 6, "x", 9, "y", 5, "z", 8 };
        final int [][] colors = {
                {227, 242, 253},
                {217, 232, 252},
                {187, 222, 251},
                {165, 212, 250},
                {144, 202, 249},
                {122, 191, 248},
                {100, 181, 246},
                { 88, 173, 245},
                { 66, 165, 245},
                { 49, 158, 244},
                { 33, 150, 243},
                { 32, 143, 236},
                { 30, 136, 229},
                { 27, 127, 219},
                { 25, 118, 210},
                { 22, 109, 200}
        };
        final double [] alpha = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        insertWords(Arrays.asList(words));
        insertSymbolScores(symbolScores);
        insertTileColors(colors, alpha);
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
    
    private void insertTileColors(int[][] colors, double[] alpha) {
        for (int i = 0; i < colors.length; i++) {
            TileColor color = new TileColor(colors[i][0],colors[i][1],colors[i][2],alpha[i]);
            tileColorRepository.saveAndFlush(color);
        }
        
    }

}
