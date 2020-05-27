package lib.games.data;

import java.io.Serializable;
import java.util.UUID;

public class Localisation implements Serializable {
    private final String id;
    private String locale;

    public Localisation(String id, String locale) {
        this.id = id;
        this.locale = locale;
    }

    public Localisation(String locale) {
        this.id = UUID.randomUUID().toString();
        this.locale = locale;
    }

    public String getId() {
        return id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
