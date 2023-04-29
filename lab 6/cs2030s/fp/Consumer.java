package cs2030s.fp;

/**
 * Represents a function that consumes a value.
 * CS2030S Lab 5
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 * @param <T> The type of variable to be consumed by the consumer.
 */
@FunctionalInterface
public interface Consumer<T> {
  /**
   * The functional method to consume a value.
   *
   * @param arg the value to be consumed by the consumer
   */
  public abstract void consume(T arg);
}
