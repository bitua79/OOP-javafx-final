package Notification;;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static Constants.Notifications.*;
import static Constants.Ports.CONNECTION;
import static Main.Color.PURPLE_BOLD_BRIGHT;
import static Main.Color.RESET;


public class Connection implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(CONNECTION);
            while (true){
                System.out.println("Waiting for message from server...");
                Socket socket = serverSocket.accept();
                System.out.println(PURPLE_BOLD_BRIGHT+"new message"+RESET+" receive from server in in "+new Date());
                new Thread(new ConnectionHandle(socket)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

class ConnectionHandle implements Runnable{

    Socket socket;
    public ConnectionHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream from = new DataInputStream(socket.getInputStream());
            int order = from.readInt();

            if (order == PLAY_TIC_TAC_TOE_2_REQ)
                new Thread(new PlayTic_2Req(socket)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

