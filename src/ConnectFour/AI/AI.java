package ConnectFour.AI;

import ConnectFour.Logic.Point;

import java.util.List;
import java.util.Random;

public class AI {
    public static final int searchDepth = 7;

    private AI() {

    }

    public static Point minimaxDecision() {
        // Gets the current state of the global board
        BoardState currentState = new BoardState();

        List<BoardState> children = currentState.getPossibleMoves();
        int maxEval = Integer.MIN_VALUE;
        int currentEval;

        Point bestPlacement = null;

        for (BoardState child : children) {
            currentEval =  Integer.max(maxEval, minValue(child, Integer.MIN_VALUE, Integer.MAX_VALUE, searchDepth-1));

            if (currentEval > maxEval) {
                maxEval = currentEval;
                bestPlacement = child.getLastPlacement();
            }
        }

        return bestPlacement;
    }

    private static int maxValue(BoardState boardState, int alpha, int beta, int depth) {
        int gameStateValue = boardState.evaluateGameState(boardState.getLastPlacement());

        if (depth == 0 || gameStateValue != -1) {
            // Game has ended in this board state (win, loss, draw)
            if (gameStateValue != -1)
                return gameStateValue;
            else
                return boardState.scoreBoardState();
        }

        List<BoardState> children = boardState.getPossibleMoves();
        int maxEval = Integer.MIN_VALUE;

        for (BoardState child : children) {
            maxEval =  Integer.max(maxEval, minValue(child, alpha, beta, depth-1)) - child.getNumberOfMoves();

            if (maxEval >= beta) {
                break;
            }

            alpha = Integer.max(alpha, maxEval);
        }

        return maxEval;
    }

    private static int minValue(BoardState boardState, int alpha, int beta, int depth) {
        int gameStateValue = boardState.evaluateGameState(boardState.getLastPlacement());

        if (depth == 0 || gameStateValue != -1) {
            // Game has ended in this board state (win, loss, draw)
            if (gameStateValue != -1)
                return gameStateValue;
            else
                return boardState.scoreBoardState();
        }

        List<BoardState> children = boardState.getPossibleMoves();
        int minEval = Integer.MAX_VALUE;

        for (BoardState child : children) {
            minEval =  Integer.min(minEval, maxValue(child, alpha, beta, depth-1)) + child.getNumberOfMoves();

            if (minEval <= alpha) {
                break;
            }

            beta = Integer.min(beta, minEval);
        }

        return minEval;
    }
}
