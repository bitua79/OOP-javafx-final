package Connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static Constants.GameResDataHandle.*;
import static Constants.Notifications.*;
import static Main.Color.PURPLE_BOLD_BRIGHT;
import static Main.Color.RESET;

public class GameResultConnection implements Runnable{

    ServerSocket serverSocket;
    public GameResultConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(RESET+"Waiting for "+ PURPLE_BOLD_BRIGHT +"game result"+RESET+" socket...");
                Socket socket = serverSocket.accept();
                System.out.println(PURPLE_BOLD_BRIGHT +"game result"+RESET+" socket connected in "+new Date());

                new Thread(new InitGameRes(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class InitGameRes implements Runnable{
    Socket socket;

    public InitGameRes(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream from = new DataInputStream(socket.getInputStream());

            int order_1 = from.readInt();
            if (order_1 == SHOW_RESULT){
                int order_2 = from.readInt();

                if (order_2 == TIC_TAC_TOE_RES){
                    new Thread(new TicResHandle(socket)).start();
                }
                else if (order_2 == BATTLE_SHIP_RES){
                    new Thread(new BattleResHandle(socket)).start();
                }
                else if (order_2 == SNAKE_LADDER_RES){
                    new Thread(new SnakeResHandle(socket)).start();
                }
            }else if (order_1 == ADD_RESULT){
                String gameName = from.readUTF();
                new Thread(new AddResultHandle(socket, gameName)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TicResHandle implements Runnable{

    Socket socket;
    public TicResHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream from = new DataInputStream(socket.getInputStream());
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            String username = from.readUTF();
            to.writeInt(getAll(username, "tic-tac-toe1"));
            to.writeInt(getWin(username, "tic-tac-toe1"));
            to.writeInt(getLose(username, "tic-tac-toe1"));
            to.writeInt(getDraw(username, "tic-tac-toe1"));

            System.out.println(getWin(username, "tic-tac-toe2"));
            to.writeInt(getAll(username, "tic-tac-toe2"));
            to.writeInt(getWin(username, "tic-tac-toe2"));
            to.writeInt(getLose(username, "tic-tac-toe2"));
            to.writeInt(getDraw(username, "tic-tac-toe2"));

            System.out.println(PURPLE_BOLD_BRIGHT +"User: username: "+username+" received her/his tic tac toe game Result in "+new Date());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class BattleResHandle implements Runnable{

    Socket socket;
    public BattleResHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream from = new DataInputStream(socket.getInputStream());
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            String username = from.readUTF();
            to.writeInt(getAll(username, "battle-ship1"));
            to.writeInt(getWin(username, "battle-ship1"));
            to.writeInt(getLose(username, "battle-ship1"));

            to.writeInt(getAll(username, "battle-ship2"));
            to.writeInt(getWin(username, "battle-ship2"));
            to.writeInt(getLose(username, "battle-ship2"));

            System.out.println(PURPLE_BOLD_BRIGHT +"User: username: "+username+" received her/his  battle ship game Result in "+new Date());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SnakeResHandle implements Runnable{

    Socket socket;
    public SnakeResHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream from = new DataInputStream(socket.getInputStream());
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            String username = from.readUTF();
            to.writeInt(getAll(username, "snake1"));
            to.writeInt(getWin(username, "snake1"));
            to.writeInt(getLose(username, "snake1"));

            to.writeInt(getAll(username, "snake2"));
            to.writeInt(getWin(username, "snake2"));
            to.writeInt(getLose(username, "snake2"));

            System.out.println(PURPLE_BOLD_BRIGHT +"User: username: "+username+" received her/his snake game Result in "+new Date());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class AddResultHandle implements Runnable{

    Socket socket;
    String gameName;

    public AddResultHandle(Socket socket, String gameName) {
        this.socket = socket;
        this.gameName = gameName;
    }

    @Override
    public void run() {
        try {
            DataInputStream from = new DataInputStream(socket.getInputStream());
            String username = from.readUTF();
            int result = from.readInt();

            if (result == WINPlayer1){
                win(username, gameName);
                System.out.println(PURPLE_BOLD_BRIGHT +"User: username: "+username+" win in "+gameName+" in "+new Date());
            }else if (result == LOSEPlayer1){
                lose(username, gameName);
                System.out.println(PURPLE_BOLD_BRIGHT +"User: username: "+username+" lose in "+gameName+" in "+new Date());
            }else if (result == DRAW){
                draw(username, gameName);
                System.out.println(PURPLE_BOLD_BRIGHT +"User: username: "+username+" has a draw "+gameName+" in "+new Date());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
