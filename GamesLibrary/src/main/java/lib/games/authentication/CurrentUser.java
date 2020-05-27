package lib.games.authentication;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import lib.games.data.AccessLevel;
import lib.games.data.User;

public class CurrentUser {

    public static AccessLevel level;
    public static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = CurrentUser.class
            .getCanonicalName();

    public CurrentUser() {
    }



    public static String get() {
        String currentUser = (String) getCurrentRequest().getWrappedSession()
                .getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        if (currentUser == null) {
            return "";
        } else {
            return currentUser;
        }
    }

    public static void set(User currentUser) {
        if (currentUser == null) {
            getCurrentRequest().getWrappedSession().removeAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        } else {
            level = currentUser.getAccessLevel();
            getCurrentRequest().getWrappedSession().setAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY, currentUser.getUsername());
        }
    }

    private static VaadinRequest getCurrentRequest() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException(
                    "No request bound to current thread.");
        }
        return request;
    }
}
