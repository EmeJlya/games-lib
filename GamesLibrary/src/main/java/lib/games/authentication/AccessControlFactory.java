package lib.games.authentication;

import lib.games.data.User;

import java.util.HashSet;
import java.util.Set;

public class AccessControlFactory {
    private static final AccessControlFactory INSTANCE = new AccessControlFactory();
    private final BasicAccessControl accessControl = new BasicAccessControl();

    private AccessControlFactory() {
    }

    public static AccessControlFactory getInstance() {
        return INSTANCE;
    }

    public BasicAccessControl createAccessControl() {
        return accessControl;
    }
}
