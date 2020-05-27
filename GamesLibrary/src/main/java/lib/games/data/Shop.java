package lib.games.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Объект, описывающий магазин.
 */
public class Shop  implements Serializable {
    /**
     * @param id - уникальный идентификатор
     * @param name - название.
     * @param url - ссылка на страницу магазина.
     */
    private final String id;
    private String name;
    private String url;

    public Shop(String name, String url) {
        this.id  = UUID.randomUUID().toString();
        this.name = name;
        this.url = url;
    }

    public Shop(String id, String name, String url) {
        this.id  = id;
        this.name = name;
        this.url = url;
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
