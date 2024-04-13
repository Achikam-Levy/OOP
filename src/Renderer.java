/**
 * Represents the rendering interface for displaying the game board.
 */
public interface Renderer {
    /**
     * Renders the provided game board.
     *
     * @param board The game board to be rendered.
     */
    void renderBoard(Board board);
}