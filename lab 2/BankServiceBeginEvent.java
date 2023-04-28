/**
 * This class is a general class that represents
 * a service begin event which is a subclass of
 * event. It takes in a customer and a counter.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class BankServiceBeginEvent extends Event {
  private Customer customer;
  private Counter counter;
  private Bank bank;

  public BankServiceBeginEvent(double time, Customer customer, Counter counter, Bank bank) {
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
    // we make the counter unavailable until the customer is served
    this.counter.makeCounterUnavailable();
    // the time when the service ends which we will use to generate a service end event
    double serviceEndTime = this.customer.getEndTime(this.getTime());
    return new Event[] {new BankServiceEndEvent(serviceEndTime, this.customer, this.counter, 
        this.bank)};
  }

  @Override
  public String toString() {
    String str = ": " + this.customer.toString() + this.customer.getServiceType()  +
        "begin (by " +  this.counter.toString()  + ")";  
    return super.toString() + str;
  }
}