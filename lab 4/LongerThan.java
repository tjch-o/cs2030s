/**
 * A boolean condition with parameter x that can be applied to
 * a string.  Returns true if the string is longer than x; false
 * otherwise.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 */
public class LongerThan implements BooleanCondition<String> {
  private final int limit;

  public LongerThan(int limit) {
    this.limit = limit;
  }

  @Override
  public boolean test(String testString) {
    return testString.length() > this.limit;
  }
}