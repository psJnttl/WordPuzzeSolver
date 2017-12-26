package base.service;

import org.springframework.stereotype.Service;

import base.command.GameReq;

@Service
public class GameService {

    private static final int GAME_AREA_SIZE = 16;
    private static final String REG_EX = "[a-zA-Z\\-]{1,4}";
    
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
}
