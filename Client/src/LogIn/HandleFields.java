package LogIn;

import animatefx.animation.Tada;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static Constants.Paths.Words;

public interface HandleFields {

    //To Get Sure That None Of RetrievalPass page fields Is Empty
    @FXML
    public static void HandleEnterPass(TextField nameField, TextField answerField, Button enter, Label reportLbl) {
        boolean isEmpty = nameField.getText().isEmpty() || nameField.getText().trim().isEmpty() ||
                answerField.getText().isEmpty() || answerField.getText().trim().isEmpty() ||
                !reportLbl.getText().isEmpty();
        enter.setDisable(isEmpty);
    }

    //To Get Sure That None Of sign up page fields Is Empty
    @FXML
    public static void HandleEnterSignUP(TextField nameField, TextField passField, TextField emailField, TextField answerField, Button enter, Label reportLbl) {
        boolean isEmpty = nameField.getText().isEmpty() || nameField.getText().trim().isEmpty() ||
                passField.getText().isEmpty() || passField.getText().trim().isEmpty() ||
                emailField.getText().isEmpty() || emailField.getText().trim().isEmpty() ||
                answerField.getText().isEmpty() || answerField.getText().trim().isEmpty() ||
                !reportLbl.getText().isEmpty();
        enter.setDisable(isEmpty);
    }

    //To Get Sure That None Of nameFieldUp And passFieldUp Is Empty
    @FXML
    public static void HandleEnterSignIn(TextField nameField, TextField passField, Button enter, Label reportLbl) {
        boolean isEmpty = nameField.getText().isEmpty() || nameField.getText().trim().isEmpty() ||
                passField.getText().isEmpty() || passField.getText().trim().isEmpty() ||
                !reportLbl.getText().isEmpty();
        enter.setDisable(isEmpty);
    }

    public static void HandleAnswer(TextField answerField, Label reportLbl, Button enter){
        new Thread(() -> {
            if (isWord(answerField.getText().trim())){
                Platform.runLater(() -> {
                    reportLbl.setText("your answer has no meaning!");
                    enter.setDisable(true);
                    answerField.setId("Wrong-text-field");

                    Tada tada = new Tada(answerField);
                    tada.setSpeed(1.5);
                    tada.play();
                });

            }else {
                answerField.setId("");
                Platform.runLater(() -> reportLbl.setText(""));
            }
        }).start();
    }

    public static void HandlePass(TextField passField, Label reportLbl, Button enter){
        if (passField.getText().length() < 8 && passField.getText().length() > 0){
            reportLbl.setText("Password should be at least 8 characters!");
            passField.setId("Wrong-text-field");
            enter.setDisable(true);

            Tada tada = new Tada(passField);
            tada.setSpeed(1.5);
            tada.play();

        }else {
            passField.setId("");
            Platform.runLater(() -> reportLbl.setText(""));
        }
    }

    public static void HandleEmail(TextField emailField, Label reportLbl, Button enter){
        if ((!emailField.getText().contains("@") || !emailField.getText().contains(".com")) && !emailField.getText().isEmpty()) {
            reportLbl.setText("email doesn't exist!");
            emailField.setId("Wrong-text-field");
            enter.setDisable(true);

            Tada tada = new Tada(emailField);
            tada.setSpeed(1.5);
            tada.play();
        }else {
            emailField.setId("");
            Platform.runLater(() -> reportLbl.setText(""));
        }
    }

    public static boolean isWord(String string){
        try {
            FileReader fileReader = new FileReader(Words);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()){
                String word = scanner.nextLine();
                if (string.toLowerCase().equals(word))return false;
            }

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }
}

