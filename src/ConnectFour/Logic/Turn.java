package ConnectFour.Logic;

/*
 * Represents
 */
public class Turn {
    private boolean isPlayerMove;

    public Turn() {
        isPlayerMove = true;
    }

    public Turn(boolean turn) {
        isPlayerMove = turn;
    }

    public boolean isPlayerTurn() {
        return isPlayerMove;
    }

    public void changeTurn() {
        isPlayerMove = !isPlayerMove;
    }
}
