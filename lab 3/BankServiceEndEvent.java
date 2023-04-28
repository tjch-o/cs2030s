/**
 * This class is a general class that represents
 * a service end event, which is a subclass of
 * event. It takes in a customer and a counter.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class BankServiceEndEvent extends Event {
  private Customer customer;
  private Counter counter;
  private Bank bank;

  public BankServiceEndEvent(double time, Customer customer, Counter counter, Bank bank) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.bank = bank;
  }
  
  /**
   * The logic that the simulation should follow when simulating
   * this event.
   *
   * @return An array of new events to be simulated.
   */
  @Override 
  public Event[] simulate() {
    // we make the counter available again
    this.counter.makeCounterAvailable();
    // time to dequeue the next customer for service from the counter queue
    // also dequeue another customer from the entrance queue to fill the counter queue space
    // the current customer will depart
    if (!(this.counter.isCounterQueueEmpty())) {
      Customer nextCustomerToServe = this.counter.nextCustomerFromCounterQueue();
      if (this.bank.isBankQueueEmpty()) {
        // there is no one in the entrance queue
        return new Event[] {new BankDepartureEvent(this.getTime(), this.customer), 
          new BankServiceBeginEvent(this.getTime(), nextCustomerToServe, this.counter, this.bank)};
      } else {
        // someone leaves entrance queue to join counter queue
        Customer lastCustomerInCounterQueue = this.bank.nextCustomerFromBankQueue();
        return new Event[] {new BankDepartureEvent(this.getTime(), this.customer),
          new BankServiceBeginEvent(this.getTime(), nextCustomerToServe, this.counter, this.bank), 
          new BankJoinCounterQueueEvent(this.getTime(), lastCustomerInCounterQueue, this.counter)}; 
      }
    } else if (!(this.bank.isBankQueueEmpty())) {
      // the counter queue might be empty but entrance queue is not empty
      Customer nextCustomerToServe = this.bank.nextCustomerFromBankQueue();
      return new Event[] {new BankDepartureEvent(this.getTime(), this.customer), 
        new BankServiceBeginEvent(this.getTime(), nextCustomerToServe, this.counter, this.bank)};
    } else {
      // both counter queue and bank queue are empty
      return new Event[] {new BankDepartureEvent(this.getTime(), this.customer)};
    }
  }

  @Override
  public String toString() {
    String str = ": " + this.customer.toString() + " " +  this.customer.getServiceType() +
        " done (by " +  this.counter.toString() +  ")"; 
    return super.toString() + str;
  }
}