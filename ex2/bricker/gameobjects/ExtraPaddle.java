package bricker.gameobjects;

// -------- imports --------

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

// -------- imports --------

/**
 * Represents an extra paddle GameObject in the game, controlled by user input.
 * Extends the Paddle class to inherit basic paddle functionality and adds features specific to extra paddles.
 * This class introduces a collision counter, allowing the extra paddle to be removed after a certain
 * number of collisions.
 * The ExtraPaddle class enhances the Paddle functionality by introducing a collision counter, which
 * determines the lifespan of the extra paddle. After a specified number of collisions with a ball,
 * the extra paddle will be removed from the game.
 *
 * @author Achikam Levy
 * @see Paddle
 */
public class ExtraPaddle extends Paddle {

    private final String EXTRA_PADDLE_TAG = "extraPaddle";
    private final String MAIN_BALL_TAG = "mainBall";
    private final String PUCK_BALL_TAG = "puckBall";
    // Counter to track the number of collisions before removal
    private final Counter collisionCounter;

    /**
     * Constructs an ExtraPaddle instance.
     *
     * @param topLeftCorner    The initial position of the top-left corner of the extra paddle.
     * @param dimensions       The dimensions of the extra paddle GameObject.
     * @param renderable       Renderable object representing the extra paddle's visual.
     * @param inputListener    UserInputListener for processing user keyboard input.
     * @param collisionCounter Counter to track the number of collisions before removal.
     * @see Paddle
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener,
                       Counter collisionCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener);
        this.collisionCounter = collisionCounter;
        this.setTag(EXTRA_PADDLE_TAG);
    }

    /**
     * Handles collision events with other game objects.
     * Overrides the Paddle's collision handling to decrement the collision counter and remove the
     * extra paddle if needed.
     *
     * @param other     The other GameObject involved in the collision.
     * @param collision Information about the collision event.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        // Check if the collision involves a Ball
        if (other.getTag().equals(MAIN_BALL_TAG) || other.getTag().equals(PUCK_BALL_TAG)) {
            this.collisionCounter.decrement();
        }
    }

    /**
     * Gets the current value of the collision counter.
     *
     * @return The current collision counter value.
     */
    public int getCollisionCounter() {
        return this.collisionCounter.value();
    }

    /**
     * Sets the collision counter of the extra paddle to a specific value.
     *
     * @param value The value to set the collision counter to.
     */
    public void setCollisionCounter(int value) {
        this.collisionCounter.reset();
        this.collisionCounter.increaseBy(value);
    }
}
