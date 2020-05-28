package lib.games.ui.users;

import com.vaadin.flow.component.UI;
import lib.games.authentication.AccessControlFactory;
import lib.games.data.AccessLevel;
import lib.games.data.Game;
import lib.games.ui.games.GamesView;

public class UsersViewLogic {

    private final UsersView view;

    public UsersViewLogic(UsersView usersView) {
        view = usersView;
    }


    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        UI.getCurrent().navigate(GamesView.class, fragmentParameter);
    }


}
