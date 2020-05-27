package lib.games.data;

import java.io.Serializable;
import java.util.UUID;

public class GameLocalisation implements Serializable {
    private final String id;
    private final String gameId;
    private final String localisationId;

    public GameLocalisation(String id, String gameId, String localisationId) {
        this.id = id;
        this.gameId = gameId;
        this.localisationId = localisationId;
    }

    public GameLocalisation(String gameId, String localisationId) {
        this.id = UUID.randomUUID().toString();
        this.gameId = gameId;
        this.localisationId = localisationId;
    }

    public String getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public String getLocalisationId() {
        return localisationId;
    }
}
