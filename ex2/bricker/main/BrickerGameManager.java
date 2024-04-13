package bricker.main;

// -------- imports --------

import bricker.brick_strategies.BrickStrategyFactory;
import bricker.brick_strategies.CameraStrategyManager;
import bricker.gameobjects.*;

import danogl.gui.rendering.Renderable;
import danogl.gui.UserInputListener;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;

import java.awt.event.KeyEvent;
import java.util.Random;

// -------- imports --------

/**
 * The main class for the Bricker game. Extends GameManager to manage game initialization and updates.
 */
public class BrickerGameManager extends GameManager {

    // Constants for file paths
    private static final String BALL_COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String PUCK_BALL_IMAGE_PATH = "assets/mockBall.png";
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
    private static final String HEART_IMAGE_PATH = "assets/heart.png";
    private static final String BALL_IMAGE_PATH = "assets/ball.png";

    // Messages for game prompts
    private static final String PLAY_AGAIN_MESSAGE = " Play again?";
    private static final String YOU_LOSE_MESSAGE = "You lose!";
    private static final String YOU_WIN_MESSAGE = "You win!";
    private static final String WINDOW_TITLE = "Bricker game";

    // final Dimensions and sizes
    private static final int MAX_STRATEGIES_FOR_BRICK = 3;
    private static final double PUCK_BALL_FACTOR = 0.75;
    private static final int MINIMUM_VALID_LIFE = 1;
    private static final int ARGUMENTS_NUMBER = 2;
    private static final float HALF_FACTOR = 0.5F;
    private static final int SCREEN_HEIGHT = 500;
    private static final int SCREEN_WIDTH = 700;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BRICK_HEIGHT = 15;
    private static final int BORDER_WIDTH = 1000;
    private static final int HEART_RADIUS = 20;
    private static final int BALL_RADIUS = 20;
    private static final int BALL_SPEED = 200;
    private static final int INITIAL_LIFE = 3;
    private static final int MAX_LIFE = 4;
    private static final int BRICKS_GAP = 2;
    private static final int GAP = 4;

    // mutable constants
    private static int BRICKS_NUMBER_IN_COL = 7;
    private static int BRICKS_NUMBER_IN_ROW = 8;

    // Initial life and counters
    private final Counter lifeCounter = new BoundedCounter(MAX_LIFE);
    private final Counter brickCounter = new Counter(0);
    private final Renderable borderRenderable = null;

    // Game components
    private UserInputListener userInputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private ExtraPaddle extraPaddle; // only one in the game
    private Paddle paddle; // only one in the game
    private Ball ball; // only one in the game

    /**
     * Constructs a BrickerGameManager instance.
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     */
    private BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * Initializes the game components and sets up the game environment.
     *
     * @param imageReader      The ImageReader instance for loading images.
     * @param soundReader      The SoundReader instance for loading sounds.
     * @param inputListener    The UserInputListener instance for handling user input.
     * @param windowController The WindowController instance for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.userInputListener = inputListener;

