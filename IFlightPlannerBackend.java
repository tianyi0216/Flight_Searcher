// --== CS400 Project Three File Header ==--
// Name: Wendell Cai
// CSL Username: wendell
// Email: wcai54@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader:?

import java.util.List;

/**
 * the interface of backend of the Fligh Planner
 */
public interface IFlightPlannerBackend {

    /*
     * constructor: FlightPlannerBackend()
     */

    /**
     * add the path to the backend
     * 
     * @param path the path of flight
     */
    public void addPath(IPath path);

    /**
     * Find the path that connects the two airport <br>
     * </br>
     * note: the oder of the airports matters
     * 
     * @param originAirportName      the origin airport
     * @param destinationAirportName the destination airport
     * @return the path that connect the two airport, the list will follow the order
     *         from origin -> midway(s) -> destination, null when no path was found
     */
    public List<IPath> findShortestPath(String originAirportName, String destinationAirportName);

    /**
     * Find all neighbor airport by the given airport
     * 
     * @param airport airport
     * @return the list of all neighbor airport
     */
    public List<String> findAllNeighborAirports(String airport);

    /**
     * Find all airport
     * 
     * @return the list of all neighbor airport, empty list when no path/airport were added
     */
    public List<String> getAllAirports();

}
