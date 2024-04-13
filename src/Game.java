/**
 * Represents a two-player game with a game board, players, and rendering capabilities.
 * The game follows a turn-based system where players make moves and attempt to achieve a winning streak.
 * The outcome of the game can be one of three possibilities: player X wins, player O wins, or a draw (blank).
 *
 * @author Achikam Levy
 * @see Board
 * @see Player
 * @see Renderer
 * @see Mark
 */
public class Game {

    // directions of moving for winning checks
    private final int STATIC = 0;
    private final int FORWARD = 1;
    private final int BACKWARD = -1;

    private final int MAX_TURNS;  // Maximum number of turns in the game
    private final Renderer renderer;  // Renderer for displaying the game state
    private final Player playerX;  // Player X
    private final Player playerO;  // Player O
    private final Board board;  // The game board
    private int winStreak = 3;  // Number of consecutive marks required for a win

    /**
     * Constructs a Game object with default settings and initializes the game components.
     *
     * @param playerX  The player representing mark X.
     * @param playerO  The player representing mark O.
     * @param renderer The renderer for displaying the game state.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerO = playerO;
        this.playerX = playerX;
        this.renderer = renderer;

        // Initializes the game board with default size
        this.board = new Board();

        // Maximum turns based on board size
        this.MAX_TURNS = this.board.getSize() * this.board.getSize();
    }

    /**
     * Constructs a Game object with specified settings and initializes the game components.
     *
     * @param playerX   The player representing mark X.
     * @param playerO   The player representing mark O.
     * @param size      The size of the game board.
     * @param winStreak The number of consecutive marks required for a win.
     * @param renderer  The renderer for displaying the game state.
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.playerO = playerO;
        this.playerX = playerX;
        this.renderer = renderer;

        // Initializes the game board with a specified size
        this.board = new Board(size);

        // Maximum turns based on board size
        this.MAX_TURNS = this.board.getSize() * this.board.getSize();

        // Adjusts win streak within valid range : if winStreak > size so winStreak = size,
        // if 2 > winStreak so winStreak = 2, else stay winStreak.
        this.winStreak = Math.max(2, Math.min(winStreak, size));
    }

    /**
     * Retrieves the win streak required for a player to win the game.
     *
     * @return The win streak required for a player to win.
     */
    public int getWinStreak() {
        return this.winStreak;
    }

    /**
     * Retrieves the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getBoardSize() {
        return this.board.getSize();
    }

    /**
     * Checks if a player mark has won the game based on getting a winStreak in given length.
     *
     * @param winStreak The number of consecutive marks required for a win.
     * @param mark      The player's mark to check for a win.
     * @param row       The row index of the last marked cell.
     * @param col       The column index of the last marked cell.
     * @return true winning player's is mark, else false.
     */
    private boolean checkWinStreak(int winStreak, Mark mark, int row, int col) {
        // checks if the player mark Sign has won in one of tree options
        return checkRow(winStreak, mark, row, col)
                || checkCol(winStreak, mark, row, col)
                || checkDiagonals(winStreak, mark, row, col);
    }

    /**
     * Checks for a win condition in a row.
     *
     * @param winStreak The number of consecutive marks required for a win.
     * @param mark      The player's mark to check for a win.
     * @param row       The row index of the last marked cell.
     * @param col       The column index of the last marked cell.
     * @return True if there is a win in the row, false otherwise.
     */
    private boolean checkRow(int winStreak, Mark mark, int row, int col) {
        if (checkStreak(winStreak, mark, row, col, STATIC, FORWARD)) return true;
        return checkStreak(winStreak, mark, row, col, STATIC, BACKWARD);
    }

    /**
     * Checks for a win condition in a column.
     *
     * @param winStreak The number of consecutive marks required for a win.
     * @param mark      The player's mark to check for a win.
     * @param row       The row index of the last marked cell.
     * @param col       The column index of the last marked cell.
     * @return True if there is a win in the column, false otherwise.
     */
    private boolean checkCol(int winStreak, Mark mark, int row, int col) {
        if (checkStreak(winStreak, mark, row, col, FORWARD, STATIC)) return true;
        return checkStreak(winStreak, mark, row, col, BACKWARD, STATIC);
    }

    /**
     * Checks for a win condition in a diagonal.
     *
     * @param winStreak The number of consecutive marks required for a win.
     * @param mark      The player's mark to check for a win.
     * @param row       The row index of the last marked cell.
     * @param col       The column index of the last marked cell.
     * @return True if there is a win in a diagonal, false otherwise.
     */
    private boolean checkDiagonals(int winStreak, Mark mark, int row, int col) {
        if (checkStreak(winStreak, mark, row, col, FORWARD, FORWARD)) return true;
        if (checkStreak(winStreak, mark, row, col, BACKWARD, BACKWARD)) return true;
        if (checkStreak(winStreak, mark, row, col, FORWARD, BACKWARD)) return true;
        return checkStreak(winStreak, mark, row, col, BACKWARD, FORWARD);
    }

    /**
     * checks if there is a winning streak on the given direction.
     * using the signRow and the signCol to determine the direction.
     *
     * @param winStreak The number of consecutive marks required for a win.
     * @param mark      The player's mark to check for a win.
     * @param row       The row index of the last marked cell.
     * @param col       The column index of the last marked cell.
     * @param signRow   direction to move in the rows
     * @param signCol   direction to move in the columns
     * @return True if there is a win in this direction, false otherwise.
     */
    private boolean checkStreak(int winStreak, Mark mark, int row, int col, int signRow, int signCol) {
        int curStreak = 0;
        for (int i = 0; i < winStreak; i++) {
            // if the streak was broken
            if (this.board.getMark(row + (i * signRow), col + (i * signCol)) != mark) {
                break;
            }
            curStreak++;
            if (curStreak == winStreak) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks for a win based on the specified mark,
     * iterating through all possible positions on the game board.
     *
     * @param mark The mark to check for a win (X or O).
     * @return The winning mark if found, Mark.BLANK otherwise.
     */
    private Mark checkIfWin(Mark mark) {

        for (int i = 0; i < this.board.getSize(); ++i) {
            for (int j = 0; j < this.board.getSize(); ++j) {
                // Check for a winning condition of mark at each position on the board
                if (checkWinStreak(this.winStreak, mark, i, j)) {
                    return mark;
                }
            }
        }
        // in case of tie, return
        return Mark.BLANK;
    }

    /**
     * make a play turn as the given player and check if he's won.
     * @param player player that it's his turn to play
     * @param mark player mark
     * @return mark if this turn made player win, else null
     */
    private Mark markTurnWin(Player player, Mark mark) {
        // Player mark's turn
        player.playTurn(this.board, mark);

        // Check for a win after each move by Player X
        if (this.checkIfWin(mark) == mark) {
            this.renderer.renderBoard(this.board);
            return mark;
        }
        return null;
    }

    /**
     * Runs the game, allowing players to take turns until a winner is determined or the game ends in a draw.
     *
     * @return The winning mark (X, O) or Mark.BLANK for a draw.
     */
    public Mark run() {
        for (int i = 0; i < this.MAX_TURNS; ++i) {
            if (i % 2 == 0) {
                Mark x = markTurnWin(this.playerX, Mark.X);
                if (x != null) return x;
            } else {
                Mark o = markTurnWin(this.playerO, Mark.O);
                if (o != null) return o;
            }
            this.renderer.renderBoard(this.board);
        }
        return Mark.BLANK;
    }

}
