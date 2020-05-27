package lib.games.ui.games;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import lib.games.data.Game;

public class GamesForm  extends Div {

    private final VerticalLayout content;

    private final TextField name;
    private final TextField genre;
    private final TextField year;
    private final TextField developer;
    private final TextField distributor;
    private final TextArea description;
    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;

    private final GameViewLogic viewLogic;
    private final Binder<Game> binder;
    private Game currentGame;

    public GamesForm(GameViewLogic viewLogic) {
        setClassName("game-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        content.addClassName("game-form-content");
        add(content);

        this.viewLogic = viewLogic;

        name = new TextField("Name:");
        name.setWidth("100%");
        name.setRequired(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(name);

        genre = new TextField("Genre:");
        genre.setWidth("100%");
        genre.setRequired(true);
        genre.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(genre);

        year = new TextField("Release year:");
        year.setWidth("100%");
        year.setRequired(true);
        year.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(year);

        developer = new TextField("Developer:");
        developer.setWidth("100%");
        developer.setRequired(true);
        developer.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(developer);

        distributor = new TextField("Distributor:");
        distributor.setWidth("100%");
        distributor.setRequired(true);
        distributor.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(distributor);

        description = new TextArea("Description");
        name.setWidth("100%");
        name.setRequired(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(description);



        binder = new Binder<>(Game.class);

         //enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            final boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });

        save = new Button("Save");
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if (currentGame != null
                    && binder.writeBeanIfValid(currentGame)) {
                viewLogic.saveGame(currentGame);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        //discard.addClickListener(event -> viewLogic.editProduct(currentProduct));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelGame());
        cancel.addClickShortcut(Key.ESCAPE);
        getElement().addEventListener("keydown", event -> viewLogic.cancelGame()).setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentGame != null) {
                viewLogic.deleteGame(currentGame);
            }
        });

        content.add(save, discard, delete, cancel);
    }


    public void editGame(Game game) {
        if (game == null) {
            game = new Game("", "", "", "", "", "");
        }
        currentGame = game;
        binder.readBean(game);
    }
}
