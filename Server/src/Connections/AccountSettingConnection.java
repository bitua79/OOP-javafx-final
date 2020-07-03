package Connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static Constants.AllUsersDataHandle.*;
import static Constants.FriendsDataHandle.deleteFromAll;
import static Constants.FriendsDataHandle.setUsernameForAll;
import static Constants.Notifications.*;
import static Constants.Report.*;
import static Main.Color.*;

public class AccountSettingConnection implements Runnable{

    ServerSocket serverSocket;
    public AccountSettingConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(RESET+"Waiting for "+ CYAN_BOLD_BRIGHT +"account setting"+RESET+" socket...");
                Socket socket = serverSocket.accept();
                System.out.println(RED_BOLD_BRIGHT +"account setting"+RESET+" socket connected in "+new Date());
                new Thread(new InitAccountSe(socket)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class InitAccountSe implements Runnable{

    Socket socket;
    public InitAccountSe(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int order = 0;
        try {
            order = new DataInputStream(socket.getInputStream()).readInt();
            if (order == CHANGE_USERNAME) {
                new Thread(new ChangeUsernameHandle(socket)).start();
            }else if (order == CHANGE_PASSWORD){
                new Thread( new ChangePassHandle(socket)).start();
            }else if (order == DELETE_ACCOUNT){
                new Thread(new DeleteAccountHandle(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ChangeUsernameHandle implements Runnable{


    //get username and password from client and return:
    //-3 -> new username is valid
    //-1 -> if username and password are not match
    // 1 -> if username and password match

    Socket socket;
    public ChangeUsernameHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
            DataInputStream fromClient = new DataInputStream(socket.getInputStream());

            String username = fromClient.readUTF();
            String password = fromClient.readUTF();
            String newUsername = fromClient.readUTF();

            if (!getPass(username).equals(password)) {
                toClient.writeInt(NOT_MATCH);
            }else if (isValidUsername(newUsername)){
                toClient.writeInt(VALID_USERNAME);
            } else {
                System.out.println(CYAN_BOLD_BRIGHT +"User: username: "+username+" changed his/her username to "+newUsername+" in "+new Date());
                setUsername(username, newUsername);
                setUsernameForAll(username, newUsername);
                toClient.writeInt(SUCCESSFUL);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ChangePassHandle implements Runnable{


    //get username and password from client and return:
    //-1 -> if username and password are not match
    // 1 -> if username and password match

    Socket socket;
    public ChangePassHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
            DataInputStream fromClient = new DataInputStream(socket.getInputStream());

            String username = fromClient.readUTF();
            String password = fromClient.readUTF();
            String newPassword = fromClient.readUTF();

            if (!getPass(username).equals(password)) {
                toClient.writeInt(NOT_MATCH);
            }
            else {
                System.out.println(CYAN_BOLD_BRIGHT +"User: username: "+username+" changed his/her password to "+newPassword+" in "+new Date());
                toClient.writeInt(SUCCESSFUL);
                setPassword(username, newPassword);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class DeleteAccountHandle implements Runnable{


    //get username and password from client and return:
    //-1 -> if username and password are not match
    // 1 -> if username and password match

    Socket socket;
    public DeleteAccountHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
            DataInputStream fromClient = new DataInputStream(socket.getInputStream());

            String username = fromClient.readUTF();
            String password = fromClient.readUTF();

            if (!getPass(username).equals(password)) {
                toClient.writeInt(NOT_MATCH);
            }
            else {
                System.out.println(CYAN_BOLD_BRIGHT +"User: username: "+username+" deleted in "+new Date());
                toClient.writeInt(SUCCESSFUL);
                deleteUser(username);
                deleteFromAll(username);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



