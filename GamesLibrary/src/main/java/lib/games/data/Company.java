package lib.games.data;

import java.io.Serializable;
import java.util.UUID;

/**
 *  Объект описывающий организацию (разработчик, издатель, локализатор).
 */
public class Company implements Serializable {
    /**
     * @param id - уникальный идентификатор
     * @param name - название
     * @param country - страна
     * @param foundationYear - год основания
     */
    private final String id;
    private String name;
    private String country;
    private String foundationYear;

    public Company(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Company(String name, String foundationYear) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.foundationYear = foundationYear;
    }

    public Company(String id, String name, String country, String foundationYear) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.foundationYear = foundationYear;
    }

    public String getId () {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(String foundationYear) {
        this.foundationYear = foundationYear;
    }
}
