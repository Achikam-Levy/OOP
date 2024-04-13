package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Represents a collision strategy that does nothing upon collision.
 * This strategy is used when a brick is assigned the "double" type,
 * indicating that it should have two collision strategies associated with it.
 * this strategy does not perform any specific action upon collision and is just used for identification.
 *
 * @author Achikam Levy
 * @see CollisionStrategy
 * @see BrickStrategyFactory
 */
abstract class DoubleStrategyDecorator implements CollisionStrategy {

    /**
     * The collision strategy associated with this decorator.
     */
    protected final CollisionStrategy collisionStrategy;

    /**
     * Constructs a DoubleStrategyDecorator with the specified collision strategy.
     *
     * @param collisionStrategy The collision strategy associated with this decorator.
     */
    public DoubleStrategyDecorator(CollisionStrategy collisionStrategy) {
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Empty implementation of the onCollision method.
     * This method is called when a collision occurs between the brick and another game object,
     * but since this strategy does not perform any action, the method body is left empty.
     *
     * @param thisObj  The game object on which the collision strategy is applied.
     * @param otherObj The other game object involved in the collision.
     * @see CollisionStrategy
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.collisionStrategy.onCollision(thisObj, otherObj);
    }
}
