package lib.games.ui.localisations;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import lib.games.data.Localisation;
import lib.games.data.Shop;
import lib.games.ui.additional.LocalisationDataProvider;


public class EditLocalisationDialog extends Dialog {
    static Localisation localisation;

    public EditLocalisationDialog(LocalisationDataProvider localisationDataProvider, Localisation demoLocalisation) {
        VerticalLayout verticalLayout = new VerticalLayout();

        Label info = new Label("Enter locale!");
        info.setVisible(false);
        TextField locale = new TextField("Locale:");

        localisation = demoLocalisation;

        if (localisation != null) {
            locale.setValue(localisation.getLocale());
        }

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button confirmButton = new Button("Confirm", event -> {
            if(locale.getValue().isEmpty()) {
                info.setVisible(true);
            }
            else {
                if (localisation == null) {
                    localisation = new Localisation(locale.getValue());
                    localisationDataProvider.saveNew(localisation);
                    close();
                    UI.getCurrent().getPage().reload();
                }
                else {
                    localisation.setLocale(locale.getValue());
                    close();
                    localisationDataProvider.refreshAll();
                }
            }
        });

        Button cancelButton = new Button("Cancel", event -> {
            close();
        });

        horizontalLayout.add(confirmButton, cancelButton);

        verticalLayout.add(info, locale, horizontalLayout);
        add(verticalLayout);
    }
}
