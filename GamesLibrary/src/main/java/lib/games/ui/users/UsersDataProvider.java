package lib.games.ui.users;

import com.vaadin.flow.data.provider.ListDataProvider;
import lib.games.backend.DataService;
import lib.games.data.Game;
import lib.games.data.User;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public class UsersDataProvider extends ListDataProvider<User> {

    private String filterText = "";

    public UsersDataProvider() {
        super(DataService.getInstance().getAllUsers());
    }

    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(user -> passesFilter(user.getUsername(), this.filterText));
    }

    public void save(User user) {
        DataService.getInstance().updateUser(user);
    }

    public void delete(User user) {
        DataService.getInstance().deleteUser(user.getId());
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
    }
}
