package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

/**
 * A factory class responsible for generating collision strategies for bricks in the game.
 * The BrickStrategyFactory class facilitates the creation of various collision strategies for bricks,
 * considering different probabilities for each strategy.
 *
 * @author Achikam Levy
 * @see CameraStrategyManager
 * @see Strategies
 * @see PuckBallStrategy
 * @see AnotherPaddleStrategy
 * @see ChangeCameraStrategy
 * @see BasicCollisionStrategy
 * @see MoreLifeStrategy
 */
public class BrickStrategyFactory {

    // Constants for controlling strategy probabilities
    private final int FIRST_TOSS_BOUND = 10;
    private final int SPACIAL_CHOOSE_BOUND = 5;
    private final int DOUBLE_COIN = 0;
    private final int CAMERA_COIN = 1;
    private final int EXTRA_PADDLE_COIN = 2;
    private final int LIFE_COIN = 3;
    private final int PUCK_BALL_COIN = 4;

    // Parameters related to the game
    private final CameraStrategyManager cameraStrategyManager;
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 extraPaddleInitialPosition;
    private final Vector2 heartDimensions;
    private final Renderable puckBallRenderable;
    private final Renderable heartRenderer;
    private final ExtraPaddle extraPaddle;
    private final Counter brickCounter;
    private final Counter lifeCounter;
    private final Sound ballSound;
    private final float puckBallRadius;
    private final int MAX_STRATEGIES_FOR_BRICK;
    private final int ballSpeed;
    private int doubleStrategyCount = DOUBLE_COIN;
    private final Random random = new Random();

    /**
     * Constructs a new BrickStrategyFactory with the specified parameters.
     *
     * @param lifeCounter                The counter for tracking player lives.
     * @param heartDimensions            The dimensions of heart images.
     * @param heartRenderer              The renderer for heart images.
     * @param puckBallRenderable         The renderer for puck ball images.
     * @param ballSound                  The sound to be played on ball collisions.
     * @param cameraStrategyManager      The ball collision counter for tracking collisions.
     * @param gameObjectCollection       The collection of game objects.
     * @param extraPaddleInitialPosition The initial position of the extra paddle.
     * @param puckBallRadius             The radius of the puck ball.
     * @param ballSpeed                  The speed of the ball.
     * @param maxStrategies              The maximum number of strategies for a brick.
     * @param extraPaddle                The extra paddle game object.
     * @param brickCounter               The counter for tracking bricks.
     */
    public BrickStrategyFactory(Counter lifeCounter,
                                Vector2 heartDimensions,
                                Renderable heartRenderer,
                                Renderable puckBallRenderable,
                                Sound ballSound,
                                CameraStrategyManager cameraStrategyManager,
                                GameObjectCollection gameObjectCollection,
                                Vector2 extraPaddleInitialPosition,
                                float puckBallRadius,
                                int ballSpeed,
                                int maxStrategies,
                                ExtraPaddle extraPaddle, Counter brickCounter) {
        this.gameObjectCollection = gameObjectCollection;
        this.heartRenderer = heartRenderer;
        this.heartDimensions = heartDimensions;
        this.extraPaddleInitialPosition = extraPaddleInitialPosition;
        this.puckBallRenderable = puckBallRenderable;
        this.ballSound = ballSound;
        this.ballSpeed = ballSpeed;
        this.puckBallRadius = puckBallRadius;
        this.cameraStrategyManager = cameraStrategyManager;
        this.lifeCounter = lifeCounter;
        this.brickCounter = brickCounter;
        this.MAX_STRATEGIES_FOR_BRICK = maxStrategies;
        this.extraPaddle = extraPaddle;
    }

    /**
     * Retrieves the appropriate CollisionStrategy based on the specified strategy type.
     *
     * @param strategy          The type of strategy.
     * @param collisionStrategy The collision strategy associated with this decorator.
     * @return The corresponding CollisionStrategy.
     */
    private CollisionStrategy getStrategy(Strategies strategy, CollisionStrategy collisionStrategy) {
        // Return the appropriate CollisionStrategy based on the strategy type
        return switch (strategy) {
            case PUCK_BALL -> new PuckBallStrategy(
                    this.gameObjectCollection,
                    this.puckBallRenderable,
                    this.ballSound,
                    this.puckBallRadius,
                    this.ballSpeed,
                    collisionStrategy);
            case EXTRA_PADDLE -> new AnotherPaddleStrategy(
                    this.gameObjectCollection, this.extraPaddle,
                    this.extraPaddleInitialPosition, collisionStrategy);
            case CAMERA -> new ChangeCameraStrategy(this.cameraStrategyManager, collisionStrategy);
            case DOUBLE_STRATEGY -> null;
            case NORMAL -> collisionStrategy;
            case LIFE -> new MoreLifeStrategy(
                    this.gameObjectCollection, this.heartRenderer,
                    this.lifeCounter, this.heartDimensions, collisionStrategy, this.brickCounter);
        };
    }

