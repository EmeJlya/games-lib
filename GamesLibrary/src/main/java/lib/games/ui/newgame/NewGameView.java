package lib.games.ui.newgame;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import lib.games.authentication.AccessControl;
import lib.games.authentication.AccessControlFactory;
import lib.games.authentication.BasicAccessControl;
import lib.games.authentication.CurrentUser;
import lib.games.backend.DataService;
import lib.games.data.*;
import lib.games.ui.Layout;
import lib.games.ui.additional.CompanyDataProvider;
import lib.games.ui.additional.LocalisationDataProvider;
import lib.games.ui.additional.PlatformDataProvider;
import lib.games.ui.additional.ShopDataProvider;
import lib.games.ui.games.GamesDataProvider;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.util.List;
import java.util.Set;


@Route(value = "new", layout = Layout.class)
public class NewGameView extends VerticalLayout implements BeforeEnterObserver {

    private final CompanyDataProvider companyDataProvider = new CompanyDataProvider();
    private final ShopDataProvider shopDataProvider = new ShopDataProvider();
    private final PlatformDataProvider platformDataProvider = new PlatformDataProvider();
    private final LocalisationDataProvider localisationDataProvider = new LocalisationDataProvider();

    private TextField nameField;
    private TextField genreField;
    private TextField releaseField;
    private Upload icon;
    private TextArea description;
    private ComboBox<Company> developerBox;
    private ComboBox<Company> distributorBox;
    private MultiSelectListBox<Shop> shopBox;
    private MultiSelectListBox<Platform> platformBox;
    private MultiSelectListBox<Localisation> localisationBox;
    private Button confirmButton;
    private Button discardButton;

    public NewGameView() {

        H1 title = new H1("Add game");
        add(title);

        nameField = new TextField("Name:");
        nameField.setPlaceholder("Enter name");
        genreField = new TextField("Genre:");
        genreField.setPlaceholder("Enter genre");
        releaseField = new TextField("Release year:");
        releaseField.setPlaceholder("Enter release date");

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");


        VerticalLayout developerLayout = developerLayoutCreation();
        VerticalLayout distributorLayout = distributorLayoutCreation();

        VerticalLayout shopLayout = shopsLayoutCreation();
        VerticalLayout platformLayout = platformLayoutCreation();
        VerticalLayout localisationLayout = localisationLayoutCreation();



        confirmButton = new Button("Confirm", e -> {
            addGame();
        });

        discardButton = new Button("Discard", e -> {
            discardChanges();
        });
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.add(nameField, genreField, releaseField, developerLayout, distributorLayout, shopLayout, platformLayout, localisationLayout);

        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        discardButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        description = new TextArea("Description");
        description.setWidth("100%");

        HorizontalLayout pageLayout = new HorizontalLayout();
        pageLayout.add(fieldsLayout, description);
        fieldsLayout.setWidth("20%");
        pageLayout.setWidth("100%");

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(confirmButton, discardButton);

        add(pageLayout, buttonsLayout);
    }


    private VerticalLayout developerLayoutCreation() {
        VerticalLayout developerLayout = new VerticalLayout();

        developerBox = new ComboBox<>();
        developerBox.setLabel("Developer");
        developerBox.setPlaceholder("Choose developer");
        developerBox.setDataProvider(companyDataProvider);
        developerBox.setItemLabelGenerator(Company::getName);


        developerLayout.add(developerBox);

        return developerLayout;
    }

    private VerticalLayout distributorLayoutCreation() {
        VerticalLayout distributorLayout = new VerticalLayout();

        distributorBox = new ComboBox<>();
        distributorBox.setLabel("Distributor");
        distributorBox.setPlaceholder("Choose distributor");
        distributorBox.setDataProvider(companyDataProvider);
        distributorBox.setItemLabelGenerator(Company::getName);

        distributorLayout.add(distributorBox);

        return distributorLayout;
    }

    private VerticalLayout shopsLayoutCreation() {
        VerticalLayout shopsLayout = new VerticalLayout();

        Label shopHeader = new Label("Shops:");
        shopBox = new MultiSelectListBox<>();
        shopBox.setDataProvider(shopDataProvider);

        shopBox.setRenderer(new ComponentRenderer<>(item -> {
            Label label = new Label(item.getName());
            return label;
        }));

        shopsLayout.add(shopHeader, shopBox);

        return shopsLayout;
    }

    private VerticalLayout platformLayoutCreation() {
        VerticalLayout platformLayout = new VerticalLayout();

        Label platformHeader = new Label("Platforms:");

        platformBox = new MultiSelectListBox<>();
        platformBox.setDataProvider(platformDataProvider);

        platformBox.setRenderer(new ComponentRenderer<>(item -> {
            Label label = new Label(item.getName());
            return label;
        }));
        platformLayout.add(platformHeader, platformBox);

        return platformLayout;
    }

    private VerticalLayout localisationLayoutCreation() {
        VerticalLayout localisationLayout = new VerticalLayout();

        Label localisationHeader = new Label("Localisations:");

        localisationBox = new MultiSelectListBox<>();
        localisationBox.setDataProvider(localisationDataProvider);

        localisationBox.setRenderer(new ComponentRenderer<>(item -> {
            Label label = new Label(item.getLocale());
            return label;
        }));
        localisationLayout.add(localisationHeader, localisationBox);

        return localisationLayout;
    }

    public void discardChanges() {
        nameField.setValue("");
        genreField.setValue("");
        releaseField.setValue("");
        developerBox.clear();
        distributorBox.clear();
        shopBox.deselectAll();
        platformBox.deselectAll();
        localisationBox.deselectAll();
        description.clear();
    }

    public void addGame(){
        if (!nameField.getValue().isEmpty() && !genreField.getValue().isEmpty() && !releaseField.getValue().isEmpty() && !developerBox.isEmpty() && !distributorBox.isEmpty()) {
            Game game = new Game(nameField.getValue(), " ", developerBox.getValue().getId(), distributorBox.getValue().getId(), releaseField.getValue(), genreField.getValue());
            game.setDescription(description.getValue());
            DataService.getInstance().addGame(game);
            Set<Shop> gameShop = shopBox.getSelectedItems();
            for (Shop shop: gameShop) {
                DataService.getInstance().addGameShop(new GameShop(game.getId(), shop.getId()));
            }

            Set<Platform> gamePlatform = platformBox.getSelectedItems();
            for (Platform platform: gamePlatform) {
                DataService.getInstance().addGamePlatform(new GamePlatform(game.getId(), platform.getId()));
            }

            Set<Localisation> gameLocalisation = localisationBox.getSelectedItems();
            for (Localisation localisation: gameLocalisation) {
                DataService.getInstance().addGameLocalisation(new GameLocalisation(game.getId(), localisation.getId()));
            }
         }
        discardChanges();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        AccessControl accessControl = AccessControlFactory.getInstance().createAccessControl();
        if (!accessControl.isUserSignedIn()) {
            UI.getCurrent().navigate("login");
            UI.getCurrent().getPage().reload();
        } else if (!accessControl.isUserInRole(AccessLevel.MODERATOR)) {
            UI.getCurrent().navigate("games");
            UI.getCurrent().getPage().reload();
        }
    }
}
