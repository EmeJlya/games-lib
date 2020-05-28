package lib.games.ui.games;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import lib.games.authentication.AccessControlFactory;
import lib.games.backend.DataService;
import lib.games.data.*;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.Null;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class GamesForm  extends Div {

    private final VerticalLayout content;
    private final VerticalLayout commentsLayout;

    private final TextField name;
    private final TextField genre;
    private final TextField year;
    private final TextField platforms; // Лучше делать не текстовым полем а выпадающим списком (для заполнения)
    private final TextField shops;     // Лучше делать не текстовым полем а выпадающим списком (для заполнения)
    private final TextField developer;
    private final TextField distributor;
    private final TextArea description;
    private Button showComments;
    private Button back;
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

        commentsLayout = new VerticalLayout();
        commentsLayout.getStyle().set("border", "1px solid #9E9E9E");

        this.viewLogic = viewLogic;

        back = new Button("Return", new Icon(VaadinIcon.ARROW_LEFT));
        back.setWidth("20%");
        back.addClickListener(event -> viewLogic.cancelGame());
        content.add(back);

        name = new TextField("Name:");
        name.setWidth("100%");
        name.setRequired(true);
        content.add(name);

        genre = new TextField("Genre:");
        genre.setWidth("100%");
        genre.setRequired(true);
        content.add(genre);

        year = new TextField("Release year:");
        year.setWidth("100%");
        year.setRequired(true);
        content.add(year);

        platforms = new TextField("Platforms:");
        platforms.setWidth("100%");
        platforms.setRequired(true);
        content.add(platforms);

        shops = new TextField("Shops:");
        shops.setWidth("100%");
        shops.setRequired(true);
        content.add(shops);

        developer = new TextField("Developer:");
        developer.setWidth("100%");
        developer.setRequired(true);
        content.add(developer);

        distributor = new TextField("Distributor:");
        distributor.setWidth("100%");
        distributor.setRequired(true);
        content.add(distributor);

        description = new TextArea("Description");
        description.setWidth("100%");
        description.setRequired(true);
        content.add(description);

        setReadOnly(true);



        binder = new Binder<>(Game.class);

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
        setButtonsShow(AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessLevel.MODERATOR));

        showComments = new Button("Show Comments");
        showComments.setWidth("100%");
        showComments.addClickListener(event ->{
           content.add(commentsLayout);
           showComments.setEnabled(false);
        });
        content.add(showComments);
    }


    public void editGame(Game game) {
        if (game == null) {
            game = new Game("", "", "", "", "", "", "");
        }
        currentGame = game;
        binder.readBean(game);


        if (currentGame != null){
            name.setValue(currentGame.getName());
            genre.setValue(currentGame.getGenre());
            year.setValue(currentGame.getReleaseYear());

            List comments = DataService.getInstance().getCommentsByObject(currentGame.getId(), "gameid");
            for (Object i: comments){
                Comment comment = (Comment)i;
                String username = DataService.getInstance().getUser(comment.getUserId()).getUsername();
                String text = comment.getText();
                TextArea field = new TextArea("User" + username);
                field.setWidthFull();
                field.setReadOnly(true);
                field.setValue(text);
                commentsLayout.add(field);
            }


            List gameShop = DataService.getInstance().getGameShopsBy("gameid",currentGame.getId());
            String s ="|";
            for (Object i: gameShop){
                GameShop gameshop = (GameShop)i;
                s += " " + DataService.getInstance().getShop(gameshop.getId()).getName() + " |";
            }

            List gamePlatform = DataService.getInstance().getGamePlatformsBy("gameid",currentGame.getId());
            String p ="|";
            for (Object i: gamePlatform){
                GamePlatform gameplatform = (GamePlatform) i;
                p += " " + DataService.getInstance().getShop(gameplatform.getId()).getName() + " |";
            }

            platforms.setValue(p);
            shops.setValue(s);
            String name1,name2;
            try{name1 = DataService.getInstance().getCompany(currentGame.getDeveloper()).getName();
                if (name1 != null){developer.setValue(name1);}}catch (NullPointerException e) {}
            try{name2 = DataService.getInstance().getCompany(currentGame.getDistributor()).getName();
                if (name2 != null){distributor.setValue(name2);}}catch (NullPointerException e){}
            if (currentGame.getDescription() != null){description.setValue(currentGame.getDescription());}
        }
    }

    public void setReadOnly(Boolean read){
        name.setReadOnly(read);
        genre.setReadOnly(read);
        year.setReadOnly(read);
        platforms.setReadOnly(read); // Лучше делать не текстовым полем а выпадающим списком
        shops.setReadOnly(read);     //
        developer.setReadOnly(read);
        distributor.setReadOnly(read);
        description.setReadOnly(read);
    }

    public void setButtonsShow(Boolean show){
        if (show){content.add(save, discard, delete);}
    }
}
