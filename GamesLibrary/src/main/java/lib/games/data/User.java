package lib.games.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Объект, описывающий пользователя.
 */
public class User implements Serializable {
    /**
     * @param id - идентификатор
     * @param fullName - "реальное" имя пользователья
     * @param nickname - никнейм
     * @param password - пароль
     * @param email - адрес электронной почты
     */
    private final String id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private AccessLevel accessLevel;


    public User(String username, String fullName, String password, String email) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accessLevel = AccessLevel.USER;
    }

    public User(String id, String username, String fullName, String password, String email, AccessLevel level) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accessLevel = level;
    }

    public User(String username, String password) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.accessLevel = AccessLevel.USER;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
