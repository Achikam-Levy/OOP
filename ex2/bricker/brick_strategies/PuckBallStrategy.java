package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * A collision strategy that simulates the creation of multiple mock ball game objects upon collision.
 * Each mock ball is an instance of the Ball class, and they are released in different directions.
 * This strategy extends the BasicCollisionStrategy and is associated with the Strategies.PUCK_BALL type.
 * The PuckBallStrategy class defines a collision strategy for creating and managing multiple mock ball
 * game objects upon collision. It extends the BasicCollisionStrategy and is associated with the
 * Strategies.PUCK_BALL type.
 * The strategy involves releasing a specified number of Ball objects in different directions upon collision.
 *
 * @author Achikam Levy
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see Ball
 * @see Strategies
 */
public class PuckBallStrategy extends DoubleStrategyDecorator {

    private final GameObjectCollection gameObjectCollection;

    private static final int PUCK_BALL_NUMBER = 2;

    private final int PUCK_BALL_SPEED;

    private final Random random = new Random();

    /**
     * Array to store the instances of the Ball class representing the mock balls.
     */
    private final Ball[] balls = new Ball[PUCK_BALL_NUMBER];

    /**
     * Constructs a PuckBallStrategy with the specified game object collection.
     *
     * @param gameObjectCollection The collection of game objects where this strategy operates.
     * @param mockBallRenderable   Renderable object representing the mock ball's visual.
     * @param ballSound            Sound object representing the sound associated with the mock ball.
     * @param puckBallRadius       The radius of the mock ball.
     * @param puckBallSpeed        The speed at which the mock ball moves.
     * @param collisionStrategy    The collision strategy associated with this decorator.
     * @see BasicCollisionStrategy
     * @see Ball
     * @see Strategies
     */
    public PuckBallStrategy(GameObjectCollection gameObjectCollection, Renderable mockBallRenderable,
                            Sound ballSound, float puckBallRadius, int puckBallSpeed, CollisionStrategy
                                    collisionStrategy) {
        super(collisionStrategy);
        this.gameObjectCollection = gameObjectCollection;
        this.PUCK_BALL_SPEED = puckBallSpeed;

        createPuckBalls(mockBallRenderable, ballSound, puckBallRadius);
    }

    /**
     * Creates instances of the Ball class representing mock balls.
     *
     * @param mockBallRenderable Renderable object representing the mock ball's visual.
     * @param ballSound          Sound object representing the sound associated with the mock ball.
     * @param puckBallRadius     The radius of the mock ball.
     */
    private void createPuckBalls(Renderable mockBallRenderable, Sound ballSound, float puckBallRadius) {
        for (int i = 0; i < PUCK_BALL_NUMBER; i++) {
            this.balls[i] = new Ball(
                    Vector2.ZERO,
                    new Vector2(puckBallRadius, puckBallRadius),
                    mockBallRenderable,
                    ballSound);
            this.balls[i].setTag("puckBall");
        }
    }

    /**
     * Handles the collision by triggering the release of multiple mock ball game objects.
     * Each mock ball is given a different initial velocity and released in different directions.
     *
     * @param thisObj  The game object on which the collision strategy is applied.
     * @param otherObj The other game object involved in the collision.
     * @see BasicCollisionStrategy
     * @see Ball
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        Vector2 breakCenterVector = thisObj.getCenter();
        addPuckBalls(breakCenterVector);
    }

    /**
     * Adds instances of the Ball class to the game object collection, simulating the release
     * of mock balls in different directions.
     *
     * @param breakCenterVector The center vector of the collision.
     */
    private void addPuckBalls(Vector2 breakCenterVector) {
        for (int i = 0; i < PUCK_BALL_NUMBER; i++) {
            this.balls[i].setCenter(breakCenterVector);
            float velocityX = this.PUCK_BALL_SPEED;
            float velocityY = this.PUCK_BALL_SPEED;

            if (this.random.nextBoolean()) velocityX *= -1;
            if (this.random.nextBoolean()) velocityY *= -1;
            this.balls[i].setVelocity(new Vector2(velocityX, velocityY));
            this.gameObjectCollection.addGameObject(this.balls[i]);
        }
    }
}
