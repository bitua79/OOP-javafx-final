package Constants;

import javafx.scene.image.Image;

public interface Paths {

    public static final String PHOTO = "file:" + System.getProperty("user.dir")+ "\\src\\Resources\\Photos\\";
    public static String MEDIA =System.getProperty("user.dir")+"\\src\\Resources\\Media\\";

    public static final Image CURSOR_GAME = new Image(PHOTO +"GAMECURSOR.gif");
    public static final Image CURSOR_NORMAL = new Image(PHOTO +"MENUCURSOR.gif");

    public static final String ALL_USERS_SQL = "jdbc:sqlite:src/Users/USERS.db";
    public static final String NEW_USER_SQL = "jdbc:sqlite:src/Users/";
    public static final String NEW_USER = "src/Users/";


}
