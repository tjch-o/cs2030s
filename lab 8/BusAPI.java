import java.io.IOException;
import java.lang.InterruptedException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * The BusAPI class interface with the Web API running at 
 * cs2030-bus-api.herokuapp.com to retrieve:
 * - bus services that serve a given bus stop 
 * - bus stop visited by a bus service.
 *
 * @author:  Choo Tze Jie (Group 08H)
 * @version: CS2030S AY22/23 Semester 2, Lab 8
 **/
class BusAPI {
  /** 
   * URL to query for bus stops. 
   * An alternative is "https://www.comp.nus.edu.sg/~ooiwt/bus_services/"
   */
  private static final String BUS_SERVICE_API = 
      "https://cs2030-bus-api.herokuapp.com/bus_services/";

  /** 
   * URL to query for bus services. 
   * An alternative is "https://www.comp.nus.edu.sg/~ooiwt/bus_stops/"
   **/
  private static final String BUS_STOP_API = 
      "https://cs2030-bus-api.herokuapp.com/bus_stops/";

  /**
   * Given a URL, asynchronously obtained the HTTP response string stored in a CompletableFuture
   * from the URL.  It returns an empty string in a CompletableFuture and prints an error message 
   * if the URL is invalid (not the best behavior).
   * @param url The URL to query
   * @return The HTTP response body, or an empty string if the 
   *     query fails, in which it is encapsulated within a CompletableFuture.
   */
  private static CompletableFuture<String> httpGet(String url) {
    HttpClient client = HttpClient.newBuilder()
        .build();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .build();
    CompletableFuture<HttpResponse<String>> responseCF;
 
    responseCF = client.sendAsync(request, BodyHandlers.ofString()).exceptionally(e -> { 
      throw new CompletionException(e);
    });

    return responseCF.thenApply(response -> {
      if (response.statusCode() != 200) {
        System.out.println(response + " " + response.statusCode());
        return "";
      }
      return response.body();
    });
  }

  /**
   * Returns a CompletableFuture containing the string from CS2030 BUS API when given a bus 
   * service ID.
   * @param serviceId Bus service id
   * @return A CompletableFuture containing the string returned by the CS2030 BUS API service 
   *     listing the bus stops that are serviced at this bus. Return a CompletableFuture 
   *     containing an empty string if something goes wrong.
   */ 
  public static CompletableFuture<String> getBusStopsServedBy(String serviceId) {
    return httpGet(BUS_SERVICE_API + serviceId);
  }

  /**
   * Returns a CompletableFuture containing the string from CS2030 BUS API given a bus stop ID.
   * @param stopId Bus stop id
   * @return A CompletableFuture containing the string returned by the CS2030 BUS API service 
   *     listing the bus services that stopped at this bus stop; CompletableFuture containing an 
   *     empty string if the API query failed.
   */ 
  public static CompletableFuture<String> getBusServicesAt(String stopId) {
    return httpGet(BUS_STOP_API + stopId);
  }
}