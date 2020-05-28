package lib.games.ui.additional;

import com.vaadin.flow.data.provider.ListDataProvider;
import lib.games.backend.DataService;
import lib.games.data.Company;
import lib.games.data.User;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public class CompanyDataProvider extends ListDataProvider<Company> {

    private String filterText = "";
    public CompanyDataProvider() {
        super(DataService.getInstance().getAllCompanies());
    }

    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(company -> passesFilter(company.getName(), this.filterText) || passesFilter(company.getCountry(), this.filterText) ||
                passesFilter(company.getFoundationYear(), this.filterText));
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
    }

    public void save(Company company) {
        DataService.getInstance().updateCompany(company);
    }

    public void saveNew(Company company) {
        DataService.getInstance().addCompany(company);
    }

    public void delete(Company company) {
        DataService.getInstance().deleteCompany(company.getId());
    }


}
