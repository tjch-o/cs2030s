/**
 * This class is a general class that represents
 * a join queue event, which is a subclass of event.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class BankJoinCounterQueueEvent extends Event {
  private Customer customer;
  private Counter counter;

  public BankJoinCounterQueueEvent(double time, Customer customer, Counter counter) {
    super(time);
    this.customer = customer;
    this.counter = counter;
  }

  @Override
  public Event[] simulate() {
    this.counter.addCustomerToCounterQueue(this.customer); 
    // they just join the queue no other event to be created
    return new Event[] {};
  }

  @Override 
  public String toString() {
    String str = ": " + this.customer.toString() + " joined counter queue (at " +
        this.counter.toString() + ")"; 
    return super.toString() + str;
  }
}
