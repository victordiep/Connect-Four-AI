package ConnectFour.Logic;

/*
 * A singleton class to keep one instance of the board
 */
public class GlobalBoard {
    private static Board board;

    private GlobalBoard() {

    }

    public static boolean isGameOver() {
        if (board == null) {
            board = new Board();
        }

        return board.isGameOver();
    }

    public static boolean getTurn() {
        if (board == null) {
            board = new Board();
        }

        return board.getTurn();
    }

    public static Disc getDisc(int x, int y) {
        if (board == null) {
            board = new Board();
        }

        return board.getDisc(x, y).orElse(null);
    }

    public static Point placeDisc(Disc disc, int column) {
        if (board == null) {
            board = new Board();
        }

        return board.placeDisc(disc, column);
    }

    public static Point getLastPlacement() {
        if (board == null) {
            board = new Board();
        }

        return board.getLastPlacement();
    }
}
