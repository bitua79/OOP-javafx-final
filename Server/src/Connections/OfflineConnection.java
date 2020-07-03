package Connections;

import Main.Server;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static Main.Color.*;


public class OfflineConnection implements Runnable {

    ServerSocket serverSocket;
    public OfflineConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(RESET+"Waiting for "+ RED_BOLD_BRIGHT +"offline "+RESET+"socket...");
                Socket socket = serverSocket.accept();
                System.out.println(RED_BOLD_BRIGHT +"offline "+RESET+"socket connected in "+new Date());
                new Thread(new OfflineHandle(socket)).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class OfflineHandle implements Runnable{

    Socket socket;
    public OfflineHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            DataInputStream from = new DataInputStream(socket.getInputStream());

            String username = from.readUTF();
            Server.onlineUsers.remove(username);
            System.out.println(RED_BOLD_BRIGHT +"User: "+username+" got offline in "+new Date());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
