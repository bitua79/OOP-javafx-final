package Game.Games.TicTacToe_2;
import Main.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Constants.Notifications.*;
import static Constants.Paths.*;
import static Constants.Ports.GAME_RESULT;
import static Constants.Ports.HOST;
import static Game.Games.TicTacToe_2.Tic_2Con.stopChat;
import static Main.Main.*;

public class Tic_Tac_Toe_Game_2 extends HBox {

    private boolean waiting = true;
    private boolean isTurn = false;
    private boolean continuePlay = true;
    private boolean player1;

    private char myChar = ' ';
    private char enemyChar = ' ';

    private int rowIndex;
    private int columnIndex;

    private DataInputStream input;
    private DataOutputStream output;

    private Label text;
    private String friendName;
    private String player1Name;

    private DataOutputStream to;

    public Tic_Tac_Toe_Game_2(Socket socket, Label text, String player1Name, String friendName) {
        this.text  = text;
        this.player1Name = player1Name;
        this.friendName = friendName;

        Board board = new Board(event -> {
            Cell cell = (Cell) event.getSource();
            if (cell.getShoot() ==' ' && isTurn){
                waiting = false;
                isTurn = false;
                columnIndex = cell.getColumn();
                rowIndex = cell.getRow();
                cell.setShoot(myChar);
            }
        });

        getChildren().add(board);

        try {
            playGame(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playGame(Socket socket) throws IOException {
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        new Thread(()->{
            try {
                //read a boolean to mention he/she is X/O player
                player1 = input.readBoolean();

                //handle first/second player join and characters
                if (player1){
                    myChar = 'X';
                    enemyChar = 'O';
                    Platform.runLater(()->{
                        text.setText("You joined the game!You are player 1(X) ... Wait for second player to join...");
                    });

                    output.writeUTF(player1Name);
                    output.writeUTF(friendName);

                    //player2 answer to player1 request
                    int report = input.readInt();
                    if (report == REJECT) {
                        Platform.runLater(() -> {
                            text.setText("Your friend can't play with you now!");
                        });
                        return;
                    }else {
                        Platform.runLater(()->{
                            text.setText("player2 joined the game!Now you should play your turn");

                            Socket resultSocket = null;
                            try {
                                resultSocket = new Socket(HOST, GAME_RESULT);
                                to = new DataOutputStream(resultSocket.getOutputStream());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                        isTurn = true;
                    }
                }else {
                    myChar = 'O';
                    enemyChar = 'X';
                    Platform.runLater(()->{
                        text.setText("You joined the game!You are player 2(O) ... Wait for first player to play...");
                    });

                    Socket resultSocket = null;
                    try {
                        resultSocket = new Socket(HOST, GAME_RESULT);
                        to = new DataOutputStream(resultSocket.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                while (continuePlay){
                    //wait for person to choose cell -> send action
                    //snd move to another player -> receive another player action
                    if (player1){
                        waitingFor();
                        sendMove();
                        receiveAction();
                    }else {
                        //receive first player action -> wait for player
                        //to choose cell -> send action and move to another player
                        receiveAction();
                        waitingFor();
                        sendMove();
                    }
                }
            }catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void waitingFor() throws InterruptedException {
        if (!continuePlay)return;
        Platform.runLater(() -> {
            text.setText("This is your turn ( "+myChar+" )");
        });
        while (waiting){
            Thread.sleep(100);
        }
        Platform.runLater(() -> {
            text.setText("This is your friend turn");
        });
        waiting = true;
    }

    public void sendMove() throws IOException {
        output.writeInt(rowIndex);
        output.writeInt(columnIndex);
    }

    //receive a number which mention game situation
    public void receiveAction() throws IOException {
        int result = input.readInt();

        //if 0 -> continue the game and change the turn
        if (result==CONTINUE){
            receiveMove();
            isTurn = true;
            return;
        }

        //Add result to data
        else {
            try {
                stopChat();
                to.writeInt(ADD_RESULT);
                to.writeUTF("tic-tac-toe2");
                to.writeUTF(USERNAME);

                //win amount is when 1st player wins
                // and such it about lose
                //so for 2nd player win means lose and lose mean win
                if (result == DRAW) {
                    to.writeInt(DRAW);

                }else if (myChar == 'X'){
                    to.writeInt(result);

                }else if (myChar == 'O'){
                    if (result == LOSEPlayer1)
                        to.writeInt(WINPlayer1);

                    else if (result == WINPlayer1)
                        to.writeInt(LOSEPlayer1);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //stop game and mention winner
        continuePlay = false;

        //1 -> X win
        if (result == WINPlayer1) {
            if (myChar == 'X') {
                Platform.runLater(() -> {
                    text.setText("I won (X) Click anywhere to exit!");
                    mainScene.setOnMouseClicked(event -> {
                        stopChat();
                        openFxml(MenuFXMLPATH, WinsFXMLPATH);
                        mainScene.setOnMouseClicked(null);
                    });
                });
            } else if (myChar == 'O') {
                Platform.runLater(() -> {
                    text.setText("player2 won (X) Click anywhere to exit!");
                    mainScene.setOnMouseClicked(event -> {
                        stopChat();
                        openFxml(MenuFXMLPATH, LoseFXMLPATH);
                        mainScene.setOnMouseClicked(null);
                    });
                });

                receiveMove();
            }
        }

        //2 ->  Y win
        else if (result == LOSEPlayer1){
            if (myChar == 'O'){
                Platform.runLater(()->{
                    text.setText("I won (O) Click anywhere to exit!");
                    mainScene.setOnMouseClicked(event -> {
                        stopChat();
                        openFxml(MenuFXMLPATH, WinsFXMLPATH);
                        mainScene.setOnMouseClicked(null);
                    });
                });

            }else if (myChar == 'X'){
                Platform.runLater(()->{
                    text.setText("player2 won (O) Click anywhere to exit!");
                    mainScene.setOnMouseClicked(event -> {
                        stopChat();
                        openFxml(MenuFXMLPATH, LoseFXMLPATH);
                        mainScene.setOnMouseClicked(null);
                    });
                });

                receiveMove();
            }
        }

        //3 -> it is a draw
        else if(result == DRAW){
            Platform.runLater(()->{
                text.setText("game has been draw! Click anywhere to exit!");
                Main.mainScene.setOnMouseClicked(event -> {
                    stopChat();
                    openFxml(MenuFXMLPATH, DrawFXMLPATH);
                    mainScene.setOnMouseClicked(null);
                });
            });

            //because only player1 can move in last turn in a draw game(turn counts is odd)
            if (myChar == 'O') {
                receiveMove();
            }
        }
    }

    //set another player action to this player board too
    public void receiveMove() throws IOException {
        rowIndex = input.readInt();
        columnIndex = input.readInt();
        Platform.runLater(()->{
            Board.cell[rowIndex][columnIndex].setShoot(enemyChar);
        });
    }
}
