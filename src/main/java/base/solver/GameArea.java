package base.solver;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.service.SymbolService;
import base.service.WordService;
import base.solver.symbol.EitherOr;
import base.solver.symbol.Symbol;
import base.solver.symbol.SymbolFactory;
import base.solver.symbol.SymbolScore;

@Component
public class GameArea {

    @Autowired
    private WordService wordService;

    @Autowired
    private SymbolService symbolService;
    
    private static final int X_SIZE = 4;
    private static final int Y_SIZE = 4;
    private static final int MAX_WORD_LENGTH = X_SIZE * Y_SIZE;

    private Symbol[][] gameArea;
    private Symbol[][] tempGameArea;
    private SymbolScore symbolScore;
    private Vocabulary vocabulary;
    private FoundWords foundWords;
    private Word word;
    private List<Symbol[][]> gameAreaStack;

    public GameArea() throws InvalidKeyException {
        this.symbolScore = new SymbolScore();
        this.vocabulary = new Vocabulary();
    }

    public void setGameArea(List<String> gameArea) {
        if (null == gameArea || gameArea.isEmpty()) {
            throw new NullPointerException("Must have game area as String.");
        }
        else if (gameArea.size() < 16 || gameArea.size() > 16) {
            throw new InvalidParameterException("Game area String must be 16 characters.");
        }
        insertSymbols(gameArea);
        vocabulary.setVocabulary(wordService.listAllAsString());
        symbolScore.setSymbolScores(symbolService.listAll());
    }

    private void insertSymbols(List<String> gameArea) {
        List<String> gameData = gameArea;
        this.gameArea = new Symbol[Y_SIZE][X_SIZE];
        for (int i = 0; i < Y_SIZE; i++) {
            Symbol[] line = new Symbol[4];
            for (int j = 0; j < X_SIZE; j++) {
                String letters = gameData.get(i * 4 + j);
                int points = symbolScore.getScore(letters);
                Symbol symbol = SymbolFactory.create(letters, (i * 4 + j), points);
                line[j] = symbol;
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
        foundWords = new FoundWords();
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                Symbol symbol = gameArea[i][j];
                vocabulary.useSubVocabulary(symbol);
                processVocabulary(symbol, i, j);
                if (symbol.getClass().equals(EitherOr.class)) {
                    EitherOr eo = (EitherOr)symbol;
                    eo.toggle();
                    vocabulary.useSubVocabulary(eo);
                    processVocabulary(eo, i, j);
                }
            }
        }
    }

    private void processVocabulary(Symbol symbol, int gY, int gX) {
        String nextWord = "";
        while (true) {
            Optional<String> nextWordConditional = vocabulary.getNextWord();
            if (nextWordConditional.isPresent()) {
                nextWord = nextWordConditional.get();
                this.tempGameArea = backUpGameArea(this.gameArea); // mutating
                word = new Word(); // track word path
                gameAreaStack = new ArrayList<>();
                searchPath(nextWord, 0, gY, gX);
            }
            else {
                break;
            }
        }
    }

    private Symbol[][] backUpGameArea(Symbol[][] gameTiles) {
        Symbol[][] tempGameArea = new Symbol[Y_SIZE][X_SIZE];
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                tempGameArea[i][j] = gameTiles[i][j].copyOf();
            }
        }
        return tempGameArea;
    }

    private void searchPath(String nextWord, int wordIndex, int gY, int gX) {
        if (gY >= Y_SIZE || gY < 0 || gX >= X_SIZE || gX < 0 || wordIndex >= MAX_WORD_LENGTH) {
            return;
        }
        Symbol tileSymbol = tempGameArea[gY][gX];
        int status = tileSymbol.verifyNextSymbol(nextWord, wordIndex);
        if (status > 0) {
            word.insertSymbol(tileSymbol);
            if (nextWord.length() == (wordIndex + status)) {
                word.setComplete();
                foundWords.insertWord(word);
                word.removeLastSymbol();
                return;
            }
            else {
                Symbol order = SymbolFactory.create(Integer.toString(wordIndex), 0, 0);
                this.gameAreaStack.add(backUpGameArea(this.tempGameArea)); // for
                                                                           // backtracking
                this.tempGameArea[gY][gX] = order;
                wordIndex += status;
            }
        }
        else if (status == 0) {
            return;
        }
        searchPath(nextWord, wordIndex, gY, gX + 1); // E
        searchPath(nextWord, wordIndex, gY + 1, gX + 1); // SE
        searchPath(nextWord, wordIndex, gY + 1, gX); // S
        searchPath(nextWord, wordIndex, gY + 1, gX - 1); // SW
        searchPath(nextWord, wordIndex, gY, gX - 1); // W
        searchPath(nextWord, wordIndex, gY - 1, gX - 1); // NW
        searchPath(nextWord, wordIndex, gY - 1, gX); // N
        searchPath(nextWord, wordIndex, gY - 1, gX + 1); // NE
        // nowhere to go, word not found
        this.tempGameArea = gameAreaStack.remove(gameAreaStack.size() - 1);
        this.word.removeLastSymbol();
    }
    
    public void printResults() {
        foundWords.printWordList();
    }
    
    public List<Word> getResults() {
        return foundWords.getResults();
    }
}
