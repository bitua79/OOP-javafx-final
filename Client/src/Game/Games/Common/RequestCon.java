package Game.Games.Common;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static Constants.MediaPlayers.*;
import static Constants.Responsive.*;
import static Constants.Responsive.ResponsiveLBL;
import static Main.Main.*;

public class RequestCon {

    private String friendName;
    private Runnable yes;
    private Runnable no;

    public RequestCon(String friendName, Runnable yes, Runnable no) {
        this.friendName = friendName;
        this.yes = yes;
        this.no = no;
    }

    @FXML private AnchorPane anchorPane;
    @FXML private ImageView backImg;
    @FXML private ImageView imageView;

    @FXML private Label questionLbl;
    @FXML private ImageView yesIMG;
    @FXML private Label yesLbl;
    @FXML private ImageView noIMG;
    @FXML private Label noLbl;

    public void initialize() {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveIMG(backImg, 73, 62, 17, 17.5);
        ResponsiveIMG(imageView, 73, 62, 17, 17.5);

        ResponsiveLBL(questionLbl, 10, 20, 26, 53, 70);
        questionLbl.setText(questionLbl.getText()+ friendName+" ?");

        ResponsiveIMG(yesIMG, 19,  12, 54, 50);
        yesIMG.setOnMouseClicked(event -> {
            PlayEffect(BUTTON_EFFECT);
            yes.run();
        });
        ResponsiveLBL(yesLbl, 1, 20, 59, 54, 60);
        yesLbl.setOnMouseClicked(event -> {
            PlayEffect(BUTTON_EFFECT);
            yes.run();
        });

        ResponsiveIMG(noIMG, 19, 12, 54, 61);
        noIMG.setOnMouseClicked(event -> {
            PlayEffect(BUTTON_EFFECT);
            no.run();
        });
        ResponsiveLBL(noLbl, 1, 20, 59, 65, 60);
        noLbl.setOnMouseClicked(event -> {
            PlayEffect(BUTTON_EFFECT);
            no.run();
        });

        InnerShadow(yesLbl, noLbl, questionLbl);
    }

}
