/**
 * A transformer with a parameter k that takes in an object x 
 * and outputs the last k digits of the hash value of x.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 */
public class LastDigitsOfHashCode implements Transformer<Object, Integer> {
  // we know that lastDigitsOfHashCode class is non-generic
  // it takes in any type of the content of the box and converts it into an integer
  // hence we know Type T of transformer should be an Object
  private final int lastKDigits;

  public LastDigitsOfHashCode(int k) {
    this.lastKDigits = k;
  }
  
  @Override
  public Integer transform(Object obj) {
    int hashcode = obj.hashCode();
    int tenPowerOfK = (int) Math.pow(10, this.lastKDigits);
    // test case has negative k
    int result = Math.abs(hashcode % tenPowerOfK);
    return result;
  }
}