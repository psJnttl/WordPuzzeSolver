package base.solver.symbol;

public class EitherOr implements Symbol {

    private String rawPresentation;
    private String either;
    private String or;
    private boolean other = false;
    private int gameAreaIndex;
    private int points;

    public EitherOr(String letters, int gameAreaIndex, int points) {
        this.rawPresentation = letters;
        this.either = letters.substring(0, 1);
        this.or = letters.substring(2);
        this.gameAreaIndex = gameAreaIndex;
        this.points = points;
    }

    @Override
    public int verifyNextSymbol(String nextWord, int wordIndex) {
        String testWord = nextWord.substring(wordIndex);
        int foundAt = testWord.indexOf(either);
        if (0 == foundAt) {
            other = false;
            return 1;
        }
        foundAt = testWord.indexOf(or);
        if (0 == foundAt) {
            other = true;
            return 1;
        }
        return 0;
    }

    @Override
    public int points() {
        return points;
    }

    @Override
    public Symbol copyOf() {
        EitherOr eor = new EitherOr(this.rawPresentation, this.gameAreaIndex, this.points); 
        if (this.other) {
            eor.toggle();
        }
        return eor;
    }

    @Override
    public int getCoordinates() {
        return gameAreaIndex;
    }

    @Override
    public String toString() {
        return other ? or : either;
    }

    public void toggle() {
        other = !other;
    }

}
