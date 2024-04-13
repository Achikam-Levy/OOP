package bricker.brick_strategies;

import danogl.GameObject;

/**
 * An interface representing a strategy for handling collisions between game objects.
 * Classes implementing this interface define specific behaviors when collisions occur.
 * Implementing classes should provide custom logic in the {@code onCollision} method.
 *
 * @author Ahcikam Levy
 * @see Strategies
 */
public interface CollisionStrategy {

    /**
     * Handles the collision between two game objects.
     * Implementing classes should define specific behaviors for collision events.
     *
     * @param thisObj  The game object on which the collision strategy is applied.
     * @param otherObj The other game object involved in the collision.
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}
