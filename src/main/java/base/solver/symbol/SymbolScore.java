package base.solver.symbol;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.dto.SymbolDto;

/**
 * Contains game tile / symbol scoring. Single letters yield less that
 * multiletter digrams. Either or symbols have the highest scores. This class
 * does not contain score for every symbol/tile in the game also the points
 * apply in normal gaming, special rounds might yield different scores.
 * 
 * @author Pasi
 *
 */

public class SymbolScore {
    private static final Object[] keysNvalues = { "a", 2, "at", 6, "b", 5, "c", 3, "d", 3, "e", 1, "ed", 7, "-est", 12,
            "f", 5, "g", 4, "h", 4, "i", 2, "i/m", 20, "in-", 12, "j", 10, "k", 6, "l", 3, "ll", 8, "m", 4, "n", 2, "o",
            2, "p", 4, "q", 10, "r", 2, "s", 2, "t", 2, "u", 4, "u/f", 20, "v", 6, "w", 6, "x", 9, "y", 5, "z", 8 };
    Map<String, Integer> symbolsScores;

    public SymbolScore() throws InvalidKeyException {
        initSymbolScores();
    }

    /**
     * Evaluates the amount of points a symbol is worth. If the symbol is
     * unknown score returned is 1.
     * 
     * @param key
     *            Precise string presentation of the symbol
     * @return Symbol value in terms of game points.
     */
    public int getScore(String key) {
        return symbolsScores.getOrDefault(key, 1);
    }

    private void initSymbolScores() throws InvalidKeyException {
        symbolsScores = new HashMap<>();
        String key = null;
        for (int i = 0; i < keysNvalues.length; i++) {
            if (i % 2 == 0) {
                key = (String) keysNvalues[i];
            }
            else {
                Integer value = (Integer) keysNvalues[i];
                if (null == key || key.isEmpty()) {
                    throw new InvalidKeyException("Key missing, can't insert value to symbolScores");
                }
                symbolsScores.put(key, value);
            }
        }
    }
    
    public void setSymbolScores(List<SymbolDto> symbolDtos) {
        symbolsScores = new HashMap<>();
        symbolDtos.stream()
                  .forEach(s -> symbolsScores.put(s.getValue(), s.getScore()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SymbolScores:\n");
        this.symbolsScores.keySet().stream().forEach(k -> {
            sb.append(k);
            sb.append(": ");
            sb.append(symbolsScores.get(k));
            sb.append("\n");
        });
        return sb.toString();
    }
}
