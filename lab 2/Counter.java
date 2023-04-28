/**
 * This class is a general class that represents a 
 * counter. A counter is either available or 
 * occupied. It also has a id assigned to it and
 * there is a lastId which is used to do increment
 * for id.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class Counter {
  private final int counterId;
  private static int lastId = 0;
  private boolean available;

  public Counter() {
    this.counterId = Counter.lastId;
    // the default state of the counter is true
    this.available = true;
    // everytime we create a new counter we increase the last id by 1
    Counter.lastId += 1;
  }

  public boolean isCounterAvailable() {
    return this.available;
  }
  
  public void makeCounterAvailable() {
    this.available = true;
  }

  public void makeCounterUnavailable() {
    this.available = false;
  }

  @Override 
  public String toString() {
    return "S" + this.counterId;
  }
}