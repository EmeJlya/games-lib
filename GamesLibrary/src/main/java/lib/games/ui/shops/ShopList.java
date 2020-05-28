package lib.games.ui.shops;

import com.vaadin.flow.component.grid.Grid;
import lib.games.authentication.AccessControlFactory;
import lib.games.authentication.CurrentUser;
import lib.games.data.AccessLevel;
import lib.games.data.Shop;
import lib.games.data.User;

public class ShopList extends Grid<Shop> {

    public ShopList() {
        setSizeFull();

        addColumn(Shop::getName).setHeader("Name").setSortable(true).setKey("name");
        addColumn(Shop::getUrl).setHeader("Url").setSortable(true).setKey("url");

        setHeightByRows(true);
    }


    public Shop getSelected() {
        return asSingleSelect().getValue();
    }
}
