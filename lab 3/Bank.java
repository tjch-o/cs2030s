/**
 * This class is a general class that represents
 * a bank with counters.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class Bank {
  private Array<Counter> counters;
  // this queue is used to handle customers
  private Queue<Customer> bankQueue;
  private int maxBankQueueLength;
  private int maxCounterQueueLength;

  public Bank(int numberOfCounters, int maxCounterQueueLength, int maxBankQueueLength) {
    this.counters = new Array<Counter>(numberOfCounters);
    for (int j = 0; j < numberOfCounters; j += 1) {
      // we initialise the counters 
      this.counters.set(j, new Counter(maxCounterQueueLength));
    }
    
    this.maxBankQueueLength = maxBankQueueLength;
    this.bankQueue = new Queue<Customer>(this.maxBankQueueLength);
    this.maxCounterQueueLength = maxCounterQueueLength;
  }

  /** 
  * A function to check if there is a counter to serve the customer or customer will depart.
  *
  * @return a Counter object which is the first available counter if there is one.
  */
  public Counter getFirstAvailableCounter() {
    Counter counter = this.counters.min();
    // there is a possibility that all the counters are unavailable
    if (counter.isCounterAvailable()) {
      return counter;
    } else {
      return null;
    }
  }

  /**
  * A function to check if there are any counter queues with space.
  *
  * @return a Counter object which has the shortest queue if it does exist.
  */
  public Counter counterWithShortestQueue() {
    Counter shortestQueueCounter = this.counters.min();
    // if even the shortest counter here is full, then we should return null
    if (shortestQueueCounter.noMoreSpaceInCounterQueue()) {
      return null;
    } else {
      return shortestQueueCounter;
    }
  }

  public boolean addCustomerToBankQueue(Customer customer) {
    return this.bankQueue.enq(customer);
  } 

  public Customer nextCustomerFromBankQueue() {
    return this.bankQueue.deq();
  }

  public boolean noMoreSpaceInBankQueue() {
    return this.bankQueue.isFull();
  }

  public boolean isBankQueueEmpty() {
    return this.bankQueue.isEmpty();
  }

  @Override
  public String toString() {
    // we need this because we cannot directly access queue's toString in BankJoinQueueEvent
    return this.bankQueue.toString();
  }
}