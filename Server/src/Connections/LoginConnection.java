package Connections;

import Constants.FriendsDataHandle;
import Constants.GameResDataHandle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static Constants.AllUsersDataHandle.*;
import static Constants.AllUsersDataHandle.getPass;
import static Constants.Notifications.*;
import static Constants.Paths.NEW_USER;
import static Constants.Ports.CONNECTION;
import static Constants.Ports.HOST;
import static Constants.Report.*;
import static Main.Color.*;
import static Main.Color.YELLOW_BOLD_BRIGHT;
import static Main.Server.onlineUsers;

public class LoginConnection implements Runnable{
        ServerSocket serverSocket;
    public LoginConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(RESET+"Waiting for "+ GREEN_BOLD_BRIGHT +"log in"+RESET+" socket...");
                Socket socket = serverSocket.accept();
                System.out.println(GREEN_BOLD_BRIGHT +"log in "+RESET+"socket connected in "+new Date());
                new Thread(new InitLogIn(socket)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class InitLogIn implements Runnable{

    Socket socket;
    public InitLogIn(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int order = 0;
        try {
            order = new DataInputStream(socket.getInputStream()).readInt();
            if (order == RETRIEVAL_PASS) {
                new Thread(new RetrievalPassHandle(socket)).start();
            }else if (order == SIGN_UP){
                new Thread( new SignUpHandle(socket)).start();
            }else if (order == SIGN_IN){
                new Thread(new SignInHandle(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SignUpHandle implements Runnable{

    //get information from client and return:
    //-3 -> if username exist
    //-2 -> if email exist
    // 1 -> if user saved successfully

    private String USERNAME;

    Socket socket;
    public SignUpHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
            DataInputStream fromClient = new DataInputStream(socket.getInputStream());

            String username = fromClient.readUTF();
            String password = fromClient.readUTF();
            String email = fromClient.readUTF();
            String question = fromClient.readUTF();
            String answer = fromClient.readUTF();

            if (isValidUsername(username)) toClient.writeInt(VALID_USERNAME);
            else if (isValidEmail(email)) toClient.writeInt(VALID_EMAIL);
            else {
                System.out.println(GREEN_BOLD_BRIGHT +"New User: username: "+username+", password: "+password+" email: "+email+", question: "+question+", answer: "+answer+"signed up in "+new Date());
                addUser(username, password, email, question, answer);
                toClient.writeInt(SUCCESSFUL);
                USERNAME = username;
                newUser();

                Socket socket = new Socket(HOST, CONNECTION);
                onlineUsers.put(username, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newUser(){
        String SQLPath = NEW_USER + USERNAME;
        try {
            Path path = Paths.get(SQLPath);
            Files.createDirectories(path);

            FriendsDataHandle.init(USERNAME);
            GameResDataHandle.init(USERNAME);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SignInHandle implements Runnable{

    //get username and password from client and return:
    //-4 -> if username doesn't exist
    //-1 -> if username and password are not match
    // 1 -> if username and password match

    Socket socket;
    public SignInHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
            DataInputStream fromClient = new DataInputStream(socket.getInputStream());

            String username = fromClient.readUTF();
            String password = fromClient.readUTF();

            if (!isValidUsername(username)) toClient.writeInt(INVALID_USERNAME);
            else if (!getPass(username).equals(password)) toClient.writeInt(NOT_MATCH);
            else {
                System.out.println(GREEN_BOLD_BRIGHT +"User: username: "+username+", password: "+password+" signed in , in "+new Date());
                toClient.writeInt(SUCCESSFUL);

                fromClient.readInt();
                Socket socket = new Socket(HOST, CONNECTION);
                onlineUsers.put(username, socket);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class RetrievalPassHandle implements Runnable{

    //get username and password from client and return:
    //-4 -> if username doesn't exist
    //-1 -> if question and answer are not match
    // 1 -> if question and answer match

    Socket socket;
    public RetrievalPassHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream fromClient = new DataInputStream(socket.getInputStream());
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

            String username = fromClient.readUTF();
            String question = fromClient.readUTF();
            String answer = fromClient.readUTF();

            if (!isValidUsername(username))toClient.writeInt(INVALID_USERNAME);
            else if(!getQuestion(username).equals(question) ||
                    !getAnswer(username).equals(answer))toClient.writeInt(NOT_MATCH);
            else {
                toClient.writeInt(SUCCESSFUL);
                toClient.writeUTF(getPass(username));
                System.out.println(GREEN_BOLD_BRIGHT +"User: username: "+username+", question: "+question+", answer: "+answer+" retrieval his/her pass , in "+new Date());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
