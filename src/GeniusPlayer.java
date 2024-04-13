import java.util.Random;

/**
 * Represents a genius computer player in a tic-tac-toe game, implementing the Player interface.
 * The GeniusPlayer attempts to make strategic moves by prioritizing certain positions on the board.
 * It first tries to place the mark in the center column, and if unsuccessful,
 * it iteratively tries each position on the board.
 *
 * @author Achikam Levy
 * @see Player
 * @see Board
 * @see Mark
 */
public class GeniusPlayer implements Player {

    // constants for the coin toss
    public final int THRESH_HOLD = 48;
    public final int BOUND = 50;

    // random object for the player strategy.
    private final Random random = new Random();

    /**
     * default constructor for GeniusPlayer class
     */
    public GeniusPlayer() {
        // nothing is need to be done
    }

    /**
     * try to fill the board column by column starts from "start" column
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The player's mark (X or O).
     * @param start the column start to mark.
     * @return true if marked.
     */
    private boolean fillColumnsFromStart(Board board, Mark mark, int start) {
        for (int j = start; j < board.getSize(); j++) {
            for (int i = 0; i < board.getSize(); i++) {
                // Attempt to place the mark on the board
                if (board.putMark(mark, i, j)) {
                    return true;
                }
            }
        }
        // there is no place on the board, shouldn't happened
        return false;
    }

    /**
     * Makes a strategic move by prioritizing certain positions on the board.
     * The GeniusPlayer first tries to place the mark in the center column, and if unsuccessful,
     * it iteratively tries each position on the board.
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The player's mark (X or O).
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        // if the second player is clever they are racing on the first column,
        // toss a coin to make sure that most of the time, the genius player can block him if he needed.
        int start = 1;
        int coin = random.nextInt(BOUND);
        if (coin > THRESH_HOLD) {
            start = 0;
        }
        // try fil the start column
        if (fillColumnsFromStart(board, mark, start)) return;

        // If unsuccessful in the first column, iteratively try each column on the board(should be only 0)
        start = 0;
        fillColumnsFromStart(board, mark, start);
    }

}
