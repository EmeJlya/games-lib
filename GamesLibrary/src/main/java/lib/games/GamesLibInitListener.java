package lib.games;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import lib.games.authentication.AccessControl;
import lib.games.authentication.AccessControlFactory;
import lib.games.ui.login.LoginView;

public class GamesLibInitListener implements VaadinServiceInitListener {

    public void serviceInit(ServiceInitEvent initEvent) {
        final AccessControl accessControl = AccessControlFactory.getInstance()
                .createAccessControl();

        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if (!accessControl.isUserSignedIn() && !LoginView.class
                        .equals(enterEvent.getNavigationTarget()))
                    enterEvent.rerouteTo(LoginView.class);
            });
        });
    }
}
