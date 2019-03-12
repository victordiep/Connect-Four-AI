package ConnectFour.Logic;

import java.util.Optional;

public class Board {
    protected Turn turn;

    public static final int COLUMNS = 7;
    public static final int ROWS = 6;

    public static final int MAX_INDEX_COLUMN = COLUMNS-1;
    public static final int MAX_INDEX_ROW = ROWS-1;

    private boolean isGameOver;

    protected Disc[][] discs;
    protected Point lastPlacement;
    protected int numberOfMoves;


    public Board() {
        discs = new Disc[COLUMNS][ROWS];
        turn = new Turn();
        isGameOver = false;
        lastPlacement = null;
        numberOfMoves = 0;
    }

    public boolean getTurn() {
        return turn.isPlayerTurn();
    }

    public Point getLastPlacement() {
        return lastPlacement;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Optional<Disc> getDisc(final int column, final int row) {
        if (column < 0 || column >= COLUMNS
                || row < 0 || row >= ROWS) {
            return Optional.empty();
        }

        return Optional.ofNullable(discs[column][row]);
    }

    public int findEmptyRow(final int column) {
        int row = MAX_INDEX_ROW;

        // Look for a row with an empty spot in the column
        do {
            Disc discOnBoard = getDisc(column, row).orElse(null);
            if (discOnBoard == null) {
                break;
            }
            row--;
        } while (row >= 0);

        return row;
    }

    public Point placeDisc(final Disc disc, final int column) {
        int row = findEmptyRow(column);

        // We broke out of the loop, therefore we found a space to place the disc
        if (row >= 0) {
            discs[column][row] = disc;
            numberOfMoves++;
        }

        Point placement = new Point(column, row);
        evaluateGameState(placement);
        lastPlacement = placement;
        turn.changeTurn();
        return placement;
    }

    // For the AI that can remove
    public void removeBottomDisc(final int column) {
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

        turn.changeTurn();
    }

    // Game end logic
    public int evaluateGameState(final Point placement) {
        if (checkWinner()) {
            isGameOver = true;
            // This should never be a null pointer or else it would never get past checkWinner
                // Integer.MAX_VALUE if player 1 won
                // Integer.MIN_VALUE if player 2 won
            return discs[placement.getX()][placement.getY()].isOwnedByPlayer()
                    ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        else if (checkDraw()) {
            isGameOver = true;
            return 0;
        }
        // Game is not over yet
        else {
            return -1;
        }
    }

    private boolean checkDraw() {
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (getDisc(x, y).orElse(null) == null) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean discsNotNull(Disc disc1, Disc disc2, Disc disc3) {
        return disc1 != null && disc2 != null && disc3 != null;
    }

    private boolean checkIfDiscOwnerMatch(boolean player, Disc disc1, Disc disc2, Disc disc3) {
        return player == disc1.isOwnedByPlayer()
                && player == disc2.isOwnedByPlayer()
                && player == disc3.isOwnedByPlayer();
    }

    public int scoreBoardState() {
        int score = 0;

        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                final Disc disc = getDisc(col, row).orElse(null);

                if (disc == null)
                    continue;

                final boolean player = disc.isOwnedByPlayer();

                Disc disc1 = getDisc(col, row+1).orElse(null);
                Disc disc2 = getDisc(col, row+2).orElse(null);
                Disc disc3 = getDisc(col, row+3).orElse(null);

                if (row+3 < ROWS) {
                    if (discsNotNull(disc1, disc2, disc3)) {
                        if (checkIfDiscOwnerMatch(player, disc1, disc2, disc3)) {
                            score += 100;
                        }
                    }
                }
                if (col+3 < COLUMNS) {
                    disc1 = getDisc(col+1, row).orElse(null);
                    disc2 = getDisc(col+2, row).orElse(null);
                    disc3 = getDisc(col+3, row).orElse(null);

                    if (discsNotNull(disc1, disc2, disc3)) {
                        if (checkIfDiscOwnerMatch(player, disc1, disc2, disc3)) {
                            score += 100;
                        }
                    }

                    disc1 = getDisc(col+1, row+1).orElse(null);
                    disc2 = getDisc(col+2, row+2).orElse(null);
                    disc3 = getDisc(col+3, row+3).orElse(null);

                    if (discsNotNull(disc1, disc2, disc3)) {
                        if (checkIfDiscOwnerMatch(player, disc1, disc2, disc3)) {
                            score += 100;
                        }
                    }

                    disc1 = getDisc(col+1, row-1).orElse(null);
                    disc2 = getDisc(col+2, row-2).orElse(null);
                    disc3 = getDisc(col+3, row-3).orElse(null);

                    if (discsNotNull(disc1, disc2, disc3)) {
                        if (checkIfDiscOwnerMatch(player, disc1, disc2, disc3)) {
                            score += 100;
                        }
                    }
                }
            }
        }

        return score;
    }

    private boolean checkWinner() {
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                final Disc disc = getDisc(col, row).orElse(null);

                if (disc == null)
                    continue;

                final boolean player = disc.isOwnedByPlayer();

                Disc disc1 = getDisc(col, row+1).orElse(null);
                Disc disc2 = getDisc(col, row+2).orElse(null);
                Disc disc3 = getDisc(col, row+3).orElse(null);

                if (row+3 < ROWS && discsNotNull(disc1, disc2, disc3)) {
                    if (checkIfDiscOwnerMatch(player, disc1, disc2, disc3)) {
                        return true;
                    }
                }
                if (col+3 < COLUMNS) {
                    disc1 = getDisc(col+1, row).orElse(null);
                    disc2 = getDisc(col+2, row).orElse(null);
                    disc3 = getDisc(col+3, row).orElse(null);

                    if (discsNotNull(disc1, disc2, disc3)) {
                        if (checkIfDiscOwnerMatch(player, disc1, disc2, disc3)) {
                            return true;
                        }
                    }

                    disc1 = getDisc(col+1, row+1).orElse(null);
                    disc2 = getDisc(col+2, row+2).orElse(null);
                    disc3 = getDisc(col+3, row+3).orElse(null);

                    if (discsNotNull(disc1, disc2, disc3)) {
                        if (checkIfDiscOwnerMatch(player, disc1, disc2, disc3)) {
                            return true;
                        }
                    }

                    disc1 = getDisc(col+1, row-1).orElse(null);
                    disc2 = getDisc(col+2, row-2).orElse(null);
                    disc3 = getDisc(col+3, row-3).orElse(null);

                    if (discsNotNull(disc1, disc2, disc3)) {
                        if (checkIfDiscOwnerMatch(player, disc1, disc2, disc3)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
