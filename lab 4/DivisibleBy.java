/**
 * A boolean condition with an integer parameter y that can be 
 * apply to another integer x.  Returns true if x is divisible 
 * by y, false otherwise.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 */
public class DivisibleBy implements BooleanCondition<Integer> {
  private final int divisor;

  public DivisibleBy(int divisor) {
    this.divisor = divisor;
  }
  
  @Override
  public boolean test(Integer input) {
    return input % divisor == 0;
  }
}

