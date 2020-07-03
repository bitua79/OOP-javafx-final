package Game.Games.BattleShip_1;

import animatefx.animation.BounceIn;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import static Constants.MediaPlayers.*;
import static Constants.Notifications.*;
import static Constants.Paths.LoseFXMLPATH;
import static Constants.Paths.MenuFXMLPATH;
import static Constants.Ports.GAME_RESULT;
import static Constants.Ports.HOST;
import static Main.Main.*;

public class BattleShipGame_1 extends HBox{
    private Goal goal = null;

    public boolean running = false;
    public boolean enemyTurn = false;

    private Label label;
    private int friendsToPlace = 11;
    private DataOutputStream to;

    private Random random = new Random();
    private Board enemyBoard, playerBoard;

    public BattleShipGame_1(Label label) {
        this.label = label;

        try {
            Socket socket = new Socket(HOST, GAME_RESULT);
            to = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        enemyBoard = new Board(true, event -> {

            //after start the game, do this
            if (!running) return;

            Cell cell = (Cell) event.getSource();
            if (cell.isDead()) return;

            enemyTurn = !cell.shoot();

            //loosing enemy and winning you!
            if (enemyBoard.pieceCount == 0) {
                Platform.runLater(() -> {
                    label.setText("You win! Click anywhere to exit!");
                    playerBoard.setDisable(true);
                    enemyBoard.setDisable(true);
                    mainScene.setOnMouseClicked(e -> {
                    try {
                        to.writeInt(ADD_RESULT);
                        to.writeUTF("battle-ship1");
                        to.writeUTF(USERNAME);
                        to.writeInt(WINPlayer1);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    openFxml(MenuFXMLPATH, WinsFXMLPATH);
                    mainScene.setOnMouseClicked(null);
                    });
                });
            }

            if (enemyTurn)
                enemyMove();
        });
        playerBoard = new Board(false, event -> {

            //before start the game, do this
            if (running)
                return;

            label.setText("Arrange your pieces!");
            Cell cell = (Cell) event.getSource();
            if (playerBoard.placePiece(new Piece(friendsToPlace, event.getButton().equals(MouseButton.PRIMARY), false), cell.getBlocX(), cell.getBlocY())) {
                PlayEffect(ARRANGE_EFFECT);
                if (--friendsToPlace == 0) {
                    startGame();
                }
            }
        });
        this.getChildren().addAll(enemyBoard, playerBoard);
        this.setSpacing(30);
    }

    private void enemyMove() {
        enemyBoard.setDisable(true);
        label.setText("Wait for your Rival...");
        Timeline wait = new Timeline(new KeyFrame(Duration.millis(1000)));
        wait.setOnFinished(event -> {
            if (playerBoard.pieceCount == 0) {
                enemyTurn = false;
                return;
            }

            while (enemyTurn) {
                int x;
                int y;
                if (goal!=null){
                    if (goal.getRight()!=null && !goal.getRight().isDead()){
                        x = goal.getRight().getBlocX();
                        y = goal.getRight().getBlocY();

                    }else if (goal.getLeft()!=null && !goal.getLeft().isDead()){
                        x = goal.getLeft().getBlocX();
                        y = goal.getLeft().getBlocY();

                    }else if (goal.getUp()!=null && !goal.getUp().isDead()) {
                        x = goal.getUp().getBlocX();
                        y = goal.getUp().getBlocY();

                    }else if(goal.getDown()!=null && !goal.getDown().isDead()) {
                        x = goal.getDown().getBlocX();
                        y = goal.getDown().getBlocY();

                    }else if(goal.getRight_up()!=null && !goal.getRight_up().isDead() && goal.getRight_up().getPiece()!=null){
                        x = goal.getRight_up().getBlocX();
                        y = goal.getRight_up().getBlocY();

                    }else if(goal.getRight_down()!=null && !goal.getRight_down().isDead() && goal.getRight_down().getPiece()!=null){
                        x = goal.getRight_down().getBlocX();
                        y = goal.getRight_down().getBlocY();

                    }else if(goal.getLeft_up()!=null && !goal.getLeft_up().isDead() && goal.getLeft_up().getPiece()!=null){
                        x = goal.getLeft_up().getBlocX();
                        y = goal.getLeft_up().getBlocY();

                    }else if(goal.getLeft_down()!=null && !goal.getLeft_down().isDead() && goal.getLeft_down().getPiece()!=null){
                        x = goal.getLeft_down().getBlocX();
                        y = goal.getLeft_down().getBlocY();

                    } else {
                        goal = null;
                        x = random.nextInt(10);
                        y = random.nextInt(10);
                    }
                }else {
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                }

                Cell cell = playerBoard.getBloc(x, y);


                if (cell.isDead())
                    continue;

                //in case bloc belongs to a ship, there is another turn
                enemyTurn = cell.shoot();
                if (enemyTurn){
                    goal = new Goal(x, y, playerBoard);
                }

                //losing you and winning enemy
                if (playerBoard.pieceCount == 0) {
                    Platform.runLater(() -> {
                        label.setText("You lose! Click anywhere to exit!");
                        playerBoard.setDisable(true);
                        enemyBoard.setDisable(true);
                        mainScene.setOnMouseClicked(e -> {
                            try {
                                to.writeInt(ADD_RESULT);
                                to.writeUTF("battle-ship1");
                                to.writeUTF(USERNAME);
                                to.writeInt(LOSEPlayer1);

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            openFxml(MenuFXMLPATH, LoseFXMLPATH);
                            mainScene.setOnMouseClicked(null);
                        });
                    });
                    return;
                }
            }
            enemyBoard.setDisable(false);
            label.setText("It's Your turn");
        });
        wait.play();

    }

    //place enemy ships
    private void startGame() {
        int pieceCount = 11;

        while (pieceCount > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placePiece(new Piece(pieceCount, Math.random() < 0.5, true), x, y)) {
                pieceCount--;
            }
        }

        new BounceIn(playerBoard).play();
        label.setText("Wait for your rival to arrange pieces!");
        Timeline placeEnemy = new Timeline(new KeyFrame(new Duration(3000)));
        placeEnemy.setOnFinished((event) -> {
            running = true;
            new BounceIn(enemyBoard).play();
            label.setText("Now Try to defend your rival!");
        });
        placeEnemy.play();
    }

}