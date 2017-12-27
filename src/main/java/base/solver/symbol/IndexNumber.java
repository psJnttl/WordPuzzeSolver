package base.solver.symbol;
/**
 * This class is used to mark word path in the game area.
 * @author Pasi
 *
 */
public class IndexNumber implements Symbol {

    private String rawPresentation;
    private int gameAreaIndex;
    private int points;

    public IndexNumber(String letter, int gameAreaIndex, int points) {
        this.rawPresentation = letter;
        this.gameAreaIndex = gameAreaIndex;
        this.points = points;
    }

    @Override
    public int verifyNextSymbol(String nextWord, int wordIndex) {
        return 0;
    }

    @Override
    public int points() {
        return points;
    }

    @Override
    public Symbol copyOf() {
        return new IndexNumber(this.rawPresentation, this.gameAreaIndex, this.points);
    }

    @Override
    public int getCoordinates() {
        return gameAreaIndex;
    }

}
