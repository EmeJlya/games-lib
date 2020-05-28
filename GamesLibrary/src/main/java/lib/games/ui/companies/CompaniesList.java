package lib.games.ui.companies;

import com.vaadin.flow.component.grid.Grid;
import lib.games.data.Company;
import lib.games.data.Shop;

public class CompaniesList extends Grid<Company> {

    public CompaniesList() {
        setSizeFull();

        addColumn(Company::getName).setHeader("Name").setSortable(true).setKey("name");
        addColumn(Company::getCountry).setHeader("Country").setSortable(true).setKey("country");
        addColumn(Company::getFoundationYear).setHeader("Foundation Year").setSortable(true).setKey("year");

        setHeightByRows(true);
    }


    public Company getSelected() {
        return asSingleSelect().getValue();
    }
}
