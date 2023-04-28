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
    // time to dequeue the next customer for service
    // the current customer will depart
    if (!(this.bank.isEmpty())) {
      Customer nextCustomerToServe = this.bank.nextCustomerFromQueue();
      return new Event[] {new BankDepartureEvent(this.getTime(), this.customer), 
        new BankServiceBeginEvent(this.getTime(), nextCustomerToServe, this.counter, this.bank)};
    } else {
      return new Event[] {new BankDepartureEvent(this.getTime(), this.customer)};
    }
  }

  @Override
  public String toString() {
    String str = ": " + this.customer.toString() + this.customer.getServiceType() +
        "done (by " +  this.counter.toString() + ")"; 
    return super.toString() + str;
  }
}
