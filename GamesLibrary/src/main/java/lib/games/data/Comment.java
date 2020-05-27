package lib.games.data;




import java.io.Serializable;
import java.util.UUID;

/**
 * Объект, описывающий коментарий.
 */
public class Comment implements Serializable {
    /**
     * @param id - идентификатор
     * @param commentAuthor - пользователь, оставивший комментарий
     * @param game - игра, к которй оставили комментарий
     * @param text - текст комментария
     */
    private final String id;
    private String userId;
    private final String gameId;
    private String text;
    //todo где-то тут должно быть время :(

    public Comment(String commentAuthor, String game, String text) {
        this.id= UUID.randomUUID().toString();
        this.userId = commentAuthor;
        this.gameId = game;
        this.text = text;
    }

    public Comment(String id, String commentAuthor, String game, String text) {
        this.id = id;
        this.userId = commentAuthor;
        this.gameId = game;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
