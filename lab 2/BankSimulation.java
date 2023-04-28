import java.util.Scanner;

/**
 * This class implements a bank simulation.
 *
 * @author Choo Tze Jie
 * @version CS2030S AY21/22 Semester 2
 */ 
class BankSimulation extends Simulation {
  private Bank bank;

  /** 
   * The list of customer arrival events to populate
   * the simulation with.
   */
  private Event[] initEvents;

  /** 
   * Constructor for a bank simulation. 
   *
   * @param sc A scanner to read the parameters from.  The first
   *           integer scanned is the number of customers; followed
   *           by the number of service counters.  Next is a 
   *           sequence of (arrival time, service time) pair, each
   *           pair represents a customer.
   */
  public BankSimulation(Scanner sc) { 
    initEvents = new Event[sc.nextInt()];
    int numOfCounters = sc.nextInt();
    int maxLengthOfQueue = sc.nextInt();

    this.bank = new Bank(numOfCounters, maxLengthOfQueue);

    int id = 0;
    while (sc.hasNextDouble()) {
      double arrivalTime = sc.nextDouble();
      double serviceTime = sc.nextDouble();
      int serviceType = sc.nextInt();
      Customer customer = new Customer(id, serviceTime, serviceType);
      initEvents[id] = new BankArrivalEvent(arrivalTime, this.bank, customer);
      id += 1;
    }
  }

  /**
   * Retrieve an array of events to populate the 
   * simulator with.
   *
   * @return An array of events for the simulator.
   */
  @Override
  public Event[] getInitialEvents() {
    return initEvents;
  }
}