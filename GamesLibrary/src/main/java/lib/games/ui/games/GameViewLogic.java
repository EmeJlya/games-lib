package lib.games.ui.games;

import com.vaadin.flow.component.UI;
import lib.games.authentication.AccessControl;
import lib.games.authentication.AccessControlFactory;
import lib.games.backend.DataService;
import lib.games.data.AccessLevel;
import lib.games.data.Game;

public class GameViewLogic {

    private final GamesView view;

    public GameViewLogic(GamesView simpleCrudView) {
        view = simpleCrudView;
    }


    public void init() {
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessLevel.MODERATOR)) {
            //view.setNewProductEnabled(false);
        }
    }

    public void cancelGame() {
        setFragmentParameter("");
        view.clearSelection();
    }


    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        UI.getCurrent().navigate(GamesView.class, fragmentParameter);
    }


    public void enter(String gameId) {
        if (gameId != null && !gameId.isEmpty()) {
                try {
                    final Game game = findGame(gameId);
                    view.selectRow(game);
                } catch (final NumberFormatException e) {
            }
        } else {
            view.showForm(false);
        }
    }

    private Game findGame(String gameId) {
        return DataService.getInstance().getGame(gameId);
    }

    public void saveNewGame(Game game) {
        view.clearSelection();
        view.addGame(game);
        setFragmentParameter("");
    }

    public void saveGame(Game game) {
        view.clearSelection();
        view.updateGame(game);
        setFragmentParameter("");
    }

    public void deleteGame(Game game) {
        view.clearSelection();
        view.removeGame(game);
        setFragmentParameter("");
    }


    public void editGame(Game game) {
        if (game == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(game.getName() + "");
        }
        view.editGame(game);
    }

    public void newGame() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editGame(new Game("", "", "", "", "", ""));
    }

    public void rowSelected(Game game) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessLevel.MODERATOR)) {
            editGame(game);
        }
    }
}