        resetBrickCounter();
        resetLifeCounter();
        createBorders();
        createPaddles(imageReader);
        createBackground(imageReader);
        createBall(imageReader, soundReader);
        createBricks(imageReader, soundReader);
        createGraphicLifeCounter(imageReader);
        createNumericLifeCounter();
    }

    /**
     * Updates the game state in each frame.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        checkPaddle(this.paddle);
        checkPaddle(this.extraPaddle);
        checkCounterExtraPaddle();
        removeObjectsOutOfBoundaries();
    }

    /**
     * Removes game objects that are out of the game window bounds.
     */
    private void removeObjectsOutOfBoundaries() {
        for (GameObject object : this.gameObjects()) {
            // Remove any object that has moved out of the game window bounds
            if(checkBoundaries(object)){
                this.gameObjects().removeGameObject(object);
            }
        }
    }

    /**
     * checks if game object is out of the screen
     * @param object the game object to check
     * @return true or false
     */
    private boolean checkBoundaries(GameObject object){
        return (object.getTopLeftCorner().y() > this.windowDimensions.y() ||
                object.getTopLeftCorner().y() + BORDER_WIDTH < 0 ||
                object.getTopLeftCorner().x() - BORDER_WIDTH> this.windowDimensions.x() ||
                object.getTopLeftCorner().x() + BORDER_WIDTH < 0);
    }

    /**
     * Creates a NumericLifeCounter and adds it to the game objects.
     */
    private void createNumericLifeCounter() {
        int numericPositionX = GAP;
        int numericPositionY = (int) (this.windowDimensions.y() - HEART_RADIUS - GAP);
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(
                this.lifeCounter,
                new Vector2(numericPositionX, numericPositionY),
                new Vector2(HEART_RADIUS, HEART_RADIUS));
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);
    }

    /**
     * Creates a GraphicLifeCounter and adds it to the game objects.
     *
     * @param imageReader The ImageReader instance for loading images.
     */
    private void createGraphicLifeCounter(ImageReader imageReader) {
        Vector2 topLeftCorner = new Vector2(
                HEART_RADIUS + GAP,
                this.windowDimensions.y() - HEART_RADIUS - GAP);
        Vector2 dimensions = new Vector2(HEART_RADIUS, HEART_RADIUS);

        Renderable heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        GraphicLifeCounter graphicLifeCounter =
                new GraphicLifeCounter(topLeftCorner,
                        dimensions,
                        this.lifeCounter,
                        heartImage,
                        gameObjects(),
                        MAX_LIFE);
        gameObjects().addGameObject(graphicLifeCounter, Layer.UI);
    }

    /**
     * Checks for game end conditions (win or lose) and handles accordingly.
     */
    private void checkForGameEnd() {
        String prompt = "";
        if ((this.brickCounter.value() < MINIMUM_VALID_LIFE)
                || ((this.userInputListener != null)
                && this.userInputListener.isKeyPressed(KeyEvent.VK_W))) {
            // Player wins
            prompt = YOU_WIN_MESSAGE;
        }
        if (checkBoundaries(this.ball)) {
            // Player loses
            prompt = YOU_LOSE_MESSAGE;
            if (this.lifeCounter.value() > MINIMUM_VALID_LIFE) {
                this.lifeCounter.decrement();
                setBallInitialPosition();
                return;
            }
        }
        if (!prompt.isEmpty()) {
            // Display end game message and prompt to play again
            prompt += PLAY_AGAIN_MESSAGE;
            if (this.windowController.openYesNoDialog(prompt)) {
                this.windowController.resetGame();
            } else {
                this.windowController.closeWindow();
            }
        }
    }

    /**
     * Resets the brick counter to its initial value.
     */
    private void resetBrickCounter() {
        this.brickCounter.reset();
        this.brickCounter.increaseBy(BRICKS_NUMBER_IN_ROW * BRICKS_NUMBER_IN_COL);
    }

    /**
     * Resets the life counter to its initial value.
     */
    private void resetLifeCounter() {
        this.lifeCounter.reset();
        this.lifeCounter.increaseBy(INITIAL_LIFE);
    }

    /**
     * Creates bricks and adds them to the game objects.
     *
     * @param imageReader The ImageReader instance for loading images.
     * @param soundReader The SoundReader instance for loading sounds.
     */
    private void createBricks(ImageReader imageReader, SoundReader soundReader) {
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(
                this.lifeCounter, // Game life counter
                new Vector2(HEART_RADIUS, HEART_RADIUS), // Hearts dimensions
                imageReader.readImage(HEART_IMAGE_PATH, true), // Heart image
                imageReader.readImage(PUCK_BALL_IMAGE_PATH, true), // Puck ball image
                soundReader.readSound(BALL_COLLISION_SOUND_PATH),
                getBallCollisionCounter(this.windowController),
                gameObjects(),
                this.windowDimensions.mult(HALF_FACTOR), // Initial position for extra paddle
                (float) (BALL_RADIUS * PUCK_BALL_FACTOR),
                BALL_SPEED,
                MAX_STRATEGIES_FOR_BRICK,
                this.extraPaddle,
                this.brickCounter
        );

        createAllBricks(imageReader.readImage(BRICK_IMAGE_PATH, false),
                brickStrategyFactory);
    }

    /**
     * Creates all the bricks based on the specified parameters and adds them to the game objects.
     *
     * @param brickImage           The renderable of a brick image.
     * @param brickStrategyFactory The BrickStrategyFactory instance for creating brick strategies.
     */
    private void createAllBricks(Renderable brickImage, BrickStrategyFactory brickStrategyFactory) {
        // Calculate the width of each brick
        int brickWidth = ((SCREEN_WIDTH - (BALL_RADIUS * 2) - ((BRICKS_NUMBER_IN_ROW * BRICKS_GAP) - 1))
                / BRICKS_NUMBER_IN_ROW);

        // Create bricks in a grid pattern using nested loops
        for (int row = 0; row < BRICKS_NUMBER_IN_ROW; row++) {
            for (int col = 0; col < BRICKS_NUMBER_IN_COL; col++) {
                // Calculate the position of each brick
                Vector2 brickPosition = new Vector2(
                        BALL_RADIUS + (row * (brickWidth + BRICKS_GAP)),
                        BALL_RADIUS + (col * (BRICK_HEIGHT + BRICKS_GAP))
                );

                // Create a single brick and add it to the game objects
                createSingleBrick(brickImage, brickPosition, new Vector2(brickWidth, BRICK_HEIGHT),
                        brickStrategyFactory);
            }
        }
    }

    /**
     * Creates a BallCollisionCounter and returns it.
     *
     * @param windowController The WindowController instance for managing the game window.
     * @return The created BallCollisionCounter.
     */
    private CameraStrategyManager getBallCollisionCounter(WindowController windowController) {
        CameraStrategyManager cameraStrategyManager = new CameraStrategyManager(this.ball,
                this,
                windowController);
        gameObjects().addGameObject(cameraStrategyManager);
        return cameraStrategyManager;
    }

    /**
     * Creates a single brick and adds it to the game objects.
     *
     * @param brickImage           The ImageReader instance for loading images.
     * @param topLeftCorner        The top-left corner position of the brick.
     * @param dimensions           The dimensions of the brick.
     * @param brickStrategyFactory The BrickStrategyFactory instance for creating brick strategies.
     */
    private void createSingleBrick(Renderable brickImage,
                                   Vector2 topLeftCorner,
                                   Vector2 dimensions,
                                   BrickStrategyFactory brickStrategyFactory) {
        Brick brick = new Brick(topLeftCorner, dimensions, brickImage,
                brickStrategyFactory.chooseStrategies());
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
    }

    /**
     * Creates a paddle and adds it to the game objects.
     *
     * @param imageReader The ImageReader instance for loading images.
     */
    private void createPaddles(ImageReader imageReader) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, false);
        Paddle paddle = createMainPaddle(paddleImage);
        gameObjects().addGameObject(paddle);

        this.extraPaddle = new ExtraPaddle(Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                this.userInputListener,
                new Counter(0));
    }

    /**
     * create the main paddle
     * @param paddleImage paddleImage
     * @return Paddle object
     */
    private Paddle createMainPaddle(Renderable paddleImage) {
        Paddle paddle = new Paddle(Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                this.userInputListener);
        this.paddle = paddle;
        paddle.setCenter(new Vector2(this.windowDimensions.x() / ARGUMENTS_NUMBER,
                this.windowDimensions.y() - (HEART_RADIUS + PADDLE_HEIGHT)));
        return paddle;
    }

    /**
     * Creates a background and adds it to the game objects.
     *
     * @param imageReader The ImageReader instance for loading images.
     */
    private void createBackground(ImageReader imageReader) {
        GameObject background = new GameObject(
                Vector2.ZERO,
                this.windowController.getWindowDimensions(),
                imageReader.readImage(BACKGROUND_IMAGE_PATH, false));
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Creates a ball and adds it to the game objects.
     *
     * @param imageReader The ImageReader instance for loading images.
     * @param soundReader The SoundReader instance for loading sounds.
     */
    private void createBall(ImageReader imageReader,
                            SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(BALL_COLLISION_SOUND_PATH);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        setBallInitialPosition();
        gameObjects().addGameObject(this.ball);
    }

    /**
     * Sets the initial position and velocity of the ball.
     * The ball's center is set to the middle of the game window, and its velocity is randomized.
     */
    private void setBallInitialPosition() {
        // Set the ball's center to the middle of the game window
        this.ball.setCenter(this.windowDimensions.mult(HALF_FACTOR));

        // Set the initial speed for the ball
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;

        // Randomize the direction of the ball's velocity direction
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -MINIMUM_VALID_LIFE;
        if (rand.nextBoolean())
            ballVelY *= -MINIMUM_VALID_LIFE;

        // Set the randomized velocity for the ball
        this.ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Checks if the paddle is within the game window boundaries and adjusts its position if needed.
     *
     * @param paddle The paddle to check.
     */
    private void checkPaddle(Paddle paddle) {
        // Ensure the paddle stays within the game window boundaries
        float distanceFromRight = this.windowDimensions.x() - this.paddle.getDimensions().x();
        int distanceFromLeft = 0;
        float paddleLeftEdge = paddle.getTopLeftCorner().x();

        if (paddleLeftEdge < distanceFromLeft) {
            paddle.transform().setTopLeftCornerX(distanceFromLeft);
        }
        if (paddleLeftEdge > distanceFromRight) {
            paddle.transform().setTopLeftCornerX(distanceFromRight);
        }
    }

    /**
     * Checks if the extra paddle's collision counter has reached zero,
     * and removes it from the game objects if so.
     */
    private void checkCounterExtraPaddle() {
        // Remove the extra paddle if the collision counter reaches zero
        if (this.extraPaddle.getCollisionCounter() < MINIMUM_VALID_LIFE) {
            this.gameObjects().removeGameObject(this.extraPaddle);
        }
    }

    /**
     * Creates borders for the game window and adds them to the game objects.
     */
    private void createBorders() {
        // left border
        gameObjects().addGameObject(new GameObject(
                new Vector2(-BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                this.borderRenderable));
        // right border
        gameObjects().addGameObject(new GameObject(
                new Vector2(this.windowDimensions.x(), 0),
                new Vector2(BORDER_WIDTH, this.windowDimensions.y()),
                this.borderRenderable));
        // top border
        gameObjects().addGameObject(new GameObject(
                new Vector2(-BORDER_WIDTH, -BORDER_WIDTH),
                new Vector2(this.windowDimensions.x() + BORDER_WIDTH, BORDER_WIDTH),
                this.borderRenderable));
    }

    /**
     * The main method to start the Bricker game.
     *
     * @param args Command-line arguments (optional BRICKS_NUMBER_IN_ROW and BRICKS_NUMBER_IN_COL).
     */
    public static void main(String[] args) {
        if (args.length == ARGUMENTS_NUMBER) {
            BRICKS_NUMBER_IN_ROW = Integer.parseInt(args[0]);
            BRICKS_NUMBER_IN_COL = Integer.parseInt(args[1]);
        }
        new BrickerGameManager(WINDOW_TITLE, new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT)).run();
    }
}
