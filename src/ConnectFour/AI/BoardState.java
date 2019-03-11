package ConnectFour.AI;

import ConnectFour.Logic.Board;
import ConnectFour.Logic.Disc;
import ConnectFour.Logic.Point;
import ConnectFour.Logic.Turn;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class BoardState extends Board {
    private Point lastPlacement;

    // Starting board state
    public BoardState(Disc[][] discs) {
        turn = new Turn();
        this.discs = new Disc[COLUMNS][ROWS];
        lastPlacement = null;

        for (int x = 0; x < Board.COLUMNS; x++) {
            for (int y = 0; y < Board.ROWS; y++) {
                discs[x][y] = new Disc(discs[x][y]);
            }
        }
    }

    // Board state based on existing board
    public BoardState(BoardState boardState) {
        turn = new Turn(boardState.getTurn());
        this.discs = new Disc[COLUMNS][ROWS];
        lastPlacement = new Point(boardState.getLastPlacement());

        for (int x = 0; x < Board.COLUMNS; x++) {
            for (int y = 0; y < Board.ROWS; y++) {
                Disc disc = boardState.getDisc(x, y).orElse(null);

                if (disc != null) {
                    this.discs[x][y] = new Disc(disc);
                }
            }
        }
    }

    public BoardState(BoardState boardState, Point moveToMake) {
        discs = boardState.discs;

        Disc disc = new Disc(getTurn());
        placeDisc(disc, moveToMake.getX());
    }

    public Point getLastPlacement() {
        return lastPlacement;
    }

    private List<Point> getAllPossiblePlacements() {
        List<Point> possibleDiscPlacements = new LinkedList<>();



        return possibleDiscPlacements;
    }

    public List<BoardState> getPossibleMoves() {
        List<BoardState> boardTree = new LinkedList<>();
        List<Point> nextPossibleMoves = getAllPossiblePlacements();

        ListIterator<Point> iter = nextPossibleMoves.listIterator();

        while (iter.hasNext()) {
            iter.next();
        }

        return boardTree;
    }
}
