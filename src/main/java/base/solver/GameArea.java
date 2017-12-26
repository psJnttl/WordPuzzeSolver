package base.solver;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.service.WordService;
import base.solver.symbol.Symbol;
import base.solver.symbol.SymbolFactory;
import base.solver.symbol.SymbolScore;

@Component
public class GameArea {

    @Autowired
    private WordService wordService;
    
    private static final int X_SIZE = 4;
    private static final int Y_SIZE = 4;

    private Symbol[][] gameArea;
    private Symbol[][] tempGameArea;
    private SymbolScore symbolScore;
    private Vocabulary vocabulary;
    private List<FoundWords> wordList;
    private Word word;
    
    public GameArea() throws InvalidKeyException {
        this.symbolScore = new SymbolScore();
        this.vocabulary = new Vocabulary();
    }

    public void setGameArea(List<String> gameArea) {
        if (null == gameArea || gameArea.isEmpty() ) {
            throw new NullPointerException("Must have game area as String.");
        }
        else if (gameArea.size() < 16 || gameArea.size() > 16) {
            throw new InvalidParameterException("Game area String must be 16 characters.");
        }
        insertSymbols(gameArea);
        vocabulary.setVocabulary(wordService.listAllAsString());
    }

    private void insertSymbols(List<String> gameArea) {
        List<String> gameData = gameArea;
        this.gameArea = new Symbol[Y_SIZE][X_SIZE];
        for (int i = 0; i < Y_SIZE; i++) {
            Symbol [] line = new Symbol[4];
            for (int j = 0; j < X_SIZE; j++) {
                Optional<Symbol> symbol = SymbolFactory.create(gameData.get(i*4 + j), (i*4 + j), 1);
                if (symbol.isPresent()) {
                    line[j] = symbol.get();
                }
                else {
                    throw new InvalidParameterException("Can't determine symbol!");
                }
            }
            this.gameArea[i] = line;
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
    
    public void solve() {
        wordList = new ArrayList<>();
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                Symbol symbol = gameArea[i][j];
                vocabulary.useSubVocabulary(symbol); 
                processVocabulary(symbol, i, j);
            }
        }
    }
    
    private void processVocabulary(Symbol symbol, int gY, int gX) {
        String nextWord = "";
        while (true) {
            Optional<String> nextWordConditional = vocabulary.getNextWord(); 
            if (nextWordConditional.isPresent()) {
                nextWord = nextWordConditional.get();
                backUpGameArea();  // mutating will take place
                word = new Word(nextWord);  // track word path
            }
            else {
                break;
            }
        }
    }
    
    private void backUpGameArea() {
        this.tempGameArea = new Symbol[Y_SIZE][X_SIZE];
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                tempGameArea[i][j] = gameArea[i][j].copyOf();
            }
        }
    }
}
