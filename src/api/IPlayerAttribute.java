package api;

/**
 * The interface for the model.player attribute class.
 *
 * @author Aninda Manocha
 */

public interface IPlayerAttribute {
    /**
     * Increases the amount of the attribute
     */
    void increase();

    /**
     * Decreases the amount of the attribute
     */
    void decrease();

    /**
     * Gets the name/type (i.e. "health") of the attribute
     *
     * @return the name/type
     */
    String getName();

    /**
     * Gets the amount of the attribute
     *
     * @return the amount
     */
    double getAmount();

    /**
     * Gets the increment by which the amount is increased by (whenever the amount of the attribute is increased, it is
     * increased by this increment amount)
     *
     * @return the increment
     */
    double getIncrement();

    /**
     * Gets the decrement by which the amount is decreased by (whenever the amount of the attribute is decreased, it is
     * decreased by this decrement amount)
     *
     * @return the decrement
     */
    double getDecrement();
}