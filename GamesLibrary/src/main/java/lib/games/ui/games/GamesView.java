package lib.games.ui.games;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import lib.games.authentication.AccessControl;
import lib.games.authentication.AccessControlFactory;
import lib.games.authentication.CurrentUser;
import lib.games.data.Game;
import lib.games.ui.Layout;

@Route(value = "games", layout = Layout.class)
public class GamesView extends HorizontalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    public static final String VIEW_NAME = "Games";
    private final GamesList list;
    private final GamesForm form;
    private TextField filter;

    private final GameViewLogic viewLogic = new GameViewLogic(this);

   private final GamesDataProvider dataProvider = new GamesDataProvider();

    public GamesView() {
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();
        list = new GamesList();
        list.setDataProvider(dataProvider);
        list.asSingleSelect().addValueChangeListener(event -> viewLogic.rowSelected(event.getValue()));
        form = new GamesForm(viewLogic);
        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(list);
        barAndGridLayout.setFlexGrow(1, list);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(list);

        add(barAndGridLayout);
        add(form);

        viewLogic.init();

    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Filter name, availability or category");
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);


        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }


    public void showNotification(String msg) {
        Notification.show(msg);
    }




    public void clearSelection() {
        list.getSelectionModel().deselectAll();
    }

    public void selectRow(Game row) {
        list.getSelectionModel().select(row);
    }


    public void addGame(Game game) {
        dataProvider.saveNew(game);
    }

    public void updateGame(Game game) {
        dataProvider.save(game);
    }

    public void removeGame(Game game) {
        dataProvider.delete(game);
    }


    public void editGame(Game game) {
        showForm(game != null);
        form.editGame(game);
    }


    public void showForm(boolean show) {
        form.setVisible(show);
        form.setEnabled(show);
        form.setWidth("800%");
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
       viewLogic.enter(parameter);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        AccessControl accessControl = AccessControlFactory.getInstance().createAccessControl();
        if (!accessControl.isUserSignedIn()) {
            UI.getCurrent().navigate("login");
            UI.getCurrent().getPage().reload();
        }
    }
}
