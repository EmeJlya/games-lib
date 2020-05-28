package lib.games.ui.users;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import lib.games.authentication.AccessControlFactory;
import lib.games.authentication.CurrentUser;
import lib.games.data.AccessLevel;
import lib.games.data.Game;
import lib.games.data.User;

public class UserList extends Grid<User> {

    public UserList() {
        setSizeFull();

        addColumn(User::getUsername).setHeader("Username").setSortable(true).setKey("username");
        addColumn(User::getFullName).setHeader("Real name").setSortable(true).setKey("realname");
        addColumn(User::getEmail).setHeader("Email").setSortable(true).setKey("email");
        addColumn(User::getAccessLevel).setHeader("AccessLevel").setSortable(true).setKey("access");

        setColumnVisibility();
        setHeightByRows(true);
    }

    private void setColumnVisibility() {
        if (AccessControlFactory.getInstance().createAccessControl().isUserSignedIn()) {
            if (AccessControlFactory.getInstance().createAccessControl().getUser().getAccessLevel() == AccessLevel.MODERATOR){
                getColumnByKey("username").setVisible(true);
                getColumnByKey("realname").setVisible(true);
                getColumnByKey("email").setVisible(true);
                getColumnByKey("access").setVisible(false);
            } else if (CurrentUser.level == AccessLevel.ADMINISTRATOR) {
                getColumnByKey("username").setVisible(true);
                getColumnByKey("realname").setVisible(true);
                getColumnByKey("email").setVisible(true);
                getColumnByKey("access").setVisible(true);
            } else  {
                getColumnByKey("username").setVisible(true);
                getColumnByKey("realname").setVisible(true);
                getColumnByKey("email").setVisible(false);
                getColumnByKey("access").setVisible(false);
            }
        }

    }

    public User getSelected() {
        return asSingleSelect().getValue();
    }
}
