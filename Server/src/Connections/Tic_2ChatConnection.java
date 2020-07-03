package Connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static Constants.Ports.STOP_CHAT_SOCKET;
import static Main.Color.*;

public class Tic_2ChatConnection implements Runnable {

    ServerSocket serverSocket;
    public Tic_2ChatConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(RESET+"Waiting for "+ WHITE_BOLD_BRIGHT +"first Player chat tic_2"+RESET+" socket...");
                Socket socket = serverSocket.accept();
                System.out.println(WHITE_BOLD_BRIGHT +"first Player chat tic_2 "+RESET+"socket connected in "+new Date());
                String firstName = new DataInputStream(socket.getInputStream()).readUTF();
//                new DataOutputStream(socket.getOutputStream()).writeUTF("hi");

                System.out.println(RESET+"Waiting for "+ WHITE_BOLD_BRIGHT +"second Player chat tic_2"+RESET+" socket...");
                Socket socket2 = serverSocket.accept();
                System.out.println(WHITE_BOLD_BRIGHT +"second Player chat tic_2 "+RESET+"socket connected in "+new Date());
                String secondName = new DataInputStream(socket2.getInputStream()).readUTF();

//                new DataOutputStream(socket2.getOutputStream()).writeUTF("hi");

                new Thread(new ChatHandle(socket, socket2, firstName)).start();
                new Thread(new ChatHandle(socket2, socket, secondName)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class ChatHandle implements Runnable{
    private Socket socket1;
    private Socket socket2;
    private String second;

    public ChatHandle(Socket socket1, Socket socket2, String second) {
        this.socket1 = socket1;
        this.socket2 = socket2;
        this.second = second;
    }

    @Override
    public void run() {
        try {
            DataInputStream from1 = new DataInputStream(socket1.getInputStream());
            DataOutputStream to1 = new DataOutputStream(socket1.getOutputStream());
            DataOutputStream to2 = new DataOutputStream(socket2.getOutputStream());

            String massage = " ";
            while (true){

                massage = from1.readUTF();

                if (massage.equals(STOP_CHAT_SOCKET)){
                    return;
                }
                to1.writeUTF("You : "+massage);
                to2.writeUTF(second+": "+massage);
                System.out.println(RESET+GREEN_UNDERLINED+"New Message: "+massage + RESET);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
