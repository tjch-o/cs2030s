import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * A BusSg class encapsulate the data related to the bus services and
 * bus stops in Singapore, and supports queries to the data.
 *
 * @author:  Choo Tze Jie (Group 08H)
 * @version: CS2030S AY22/23 Semester 2, Lab 8
 */
class BusSg {
  /**
   * Given a bus stop and a name, find the bus services that serve between the given stop and any 
   * bus stop with matching names stored in a CompletableFuture.
   * @param  stop The bus stop.  Assume to be not null.
   * @param  searchString The (partial) name of other bus stops, assume not null.
   * @return The (optional) bus routes between the stops stored in a CompletableFuture.
   */
  public static CompletableFuture<BusRoutes> findBusServicesBetween(BusStop stop, 
      String searchString) {
    try {
      CompletableFuture<Set<BusService>> busServicesCF = stop.getBusServices();
      CompletableFuture<BusRoutes> output = busServicesCF.thenApply(x -> x.stream()
            .collect(Collectors.toMap(
                service -> service, 
                service -> service.findStopsWith(searchString)))
            )
          .thenApply(validServices -> new BusRoutes(stop, searchString, validServices));
      return output;
    } catch (CompletionException e) {
      System.err.println("Unable to complete query: " + e);
      return CompletableFuture.supplyAsync(() -> new BusRoutes(stop, searchString, Map.of()));
    }
  }
}