package bricker.brick_strategies;

import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A collision strategy that provides an extra life in the form of a heart game object upon collision.
 * The heart falls down after the collision and, if caught by the player, grants an additional life.
 * This strategy extends the BasicCollisionStrategy and is associated with the Strategies.LIFE type.
 * The MoreLifeStrategy class defines a collision strategy for creating and managing a falling heart
 * in the game. It extends the BasicCollisionStrategy and is associated with the Strategies.LIFE type.
 * The strategy involves releasing a Heart object upon collision, and if caught by the player,
 * it increments the provided life counter.
 *
 * @author Achikam Levy
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see Heart
 * @see Strategies
 */
public class MoreLifeStrategy extends DoubleStrategyDecorator {

    private static final int FALL_SPEED = 100;

    private final Heart heart;

    private final GameObjectCollection gameObjectCollection;

    /**
     * Constructs a MoreLifeStrategy with the specified game object collection, image renderer, life counter.
     *
     * @param gameObjectCollection The collection of game objects where this strategy operates.
     * @param heartRenderer        Renderable object representing the heart's visual.
     * @param lifeCounter          Counter object tracking the player's remaining lives.
     * @param heartDimensions      The dimensions of the heart GameObject.
     * @param collisionStrategy    The collision strategy associated with this decorator.
     * @param brickCounter         Counter object tracking the number of bricks.
     * @see BasicCollisionStrategy
     * @see Heart
     * @see Strategies
     */
    public MoreLifeStrategy(GameObjectCollection gameObjectCollection, Renderable heartRenderer,
                            Counter lifeCounter, Vector2 heartDimensions, CollisionStrategy collisionStrategy,
                            Counter brickCounter) {
        super(collisionStrategy);
        this.gameObjectCollection = gameObjectCollection;
        this.heart = new Heart(Vector2.ZERO,
                heartDimensions,
                heartRenderer,
                new BasicCollisionStrategy(gameObjectCollection, brickCounter),
                lifeCounter);
    }

    /**
     * Handles the collision by triggering the release of a heart game object.
     * The heart falls down, and if caught by the player, grants an additional life.
     *
     * @param thisObj  The game object on which the collision strategy is applied.
     * @param otherObj The other game object involved in the collision.
     * @see BasicCollisionStrategy
     * @see Heart
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        Vector2 breakCenterVector = thisObj.getCenter();

        this.heart.setCenter(breakCenterVector);
        this.heart.setVelocity(new Vector2(0, FALL_SPEED));

        this.gameObjectCollection.addGameObject(this.heart);
    }
}
