/**
 * This class is a general class that represents
 * a customer which is identified with an id and
 * has a specific service time.
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY21/22 Semester 2
 */
public class Customer {
  private final int customerId;
  private double serviceTime;
  
  public Customer(int customerId, double serviceTime) {
    this.customerId = customerId;
    this.serviceTime = serviceTime;
  }

  public double getEndTime(double startTime) {
    return this.serviceTime + startTime;
  }

  @Override
  public String toString() {
    return "Customer " + this.customerId;
  }
}
