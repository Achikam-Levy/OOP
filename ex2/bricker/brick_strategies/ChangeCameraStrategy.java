package bricker.brick_strategies;

import danogl.GameObject;

/**
 * A collision strategy that handles camera-related actions upon collision, such as starting the camera count.
 * This strategy extends the BasicCollisionStrategy and is associated with the Strategies.CHANGE_CAMERA type.
 * The camera count is initiated by the BallCollisionCounter, which is incremented upon collision objects.
 * The ChangeCameraStrategy class defines a collision strategy for initiating camera actions collision.
 * It extends the BasicCollisionStrategy and is associated with the Strategies.CHANGE_CAMERA type.
 * The strategy is designed to work in conjunction with the BallCollisionCounter, tracking ball collisions
 * to trigger camera-related actions.
 *
 * @author Achikam Levy
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see CameraStrategyManager
 * @see Strategies
 */
public class ChangeCameraStrategy extends DoubleStrategyDecorator {

    private final String MAIN_BALL_TAG = "mainBall";
    /**
     * The BallCollisionCounter responsible for tracking ball collisions, initiating camera-related actions.
     */
    private final CameraStrategyManager cameraStrategyManager;

    /**
     * Constructs a ChangeCameraStrategy with the specified game object collection and ball collision counter.
     *
     * @param cameraStrategyManager The counter for tracking ball collisions and initiating
     *                              camera-related actions.
     * @param collisionStrategy     The collision strategy associated with this decorator.
     * @see BasicCollisionStrategy
     * @see CameraStrategyManager
     * @see Strategies
     */
    public ChangeCameraStrategy(CameraStrategyManager cameraStrategyManager,
                                CollisionStrategy collisionStrategy) {
        super(collisionStrategy);
        this.cameraStrategyManager = cameraStrategyManager;
    }

    /**
     * Handles the collision by checking the tag of the other object and starting camera count accordingly.
     * The camera count is initiated if the other object has a tag equal to "mainBall".
     *
     * @param thisObj  The game object on which the collision strategy is applied.
     * @param otherObj The other game object involved in the collision.
     * @see BasicCollisionStrategy
     * @see CameraStrategyManager
     * @see Strategies
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if (otherObj.getTag().equals(MAIN_BALL_TAG)) {
            this.cameraStrategyManager.startCount();
        }
    }
}
