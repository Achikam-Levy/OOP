/**
 * Represents a factory for creating different types of renderers for displaying the game board.
 *
 * @author [Author Name]
 * @see Renderer
 * @see VoidRenderer
 * @see ConsoleRenderer
 */
public class RendererFactory {

    // constants of renderers type options
    private final String NONE = "none";
    private final String CONSOLE = "console";

    /**
     * Constructs a new RendererFactory instance.
     * nothing is need to be done in this constructor.
     */
    public RendererFactory() {
    }

    /**
     * Builds and returns a renderer based on the provided type and size.
     *
     * @param type The type of renderer to build ("none" for VoidRenderer, "console" for ConsoleRenderer).
     * @param size The size of the game board, applicable for some renderer types.
     * @return A renderer object based on the specified type and size.
     */
    public Renderer buildRenderer(String type, int size) {
        Renderer renderer = null;
        // suppose to get them already in lower case, but remain it just in case...
        String rendererType = type.toLowerCase();

        // Build VoidRenderer for "none" type
        if (rendererType.equals(NONE)) {
            renderer = new VoidRenderer();
        }
        // Build ConsoleRenderer for "console" type with the specified size
        else if (rendererType.equals(CONSOLE)) {
            renderer = new ConsoleRenderer(size);
        }
        return renderer;
    }

    /**
     * Builds and returns a renderer based on the provided type with the default size.
     *
     * @param type The type of renderer to build ("none" for VoidRenderer, "console" for ConsoleRenderer).
     * @return A renderer object based on the specified type with the default size.
     */
    public Renderer buildRenderer(String type) {
        Renderer renderer = null;
        // suppose to get them already in lower case, but remain it just in case...
        String rendererType = type.toLowerCase();

        // Build VoidRenderer for "none" type
        if (rendererType.equals(NONE)) {
            renderer = new VoidRenderer();
        }
        // Build ConsoleRenderer for "console" type with the default size
        else if (rendererType.equals(CONSOLE)) {
            // Default size for the game board
            int defaultSize = 4;
            renderer = new ConsoleRenderer(defaultSize);
        }
        return renderer;
    }
}
