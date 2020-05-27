package lib.games.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Объект, описывающий платформу.
 */
public class Platform  implements Serializable {
    /**
     * @param id - уникальный идентификатор
     * @param name - название.
     */
    private final String id;
    private String name;
    private String url;

    public Platform(String name) {
        id =  UUID.randomUUID().toString();
        this.name = name;
    }

    public Platform(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
