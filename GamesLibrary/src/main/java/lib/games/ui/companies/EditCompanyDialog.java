package lib.games.ui.companies;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lib.games.data.Company;
import lib.games.ui.additional.CompanyDataProvider;

public class EditCompanyDialog extends Dialog {

    private static Company company;

    public EditCompanyDialog(CompanyDataProvider companyDataProvider, Company demoCompany) {

        VerticalLayout verticalLayout = new VerticalLayout();

        Label info = new Label("Enter company name!");
        info.setVisible(false);
        TextField name = new TextField("Name:");
        TextField country = new TextField("Country:");
        NumberField year = new NumberField("Foundation year");

        company = demoCompany;
        if (company != null) {
            name.setValue(company.getName());
            country.setValue(company.getCountry());
            year.setValue((double)company.getFoundationYear());
        }
        else {
            year.setValue(2000d);
        }

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button confirmButton = new Button("Confirm", event -> {
            if(name.getValue().isEmpty()) {
                info.setVisible(true);
            }
            else {
                if (company == null) {
                    if (!year.getValue().toString().isEmpty() && !country.getValue().isEmpty()) {
                        company = new Company(name.getValue(), year.getValue().intValue(), country.getValue());
                    }
                    else if (!year.getValue().toString().isEmpty()){
                        company = new Company(name.getValue(), year.getValue().intValue(), "-");
                    } else if (!country.getValue().isEmpty()) {
                        company = new Company(name.getValue(), 1900, country.getValue());
                    } else {
                        company = new Company(name.getValue(), 1900, "-");
                    }
                    companyDataProvider.saveNew(company);
                    close();
                    UI.getCurrent().getPage().reload();
                }
                else {
                    company.setName(name.getValue());
                    company.setCountry(country.getValue());
                    company.setFoundationYear(year.getValue().intValue());
                    companyDataProvider.save(company);
                    close();
                    companyDataProvider.refreshAll();
                }

            }

        });



        Button cancelButton = new Button("Cancel", event -> {
            close();
        });

        horizontalLayout.add(confirmButton, cancelButton);

        verticalLayout.add(info, name, country, year, horizontalLayout);
        add(verticalLayout);
    }
}
