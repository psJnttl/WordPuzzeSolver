package base.solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Word consist of symbols on the game tiles.
 * 
 * @author Pasi
 *
 */
public class Word {

    private List<Symbol> symbolList = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    private int points = 0;
    private String plainWord= "";
    private boolean complete = false;
    private String initWord;
    
    public Word(String word) {
        this.initWord = word;
    }
}
