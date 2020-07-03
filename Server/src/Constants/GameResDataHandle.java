package Constants;

import java.sql.*;
import java.util.ArrayList;

import static Constants.AllUsersDataHandle.AllUsers;
import static Constants.Paths.ALL_USERS_SQL;
import static Constants.Paths.NEW_USER_SQL;

public abstract class GameResDataHandle {
    public static void init(String username){

        Connection friends;
        try {
            friends = DriverManager.getConnection(NEW_USER_SQL+username+"/GameResult.db");
            Statement statement = friends.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS GAMES(gameName TEXT, win INTEGER, lose INTEGER, draw INTEGER, allGames INTEGER)");
            statement.execute("INSERT INTO GAMES VALUES ('tic-tac-toe1', '0', '0', '0', '0')");
            statement.execute("INSERT INTO GAMES VALUES ('tic-tac-toe2', '0', '0', '0', '0')");
            statement.execute("INSERT INTO GAMES (gameName, win, lose, allGames) VALUES ('battle-ship1', '0', '0', '0')");
            statement.execute("INSERT INTO GAMES (gameName, win, lose, allGames) VALUES ('battle-ship2', '0', '0', '0')");
            statement.execute("INSERT INTO GAMES (gameName, win, lose, allGames) VALUES ('snake1', '0', '0', '0')");
            statement.execute("INSERT INTO GAMES (gameName, win, lose, allGames) VALUES ('snake2', '0', '0', '0')");

            friends.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getWin(String username, String gameName){
        return get("win", gameName, username);
    }
    public static int getLose(String username, String gameName){
        return get("lose", gameName, username);
    }
    public static int getDraw(String username, String gameName){
        if (!gameName.equals("tic-tac-toe1") && !gameName.equals("tic-tac-toe2"))return -1;
        return get("draw", gameName, username);
    }
    public static int getAll(String username, String gameName){
        return get("allGames", gameName, username);
    }
    public static int get(String subject, String gameName, String username){
        int result = 0;
        try {
            Connection connection = DriverManager.getConnection(NEW_USER_SQL+username+"/GameResult.db");
            Statement statement = connection.createStatement();
            statement.execute("SELECT "+subject+" FROM GAMES WHERE gameName = '" + gameName + "'");
            ResultSet resultSet = statement.getResultSet();
            result = resultSet.getInt(1);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void win(String username, String gameName){
        try {
            Connection connection = DriverManager.getConnection(NEW_USER_SQL+username+"/GameResult.db");
            Statement statement = connection.createStatement();
            statement.execute("SELECT win FROM GAMES WHERE gameName = '" + gameName + "'");
            ResultSet resultSet = statement.getResultSet();
            int result = resultSet.getInt(1);
            result++;
            statement.execute("UPDATE GAMES SET win='"+result+"' WHERE gameName='"+gameName+"'");
            connection.close();
            add(username, gameName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void lose(String username, String gameName){
        try {
            Connection users = DriverManager.getConnection(NEW_USER_SQL+username+"/GameResult.db");
            Statement statement = users.createStatement();
            statement.execute("SELECT lose FROM GAMES WHERE gameName = '" + gameName + "'");
            ResultSet resultSet = statement.getResultSet();
            int result = resultSet.getInt(1);
            result++;
            statement.execute("UPDATE GAMES SET lose='"+result+"' WHERE gameName='"+gameName+"'");
            users.close();
            add(username, gameName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void draw(String username, String gameName){
        if (!gameName.equals("tic-tac-toe1") && !gameName.equals("tic-tac-toe2"))return;
        try {
            Connection connection = DriverManager.getConnection(NEW_USER_SQL+username+"/GameResult.db");
            Statement statement = connection.createStatement();
            statement.execute("SELECT draw FROM GAMES WHERE gameName = '" + gameName + "'");
            ResultSet resultSet = statement.getResultSet();
            int result = resultSet.getInt(1);
            result++;
            statement.execute("UPDATE GAMES SET draw='"+result+"' WHERE gameName='"+gameName+"'");
            connection.close();
            add(username, gameName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(String username, String gameName){
        try {
            Connection connection = DriverManager.getConnection(NEW_USER_SQL+username+"/GameResult.db");
            Statement statement = connection.createStatement();
            statement.execute("SELECT allGames FROM GAMES WHERE gameName = '" + gameName + "'");
            ResultSet resultSet = statement.getResultSet();
            int result = resultSet.getInt(1);
            result++;
            statement.execute("UPDATE GAMES SET allGames='"+result+"' WHERE gameName='"+gameName+"'");
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
