package solver;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import solver.Symbol;
import solver.SymbolFactory;

public class GameArea {

    private static final int X_SIZE = 4;
    private static final int Y_SIZE = 4;

    private Symbol[][] gameArea;

    public GameArea(String gameAreaString) {
        if (null == gameAreaString || gameAreaString.isEmpty()) {
            throw new NullPointerException("Must have game area as String.");
        }
        else if (gameAreaString.length() < 16 || gameAreaString.length() > 16) {
            throw new InvalidParameterException("Game area String must be 16 characters.");
        }
        insertSymbols(gameAreaString);
    }

    private void insertSymbols(String letters) {
        String[] gameDataTiles = letters.split("");
        List<String> gameData = Arrays.asList(gameDataTiles);
        this.gameArea = new Symbol[Y_SIZE][X_SIZE];
        for (int i = 0; i < Y_SIZE; i++) {
            Symbol [] line = new Symbol[4];
            for (int j = 0; j < X_SIZE; j++) {
                Optional<Symbol> symbol = SymbolFactory.create(gameData.get(i + j), (i + j), 1);
                if (symbol.isPresent()) {
                    line[j] = symbol.get();
                }
                else {
                    throw new InvalidParameterException("Can't determine symbol!");
                }
                this.gameArea[i] = line;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                sb.append(this.gameArea[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
