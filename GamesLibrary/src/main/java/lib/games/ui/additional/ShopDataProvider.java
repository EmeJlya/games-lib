package lib.games.ui.additional;

import com.vaadin.flow.data.provider.ListDataProvider;
import lib.games.backend.DataService;
import lib.games.data.Platform;
import lib.games.data.Shop;

import java.util.Locale;
import java.util.Objects;

public class ShopDataProvider extends ListDataProvider<Shop> {

    private String filterText = "";

    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(shop -> passesFilter(shop.getName(), this.filterText) || passesFilter(shop.getUrl(), this.filterText));
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
    }

    public ShopDataProvider() {
        super(DataService.getInstance().getAllShops());
    }

    public void save(Shop shop) {
        DataService.getInstance().updateShop(shop);
    }

    public void saveNew(Shop shop) {
        DataService.getInstance().addShop(shop);
    }

    public void delete(Shop shop) {
        DataService.getInstance().deleteShop(shop.getId());
    }
}
