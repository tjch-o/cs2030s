import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * Encapsulates the result of a query: for a bus stop and a search string,
 * it stores a map of bus services that servce stops with matching name.
 * e.g., stop 12345, name "MRT", with map contains:
 *    96: 11223 "Clementi MRT

/**
 * This program finds different ways one can travel by bus (with a bit 
 * of walking) from one bus stop to another.
 *
 * @author:  Choo Tze Jie (Group 08H)
 * @version: CS2030S AY22/23 Semester 2, Lab 8
 */
public class Main {
  /**
   * The program read a sequence of (id, search string) from either standard input or a file.  
   * If an invalid filename is given, the program would quit with an error message.
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    final Instant start = Instant.now();
     
    ArrayList<CompletableFuture<String>> busData = new ArrayList<>();

    try {
      Scanner sc = createScanner(args);
      while (sc.hasNext()) {
        BusStop srcId = new BusStop(sc.next());
        String searchString = sc.next();
        busData.add(BusSg.findBusServicesBetween(srcId, searchString).thenComposeAsync(route -> 
              route.description()));
      }
      sc.close();

      // allOf takes in a normal array instead of an arraylist hence we convert 
      CompletableFuture<?>[] descriptionCFArr = busData.toArray(new CompletableFuture<?>[0]);
      CompletableFuture.allOf(descriptionCFArr).thenRun(() -> busData.stream()
        .map(x -> x.join())
        .forEach(System.out::println))
        .join();
    } catch (FileNotFoundException exception) {
      System.err.println("Unable to open file " + args[0] + " "
          + exception);
    }

    final Instant stop = Instant.now();
    System.out.printf("Took %,dms\n", Duration.between(start, stop).toMillis());
  }

  /**
   * Create and return a scanner. If a command line argument is given,
   * treat the argument as a file and open a scanner on the file. Else,
   * create a scanner that reads from standard input.
   *
   * @param args The arguments provided for simulation.
   * @return A scanner.
   * @throws FileNotFoundException Throws if filename given is not valid.
   */
  private static Scanner createScanner(String[] args) throws FileNotFoundException {
    // Read from stdin if no filename is given, otherwise read from the
    // given file.
    if (args.length == 0) {
      // If there is no argument, read from standard input.
      return new Scanner(System.in);
    } 
    // Else read from file
    FileReader fileReader = new FileReader(args[0]);
    return new Scanner(fileReader);
  }
}
