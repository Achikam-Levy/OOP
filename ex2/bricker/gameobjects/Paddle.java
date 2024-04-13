package bricker.gameobjects;

// -------- imports --------

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

// -------- imports --------

/**
 * Represents the paddle GameObject in the game, controlled by user input.
 * This class extends the GameObject class and implements movement based on user input for a paddle game.
 * The Paddle class provides functionality for handling user input to move the paddle horizontally within
 * the game window. The movement speed is defined by a constant, and the class ensures the paddle stays
 * within the window boundaries.
 *
 * @author Achikam Levy
 */
public class Paddle extends GameObject {

    // Movement speed of the paddle
    private final float MOVEMENT_SPEED = 600;
    private final UserInputListener inputListener;
    private final String MAIN_PADDLE_TAG = "paddle";

    /**
     * Constructs a Paddle instance.
     *
     * @param topLeftCorner The initial position of the top-left corner of the paddle.
     * @param dimensions    The dimensions of the paddle GameObject.
     * @param renderable    Renderable object representing the paddle's visual.
     * @param inputListener UserInputListener for processing user keyboard input.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.setTag(MAIN_PADDLE_TAG);
    }

    /**
     * Updates the Paddle instance during the game loop, handling user input and movement restrictions.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDirection = Vector2.ZERO;

        // Check for user input to determine movement direction
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDirection = movementDirection.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDirection = movementDirection.add(Vector2.RIGHT);
        }

        // Set velocity based on the determined movement direction and speed
        setVelocity(movementDirection.mult(MOVEMENT_SPEED));
    }
}
