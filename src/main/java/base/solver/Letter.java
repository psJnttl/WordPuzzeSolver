package base.solver;

public class Letter implements Symbol {

    private String rawPresentation;
    private int gameAreaIndex;
    private int points;
    
    public Letter(String letter, int gameAreaIndex, int points) {
        rawPresentation = letter;
        this.gameAreaIndex = gameAreaIndex;
        this.points = points;
    }

    @Override
    public int verifyNextSymbol(String nextWord, int wordIndex) {
        String testWord = nextWord.substring(wordIndex);
        int foundAt = testWord.indexOf(rawPresentation);
        if (0 == foundAt) {
            return 1;
        }
        return 0;
    }

    @Override
    public int points() {
        return points;
    }
    
    @Override
    public String toString() {
        return rawPresentation;
    }
    
    public int getGameAreaIndex() {
        return gameAreaIndex;
    }

}
