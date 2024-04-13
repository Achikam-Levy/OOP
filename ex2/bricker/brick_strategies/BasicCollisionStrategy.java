package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * A basic collision strategy that removes the game object from the collection upon collision.
 * This strategy is typically used for handling regular collisions between game objects.
 * Implementing classes can extend this class to define more specific behaviors.
 * It removes the game object from both the STATIC_OBJECTS layer and the general game object collection upon
 * collision.
 *
 * @author Achikam Levy
 * @see CollisionStrategy
 * @see Strategies
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    // The collection of game objects where this collision strategy operates.
    private final GameObjectCollection gameObjectCollection;

    // The counter for tracking the number of bricks.
    private final Counter brickCounter;

    private final String HEART_TAG = "Heart";


    /**
     * Constructs a BasicCollisionStrategy with the specified game object collection.
     *
     * @param gameObjectCollection The collection of game objects where this strategy operates.
     * @param brickCounter          The counter for tracking the number of bricks.
     * @see GameObjectCollection
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection, Counter brickCounter) {
        this.gameObjectCollection = gameObjectCollection;
        this.brickCounter = brickCounter;
    }

    /**
     * Handles the collision by removing the game object from the collection.
     * Removal is performed from both the STATIC_OBJECTS layer and the general game object collection.
     *
     * @param thisObj  The game object on which the collision strategy is applied.
     * @param otherObj The other game object involved in the collision.
     * @see Strategies
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        // Remove the game object from both the STATIC_OBJECTS layer and the general game object collection
        if (this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)) {
            brickCounter.decrement();
        }
        // For hearts removal
        if(thisObj.getTag().equals(HEART_TAG)) {
            this.gameObjectCollection.removeGameObject(thisObj);
        }
    }
}
