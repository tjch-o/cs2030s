/**
 * This class is a general class that represents
 * a service end event, which is a subclass of
 * event. It takes in a bank and a customer.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class BankServiceEndEvent extends Event {
  private Customer customer;
  private Counter counter;

  public BankServiceEndEvent(double time, Customer customer, Counter counter) {
    super(time);
    this.customer = customer;
    this.counter = counter;
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
    return new Event[] {new BankDepartureEvent(this.getTime(), this.customer)};
  }

  @Override
  public String toString() {
    String str = ": " + this.customer.toString() + " service done (by " + 
        this.counter.toString() + ")"; 
    return super.toString() + str;
  }
}
