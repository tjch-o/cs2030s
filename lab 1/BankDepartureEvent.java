public class BankDepartureEvent extends Event {
  private Customer customer;

  public BankDepartureEvent(double time, Customer customer) {
    super(time);
    this.customer = customer;
  }

  @Override
  public String toString() {
    String str = ": " + this.customer.toString() + " departed"; 
    return super.toString() + str;
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
}
