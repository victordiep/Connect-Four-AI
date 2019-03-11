package ConnectFour.Logic;

/*
 * A singleton class to keep one instance of the board
 */
public class GlobalBoard {
    private static Board board;

    private GlobalBoard() {

    }

    public static Board getInstance() {
        if (board == null) {
            board = new Board();
        }

        return board;
    }

    public static boolean getTurn() {
        if (board == null) {
            board = new Board();
        }

        return board.getTurn();
    }
}
