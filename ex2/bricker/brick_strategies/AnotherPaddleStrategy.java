package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;

/**
 * A collision strategy that handles the creation and behavior of an extra paddle upon collision.
 * This strategy extends the BasicCollisionStrategy and is associated with the Strategies.ANOTHER_PADDLE type.
 * The AnotherPaddleStrategy class defines a collision strategy for creating and managing an extra paddle
 * in the game. It extends the BasicCollisionStrategy and is designed to be associated with the
 * Strategies.ANOTHER_PADDLE type. The strategy involves tracking collision counts and creating a new
 * ExtraPaddle object when necessary.
 *
 * @author Achikam Levy
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see ExtraPaddle
 * @see Strategies
 */
public class AnotherPaddleStrategy extends DoubleStrategyDecorator {

    // The upper limit for the collision counter to trigger the creation of a new paddle.
    private static final int PADDLE_COUNTER_BOUND = 4;

    // The collection of game objects where this strategy operates.
    private final GameObjectCollection gameObjectCollection;

    // The ExtraPaddle object representing the additional paddle created by this strategy.
    private final ExtraPaddle anotherUserPaddle;

    /**
     * Constructs an AnotherPaddleStrategy with the specified game object collection, additional paddle,
     * initial position, and collision strategy.
     *
     * @param gameObjectCollection The collection of game objects where this strategy operates.
     * @param extraPaddle          The ExtraPaddle object representing the additional paddle.
     * @param initialPosition      The initial position of the extra paddle.
     * @param collisionStrategy    The collision strategy associated with this decorator.
     * @see ExtraPaddle
     * @see Strategies
     */
    public AnotherPaddleStrategy(GameObjectCollection gameObjectCollection,
                                 ExtraPaddle extraPaddle,
                                 Vector2 initialPosition,
                                 CollisionStrategy collisionStrategy) {
        super(collisionStrategy);
        this.gameObjectCollection = gameObjectCollection;
        this.anotherUserPaddle = extraPaddle;
        this.anotherUserPaddle.setCenter(initialPosition);
    }

    /**
     * Handles the collision by checking the collision counter and creating a new paddle when necessary.
     * The new paddle is added to the game object collection.
     *
     * @param thisObj  The game object on which the collision strategy is applied.
     * @param otherObj The other game object involved in the collision.
     * @see BasicCollisionStrategy
     * @see ExtraPaddle
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if (this.anotherUserPaddle.getCollisionCounter() == 0) {
            this.gameObjectCollection.addGameObject(this.anotherUserPaddle);
            this.anotherUserPaddle.setCollisionCounter(PADDLE_COUNTER_BOUND);
        }
    }
}
