
/**
 * Represents a renderer that performs no rendering.
 * The VoidRenderer class implements the Renderer interface but does not provide any visual output.
 */
public class VoidRenderer implements Renderer {

    /**
     * Default constructor for void renderer class
     */
    public VoidRenderer() {
        // nothing is need to be done
    }

    /**
     * Renders the provided game board with no visual output.
     *
     * @param board The game board to be rendered (no action is taken).
     */
    public void renderBoard(Board board) {
        // No rendering action is performed.
    }
}