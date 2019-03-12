package ConnectFour.AI;

import ConnectFour.Logic.*;

import java.util.LinkedList;
import java.util.List;

public class BoardState extends Board {
    // Mimics the global board
    public BoardState() {
        turn = new Turn();
        this.discs = new Disc[COLUMNS][ROWS];
        lastPlacement = GlobalBoard.getLastPlacement();
        numberOfMoves = GlobalBoard.getNumberOfMoves();

        for (int x = 0; x < Board.COLUMNS; x++) {
            for (int y = 0; y < Board.ROWS; y++) {
                Disc disc = GlobalBoard.getDisc(x, y);

                if (disc != null) {
                    this.discs[x][y] = new Disc(disc);
                }
            }
        }
    }

    // Board state based on existing board
    public BoardState(BoardState boardState) {
        copyBoardState(boardState);
    }

    // Board state based on existing board and then a move is made
    private BoardState(BoardState boardState, Point moveToMake) {
        copyBoardState(boardState);

        Disc disc = new Disc(getTurn());
        lastPlacement = placeDisc(disc, moveToMake.getX());
        numberOfMoves++;
    }

    private void copyBoardState(BoardState boardState) {
        turn = new Turn(boardState.getTurn());
        this.discs = new Disc[COLUMNS][ROWS];
        numberOfMoves = boardState.getNumberOfMoves();

        if (boardState.getLastPlacement() != null) {
            lastPlacement = new Point(boardState.getLastPlacement());
        }
        else {
            lastPlacement = null;
        }

        for (int x = 0; x < Board.COLUMNS; x++) {
            for (int y = 0; y < Board.ROWS; y++) {
                Disc disc = boardState.getDisc(x, y).orElse(null);

                if (disc != null) {
                    this.discs[x][y] = new Disc(disc);
                }
            }
        }
    }

    private List<Point> getAllPossiblePlacements() {
        List<Point> possibleDiscPlacements = new LinkedList<>();

        for (int column = 0; column < COLUMNS; column++) {
            int validRow = findEmptyRow(column);

            if (validRow >= 0 && validRow < COLUMNS) {
                Point validPlacement = new Point(column, validRow);
                possibleDiscPlacements.add(validPlacement);
            }
        }

        return possibleDiscPlacements;
    }

    public List<BoardState> getPossibleMoves() {
        List<BoardState> boardTree = new LinkedList<>();
        List<Point> nextPossibleMoves = getAllPossiblePlacements();

        for (Point placement : nextPossibleMoves) {
            boardTree.add(new BoardState(this, placement));
        }

        return boardTree;
    }
}
