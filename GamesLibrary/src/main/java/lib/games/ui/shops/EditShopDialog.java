package lib.games.ui.shops;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lib.games.data.Shop;
import lib.games.ui.additional.ShopDataProvider;


public class EditShopDialog extends Dialog {

    static Shop shop;

    public EditShopDialog(ShopDataProvider shopDataProvider, Shop demoShop) {
        VerticalLayout verticalLayout = new VerticalLayout();

        Label info = new Label("Enter shop name!");
        info.setVisible(false);
        TextField name = new TextField("Name:");
        TextField url = new TextField("Url");

        shop = demoShop;

        if (shop != null) {
            name.setValue(shop.getName());
            url.setValue(shop.getUrl());
        }

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button confirmButton = new Button("Confirm", event -> {
            if(name.getValue().isEmpty()) {
                info.setVisible(true);
            }
            else {
               if (shop == null) {
                   if (url.getValue().isEmpty()) {
                       shop = new Shop(name.getValue(), "-");
                   }
                   else {
                       shop = new Shop(name.getValue(), url.getValue());
                   }
                   shopDataProvider.saveNew(shop);
                   close();
                   UI.getCurrent().getPage().reload();
               }
               else {
                   shop.setName(name.getValue());
                   shop.setUrl(url.getValue());
                   shopDataProvider.save(shop);
                   close();
                   shopDataProvider.refreshAll();
               }
            }
        });

        Button cancelButton = new Button("Cancel", event -> {
            close();
        });

        horizontalLayout.add(confirmButton, cancelButton);

        verticalLayout.add(info, name, url, horizontalLayout);
        add(verticalLayout);
    }
}
