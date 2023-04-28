/**
 * A conditional statement that returns either true of false.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 */
public interface BooleanCondition<T> {
  public abstract boolean test(T arg);
}