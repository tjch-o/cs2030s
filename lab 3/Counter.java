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
public class Counter implements Comparable<Counter> {
  private final int counterId;
  private static int lastId = 0;
  private boolean available;
  private Queue<Customer> counterQueue;

  public Counter(int maxLengthOfQueue) {
    this.counterId = Counter.lastId;
    // the default state of the counter is true
    this.available = true;
    // everytime we create a new counter we increase the last id by 1
    Counter.lastId += 1;
    this.counterQueue = new Queue<Customer>(maxLengthOfQueue);
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

  public boolean addCustomerToCounterQueue(Customer customer) {
    return this.counterQueue.enq(customer);
  }

  public Customer nextCustomerFromCounterQueue() {
    return this.counterQueue.deq();
  }

  public boolean noMoreSpaceInCounterQueue() {
    return this.counterQueue.isFull();
  }

  public boolean isCounterQueueEmpty() {
    return this.counterQueue.isEmpty();
  }

  public int lengthOfCounterQueue() {
    return this.counterQueue.length();
  }

  @Override
  public int compareTo(Counter otherCounter) {
    if (this == otherCounter) {
      // the same counter
      return 0;
    } else if (this.isCounterAvailable() && otherCounter.isCounterAvailable()) {
      // both counters are available so we take the smaller id
      return this.counterId < otherCounter.counterId ? -1 : 1;
    } else if (this.isCounterAvailable()) {
      return -1;
    } else if (otherCounter.isCounterAvailable()) {
      return 1;
    } else {
      // none of the counters are available so we return shortest queue
      if (this.lengthOfCounterQueue() < otherCounter.lengthOfCounterQueue()) {
        return -1;
      } else if (this.lengthOfCounterQueue() == otherCounter.lengthOfCounterQueue() &&
          this.counterId < otherCounter.counterId) {
        return -1;
      } else {
        return 1;
      }
    }
  }

  @Override 
  public String toString() {
    return "S" + this.counterId + " " + this.counterQueue.toString();
  }
}