/**
 * This class is a general class that represents
 * a bank with counters.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class Bank {
  private Counter[] counters;
  // this queue is used to handle customers
  private Queue queue;
  private int maxQueueLength;

  public Bank(int numberOfCounters, int maxQueueLength) {
    this.counters = new Counter[numberOfCounters];
    for (int j = 0; j < numberOfCounters; j += 1) {
      // we initialise the counters 
      this.counters[j] = new Counter();
    }
   
    this.maxQueueLength = maxQueueLength;
    this.queue = new Queue(this.maxQueueLength);
  }

  /** 
  * A function to check if there is a counter to serve the customer or customer will depart.
  *
  * @return a Counter object which is the first available counter if there is one.
  */
  public Counter getFirstAvailableCounter() {
    Counter currentCounter;
    for (int i = 0; i < this.counters.length; i += 1) {
      currentCounter = this.counters[i];
      if (currentCounter.isCounterAvailable()) {
        return currentCounter;
      }
    }
    // if we reached the end of the loop then there are no available counters
    return null;
  }

  public boolean addCustomerToQueue(Customer customer) {
    return this.queue.enq(customer);
  } 

  public Customer nextCustomerFromQueue() {
    return (Customer) this.queue.deq();
  }

  public boolean noMoreSpace() {
    return this.queue.isFull();
  }

  public boolean isEmpty() {
    return this.queue.isEmpty();
  }

  @Override
  public String toString() {
    // we need this because we cannot directly access queue's toString in BankJoinQueueEvent
    return this.queue.toString();
  }
}