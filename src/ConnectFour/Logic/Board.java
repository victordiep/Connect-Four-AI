package ConnectFour.Logic;

import java.util.Optional;

public class Board {
    public Turn turn;

    public static final int COLUMNS = 7;
    public static final int ROWS = 6;

    protected static final int MAX_INDEX_COLUMN = COLUMNS-1;
    protected static final int MAX_INDEX_ROW = ROWS-1;

    protected static Disc[][] discs;

    public Board() {
        discs = new Disc[COLUMNS][ROWS];
    }

    private static Optional<Disc> getDisc(final int column, final int row) {
        if (column < 0 || column >= COLUMNS
                || row < 0 || row >= ROWS) {
            return Optional.empty();
        }

        return Optional.ofNullable(discs[column][row]);
    }

    public static Point placeDisc(final Disc disc, final int column) {
        int row = MAX_INDEX_ROW;

        // Look for a row with an empty spot in the column
        do {
            Disc discOnBoard = getDisc(column, row).orElse(null);
            if (discOnBoard == null) {
                break;
            }
            row--;
        } while (row >= 0);

        // We broke out of the loop, therefore we found a space to place the disc
        if (row >= 0) {
            discs[column][row] = disc;
        }

        return new Point(column, row);
    }

    // For the AI that can remove
    public static void removeBottomDisc(final int column) {
        Disc bottomDisc = getDisc(column, ROWS-1).orElse(null);

        // Shift all the discs
        if (bottomDisc != null) {
            for (int row = 0; row < MAX_INDEX_ROW-1; row++) {
                Disc nextDisc = getDisc(column, row+1).orElse(null);

                if (nextDisc == null) {
                    // Set the top disc to null since we shifted it down
                    discs[column][row] = null;
                    break;
                }

                discs[column][row] = nextDisc;
            }
        }
    }
}
