import java.util.Random;

/**
 * Represents a player in a tic-tac-toe game that makes random moves.
 * Implements the Player interface.
 * The WhateverPlayer plays its turn by choosing random positions on the board.
 * If the selected position is already occupied, it repeats the process until a valid move is made.
 *
 * @author Achikam Levy
 * @see Player
 * @see Board
 * @see Mark
 */
public class WhateverPlayer implements Player {

    // for getting random coordinates
    private final Random random = new Random();

    /**
     * default constructor for WhateverPlayer class
     */
    public WhateverPlayer() {
        // nothing is need to be done
    }

    /**
     * Initiates the player's turn by invoking the WhateverTurn method.
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The player's mark (X or O).
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        WhateverTurn(board, mark);
    }

    /**
     * Makes a random move on the board.
     * Continues to select random positions until an empty spot is found,
     * then places the player's mark on that position.
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The player's mark (X or O).
     */
    private void WhateverTurn(Board board, Mark mark) {
        while (true) {

            // get random coordinates
            int row = this.random.nextInt(board.getSize());
            int col = this.random.nextInt(board.getSize());

            // Attempt to place the mark on the board
            if (board.putMark(mark, row, col)) {
                return;
            }
        }
    }
}
