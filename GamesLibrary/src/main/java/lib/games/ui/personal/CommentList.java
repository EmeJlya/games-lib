package lib.games.ui.personal;

import com.vaadin.flow.component.grid.Grid;
import lib.games.data.Comment;
import lib.games.data.User;

public class CommentList extends Grid<Comment> {

    public CommentList () {
        setSizeFull();


        addColumn(Comment::getGameId).setHeader("Username").setSortable(true).setKey("username");
        addColumn(Comment::getText).setHeader("Real name").setSortable(true).setKey("realname");

        setHeightByRows(true);
    }
}
