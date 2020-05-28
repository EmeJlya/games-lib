package lib.games.ui.additional;

import com.vaadin.flow.data.provider.ListDataProvider;
import lib.games.backend.DataService;
import lib.games.data.Company;
import lib.games.data.Platform;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public class PlatformDataProvider extends ListDataProvider<Platform> {

    private String filterText = "";

    public PlatformDataProvider() {
        super(DataService.getInstance().getAllPlatforms());
    }

    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(platform -> passesFilter(platform.getName(), this.filterText) || passesFilter(platform.getUrl(), this.filterText));
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
    }

    public void save(Platform platform) {
        DataService.getInstance().updatePlatform(platform);
    }

    public void saveNew(Platform platform) {
        DataService.getInstance().addPlatform(platform);
    }

    public void delete(Platform platform) {
        DataService.getInstance().deletePlatform(platform.getId());
    }
}
