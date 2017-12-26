package base.command;

import java.util.List;

public class GameReq {

    private List<String> gameTiles;
    
    public GameReq() {
    }
    
    public GameReq(List<String> gameTiles) {
        this.gameTiles = gameTiles;
    }

    public List<String> getGameTiles() {
        return gameTiles;
    }

    public void setGameTiles(List<String> gameTiles) {
        this.gameTiles = gameTiles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gameTiles == null) ? 0 : gameTiles.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GameReq other = (GameReq) obj;
        if (gameTiles == null) {
            if (other.gameTiles != null) return false;
        }
        else if (!gameTiles.equals(other.gameTiles)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "GameReq [gameTiles=" + gameTiles + "]";
    }
    
}
