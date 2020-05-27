package lib.games.data;

public enum AccessLevel {
    USER(1),
    MODERATOR(2),
    ADMINISTRATOR(3);
    private int id;
    AccessLevel (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AccessLevel getById (int id) {
        for (AccessLevel i: values()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
}