package cs2030s.fp;

/**
 * Represents a conditional statement that returns either true of false.
 * CS2030S Lab 5
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 * @param <T> The type of the variable to be tested with this conditional statement.
 */
@FunctionalInterface
public interface BooleanCondition<T> {
  /**
   * The functional method to test if the condition is true/false on the given value t.
   *
   * @param t The variable to test
   * @return The return value of the test.
   */
  boolean test(T t);
}
