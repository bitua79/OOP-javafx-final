package Constants;

import javafx.scene.image.Image;

public interface Paths {

    public static final String PHOTO = "file:" + System.getProperty("user.dir")+ "\\src\\Resources\\Photos\\";
    public static String MEDIA =System.getProperty("user.dir")+"\\src\\Resources\\Media\\";

    public static final Image CURSOR_GAME = new Image(PHOTO +"Game_Cursor.gif");
    public static final Image CURSOR_NORMAL = new Image(PHOTO +"Menu_Cursor.gif");

    public static final String Words = "src/Constants/words.txt";

    public static final String EntranceFXMLPATH = "/Entrance/EntranceFxml.fxml";
    public static final String LoadingFXMLPATH = "/Loading/MainLoad/LoadingFxml.fxml";
    public static final String LoadProgressFXMLPATH = "/Loading/ProgressLoad/LoadProgressFxml.fxml";

    public static final String SignUpFXMLPATH = "/LogIn/SignUp/SignUpFxml.fxml";
    public static final String SignInFXMLPATH = "/LogIn/SignIn/SignInFxml.fxml";
    public static final String RetrievalPassFXMLPATH = "/LogIn/RetrievalPass/RetrievalPassFxml.fxml";

    public static final String MenuFXMLPATH = "/Menu/MenuFxml.fxml";
    public static final String OptionFXMLPATH = "/Options/Main/OptionsFxml.fxml";

    public static final String AcSettingFXMLPATH = "/Options/AccountSetting/AcSeFxml.fxml";
    public static final String ChangeNameFXMLPATH = "/Options/AccountSetting/ChangeName/ChangeNameFxml.fxml";
    public static final String ChangePassFXMLPATH = "/Options/AccountSetting/ChangePass/ChangePassFxml.fxml";
    public static final String DeleteAccFXMLPATH = "/Options/AccountSetting/DeleteAcc/DeleteAccFxml.fxml";
    public static final String LogoutFXMLPATH = "/Options/AccountSetting/LogOut/LogOutFxml.fxml";

    public static final String FriendSettingFXMLPATH = "/Options/Friends/FrSeFxml.fxml";

    public static final String MainStoryFXMLPATH = "/Game/Story/MainStoryFxml.fxml";
    public static final String GameListFXMLPATH = "/Game/GameList/GameListFxml.fxml";
    public static final String GameResultFXMLPATH = "/Game/GameList/GameResult/GameResultFxml.fxml";

    public static final String ChooseModFXMLPATH = "/Game/ChooseMod/ChooseModFxml.fxml";
    public static final String ChooseFrFXMLPATH = "/Game/Games/Common/ChooseFrFxml.fxml";
    public static final String RequestFXMLPATH = "/Game/Games/Common/RequestFxml.fxml";

    public static final String WinsFXMLPATH = "/Game/Games/Common/EndGame/WinFxml.fxml";
    public static final String LoseFXMLPATH = "/Game/Games/Common/EndGame/LoseFxml.fxml";
    public static final String DrawFXMLPATH = "/Game/Games/Common/EndGame/DrawFxml.fxml";

    public static final String Tic_1displayFXMLPATH = "/Game/Games/TicTacToe_1/Tic_1Fxml.fxml";
    public static final String Tic_2displayFXMLPATH = "/Game/Games/TicTacToe_2/Tic_2Fxml.fxml";

    public static final String Battle_1displayFXMLPATH = "/Game/Games/BattleShip_1/Battle_1Fxml.fxml";
    public static final String Snake_1displayFXMLPATH = "/Game/Games/SnakeLadder_1/Snake_1Fxml.fxml";



}
