// --== CS400 Project Three File Header ==--
// Name: Wendell Cai
// CSL Username: wendell
// Email: wcai54@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader:?

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FlightPlannerBackend implements IFlightPlannerBackend {
    protected IDijkstraGraph<String> graph;

    public FlightPlannerBackend() {
        graph = new DijkstraGraph<>();
    }

    /**
     * add the path to the backend
     * 
     * @param path the path of flight
     */
    @Override
    public void addPath(IPath path) {
        graph.insertVertex(path.getOrigin());
        graph.insertVertex(path.getDestination());
        graph.insertEdge(path.getOrigin(), path.getDestination(), path.getPathCost());
    }

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
    @Override
    public List<IPath> findShortestPath(String originAirportName, String destinationAirportName) {
        List<String> airports;
        // in case that no airport were found
        try {
            airports = graph.shortestPath(originAirportName, destinationAirportName);
            List<IPath> paths = new ArrayList<>();
            String currentA = originAirportName;
            for (int i = 1; i < airports.size(); i++) {
                String currentAirport = currentA + "";
                String currentB = airports.get(i);
                int cost = graph.getPathCost(currentAirport, currentB);
                paths.add(new IPath() {
                    public String getOrigin() {
                        return currentAirport;
                    }

                    public String getDestination() {
                        return currentB;
                    }

                    public int getPathCost() {
                        return cost;
                    }

                    public String toString() {
                        return currentAirport + "\t=>\t" + currentB + ": " + cost + " Miles";
                    }
                });
                currentA = currentB;
            }
            return paths;
        } catch (NoSuchElementException e) {
            // not path were found, return null
            return null;
        }
    }

    /**
     * Find all neighbor airport by the given airport
     * 
     * @param airport airport
     * @return the list of all neighbor airport
     */
    @Override
    public List<String> findAllNeighborAirports(String airport) {
        List<String> ret = graph.allNeighborVertices(airport);
        ret.sort(null);
        return ret;
    }

    /**
     * Find all airport
     * 
     * @return the list of all neighbor airport
     */
    @Override
    public List<String> getAllAirports() {
        List<String> ret = graph.getAllVertices();
        ret.sort(null);
        return ret;
    }

}