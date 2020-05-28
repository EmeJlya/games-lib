package lib.games.ui.games;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import lib.games.data.Game;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.stream.Collectors;

public class GamesList extends Grid<Game> {

    public GamesList() {

        setSizeFull();

        addColumn(Game::getName).setHeader("Name").setFlexGrow(20).setSortable(true).setKey("gamename");
        addColumn(Game::getGenre).setHeader("Genre").setFlexGrow(20).setSortable(true).setKey("gamegenre");
        addColumn(Game::getReleaseYear).setHeader("Release Year").setFlexGrow(20).setSortable(true).setKey("gameyear");
        addColumn(Game::getDeveloper).setHeader("Developer").setFlexGrow(20).setSortable(true).setKey("gamedev");
        addColumn(Game::getDeveloper).setHeader("Distributor").setFlexGrow(20).setSortable(true).setKey("gamedistr");


        UI.getCurrent().getPage().addBrowserWindowResizeListener(e -> setColumnVisibility(e.getWidth()));
    }

    private void setColumnVisibility(int width) {
        if (width > 800) {
            getColumnByKey("gamename").setVisible(true);
            getColumnByKey("gamegenre").setVisible(true);
            getColumnByKey("gameyear").setVisible(true);
            getColumnByKey("gamedev").setVisible(true);
            getColumnByKey("gamedistr").setVisible(true);
        } else if (width > 400){
            getColumnByKey("gamename").setVisible(true);
            getColumnByKey("gamegenre").setVisible(true);
            getColumnByKey("gameyear").setVisible(false);
            getColumnByKey("gamedev").setVisible(true);
            getColumnByKey("gamedistr").setVisible(false);
        } else {
            getColumnByKey("gamename").setVisible(true);
            getColumnByKey("gamegenre").setVisible(true);
            getColumnByKey("gameyear").setVisible(false);
            getColumnByKey("gamedev").setVisible(false);
            getColumnByKey("gamedistr").setVisible(false);
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        UI.getCurrent().getInternals().setExtendedClientDetails(null);
        UI.getCurrent().getPage().retrieveExtendedClientDetails(e -> {
            setColumnVisibility(e.getBodyClientWidth());
        });
    }

    public Game getSelected() {
        return asSingleSelect().getValue();
    }

    public void refresh(Game game) {
        getDataCommunicator().refresh(game);
    }

}
