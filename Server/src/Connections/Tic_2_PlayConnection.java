package Connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static Constants.Notifications.*;
import static Main.Color.*;
import static Main.Server.onlineUsers;

public class Tic_2_PlayConnection implements Runnable{

    ServerSocket serverSocket;
    public Tic_2_PlayConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(RESET+"Waiting for "+ YELLOW_BOLD_BRIGHT +"first Player play tic_2"+RESET+" socket...");
                Socket socket = serverSocket.accept();
                System.out.println(YELLOW_BOLD_BRIGHT +"first Player play tic_2 "+RESET+"socket connected in "+new Date());

                DataInputStream from = new DataInputStream(socket.getInputStream());
                DataOutputStream to = new DataOutputStream(socket.getOutputStream());

                to.writeBoolean(true);
                String username = from.readUTF();
                String friendName = from.readUTF();

                sendReqToSecondPlayer(username, friendName, to, socket);
                Socket friendConSocket = (Socket) onlineUsers.get(friendName);
                DataOutputStream to2 = new DataOutputStream(friendConSocket.getOutputStream());
                DataInputStream from2 = new DataInputStream(friendConSocket.getInputStream());

                //send username to his/her friend and ask for play with him/her
                to2.writeInt(PLAY_TIC_TAC_TOE_2_REQ);
                to2.writeUTF(username);

                getResponseFromSecondPlayer(to, from2, socket);

                int result = from2.readInt();
                if (result == BUSY_IS_PLAYING){
                    to.writeInt(BUSY_IS_PLAYING);
                }else if (result == REJECT){
                    to.writeInt(REJECT);
                }else {
                    System.out.println(RESET+"Waiting for "+ YELLOW_BOLD_BRIGHT +"second Player play tic_2"+RESET+" socket...");
                    Socket friendSocket = serverSocket.accept();
                    System.out.println(YELLOW_BOLD_BRIGHT +"second Player play tic_2 "+RESET+"socket connected in "+new Date());

                    new DataOutputStream(friendSocket.getOutputStream()).writeBoolean(false);
                    new Thread(new PlayTic(socket, friendSocket)).start();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendReqToSecondPlayer(String username, String friendName, DataOutputStream to, Socket socket){
        try {
            Socket friendConSocket = (Socket) onlineUsers.get(friendName);
            DataOutputStream to2 = new DataOutputStream(friendConSocket.getOutputStream());
            DataInputStream from2 = new DataInputStream(friendConSocket.getInputStream());

            //send username to his/her friend and ask for play with him/her
            to2.writeInt(PLAY_TIC_TAC_TOE_2_REQ);
            to2.writeUTF(username);

            getResponseFromSecondPlayer(to, from2, socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getResponseFromSecondPlayer(DataOutputStream to, DataInputStream from2, Socket socket){
        try {
            int result = from2.readInt();
            if (result == BUSY_IS_PLAYING){
                to.writeInt(BUSY_IS_PLAYING);
            }else if (result == REJECT){
                to.writeInt(REJECT);
            }else {
                System.out.println(RESET+"Waiting for "+ YELLOW_BOLD_BRIGHT +"second Player play tic_2"+RESET+" socket...");
                Socket friendSocket = serverSocket.accept();
                System.out.println(YELLOW_BOLD_BRIGHT +"second Player play tic_2 "+RESET+"socket connected in "+new Date());

                new DataOutputStream(friendSocket.getOutputStream()).writeBoolean(false);
                new Thread(new PlayTic(socket, friendSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class PlayTic implements Runnable{
    public static char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    private Socket player1;
    private Socket player2;

    public PlayTic(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        try {
            board = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
            DataInputStream inputStreamPlayer1 = new DataInputStream(player1.getInputStream());
            DataOutputStream outputStreamPlayer1 = new DataOutputStream(player1.getOutputStream());
            DataInputStream inputStreamPlayer2 = new DataInputStream(player2.getInputStream());
            DataOutputStream outputStreamPlayer2 = new DataOutputStream(player2.getOutputStream());

            //player2 accepted and joined
            outputStreamPlayer1.writeInt(ACCEPT);

            while (true) {

                int row = inputStreamPlayer1.readInt();
                int column = inputStreamPlayer1.readInt();
                board[row][column] = 'X';

                // Check if Player 1 wins
                if (checkPlayGround()== WINPlayer1) {
                    outputStreamPlayer1.writeInt(WINPlayer1);
                    outputStreamPlayer2.writeInt(WINPlayer1);
                    sendMove(outputStreamPlayer2, row, column);
                    break;
                }
                else if (checkPlayGround()==DRAW) {
                    // Check if all cells are filled
                    outputStreamPlayer1.writeInt(DRAW);
                    outputStreamPlayer2.writeInt(DRAW);
                    sendMove(outputStreamPlayer2, row, column);
                    break;
                }
                else if (checkPlayGround()==CONTINUE){
                    outputStreamPlayer2.writeInt(CONTINUE);
                    sendMove(outputStreamPlayer2, row, column);
                }

                // Receive a move from Player 2
                row = inputStreamPlayer2.readInt();
                column = inputStreamPlayer2.readInt();
                board[row][column] = 'O';

                // Check if Player 2 wins
                if (checkPlayGround()== LOSEPlayer1) {
                    outputStreamPlayer1.writeInt(LOSEPlayer1);
                    outputStreamPlayer2.writeInt(LOSEPlayer1);
                    sendMove(outputStreamPlayer1, row, column);
                    break;
                }
                else if (checkPlayGround()==CONTINUE){
                    outputStreamPlayer1.writeInt(CONTINUE);
                    sendMove(outputStreamPlayer1, row, column);
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMove(DataOutputStream output, int row, int column) throws IOException {
        output.writeInt(row);
        output.writeInt(column);
    }

    public static int checkPlayGround() {
        // returns 0 if game is not finished
        // returns 1 if X wins
        // returns 2 if O wins
        // returns 3 if game has been draw
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == 'X' && board[i][1] == 'X' && board[i][2] == 'X') return WINPlayer1;
            if (board[i][0] == 'O' && board[i][1] == 'O' && board[i][2] == 'O') return LOSEPlayer1;
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == 'X' && board[1][i] == 'X' && board[2][i] == 'X') return WINPlayer1;
            if (board[0][i] == 'O' && board[1][i] == 'O' && board[2][i] == 'O') return LOSEPlayer1;
        }
        if (board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X') return WINPlayer1;
        if (board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] == 'X') return WINPlayer1;

        if (board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O') return LOSEPlayer1;
        if (board[0][2] == 'O' && board[1][1] == 'O' && board[2][0] == 'O') return LOSEPlayer1;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') return CONTINUE;

        return DRAW;
    }

}
