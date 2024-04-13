package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * Represents a class responsible for managing ball collisions and game camera.
 * This class extends GameObject and provides functionality to count ball collisions
 * and dynamically adjust the game camera based on collision count.
 *
 * @see Ball
 * @author Achikam Levy
 */
public class CameraStrategyManager extends GameObject {

    // Constants
    private final float CAMERA_CHANGE_FACTOR = 1.2F; // Factor for changing camera size
    private final int CAMERA_STRATEGY_BOUND = 4; // Threshold for camera strategy activation

    // Variables
    private int currentCollisionCount = 0; // Current collision count

    // Associated game objects
    private final Ball ball; // The associated ball for collision counting
    private final BrickerGameManager gameManager; // The game manager managing game state
    private final WindowController windowController; // The window controller managing game window

    /**
     * Constructor for the CameraManagement class.
     *
     * @param ball             The Ball object associated with this collision counter.
     * @param gameManager      The BrickerGameManager managing the overall game state.
     * @param windowController The WindowController managing the game window.
     */
    public CameraStrategyManager(Ball ball, BrickerGameManager gameManager,
                                 WindowController windowController) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.gameManager = gameManager;
        this.windowController = windowController;
    }

    /**
     * Starts counting ball collisions and manages the game camera.
     */
    public void startCount() {
        // If camera is not set, reset collision count and manage camera
        if (this.gameManager.camera() == null) {
            this.currentCollisionCount = this.ball.getCollisionCounter();
            manageCamera();
        }
    }

    /**
     * Overrides the update method from the GameObject class.
     * Updates the collision counter and resets the camera when a specific collision count is reached.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // If collision count reaches threshold, reset camera and the collision count
        if (this.ball.getCollisionCounter() >= this.currentCollisionCount + this.CAMERA_STRATEGY_BOUND) {
            this.gameManager.setCamera(null);
            this.currentCollisionCount = this.ball.getCollisionCounter();
        }
    }

    /**
     * Manages the game camera based on the ball's collision count.
     */
    private void manageCamera() {
        // If collision count matches current count and camera is not set, adjust camera size
        if (this.currentCollisionCount == this.ball.getCollisionCounter()
                && this.gameManager.camera() == null) {
            this.gameManager.setCamera(
                    new Camera(
                            this.ball,
                            Vector2.ZERO,
                            this.windowController.getWindowDimensions().mult(CAMERA_CHANGE_FACTOR),
                            this.windowController.getWindowDimensions()
                    )
            );
        }
    }
}
