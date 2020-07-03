package Constants;
import java.sql.*;
import java.util.ArrayList;

import static Constants.AllUsersDataHandle.AllUsers;
import static Constants.Paths.*;

public abstract class FriendsDataHandle {

    public static void init(String username){

        Connection friends;
        try {
            friends = DriverManager.getConnection(NEW_USER_SQL+username+"/FriendsList.db");
            Statement statement = friends.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS FRIENDS(username TEXT)");
            friends.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setUsername(String username, String friendUsername, String friendNewUsername){
        try {
            Connection users = DriverManager.getConnection(NEW_USER_SQL+username+"/FriendsList.db");
            Statement statement = users.createStatement();
            statement.execute("UPDATE FRIENDS SET username = '" + friendNewUsername + "' WHERE username = '"+friendUsername+"'");
            users.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidFriend(String username, String friendUsername) {
        int result = 0;
        try {
            Connection connection = DriverManager.getConnection(NEW_USER_SQL+username+"/FriendsList.db");
            Statement statement = connection.createStatement();

            statement.execute("SELECT EXISTS(SELECT * FROM FRIENDS WHERE username = '" + friendUsername + "')");
            ResultSet resultSet = statement.getResultSet();
            result = resultSet.getInt(1);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result == 1) return true;
        else return false;
    }

    public static void addFriend(String username, String friendUsername) {
        try {
            Connection connection = DriverManager.getConnection(NEW_USER_SQL+username+"/FriendsList.db");
            Statement statement = connection.createStatement();
            if (!isValidFriend(username, friendUsername))
                statement.execute("INSERT INTO FRIENDS VALUES ('" + friendUsername + "')");
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFriend(String username, String friendUsername) {
        try {
            Connection connection = DriverManager.getConnection(NEW_USER_SQL+username+"/FriendsList.db");
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM FRIENDS WHERE username = '" + friendUsername + "'");
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromAll(String friend){

        ArrayList<String> allUsers = AllUsers();
        for (String allUser : allUsers) {
            if (isValidFriend(allUser, friend))
                deleteFriend(allUser, friend);
        }
    }

    public static void setUsernameForAll(String friendUsername, String newUsername){

        ArrayList<String> allUsers = AllUsers();
        for (String allUser : allUsers) {
            if (isValidFriend(allUser, friendUsername))
                setUsername(allUser, friendUsername, newUsername);
        }

    }

    public static ArrayList<String> allFriends(String username){
        ArrayList<String> friends = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(NEW_USER_SQL+username+"/FriendsList.db");
            Statement statement = connection.createStatement();
            statement.execute("SELECT username FROM FRIENDS");
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){
                friends.add(resultSet.getString("username"));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }
}
