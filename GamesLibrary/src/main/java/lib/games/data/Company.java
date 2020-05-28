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
    private int foundationYear;

    public Company(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.country = "";
        this.foundationYear = 1900;
    }

    public Company(String name, int foundationYear) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.country = "";
        this.foundationYear = foundationYear;
    }

    public Company(String name, int foundationYear, String country) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.foundationYear = foundationYear;
        this.country = country;
    }

    public Company(String id, String name, String country, int foundationYear) {
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

    public int getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(int foundationYear) {
        this.foundationYear = foundationYear;
    }
}
