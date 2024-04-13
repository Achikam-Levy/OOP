package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a brick GameObject in the game.
 * This class extends GameObject and includes functionality specific to a game brick,
 * such as collision handling, strategies, and a counter.
 * The Brick class manages the behavior of bricks within the game, including collision handling,
 * removal strategies, and tracking the number of bricks. It is responsible for executing strategies
 * upon collisions and updating the brick counter accordingly.
 *
 * @author Achikam Levy
 * @see GameObject
 * @see CollisionStrategy
 * @see Collision
 */
public class Brick extends GameObject {

    // The collision strategy of the brick
    private final CollisionStrategy collisionStrategy;

    /**
     * Constructor for the Brick class.
     *
     * @param topLeftCorner     The top-left corner position of the brick.
     * @param dimensions        The dimensions (size) of the brick.
     * @param renderable        The Renderable object for rendering the brick.
     * @param collisionStrategy The CollisionStrategy instance for handling collisions.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
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
        this.collisionStrategy.onCollision(this, other);
    }
}
