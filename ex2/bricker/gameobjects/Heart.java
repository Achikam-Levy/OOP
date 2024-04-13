package bricker.gameobjects;

// -------- imports --------

import bricker.main.BrickerGameManager;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

// -------- imports --------



/**
 * Represents a heart GameObject in the game, functioning as a life bonus.
 * This class extends the GameObject class and implements a CollisionStrategy to manage collision behavior.
 * The Heart object awards an extra life when colliding with the player's paddle.
 *
 * @author Achikam Levy
 * @see CollisionStrategy
 * @see Collision
 */
public class Heart extends GameObject {

    private final String PADDLE_TAG = "paddle";
    private final String HEART_TAG = "Heart";
    // Collision strategy to be executed upon collision with the player's paddle
    private final CollisionStrategy collisionStrategy;

    // Counter object tracking the player's remaining lives
    private final Counter lifeCounter;

    /**
     * Constructs a Heart instance for falling and collection during the extra life strategy.
     *
     * @param position        The initial position of the heart.
     * @param dimensions      The dimensions of the heart GameObject.
     * @param heartImage      Renderable object representing the heart's visual.
     * @param collisionStrategy  The collision strategy to be executed upon collision.
     * @param lifeCounter      Counter object tracking the player's remaining lives.
     * @see CollisionStrategy
     */
    public Heart(Vector2 position, Vector2 dimensions, Renderable heartImage,
                 CollisionStrategy collisionStrategy, Counter lifeCounter) {
        super(position, dimensions, heartImage);
        this.collisionStrategy = collisionStrategy;
        this.lifeCounter = lifeCounter;
        this.setTag(HEART_TAG);
    }

    /**
     * Handles the behavior when a collision occurs with another GameObject.
     * Executes the CollisionStrategy and increments the player's life if it's below the maximum.
     *
     * @param other     The GameObject with which the collision occurred.
     * @param collision The collision information.
     * @see CollisionStrategy
     * @see Counter
     * @see BrickerGameManager
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionStrategy.onCollision(this, other);
        this.lifeCounter.increment();
    }

    /**
     * Determines whether the Heart should collide with a specific GameObject.
     * Overrides the method to specify that the Heart should only collide with objects tagged as "paddle."
     *
     * @param other The GameObject to check for collision.
     * @return True if the Heart should collide with the specified GameObject, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(PADDLE_TAG);
    }
}
