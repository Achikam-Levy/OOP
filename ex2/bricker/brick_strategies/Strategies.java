package bricker.brick_strategies;

/**
 * Enumeration representing different strategies associated with game objects in the Bricker game.
 * Each strategy corresponds to a specific behavior or action that can be applied upon collisions.
 * The Strategies enum is used to classify the various collision strategies employed in the game.
 *
 * @author Achikam Levy
 * @see CollisionStrategy
 * @see BasicCollisionStrategy
 * @see ChangeCameraStrategy
 * @see AnotherPaddleStrategy
 * @see MoreLifeStrategy
 * @see PuckBallStrategy
 */
public enum Strategies {
    /**
     * Strategy representing the behavior associated with a mock ball collision.
     * @see PuckBallStrategy
     */
    PUCK_BALL,

    /**
     * Strategy representing the behavior associated with a camera-related action upon collision.
     * @see ChangeCameraStrategy
     */
    CAMERA,

    /**
     * Strategy representing the behavior associated with the creation of another paddle upon collision.
     * @see AnotherPaddleStrategy
     */
    EXTRA_PADDLE,

    /**
     * Strategy representing the behavior associated with gaining an extra life upon collision.
     * @see MoreLifeStrategy
     */
    LIFE,

    /**
     * Strategy representing a double strategy, which may have specific effects based on the game's design.
     * @see BasicCollisionStrategy
     */
    DOUBLE_STRATEGY,

    /**
     * Default collision strategy representing basic collision behavior.
     * @see BasicCollisionStrategy
     */
    NORMAL
}

