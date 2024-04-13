package bricker.gameobjects;

// -------- imports --------

import danogl.util.Counter;

// -------- imports --------


/**
 * BoundedCounter is a Counter implementation with an upper bound, uses for represent the game counter that
 * should not be more than a specific value.
 */
public class BoundedCounter extends Counter {
    private final int bound;

    /**
     * Constructs a BoundedCounter with an initial value and a specified upper bound.
     *
     * @param initValue Initial value of the counter.
     * @param bound     Upper bound of the counter.
     */
    public BoundedCounter(int initValue, int bound) {
        super(initValue);
        this.bound = bound;
        boundValue();
    }

    /**
     * Constructs a BoundedCounter with a specified upper bound and an initial value of 0.
     *
     * @param bound Upper bound of the counter.
     */
    public BoundedCounter(int bound) {
        super();
        this.bound = bound;
    }

    /**
     * Increments the counter and ensures the value does not exceed the upper bound.
     */
    @Override
    public void increment() {
        super.increment();
        boundValue();
    }

    /**
     * Increases the counter by a specified value and ensures the result does not exceed the upper bound.
     *
     * @param val Value to increase the counter by.
     */
    @Override
    public void increaseBy(int val) {
        super.increaseBy(val);
        boundValue();
    }

    /**
     * Bounds the counter value, resetting value to bound if it exceeds the bound.
     */
    private void boundValue() {
        if (this.value() > bound) {
            this.reset();
            this.increaseBy(this.bound);
        }
    }
}
