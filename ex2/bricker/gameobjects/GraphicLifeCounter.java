package bricker.gameobjects;

// -------- imports --------


import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

// -------- imports --------


/**
 * Graphic representation of the player's life counter in the game.
 * The GraphicLifeCounter class manages the visual display of player lives using heart icons. It dynamically
 * updates the display based on the current number of lives.
 * It tracks the number of lives using a Counter object and updates the display dynamically.
 * Hearts are added or removed from the display based on changes in the player's life count.
 *
 * @author Achikam Levy
 */
public class GraphicLifeCounter extends GameObject {

    private final int INITIAL_LIFE_START = 0;
    // Constant gap between heart icons
    private final int GAP = 4;

    // Collection of game objects to manage
    private final GameObjectCollection gameObjectCollection;

    // Top-left corner position of the life counter widget
    private final Vector2 widgetTopLeftCorner;

    // Dimensions of the life counter widget
    private final Vector2 widgetDimensions;

    // Renderable component for the life counter widget
    private final Renderable widgetRenderable;

    // Counter object tracking the player's remaining lives
    private final Counter livesCounter;

    // Array to store heart game objects
    private final GameObject[] hearts;

    // Number of initial lives
    private int numOfLives;

    /**
     * Constructs a GraphicLifeCounter instance.
     *
     * @param widgetTopLeftCorner  The top-left corner position of the life counter widget.
     * @param widgetDimensions     The dimensions of the life counter widget.
     * @param livesCounter         Counter object tracking the player's remaining lives.
     * @param widgetRenderable     The renderable component for the life counter widget.
     * @param gameObjectCollection The collection of game objects to which hearts are added or removed.
     * @param numOfLives           The initial number of lives.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner,
                              Vector2 widgetDimensions,
                              Counter livesCounter,
                              Renderable widgetRenderable,
                              GameObjectCollection gameObjectCollection,
                              int numOfLives) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.gameObjectCollection = gameObjectCollection;
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.widgetTopLeftCorner = widgetTopLeftCorner;
        this.widgetDimensions = widgetDimensions;
        this.widgetRenderable = widgetRenderable;
        this.hearts = new GameObject[numOfLives];
        createHeartsImages(INITIAL_LIFE_START);
    }

    /**
     * Updates the life counter based on the current number of lives.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        int currentCounter = this.livesCounter.value();
        if (currentCounter != this.numOfLives) {
            if (currentCounter > this.numOfLives) {
                createHeartsImages(this.numOfLives);
            } else {
                removeHearts(currentCounter);
            }
            this.numOfLives = currentCounter;
        }
    }

    /**
     * Removes heart icons from the display starting from the specified index.
     *
     * @param start The index from which hearts should be removed.
     */
    private void removeHearts(int start) {
        for (int i = start; i < this.numOfLives; ++i) {
            this.gameObjectCollection.removeGameObject(this.hearts[i], Layer.UI);
        }
    }

    /**
     * Creates heart icons for the display starting from the specified index.
     *
     * @param start The index from which hearts should be created.
     */
    private void createHeartsImages(int start) {
        for (int i = start; i < this.livesCounter.value(); ++i) {
            Vector2 currentTopLeftCorner = new Vector2(this.widgetTopLeftCorner.x() +
                    (this.widgetDimensions.x() * i) +
                    i * GAP,
                    this.widgetTopLeftCorner.y());

            this.hearts[i] = new GameObject(currentTopLeftCorner,
                    this.widgetDimensions,
                    this.widgetRenderable);
            createSingleHeart(this.hearts[i]);
        }
    }

    /**
     * Adds a single heart icon to the game object collection.
     *
     * @param heartI The heart game object to be added.
     */
    private void createSingleHeart(GameObject heartI) {
        this.gameObjectCollection.addGameObject(heartI, Layer.UI);
    }
}
