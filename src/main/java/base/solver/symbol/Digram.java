package base.solver.symbol;

public class Digram implements Symbol {

    private String rawPresentation;
    private int gameAreaIndex;
    private int points;
    
    public Digram(String letters, int gameAreaIndex, int points) {
        this.rawPresentation = letters;
        this.gameAreaIndex = gameAreaIndex;
        this.points = points;
    }

    @Override
    public int verifyNextSymbol(String nextWord, int wordIndex) {
        String testWord = nextWord.substring(wordIndex);
        int foundAt = testWord.indexOf(rawPresentation);
        if (0 == foundAt) {
            return rawPresentation.length();
        }
        return 0;
    }

    @Override
    public int points() {
        return points;
    }

    @Override
    public Symbol copyOf() {
        return new Digram(this.rawPresentation, this.gameAreaIndex, this.points);
    }

    @Override
    public int getCoordinates() {
        return gameAreaIndex;
    }

    @Override
    public String toString() {
        return rawPresentation;
    }
    
    

}
