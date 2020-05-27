package lib.games.data;

import java.io.Serializable;
import java.util.UUID;

public class GameShop implements Serializable {
    private final String id;
    private final String gameId;
    private final String shopId;

    public GameShop(String gameId, String shopId) {
        this.id = UUID.randomUUID().toString();
        this.gameId = gameId;
        this.shopId = shopId;
    }

    public GameShop(String id, String gameId, String shopId) {
        this.id = id;
        this.gameId = gameId;
        this.shopId = shopId;
    }

    public String getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public String getShopId() {
        return shopId;
    }
}
