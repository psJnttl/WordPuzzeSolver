package solver;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import solver.Symbol;
import solver.SymbolFactory;

public class GameArea {

    private static final int X_SIZE = 4;
    private static final int Y_SIZE = 4;

    private Symbol[][] gameArea;
    private SymbolScore symbolScore;
    
    public GameArea() throws InvalidKeyException {
        this.symbolScore = new SymbolScore();
    }

    public void setGameArea(List<String> gameArea) {
        if (null == gameArea || gameArea.isEmpty() ) {
            throw new NullPointerException("Must have game area as String.");
        }
        else if (gameArea.size() < 16 || gameArea.size() > 16) {
            throw new InvalidParameterException("Game area String must be 16 characters.");
        }
        insertSymbols(gameArea);
    }

    private void insertSymbols(List<String> gameArea) {
        List<String> gameData = gameArea;
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
