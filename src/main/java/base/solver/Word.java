package base.solver;

import java.util.ArrayList;
import java.util.List;

import base.solver.symbol.Symbol;

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
    
    public void insertSymbol(Symbol symbol) {
        Symbol newSymbol = symbol.copyOf();
        symbolList.add(newSymbol);
        points += symbol.points(); 
        path.add(symbol.getCoordinates()); 
    }

    public void setComplete() {
        this.complete = true;
        symbolList.stream().forEach(s -> this.plainWord += s.toString());
        points = calculatePoints();
    }
    
    public boolean isComplete() {
        return this.complete;
    }
    
    public void removeLastSymbol() {
        Symbol removed = this.symbolList.remove(this.symbolList.size() - 1);
        this.points -= removed.points();
        this.path.remove(this.path.size() - 1);
    }

    @Override
    public String toString() {
        return plainWord;
    }
 
    /**
     * Calculate the amount of points word is worth.
     * Length does matter.
     * @return  int amount of points
     */
    private int calculatePoints() {
        int points = this.points;
        if (plainWord.length() == 5) {
            points = points * 3 / 2; // on purpose: no decimals and rounding down
        }
        else if (plainWord.length() > 5 && plainWord.length() < 8) {
            points *= 2;
        }
        else if (plainWord.length() >= 8) {
            points = points * 5 / 2;
        }
        return points;
    }

    public int getPoints() {
        return points;
    }

}    
