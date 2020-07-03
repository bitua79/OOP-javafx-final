package Game.Games.Common;

import Main.Main;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class GamesCommon {

    public static TranslateTransition createTransition(Button button, double xPercent, double yPercent){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), button);
        transition.setByX(Main.stage.getWidth() * xPercent / 100.00);
        transition.setByY(Main.stage.getHeight() * yPercent / 100.00);

        return transition;
    }

    public static boolean optionIsComposed = true;
    public static void compose(TranslateTransition... transitions){
        for (TranslateTransition transition : transitions) {
            transition.setByX(-transition.getByX());
            transition.setByY(-transition.getByY());

            transition.play();
        }
        optionIsComposed = !optionIsComposed;
    }

}
