/**
 * This class is a general class that represents
 * a departure event, which is a subclass of
 * event. It takes in a customer.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class BankDepartureEvent extends Event {
  private Customer customer;

  public BankDepartureEvent(double time, Customer customer) {
    super(time);
    this.customer = customer;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this event.
   *
   * @return An array of new events to be simulated.
   */
  @Override 
  public Event[] simulate() {
    return new Event[]{};
  }

  @Override
  public String toString() {
    String str = ": " + this.customer.toString() + " departed"; 
    return super.toString() + str;
  }
}