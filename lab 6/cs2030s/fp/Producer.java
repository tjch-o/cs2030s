package cs2030s.fp;

/**
 * Represent a function that produce a value.
 * CS2030S Lab 5
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 * @param <T> The type of the value produced.
 */
@FunctionalInterface
public interface Producer<T> {
  /**
   * The functional method to produce a value.
   *
   * @return The value produced.
   */
  T produce();
}
