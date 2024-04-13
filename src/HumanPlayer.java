
/**
 * Represents a human player in a tic-tac-toe game, implementing the Player interface.
 * Allows the player to make a move by providing coordinates through the keyboard input.
 *
 * @author Achikam Levy
 * @see Player
 * @see Board
 * @see Mark
 * @see KeyboardInput
 */
public class HumanPlayer implements Player {

    /**
     * default constructor for HumanPlayer class
     */
    public HumanPlayer() {
        // nothing is need to be done
    }

    /**
     * Allows the human player to make a move by entering coordinates through the keyboard input.
     * Coordinates are entered as a two-digit integer,
     * where the tens digit represents the row and the ones digit represents the column.
     * Continues prompting for coordinates until a valid move is made.
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The player's mark (X or O).
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        // asks the player to type coordinates
        System.out.println(Constants.playerRequestInputString(mark.name()));

        // until the human player enter valid coordinates
        while (true) {
            int coordinates = KeyboardInput.readInt();
            int row = coordinates / 10; // get the second digit
            int col = coordinates % 10; // get the first digit

            // Check if the move is valid, first check if the coordinates are legal.
            if (col >= board.getSize() || col < 0 || row >= board.getSize() || row < 0) {
                System.out.println(Constants.INVALID_COORDINATE);
                continue;
            }
            if (!board.putMark(mark, row, col)) { // try to put mark, if not occupied.
                System.out.println(Constants.OCCUPIED_COORDINATE);
            } else {
                return;
            }
        }
    }
}
