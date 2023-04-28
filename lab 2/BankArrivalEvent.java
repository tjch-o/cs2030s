/**
 * This class is a general class that represents
 * a arrival event, which is a subclass of event.
 * It takes in a bank and a customer since a 
 * customer arrives at the bank and looks for the 
 * available bank counter.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class BankArrivalEvent extends Event {
  private Bank bank;
  private Customer customer;

  public BankArrivalEvent(double time, Bank bank, Customer customer) {
    super(time);
    this.bank = bank;
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
    Counter availableCounter = this.bank.getFirstAvailableCounter();
    // we check if there is a available counter object 
    if (availableCounter != null) {
      return new Event[] {new BankServiceBeginEvent(this.getTime(), this.customer, 
          availableCounter, this.bank)};
    } else {
      // there is no more space in the queue so we chase the customer away
      if (this.bank.noMoreSpace()) {
        return new Event[] {new BankDepartureEvent(this.getTime(), this.customer)};
      } else {
        return new Event[] {new BankJoinQueueEvent(this.getTime(), this.customer, this.bank)}; 
      }
    }
  }

  @Override
  public String toString() {
    String str = ": " + this.customer.toString() + " arrived " + this.bank.toString(); 
    return super.toString() + str;
  }
}