
/**
 * Represents a game board for a two-player game with methods for managing and checking game state.
 * The board is a square grid with cells that can be marked by players.
 *
 * @author Achikan Levy
 * @see Mark
 */
public class Board {

    private final Mark[][] board;  // Represents the game board
    private int size = 4;  // Default size of the board

    /**
     * Constructs a Board object with the default size and initializes the board with blank cells.
     */
    public Board() {
        this.board = new Mark[this.size][this.size];
        blankBoard(this.size);
    }

    /**
     * Constructs a Board object with a specified size and initializes the board with blank cells.
     *
     * @param size The size of the square game board.
     */
    public Board(int size) {
        this.size = size;
        this.board = new Mark[this.size][this.size];
        blankBoard(this.size);
    }

    /**
     * Initializes the game board with blank cells.
     *
     * @param size The size of the square game board.
     */
    private void blankBoard(int size) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * Retrieves the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Checks if a given index is within the legal range for the game board.
     *
     * @param index The index to be checked.
     * @return True if the index is legal, false otherwise.
     */
    private boolean isIndexLegal(int index) {
        return (index >= 0) && (index < this.size);
    }

    /**
     * Marks a cell on the game board with a specified player's mark.
     *
     * @param mark The player's mark (X or O).
     * @param row  The row index of the cell.
     * @param col  The column index of the cell.
     * @return True if the mark was successfully placed,
     * false if the cell is already marked or the indices are out of bounds.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (isIndexLegal(row) && isIndexLegal(col)) {
            if (this.board[row][col] == Mark.BLANK) {
                this.board[row][col] = mark;
                return true;
            }
        }
        return false;
    }


    /**
     * Retrieves the mark at the specified cell on the game board.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The mark at the specified cell or Mark.BLANK if the indices are out of bounds.
     */
    public Mark getMark(int row, int col) {
        if (!isIndexLegal(row) || !isIndexLegal(col)) {
            return Mark.BLANK;
        }
        return this.board[row][col];
    }
}
