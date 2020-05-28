package lib.games.ui.additional;

import com.vaadin.flow.data.provider.ListDataProvider;

import lib.games.backend.DataService;
import lib.games.data.Localisation;
import lib.games.data.Platform;

import java.util.Locale;
import java.util.Objects;

public class LocalisationDataProvider extends ListDataProvider<Localisation> {

    private String filterText = "";

    public LocalisationDataProvider() {
        super(DataService.getInstance().getAllLocalisations());
    }

    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(localisation -> passesFilter(localisation.getLocale(), this.filterText));
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
    }

    public void saveNew(Localisation localisation) {
        DataService.getInstance().addLocalisation(localisation);
    }

    public void delete(Localisation localisation) {
        DataService.getInstance().deleteLocalisation(localisation.getId());
    }
}
