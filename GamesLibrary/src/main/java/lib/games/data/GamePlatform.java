package lib.games.data;

import java.io.Serializable;
import java.util.UUID;

public class GamePlatform implements Serializable {
    private final String id;
    private final String gameId;
    private final String platformId;

    public GamePlatform(String id, String gameId, String platformId) {
        this.id = id;
        this.gameId = gameId;
        this.platformId = platformId;
    }

    public GamePlatform(String gameId, String platformId) {
        this.id = UUID.randomUUID().toString();
        this.gameId = gameId;
        this.platformId = platformId;
    }

    public String getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlatformId() {
        return platformId;
    }

}
