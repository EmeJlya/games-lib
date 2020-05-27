package lib.games.ui.games;

import com.vaadin.flow.data.provider.ListDataProvider;
import lib.games.backend.DataService;
import lib.games.data.Game;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public class GamesDataProvider extends ListDataProvider<Game> {

    private String filterText = "";

    public GamesDataProvider() {
        super(DataService.getInstance().getAllGames());
    }

    public void save(Game game) {
        DataService.getInstance().updateGame(game);
    }


    public void saveNew(Game game) {
        DataService.getInstance().addGame(game);
    }


    public void delete(Game game) {
        DataService.getInstance().deleteGame(game.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for product name, availability and category.
     *
     * @param filterText
     *            the text to filter by, never null
     */
    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(game -> passesFilter(game.getName(), this.filterText)
                || passesFilter(game.getGenre(), this.filterText)
                || passesFilter(game.getDeveloper(), this.filterText));
    }

    @Override
    public String getId(Game game) {
        Objects.requireNonNull(game,
                "Cannot provide an id for a null product.");

        return game.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
