package lib.games.authentication;

import lib.games.data.AccessLevel;

import java.io.Serializable;

public interface AccessControl extends Serializable {

    String ADMIN_ROLE_NAME = "admin";
    String ADMIN_USERNAME = "admin";

    boolean signIn(String username, String password);

    boolean isUserSignedIn();

    boolean isUserInRole(AccessLevel level);

    String getPrincipalName();

    void signOut();
}
