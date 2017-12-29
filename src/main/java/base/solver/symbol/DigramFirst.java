package base.solver.symbol;

public class DigramFirst implements Symbol {

    private String rawPresentation;
    private String refinedPresentation;
    private int gameAreaIndex;
    private int points;

    public DigramFirst(String letters, int gameAreaIndex, int points) {
        this.rawPresentation = letters;
        this.refinedPresentation = letters.substring(0, (letters.length() -1 ));
        this.gameAreaIndex = gameAreaIndex;
        this.points = points;
    }

    @Override
    public int verifyNextSymbol(String nextWord, int wordIndex) {
        if (0 != wordIndex) {
            return 0;
        }
        int foundAt = nextWord.indexOf(refinedPresentation);
        if ( 0 == foundAt) {
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
        return new DigramFirst(this.rawPresentation, this.gameAreaIndex, this.points);
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
