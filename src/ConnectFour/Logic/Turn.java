package ConnectFour.Logic;

public class Turn {
    private static boolean isPlayerMove = true;

    public Turn() {

    }

    public static boolean isPlayerTurn() {
        return isPlayerMove;
    }

    public static void changeToAITurn() {
        isPlayerMove = false;
    }
}
