package Connections;

import Main.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import static Constants.AllUsersDataHandle.isValidUsername;
import static Constants.FriendsDataHandle.*;
import static Constants.Notifications.*;
import static Constants.Report.*;
import static Main.Color.BLUE_BOLD_BRIGHT;
import static Main.Color.RESET;

public class FriendsConnection implements Runnable{

    ServerSocket serverSocket;
    public FriendsConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(RESET+"Waiting for "+ BLUE_BOLD_BRIGHT +"friend setting"+RESET+" socket...");
                Socket socket = serverSocket.accept();
                System.out.println(BLUE_BOLD_BRIGHT +"friend setting "+RESET+"socket connected in "+new Date());

                new Thread(new InitFrSe(socket)).start();
//                //notification
//                int order = new DataInputStream(socket.getInputStream()).readInt();
//                if (order == REMOVE_FRIEND) {
//                    new Thread(new RemoveFriendHandle(socket)).start();
//                }else if (order == CHAT_FRIEND){
//                    new Thread( new ChatFriendHandle(socket)).start();
//                }else if (order == ADD_FRIEND){
//                    new Thread(new AddFriendHandle(socket)).start();
//                }else if (order == SHOW_ALL_FRIENDS){
//                    new Thread(new ShowFriends(socket)).start();
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class InitFrSe implements Runnable {
    Socket socket;
    public InitFrSe(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        int order = 0;
        try {
            order = new DataInputStream(socket.getInputStream()).readInt();
            if (order == REMOVE_FRIEND) {
                new Thread(new RemoveFriendHandle(socket)).start();
            }else if (order == CHAT_FRIEND){
                new Thread( new ChatFriendHandle(socket)).start();
            }else if (order == ADD_FRIEND){
                new Thread(new AddFriendHandle(socket)).start();
            }else if (order == SHOW_ALL_FRIENDS){
                new Thread(new ShowFriends(socket)).start();
            }else if (order == SHOW_ONLINE_FRIENDS){
                new Thread(new ShowOnlineFriends(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class RemoveFriendHandle implements Runnable{

    //get username and friendUsername from client and return:
    //-4 -> if friend doesn't exist
    //-5 -> if friend doesn't exist in the list
    // 1 -> if friend removed successfully


    Socket socket;
    public RemoveFriendHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            DataInputStream from = new DataInputStream(socket.getInputStream());
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            String username = from.readUTF();
            String friendUsername = from.readUTF();

            deleteFriend(username, friendUsername);
            System.out.println(BLUE_BOLD_BRIGHT +"User: "+username+" removed "+friendUsername+" from his/her friends list in "+new Date());
            to.writeInt(SUCCESSFUL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ChatFriendHandle implements Runnable{

    Socket socket;
    public ChatFriendHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
    }
}

class AddFriendHandle implements Runnable{

    //get username and friendUsername from client and return:
    //-4 -> if friend doesn't exist
    //-3 -> if friend already exist in list
    // 1 -> if friend added successfully


    Socket socket;
    public AddFriendHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            DataInputStream from = new DataInputStream(socket.getInputStream());
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            String username = from.readUTF();
            String friendUsername = from.readUTF();

            if (!isValidUsername(friendUsername)){
                to.writeInt(INVALID_USERNAME);

            }else if (isValidFriend(username, friendUsername)){
                to.writeInt(VALID_USERNAME);

            }else {
                to.writeInt(SUCCESSFUL);
                addFriend(username, friendUsername);
                System.out.println(BLUE_BOLD_BRIGHT +"User: "+username+" added "+friendUsername+" to his/her friends list in "+new Date());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ShowFriends implements Runnable{
    Socket socket;
    public ShowFriends(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            DataInputStream from = new DataInputStream(socket.getInputStream());
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            String username = from.readUTF();

            ArrayList<String> allFriends = allFriends(username);
            to.writeInt(allFriends.size());

            for (String allFriend : allFriends) {
                to.writeUTF(allFriend);
            }

            System.out.println(BLUE_BOLD_BRIGHT +"User: "+username+" received his/her friends list in "+new Date());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ShowOnlineFriends implements Runnable{
    Socket socket;
    public ShowOnlineFriends(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            DataInputStream from = new DataInputStream(socket.getInputStream());
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            String username = from.readUTF();

            ArrayList<String> allFriends = allFriends(username);
            allFriends.removeIf(allFriend -> !Server.onlineUsers.containsKey(allFriend));

            to.writeInt(allFriends.size());
            for (String allFriend : allFriends) {
                to.writeUTF(allFriend);
            }

            System.out.println(BLUE_BOLD_BRIGHT +"User: "+username+" received his/her online friends list in "+new Date());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}