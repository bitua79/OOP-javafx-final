package Main;

import Connections.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import static Constants.Ports.*;

public class Server {
    public static Connection users;
    public static Map onlineUsers = new HashMap<String, String>();

    public static void main(String[] args) {
        try {
//            users = DriverManager.getConnection(ALL_USERS_SQL);
//            Statement statement = users.createStatement();
//            statement.execute("CREATE TABLE IF NOT EXISTS USERS(username TEXT, password TEXT, email TEXT,question TEXT,answer TEXT)");
//            users.close();

            ServerSocket logIn = new ServerSocket(LOGIN);
            new Thread(new LoginConnection(logIn)).start();

            ServerSocket accountSetting = new ServerSocket(ACCOUNT_SETTING);
            new Thread(new AccountSettingConnection(accountSetting)).start();

            ServerSocket friendSetting = new ServerSocket(FRIEND_SETTING);
            new Thread(new FriendsConnection(friendSetting)).start();

            ServerSocket gameResult = new ServerSocket(GAME_RESULT);
            new Thread(new GameResultConnection(gameResult)).start();

            ServerSocket offline = new ServerSocket(GET_OFFLINE);
            new Thread(new OfflineConnection(offline)).start();

            ServerSocket playTic2 = new ServerSocket(PLAY_TIC_TAC_TOE_2);
            new Thread(new Tic_2_PlayConnection(playTic2)).start();

            ServerSocket chatTic2 = new ServerSocket(CHAT_TIC_TAC_TOE_2);
            new Thread(new Tic_2ChatConnection(chatTic2)).start();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
