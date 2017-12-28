package base.solver.symbol;

public class DigramLast implements Symbol {

    private String rawPresentation;
    private String refinedPresentation;
    private int gameAreaIndex;
    private int points;

    public DigramLast(String letters, int gameAreaIndex, int points) {
        this.rawPresentation = letters;
        this.refinedPresentation = letters.substring(1);
        this.gameAreaIndex = gameAreaIndex;
        this.points = points;
    }

    @Override
    public int verifyNextSymbol(String nextWord, int wordIndex) {
        if (0 == wordIndex) {
            return 0;
        }
        if (nextWord.indexOf(refinedPresentation) == nextWord.length() - refinedPresentation.length()) {
            return refinedPresentation.length();
        }
        return 0;
    }

    @Override
    public int points() {
        return points;
    }

    @Override
    public Symbol copyOf() {
        return new DigramLast(this.rawPresentation, this.gameAreaIndex, this.points);
    }

    @Override
    public int getCoordinates() {
        return gameAreaIndex;
    }

    @Override
    public String toString() {
        return refinedPresentation;
    }

}
