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
public class Word implements Comparable<Word> {

    private List<Symbol> symbolList = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    private int points = 0;
    private String plainWord= "";

    public Word() {
    }
    
    public void insertSymbol(Symbol symbol) {
        Symbol newSymbol = symbol.copyOf();
        symbolList.add(newSymbol);
        points += symbol.points(); 
        path.add(symbol.getCoordinates()); 
    }

    public void setComplete() {
        this.plainWord = "";
        symbolList.stream().forEach(s -> this.plainWord += s.toString());
        points = calculatePoints();
    }
    
    public void removeLastSymbol() {
        Symbol removed = this.symbolList.remove(this.symbolList.size() - 1);
        points -= removed.points();
        path.remove(this.path.size() - 1);
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
        int points = symbolList.stream()
                               .mapToInt(s -> s.points())
                               .sum();
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

    @Override
    public int compareTo(Word other) {
        if (points < other.points) {
            return -1;
        }
        else if (points > other.points) {
            return 1;
        }
        return 0;
    }

    public List<Integer> getPath() {
        return path;
    }
    
    public Word(List<Symbol> symbolList, List<Integer> path ) {
        this.symbolList = symbolList;
        this.path = path;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPlainWord(String plainWord) {
        this.plainWord = plainWord;
    }

    public Word copyOf() {
        List<Integer> path = new ArrayList<>(this.path);
        List<Symbol> symbolList = new ArrayList<>(this.symbolList);
        Word copy = new Word(symbolList, path);
        copy.setPlainWord(plainWord);
        copy.setPoints(points);
        return copy;
    }

}    
