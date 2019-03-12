package ConnectFour.Logic;

import java.util.Optional;

public class Board {
    protected Turn turn;

    public static final int COLUMNS = 7;
    public static final int ROWS = 6;

    public static final int MAX_INDEX_COLUMN = COLUMNS-1;
    public static final int MAX_INDEX_ROW = ROWS-1;

    private static final int PIECES_IN_ROW_FOR_WIN = 4;

    private boolean isGameOver;

    protected Disc[][] discs;
    protected Point lastPlacement;

    public Board() {
        discs = new Disc[COLUMNS][ROWS];
        turn = new Turn();
        isGameOver = false;
        lastPlacement = null;
    }

    public boolean getTurn() {
        return turn.isPlayerTurn();
    }

    public Point getLastPlacement() {
        return lastPlacement;
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
    public int evaluateGameState(Point placement) {
        if (checkWinner(placement)) {
            // This should never be a null pointer or else it would never get past checkWinner
                // Integer.MAX_VALUE if player 1 won
                // Integer.MIN_VALUE if player 2 won
            isGameOver = true;
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

    private int incrementChainCounter(Disc disc, boolean discOwner) {
        if (disc != null) {
            if (disc.isOwnedByPlayer() == discOwner) {
                return 1;
            }
        }

        return 0;
    }

    // TODO: Fix the logic here
    private boolean checkWinner(Point placement) {
        return false;
    }

}
