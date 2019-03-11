package ConnectFour.AI;

import ConnectFour.Logic.Board;
import ConnectFour.Logic.Disc;
import ConnectFour.Logic.Point;
import ConnectFour.Logic.Turn;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class BoardState extends Board {
    private Point lastPlacement;

    private Disc[][] discs;

    public BoardState(Disc[][] discs) {
        this.discs = new Disc[COLUMNS][ROWS];

        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                this.discs[x][y] = new Disc(discs[x][y]);
            }
        }
    }

    public BoardState(BoardState boardState) {

    }

    public BoardState(BoardState boardState, Point moveToMake) {
        discs = boardState.discs;

        Disc disc = new Disc(Turn.isPlayerTurn());
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
