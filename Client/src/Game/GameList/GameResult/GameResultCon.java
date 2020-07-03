package Game.GameList.GameResult;

import Main.Main;
import MovingTextBtn.MovingTxtBtn;
import animatefx.animation.ZoomOut;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Notifications.*;
import static Constants.Ports.GAME_RESULT;
import static Constants.Ports.HOST;
import static Constants.Responsive.*;
import static Main.Main.DropShadow;
import static Main.Main.InnerShadow;

public class GameResultCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private ImageView imageView;
    @FXML private Rectangle rectangle;

    @FXML private Button back;
    private MovingTxtBtn ticBtn = new MovingTxtBtn("TicTacToe", 500, 6);
    private MovingTxtBtn battleBtn = new MovingTxtBtn("Battle Ship", 500, 6);;
    private MovingTxtBtn snakeBtn = new MovingTxtBtn("Snake & Ladder", 500, 10);;

    @FXML private AnchorPane ticPane;
    @FXML private Label ticLbl;

    @FXML private Label tic1Lbl;
    @FXML private Label win1ticLbl;
    @FXML private ProgressIndicator win1ticBar;
    @FXML private Label lose1ticLbl;
    @FXML private ProgressIndicator lose1ticBar;
    @FXML private Label draw1ticLbl;
    @FXML private ProgressIndicator draw1ticBar;

    @FXML private Label tic2Lbl;
    @FXML private Label win2ticLbl;
    @FXML private ProgressIndicator win2ticBar;
    @FXML private Label lose2ticLbl;
    @FXML private ProgressIndicator lose2ticBar;
    @FXML private Label draw2ticLbl;
    @FXML private ProgressIndicator draw2ticBar;

    @FXML private AnchorPane battlePane;
    @FXML private Label battleLbl;

    @FXML private Label battle1Lbl;
    @FXML private Label win1battleLbl;
    @FXML private ProgressIndicator win1battleBar;
    @FXML private Label lose1battleLbl;
    @FXML private ProgressIndicator lose1battleBar;

    @FXML private Label battle2Lbl;
    @FXML private Label win2battleLbl;
    @FXML private ProgressIndicator win2battleBar;
    @FXML private Label lose2battleLbl;
    @FXML private ProgressIndicator lose2battleBar;

    @FXML private AnchorPane snakePane;
    @FXML private Label snakeLbl;

    @FXML private Label snake1Lbl;
    @FXML private Label win1snakeLbl;
    @FXML private ProgressIndicator win1snakeBar;
    @FXML private Label lose1snakeLbl;
    @FXML private ProgressIndicator lose1snakeBar;

    @FXML private Label snake2Lbl;
    @FXML private Label win2snakeLbl;
    @FXML private ProgressIndicator win2snakeBar;
    @FXML private Label lose2snakeLbl;
    @FXML private ProgressIndicator lose2snakeBar;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveIMG(imageView, 97, 88.3, 1, 27);
        rectangle.setHeight(2000);
        rectangle.setWidth(2000);

        ResponsivePane(ticPane, 60, 16, 17, 37);
        ResponsivePane(battlePane, 60, 26, 17, 37);
        ResponsivePane(snakePane, 60, 26, 17, 37);
        ResponsiveBtn(back, 1, 5.5, 17, 37, 30);

        initTicTacToe();
        initBattleShip();
        initSnake();

        anchorPane.getChildren().addAll(ticBtn, battleBtn, snakeBtn);
        ResponsiveBtn(ticBtn, 5, 7, 79, 37, 35);
        ticBtn.moveText();
        ticBtn.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            ticPane.setVisible(true);
            battlePane.setVisible(false);
            snakePane.setVisible(false);
        });

        ResponsiveBtn(battleBtn, 5, 7, 79, 45, 35);
        battleBtn.moveText();
        battleBtn.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            battlePane.setVisible(true);
            ticPane.setVisible(false);
            snakePane.setVisible(false);
        });

        ResponsiveBtn(snakeBtn, 5, 10, 79, 53, 35);
        snakeBtn.moveText();
        snakeBtn.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            snakePane.setVisible(true);
            ticPane.setVisible(false);
            battlePane.setVisible(false);
        });

        InnerShadow(ticLbl, tic1Lbl, tic2Lbl, ticBtn, win1ticLbl, win2ticLbl, lose1ticLbl, lose2ticLbl, draw1ticLbl, draw2ticLbl,
                battleLbl, battle1Lbl, battle2Lbl, battleBtn, win1battleLbl, win2battleLbl, lose1battleLbl, lose2battleLbl,
                snakeLbl, snake1Lbl, snake2Lbl, snakeBtn, win1snakeLbl, win2snakeLbl, lose1snakeLbl, lose2snakeLbl, back);

        InnerShadow(win1ticBar, win2ticBar, lose1ticBar, lose2ticBar, draw1ticBar, draw2ticBar,
                win1battleBar, win2battleBar, lose1battleBar, lose2battleBar,
                win1snakeBar, win2snakeBar, lose1snakeBar, lose2snakeBar);
    }

    public void initTicTacToe(){

        //Make Contacts Responsive
        ResponsiveLBL(ticLbl, 1, 30, -1, -1, 70);
        ResponsiveLBL(tic1Lbl, 1, 30, 9, -2, 40);
        ResponsiveLBL(win1ticLbl, 1, 10, 27, -1, 40);
        ResponsivePRI(win1ticBar, 10, 10, 15, -1, 30);

        ResponsiveLBL(lose1ticLbl, 1, 10, 27, 8, 40);
        ResponsivePRI(lose1ticBar, 10, 10, 15, 8, 30);

        ResponsiveLBL(draw1ticLbl, 1, 10, 27, 17, 40);
        ResponsivePRI(draw1ticBar, 10, 10, 15, 17, 30);

        ResponsiveLBL(tic2Lbl, 1, 30, 35, -2, 40);
        ResponsiveLBL(win2ticLbl, 1, 10, 52, -1, 40);
        ResponsivePRI(win2ticBar, 10, 10, 40, -1, 30);

        ResponsiveLBL(lose2ticLbl, 1, 10, 52, 8, 40);
        ResponsivePRI(lose2ticBar, 10, 10, 40, 8, 30);

        ResponsiveLBL(draw2ticLbl, 1, 10, 52, 17, 40);
        ResponsivePRI(draw2ticBar, 10, 10, 40, 17, 30);

        getResultTic();
    }

    public void getResultTic(){
        try {
            Socket socket = new Socket(HOST, GAME_RESULT);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(SHOW_RESULT);
            to.writeInt(TIC_TAC_TOE_RES);

            to.writeUTF(Main.USERNAME);
            int all1 = from.readInt();
            tic1Lbl.setText("1 player          all games : "+all1);

            int win1 = from.readInt();
            win1ticBar.setProgress(percent(win1, all1));

            int lose1 = from.readInt();
            lose1ticBar.setProgress(percent(lose1, all1));

            int draw1 = from.readInt();
            draw1ticBar.setProgress(percent(draw1, all1));

            int all2 = from.readInt();
            tic2Lbl.setText("2 player          all games : "+all2);

            int win2 = from.readInt();
            win2ticBar.setProgress(percent(win2, all2));

            int lose2 = from.readInt();
            lose2ticBar.setProgress(percent(lose2, all2));

            int draw2 = from.readInt();
            draw2ticBar.setProgress(percent(draw2, all2));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initBattleShip(){

        //Make Contacts Responsive
        ResponsiveLBL(battleLbl, 1, 30, -1, -1, 70);
        ResponsiveLBL(battle1Lbl, 1, 30, 9, -2, 40);
        ResponsiveLBL(win1battleLbl, 1, 10, 27, 3, 40);
        ResponsivePRI(win1battleBar, 10, 10, 15, 3, 30);

        ResponsiveLBL(lose1battleLbl, 1, 10, 27, 13, 40);
        ResponsivePRI(lose1battleBar, 10, 10, 15, 13, 30);

        ResponsiveLBL(battle2Lbl, 1, 30, 35, -2, 40);
        ResponsiveLBL(win2battleLbl, 1, 10, 52, 3, 40);
        ResponsivePRI(win2battleBar, 10, 10, 40, 3, 30);

        ResponsiveLBL(lose2battleLbl, 1, 10, 52, 13, 40);
        ResponsivePRI(lose2battleBar, 10, 10, 40, 13, 30);

        getResultBattle();
    }

    public void getResultBattle(){
        try {
            Socket socket = new Socket(HOST, GAME_RESULT);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(SHOW_RESULT);
            to.writeInt(BATTLE_SHIP_RES);

            to.writeUTF(Main.USERNAME);
            int all1 = from.readInt();
            battle1Lbl.setText("1 player          all games : "+all1);

            int win1 = from.readInt();
            win1battleBar.setProgress(percent(win1, all1));

            int lose1 = from.readInt();
            lose1battleBar.setProgress(percent(lose1, all1));


            int all2 = from.readInt();
            battle2Lbl.setText("2 player          all games : "+all2);

            int win2 = from.readInt();
            win2battleBar.setProgress(percent(win2, all2));

            int lose2 = from.readInt();
            win2battleBar.setProgress(percent(lose2, all2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initSnake(){

        //Make Contacts Responsive
        ResponsiveLBL(snakeLbl, 1, 30, -1, -1, 70);
        ResponsiveLBL(snake1Lbl, 1, 30, 9, -2, 40);
        ResponsiveLBL(win1snakeLbl, 1, 10, 27, 3, 40);
        ResponsivePRI(win1snakeBar, 10, 10, 15, 3, 30);

        ResponsiveLBL(lose1snakeLbl, 1, 10, 27, 13, 40);
        ResponsivePRI(lose1snakeBar, 10, 10, 15, 13, 30);

        ResponsiveLBL(snake2Lbl, 1, 30, 35, -2, 40);
        ResponsiveLBL(win2snakeLbl, 1, 10, 52, 3, 40);
        ResponsivePRI(win2snakeBar, 10, 10, 40, 3, 30);

        ResponsiveLBL(lose2snakeLbl, 1, 10, 52, 13, 40);
        ResponsivePRI(lose2snakeBar, 10, 10, 40, 13, 30);

        getResultSnake();
    }

    public void getResultSnake(){
        try {
            Socket socket = new Socket(HOST, GAME_RESULT);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(SHOW_RESULT);
            to.writeInt(SNAKE_LADDER_RES);

            to.writeUTF(Main.USERNAME);
            int all1 = from.readInt();
            snake1Lbl.setText("1 player          all games : "+all1);

            int win1 = from.readInt();
            win1snakeBar.setProgress(percent(win1, all1));

            int lose1 = from.readInt();
            lose1snakeBar.setProgress(percent(lose1, all1));


            int all2 = from.readInt();
            snake2Lbl.setText("2 player          all games : "+all2);

            int win2 = from.readInt();
            win2snakeBar.setProgress(percent(win2, all2));

            int lose2 = from.readInt();
            lose2snakeBar.setProgress(percent(lose2, all2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double percent(int partCount, int allCount){
        if (allCount == 0)return 0;
        return partCount * 1.00/ allCount ;
    }

    public void close(){
        PlayEffect(BUTTON_EFFECT);
        ZoomOut zoomOut = new ZoomOut(anchorPane);
        zoomOut.setOnFinished(event -> {
            AnchorPane mainAnchorPane = (AnchorPane) anchorPane.getParent();
            mainAnchorPane.getChildren().remove(anchorPane);
        });
        zoomOut.play();
    }
}
