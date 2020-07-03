package Constants;

public interface Ports {
    public static final String HOST = "localhost";
    public static final int CONNECTION = 8000;

    public static final int LOGIN = 8003;                   //sign in/ sign up/ retrieval password
    public static final int GET_OFFLINE = 8004;
    public static final int FRIEND_SETTING = 8005;          //add/ remove/ show friends
    public static final int ACCOUNT_SETTING = 8006;       //delete account/ change password and username/ log out

    public static final int GAME_RESULT = 8007;

    public static final int PLAY_TIC_TAC_TOE_2 = 8008;
    public static final int CHAT_TIC_TAC_TOE_2 = 8010;

    public static final String STOP_CHAT_SOCKET = "IMBITAKARVIZIANDITELLYOUSTOPCHATING";
}
