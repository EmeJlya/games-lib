package lib.games.ui.personal;

import com.vaadin.flow.data.provider.ListDataProvider;
import lib.games.backend.DataService;
import lib.games.data.Comment;
import lib.games.data.Company;
import lib.games.data.Game;
import lib.games.data.User;

public class CommentDataProvider extends ListDataProvider<Comment> {


    public CommentDataProvider(String id, String type) {
            super(DataService.getInstance().getCommentsByObject(id, type));
    }


    public void updateComment(Comment comment) {
        DataService.getInstance().updateComment(comment);
    }

    public void deleteComment(Comment comment) {
        DataService.getInstance().deleteComment(comment.getId());
    }

    public void saveComment(Comment comment) {
        DataService.getInstance().addComment(comment);
    }

    public Game getGameByComment(Comment comment) {
        return DataService.getInstance().getGame(comment.getGameId());
    }

    public User getUserByComment(Comment comment) {
        return DataService.getInstance().getUser(comment.getUserId());
    }
}
