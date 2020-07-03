package Constants;

import Main.Main;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static Constants.MediaPlayers.*;
import static Constants.MediaPlayers.BUTTON_EFFECT;
import static Constants.Paths.CURSOR_NORMAL;
import static Main.Main.stage;

public interface Responsive {
    public static void ResponsivePane(AnchorPane anchorPane, double height_percent, double width_percent){
        anchorPane.prefHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        anchorPane.prefWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));
        anchorPane.setCursor(new ImageCursor(CURSOR_NORMAL));
    }

    public static void ResponsivePane(AnchorPane anchorPane, double height_percent, double width_percent, double y_percent, double x_percent){
        anchorPane.prefHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        anchorPane.prefWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));

        anchorPane.setCursor(new ImageCursor(CURSOR_NORMAL));
        ResponsivePos(anchorPane, y_percent, x_percent);
    }

    public static void ResponsivePane(VBox vbox, double height_percent, double width_percent, double y_percent, double x_percent){
        vbox.prefHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        vbox.prefWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));
        vbox.setCursor(new ImageCursor(CURSOR_NORMAL));
        ResponsivePos(vbox, y_percent, x_percent);
    }

    public static void ResponsivePane(HBox hBox, double height_percent, double width_percent, double y_percent, double x_percent){
        hBox.prefHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        hBox.prefWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));
        hBox.setCursor(new ImageCursor(CURSOR_NORMAL));
        ResponsivePos(hBox, y_percent, x_percent);
    }

    public static void ResponsivePane(TabPane tabPane, double height_percent, double width_percent, double y_percent, double x_percent, double maxTabW, double maxTabH){
        tabPane.prefHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        tabPane.prefWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));

        tabPane.setTabMaxHeight(maxTabH);
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {

            for (int i=0; i<tabPane.getTabs().size(); i++){
                tabPane.getTabs().get(i).setStyle("-fx-font: "+fontSizeTab(newValue.doubleValue())+"px Gabriola ;");
            }
            tabPane.setTabMaxWidth(stage.widthProperty().multiply(maxTabW).divide(100.0).doubleValue());

        });

        tabPane.setCursor(new ImageCursor(CURSOR_NORMAL));
        ResponsivePos(tabPane, y_percent, x_percent);
    }

    public static void ResponsivePane(ScrollPane scrollPane, double height_percent, double width_percent, double y_percent, double x_percent){
        scrollPane.prefHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        scrollPane.prefWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));
        scrollPane.setCursor(new ImageCursor(CURSOR_NORMAL));
        ResponsivePos(scrollPane, y_percent, x_percent);
    }

    public static void ResponsivePane(GridPane gridPane, double height_percent, double width_percent, double y_percent, double x_percent){
        gridPane.maxHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        gridPane.maxWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));
        gridPane.setCursor(new ImageCursor(CURSOR_NORMAL));
        ResponsivePos(gridPane, y_percent, x_percent);
    }

    public static void ResponsiveShape(Rectangle rectangle, double percent){
        rectangle.heightProperty().bind(stage.heightProperty().multiply(percent).divide(100.0));
        rectangle.widthProperty().bind(stage.heightProperty().multiply(percent).divide(100.0));
    }

    public static void ResponsiveShape(Rectangle rectangle, double height_percent, double width_percent, double y_percent, double x_percent){
        rectangle.heightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        rectangle.widthProperty().bind(stage.heightProperty().multiply(width_percent).divide(100.0));
        ResponsivePos(rectangle, y_percent, x_percent);
    }

    //there is a relation between height and width in imageView & mediaView
    //so we need to set just one side in it's better to set property in height because stage height is not independent

    public static void ResponsiveMV(MediaView mediaView, double height_percent, double width_percent, double y_percent, double x_percent){
        mediaView.fitHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        mediaView.fitWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));
        ResponsivePos(mediaView, y_percent, x_percent);

    }

    public static void ResponsivePRB(ProgressBar progressBar, double width_sub, double y_percent, double y_sub){
        progressBar.prefWidthProperty().bind(stage.widthProperty().subtract(width_sub));
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            progressBar.setLayoutY((stage.getHeight() * y_percent) - y_sub);
        });

    }

    public static void ResponsiveIMG(ImageView imageView, double height_percent, double y_percent, double x_percent){
        imageView.fitHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));

        ResponsivePos(imageView, y_percent, x_percent);
    }

    public static void ResponsiveIMG(ImageView imageView, double height_percent, double width_percent, double y_percent, double x_percent){
        imageView.fitHeightProperty().bind(stage.heightProperty().multiply(height_percent).divide(100.0));
        imageView.fitWidthProperty().bind(stage.widthProperty().multiply(width_percent).divide(100.0));

        ResponsivePos(imageView, y_percent, x_percent);
    }

    public static void ResponsiveTXF(TextField textField, double height_percent, double width_percent, double y_percent, double x_percent, int init){
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            textField.setFont(Font.font("Gabriola", fontSize(init, newValue.doubleValue())));
            textField.setPrefWidth(stage.getWidth() * width_percent / 100.00);
            textField.setPrefHeight(stage.getHeight() * height_percent / 100.00);

            textField.textProperty().addListener(e -> PlayEffect(TYPE_EFFECT));
        });
        ResponsivePos(textField, y_percent, x_percent);
    }

    public static void ResponsiveChoiceBox(ChoiceBox choiceBox, double height_percent, double width_percent, double y_percent, double x_percent, int init){
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            choiceBox.setStyle("-fx-font: "+fontSize(init, newValue.doubleValue())+"px Gabriola;");
            choiceBox.setPrefWidth(stage.getWidth() * width_percent / 100.00 );
            choiceBox.setPrefHeight(stage.getHeight() * height_percent / 100.00 );

            choiceBox.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
                PlayEffect(BUTTON_EFFECT);
            });
        });
        ResponsivePos(choiceBox, y_percent, x_percent);
    }

    public static void ResponsiveCheckBox(CheckBox checkBox, double height_percent, double width_percent, double y_percent, double x_percent, int init){
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            checkBox.setStyle("-fx-font: "+fontSize(init, newValue.doubleValue())+"px Uechi-Gothic;");
            checkBox.setPrefWidth(stage.getWidth() * width_percent / 100.00 );
            checkBox.setPrefHeight(stage.getHeight() * height_percent / 100.00 );

            checkBox.selectedProperty().addListener((observable1, oldValue1, newValue1) -> {
                PlayEffect(BUTTON_EFFECT);
            });
        });
        ResponsivePos(checkBox, y_percent, x_percent);
    }

    public static void ResponsivePRI(ProgressIndicator progressIndicator, double height_percent, double width_percent, double y_percent, double x_percent, int init){
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            progressIndicator.setStyle("-fx-font: "+fontSize(init, newValue.doubleValue())+"px Uechi-Gothic;" +
                    "    -fx-fill: linear-gradient(to right, wheat, plum);");
            progressIndicator.setPrefWidth(stage.getWidth() * width_percent / 100.00 );
            progressIndicator.setPrefHeight(stage.getHeight() * height_percent / 100.00 );
        });
        ResponsivePos(progressIndicator, y_percent, x_percent);
    }

    public static void ResponsiveSlider(Slider slider, double height_percent, double width_percent, double y_percent, double x_percent){
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            slider.setPrefWidth(stage.getWidth() * width_percent / 100.00 );
            slider.setPrefHeight(stage.getHeight() * height_percent / 100.00 );
        });
        ResponsivePos(slider, y_percent, x_percent);
    }

    public static void ResponsiveLBL(Label label, double height_percent, double width_percent, double y_percent, double x_percent, int init){
        Main.stage.heightProperty().addListener((observable, oldValue, newValue) -> {

            label.setFont(Font.font("Uechi-Gothic", fontSize(init, newValue.doubleValue())));
            label.setPrefWidth(Main.stage.getWidth()* width_percent / 100.00);
            label.setPrefHeight(Main.stage.getWidth()* height_percent / 100.00);
        });
        ResponsivePos(label, y_percent, x_percent);
    }

    public static void ResponsiveBtn(Button button, double height_percent, double width_percent, double y_percent, double x_percent, int init){
        boolean disable = button.isDisable();
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            button.setStyle("-fx-font: "+fontSizeBtn(init, newValue.doubleValue())+"px Uechi-Gothic;");
            button.setPrefWidth(stage.getWidth() * width_percent / 100.00 );
            button.setPrefHeight(stage.getHeight() * height_percent / 100.00 );

        });
        ResponsivePos(button, y_percent, x_percent);
        button.setDisable(disable);
    }

    public static void ResponsiveTxtArea(TextArea textArea, double height_percent, double width_percent, double y_percent, double x_percent, int init){
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setFont(Font.font("Gabriola", fontSize(init, newValue.doubleValue())));
            textArea.setPrefHeight(stage.heightProperty().multiply(height_percent).divide(100.0).doubleValue());
            textArea.setPrefWidth(stage.widthProperty().multiply(width_percent).divide(100.0).doubleValue());
        });
        textArea.textProperty().addListener(e -> PlayEffect(TYPE_EFFECT));
        ResponsivePos(textArea, y_percent, x_percent);
    }

    public static void ResponsivePos(Node node, double y_percent, double x_percent){
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            node.setLayoutX(stage.getWidth() * x_percent / 100.00);
        });
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            node.setLayoutY(stage.getHeight() * y_percent / 100.00);
        });
    }

    public static int fontSize(int init, double value){
        int reducer = init/5 - 1;
        return init - (reducer - (int) value /(1000/reducer)) * 5;
    }

    public static int fontSizeBtn(int init, double value){
        int result = init - (10 - (int) value /100) * 6 + 3;
        if (result <= 3)return 1;
        return result;
    }

    public static int fontSizeTab(double value){
        return  24 - (5-((int) value /200)) * 4;
    }
}
