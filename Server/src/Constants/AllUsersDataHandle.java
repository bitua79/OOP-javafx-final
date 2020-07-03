package Constants;

import Main.Server;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import static Constants.Paths.ALL_USERS_SQL;
import static Constants.Paths.NEW_USER;

public abstract class AllUsersDataHandle {

    public static String getPass(String username) {
        return get("password", username);
    }

    public static String getQuestion(String username) {
        return get("question", username);
    }

    public static String getAnswer(String username) {
        return get("answer", username);
    }

    public static String get(String subject, String username){
        String result = null;
        try {
            Connection users = DriverManager.getConnection(ALL_USERS_SQL);
            Statement statement = users.createStatement();
            statement.execute("SELECT "+subject+" FROM USERS WHERE username = '" + username + "'");
            ResultSet resultSet = statement.getResultSet();
            result = resultSet.getString(1);
            users.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void setPassword(String username, String newPass){
        set("password", username, newPass);
    }

    public static void setUsername(String username, String newUsername){
        set("username", username, newUsername);

        File sourceFile = new File(NEW_USER+username);
        File destFile = new File(NEW_USER+newUsername);

        if (!sourceFile.renameTo(destFile)) {
            try {
                throw new Exception("Rename file was failed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void set(String subject, String username, String neW){
        try {
            Connection users = DriverManager.getConnection(ALL_USERS_SQL);
            Statement statement = users.createStatement();
            statement.execute("UPDATE USERS SET "+subject+" = '" + neW + "' WHERE username = '"+username+"'");
            users.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean isValidUsername(String username) {
        return isValid("username", username);
    }

    public static boolean isValidEmail(String email) {
        return isValid("email", email);
    }

    public static boolean isValid(String subject, String subjectStr){
        int result = 0;
        try {
            Connection users = DriverManager.getConnection(ALL_USERS_SQL);
            Statement statement = users.createStatement();

            statement.execute("SELECT EXISTS(SELECT * FROM USERS WHERE "+subject+" = '" + subjectStr + "')");
            ResultSet resultSet = statement.getResultSet();
            result = resultSet.getInt(1);
            users.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result == 1) return true;
        else return false;
    }


    public static void addUser(String username, String password, String email, String question, String answer) {
        try {
            Connection users = DriverManager.getConnection(ALL_USERS_SQL);
            Statement statement = users.createStatement();
            statement.execute("INSERT INTO USERS VALUES ('" + username + "', '" + password + "', '" + email + "', '" + question + "', '" + answer + "')");
            users.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String username) {
        try {
            Connection users = DriverManager.getConnection(ALL_USERS_SQL);
            Statement statement = users.createStatement();
            statement.execute("DELETE FROM USERS WHERE username = '" + username + "'");
            users.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //delete user folder and all its files
        File file = new File(NEW_USER+username);
        String[] entries = file.list();
        for(String s: entries){
            File currentFile = new File(file.getPath(), s);
            if (!currentFile.delete()) {
                try {
                    throw new Exception("Delete file was failed!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!file.delete()){
            try {
                throw new Exception("Delete file was failed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> AllUsers(){
        ArrayList<String> people = new ArrayList<>();
        try {
            Connection users = DriverManager.getConnection(ALL_USERS_SQL);
            Statement statement = users.createStatement();
            statement.execute("SELECT username FROM USERS");
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){
                people.add(resultSet.getString("username"));
            }

            users.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }
}