    /**
     * Generates an array of CollisionStrategy instances representing the strategies for a brick.
     * The array size is determined by the MAX_STRATEGIES_FOR_BRICK constant.
     * Strategies are selected based on probabilities defined in the chooseStrategy method.
     *
     * @return An array of CollisionStrategy instances representing the brick's strategies.
     */
    public CollisionStrategy chooseStrategies() {
        BasicCollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(
                this.gameObjectCollection,
                this.brickCounter);
        // Perform the first toss
        int coin = random.nextInt(FIRST_TOSS_BOUND);
        // Ensure correct probabilities
        CollisionStrategy collisionStrategy = getStrategies(coin, basicCollisionStrategy);
        // Handle the case of a regular strategy
        if (coin != DOUBLE_COIN) {
            return collisionStrategy;
            // Handle the case of a double strategy
        } else {
            this.doubleStrategyCount++; // Ensure that a special strategy was chosen
        }
        // Calculate the total number of double strategies that are randomized
        int totalDoubleCount = getTotalDoubleCount();
        // Choose functional strategies based on the number of double strategies
        collisionStrategy = chooseFinalSpecialStrategies(totalDoubleCount, basicCollisionStrategy);
        return collisionStrategy;
    }

    /**
     * Retrieves the appropriate strategy based on the result of a coin toss.
     *
     * @param coin              The result of the coin toss.
     * @param collisionStrategy The collision strategy associated with this decorator.
     * @return The chosen strategy.
     */
    private CollisionStrategy getStrategies(int coin, CollisionStrategy collisionStrategy) {
        return switch (coin) {
            case DOUBLE_COIN -> getStrategy(Strategies.DOUBLE_STRATEGY, collisionStrategy);
            case CAMERA_COIN -> getStrategy(Strategies.CAMERA, collisionStrategy);
            case EXTRA_PADDLE_COIN -> getStrategy(Strategies.EXTRA_PADDLE, collisionStrategy);
            case LIFE_COIN -> getStrategy(Strategies.LIFE, collisionStrategy);
            case PUCK_BALL_COIN -> getStrategy(Strategies.PUCK_BALL, collisionStrategy);
            default -> getStrategy(Strategies.NORMAL, collisionStrategy);
        };
    }

    /**
     * Calculates the total number of double strategies that are randomized.
     *
     * @return The total number of double strategies.
     */
    private int getTotalDoubleCount() {
        int coin;
        int totalDoubleCount = doubleStrategyCount;
        for (int i = 0; i < this.doubleStrategyCount; i++) {
            for (int j = 0; j < 2; ++j) {
                coin = this.random.nextInt(SPACIAL_CHOOSE_BOUND);
                if (coin == DOUBLE_COIN) {
                    this.doubleStrategyCount++;
                    totalDoubleCount++;
                }
            }
            this.doubleStrategyCount--;
        }
        return totalDoubleCount;
    }

    /**
     * Chooses final special strategies based on the total number of double strategies.
     *
     * @param totalDoubleCount The total number of double strategies.
     * @param collisionStrategy The collision strategy associated with this decorator.
     * @return The chosen collision strategy.
     */
    private CollisionStrategy chooseFinalSpecialStrategies(int totalDoubleCount,
                                                           CollisionStrategy collisionStrategy) {
        int coin;
        int i = 0;
        int numOfSpecialStrategiesWithoutDouble = totalDoubleCount + 1;
        CollisionStrategy newCollisionStrategy = collisionStrategy;
        while (i < this.MAX_STRATEGIES_FOR_BRICK && i < numOfSpecialStrategiesWithoutDouble) {
            coin = this.random.nextInt(CAMERA_COIN, SPACIAL_CHOOSE_BOUND);
            newCollisionStrategy = getStrategies(coin, newCollisionStrategy);
            i++;
        }
        return newCollisionStrategy;
    }
}
