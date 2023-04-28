/**
 * This class is a general class that represents
 * a join queue event, which is a subclass of event.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class BankJoinBankQueueEvent extends Event {
  private Customer customer;
  private Bank bank;

  public BankJoinBankQueueEvent(double time, Customer customer, Bank bank) {
    super(time);
    this.customer = customer;
    this.bank = bank;
  }

  @Override
  public Event[] simulate() {
    this.bank.addCustomerToBankQueue(this.customer); 
    return new Event[] {};
  }

  @Override 
  public String toString() {
    String str = ": " + this.customer.toString() + " joined bank queue " + this.bank.toString(); 
    return super.toString() + str;
  }
}