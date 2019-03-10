package ConnectFour.Logic;

public class Board {
    public static final int COLUMNS = 7;
    public static final int ROWS = 6;

    private static Disc[][] discs;

    public Board() {
        discs = new Disc[COLUMNS][ROWS];
    }
}
