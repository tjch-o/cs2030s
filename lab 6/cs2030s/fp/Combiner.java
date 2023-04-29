package cs2030s.fp;

/**
 * Represents a function that combines two values into one.  The two inputs
 * and the result can be of different types.
 * CS2030S Lab 5
 * AY22/23 Semester 2
 * 
 * @author Choo Tze Jie (Lab Group 08H)
 * @param <S> The type of the first input value
 * @param <T> The type of the second input value
 * @param <R> The type of the return value
 */
@FunctionalInterface
public interface Combiner<S, T, R> {
  /**
   * The function method to combines two values into one.
   *
   * @param s The first input value
   * @param t The second input value
   * @return  The value after combining s and t.
   */
  R combine(S s, T t);
}
