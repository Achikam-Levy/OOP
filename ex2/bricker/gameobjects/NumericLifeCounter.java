package bricker.gameobjects;

// -------- imports --------

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;

// -------- imports --------


/**
 * Represents a numeric representation of the player's life counter in the game.
 * The NumericLifeCounter class manages the display of the player's remaining lives as a numeric value.
 * It updates the displayed count based on the provided Counter object.
 * The color of the displayed text changes based on the current number of lives, with different colors
 * indicating various life count ranges.
 *
 * @author Achikam Levy
 */
public class NumericLifeCounter extends GameObject {

    // TextRenderable object for rendering numeric life count
    private final TextRenderable textRenderable = new TextRenderable("");

    // Counter object tracking the player's remaining lives
    private final Counter livesCounter;

    // String representation of the current number of lives
    private String livesString;

    /**
     * Constructs a NumericLifeCounter instance.
     *
     * @param livesCounter   Counter object tracking the player's remaining lives.
     * @param topLeftCorner  The top-left corner position of the numeric life counter.
     * @param dimensions     The dimensions of the numeric life counter.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions, null);
        this.renderer().setRenderable(this.textRenderable);
        this.textRenderable.setColor(Color.GREEN);  // Default color for full lives
        this.livesCounter = livesCounter;
        this.livesString = this.livesCounter.toString();
    }

    /**
     * Updates the numeric life counter based on the current number of lives.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int currentLives = this.livesCounter.value();
        this.livesString = Integer.toString(currentLives);
        this.textRenderable.setString(this.livesString);

        // Change color based on the current number of lives
        if (currentLives == 2) {
            this.textRenderable.setColor(Color.YELLOW);  // Yellow for two lives
        } else if (currentLives == 1) {
            this.textRenderable.setColor(Color.RED);     // Red for one life
        } else {
            this.textRenderable.setColor(Color.GREEN);   // Default color for other cases
        }
    }
}
