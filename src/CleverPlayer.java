
/**
 * Represents a clever computer player in a tic-tac-toe game, implementing the Player interface.
 * The CleverPlayer attempts to make a move by iteratively trying each position on the board.
 * This serves as a fallback strategy when other more strategic moves are not applicable.
 *
 * @author Achikam Levy
 * @see Player
 * @see Board
 * @see Mark
 */
public class CleverPlayer implements Player {

    /**
     * default constructor for CleverPlayer class
     */
    public CleverPlayer() {
        // nothing is need to be done
    }

    /**
     * Initiates the player's turn by invoking the playTurn method.
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The player's mark (X or O).
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                // Attempt to place the mark on the board, in order from the start.
                if (board.putMark(mark, i, j)) {
                    return;
                }
            }
        }
    }
}
