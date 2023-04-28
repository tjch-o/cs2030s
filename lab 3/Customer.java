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
  private int serviceType;
  
  public Customer(int customerId, double serviceTime, int serviceType) {
    this.customerId = customerId;
    this.serviceTime = serviceTime;
    this.serviceType = serviceType;
  }

  public double getEndTime(double startTime) {
    return this.serviceTime + startTime;
  }

  public String getServiceType() {
    if (this.serviceType == 0) {
      return "Deposit";
    } else if (this.serviceType == 1) {
      return "Withdrawal";
    } else {
      return "OpenAccount";
    }
  }
 
  @Override
  public String toString() {
    return "C" + this.customerId;
  }
}