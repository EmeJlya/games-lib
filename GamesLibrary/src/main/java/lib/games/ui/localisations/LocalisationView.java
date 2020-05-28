package lib.games.ui.localisations;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import lib.games.authentication.AccessControl;
import lib.games.authentication.AccessControlFactory;
import lib.games.data.AccessLevel;
import lib.games.ui.Layout;

@Route(value = "localisations", layout = Layout.class)
public class LocalisationView extends VerticalLayout implements BeforeEnterObserver {

    public LocalisationView() {

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        AccessControl accessControl = AccessControlFactory.getInstance().createAccessControl();
        if (!accessControl.isUserSignedIn()) {
            UI.getCurrent().navigate("login");
            UI.getCurrent().getPage().reload();
        } else if (!accessControl.isUserInRole(AccessLevel.MODERATOR)) {
            UI.getCurrent().navigate("games");
            UI.getCurrent().getPage().reload();
        }
    }
}
