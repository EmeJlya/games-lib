package lib.games.ui.localisations;

import com.vaadin.flow.component.grid.Grid;
import lib.games.data.Localisation;
import lib.games.data.Shop;

public class LocalisationsList extends Grid<Localisation> {

    public  LocalisationsList() {
        setSizeFull();

        addColumn(Localisation::getLocale).setHeader("Locale").setSortable(true).setKey("locale");

        setHeightByRows(true);
    }


    public Localisation getSelected() {
        return asSingleSelect().getValue();
    }
}
