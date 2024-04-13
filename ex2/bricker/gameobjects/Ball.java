package bricker.gameobjects;

// -------- imports --------

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

// -------- imports --------


/**
 * Represents a ball GameObject in the game.
 * This class extends GameObject and includes additional functionality specific to a game ball,
 * such as collision handling and a collision counter.
 * The Ball class is responsible for managing the behavior of the game ball, including its collisions
 * with other GameObjects, collision sound effects, and keeping track of the collision count.
 *
 * @author Achikam Levy
 */
public class Ball extends GameObject {

    private final String MAIN_BALL_TAG = "mainBall";
    // Sound to be played on collisions with other GameObjects
    private final Sound collisionSound;

    // Counter to keep track of the number of collisions
    private final Counter ballCollisionCounter;

    /**
     * Constructor for the Ball class.
     *
     * @param topLeftCorner   The top-left corner position of the ball.
     * @param dimensions      The dimensions (size) of the ball.
     * @param renderable      The Renderable object for rendering the ball.
     * @param collisionSound  The Sound to be played on collisions.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.ballCollisionCounter = new Counter(0);
        this.setTag(MAIN_BALL_TAG);
    }

    /**
     * Gets the current collision count of the ball.
     *
     * @return The current collision count.
     */
    public int getCollisionCounter() {
        // Returns the current number of collisions that the ball has experienced.
        return ballCollisionCounter.value();
    }

    /**
     * Overrides the onCollisionEnter method from the GameObject class.
     * Handles actions to be taken when a collision with another GameObject occurs.
     *
     * @param other     The other GameObject involved in the collision.
     * @param collision The Collision object representing the details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        // Adjusts the ball's velocity based on the collision normal
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        // Increments the collision count
        this.ballCollisionCounter.increment();
        // Plays the collision sound
        this.collisionSound.play();
    }
}
