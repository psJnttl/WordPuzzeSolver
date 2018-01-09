package base.service;

import java.security.InvalidKeyException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.command.GameReq;
import base.dto.SolvedGameDto;
import base.solver.GameArea;
import base.solver.Word;

@Service
public class GameService {

    private GameArea gameArea;
    
    private static final int GAME_AREA_SIZE = 16;
    private static final String REG_EX = "([a-zA-Z\\-]){1,4}|([a-zA-Z]\\/[a-zA-Z])";

    @Autowired
    public GameService(GameArea gameArea) throws InvalidKeyException {
        this.gameArea = gameArea;
    }

    public boolean isGameAreaValid(GameReq gameArea) {
        if (null == gameArea || null == gameArea.getGameTiles() ||
            gameArea.getGameTiles().isEmpty() || 
            GAME_AREA_SIZE != gameArea.getGameTiles().size()) {
        return false;
        }
        long count = gameArea.getGameTiles().stream()
                             .filter(w -> w.matches(REG_EX))
                             .count();
        if (GAME_AREA_SIZE != count) {
            return false;
        }
        return true;
    }

    public SolvedGameDto solve(GameReq game) {
        gameArea.setGameArea(game.getGameTiles());
        gameArea.solve();
        List<Word> words = gameArea.getResults();
        return mapWordsToDto(words);
    }
    
    private SolvedGameDto mapWordsToDto(List<Word> words) {
        List<SolvedWord> solvedWords = words.stream()
                                            .map(w -> new SolvedWord(w.toString(), w.getPoints(), w.getPath()))
                                            .collect(Collectors.toList());
        return new SolvedGameDto(solvedWords);
    }
}
