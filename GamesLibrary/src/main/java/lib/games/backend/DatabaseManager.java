package lib.games.backend;


import lib.games.data.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager(){
        try {
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                            "jdbc:postgresql://127.0.0.1:5432/games",
                    "postgres", "admin" );
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public  static  synchronized DatabaseManager getInstance() {
        if ( instance == null )
        {
            instance = new DatabaseManager ();
        }
        return instance;
    }

    public void execute (final String sql) throws SQLException {
        final Statement statement = connection.createStatement();
        final boolean execute = statement.execute(sql);
        statement.close();
    }

    public int executeUpdate ( final String sql ) throws SQLException
    {
        final Statement statement = connection.createStatement ();
        final int count = statement.executeUpdate ( sql );
        statement.close ();
        return count;
    }
    public List get (final String sql, Class clazz ) throws SQLException
    {
        final Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery ( sql );
        List result = new ArrayList();
        while ( resultSet.next () )
        {
            result.add ( buildObject ( resultSet, clazz ) );
        }
        statement.close ();
        return result;
    }

    private Object buildObject ( final ResultSet resultSet, final Class clazz ) throws SQLException
    {
        if ( clazz.equals ( Game.class ) ) {
            final Game game = new Game (resultSet.getString ( "id" ), resultSet.getString ( "name" ), resultSet.getString("icon"), resultSet.getString("developerid"), resultSet.getString("distributorid"), resultSet.getString("release"), resultSet.getString("genre"));
            game.setDescription(resultSet.getString(("description")));
            return game;
        }
        if ( clazz.equals ( User.class ) ) {
            return new User ( resultSet.getString ( "id" ), resultSet.getString ( "username" ), resultSet.getString ( "realname" ), resultSet.getString ( "password" ), resultSet.getString ( "email" ), AccessLevel.getById(resultSet.getInt("access")));
        }
        if ( clazz.equals(Company.class)) {
            return new Company(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("countryid"), resultSet.getInt("foundation"));
        }
        if ( clazz.equals(Comment.class)) {
            return new Comment(resultSet.getString("id"), resultSet.getString("userid"), resultSet.getString("gameid"), resultSet.getString("text"));
        }
        if ( clazz.equals(Platform.class)) {
            return new Platform(resultSet.getString("id"), resultSet.getString("name"));
        }
        if ( clazz.equals(Shop.class)) {
            return new Shop(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("url"));
        }
        if ( clazz.equals(Localisation.class)) {
            return new Localisation(resultSet.getString("id"), resultSet.getString("locale"));
        }
        if ( clazz.equals(GameLocalisation.class)) {
            return new GameLocalisation(resultSet.getString("id"), resultSet.getString("gameid"), resultSet.getString("localisationid"));
        }
        if ( clazz.equals(GamePlatform.class)) {
            return new GamePlatform(resultSet.getString("id"), resultSet.getString("gameid"), resultSet.getString("platformid"));
        }
        if ( clazz.equals(GameShop.class)) {
            return new GameShop(resultSet.getString("id"), resultSet.getString("gameid"), resultSet.getString("shopid"));
        }
        return null;
    }


}
