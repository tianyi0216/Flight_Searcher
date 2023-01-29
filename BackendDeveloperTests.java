// --== CS400 Project Three File Header ==--
// Name: Wendell Cai
// CSL Username: wendell
// Email: wcai54@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader:?

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * class for test FlightPlannerBackend
 */
public class BackendDeveloperTests {

    FlightPlannerBackend bd;
    List<IPath> paths;

    @BeforeEach
    public void initialize() {
        bd = new FlightPlannerBackend();
    }

    /**
     * test for single path input for backend
     */
    @Test
    public void testAddAirport() {
        bd.addPath(new Path("A", "B", 6));
        assertEquals(bd.getAllAirports().toString(), "[A, B]");
        assertEquals(bd.findAllNeighborAirports("A").toString(), "[B]");
        assertEquals(bd.findAllNeighborAirports("B").toString(), "[]");
        assertEquals(bd.findShortestPath("A", "B").get(0).toString(), "A\t=>\tB: 6 Miles");
    }

    /**
     * Test for backend add multiple path correctly, no airport has been added
     * multiple times
     */
    @Test
    public void testAddMultiplePath() {
        // add all paths but in different order
        bd.addPath(new Path("A", "B", 6));
        bd.addPath(new Path("A", "C", 2));
        bd.addPath(new Path("A", "D", 5));
        bd.addPath(new Path("B", "E", 1));
        bd.addPath(new Path("B", "C", 2));
        bd.addPath(new Path("C", "B", 3));
        bd.addPath(new Path("C", "F", 1));
        bd.addPath(new Path("D", "E", 3));
        bd.addPath(new Path("E", "A", 4));
        bd.addPath(new Path("F", "A", 1));
        bd.addPath(new Path("F", "D", 1));
        List<String> destinations = bd.getAllAirports();

        destinations.sort(null);
        assertEquals(destinations.toString().replace(" ", ""), "[A,B,C,D,E,F]");
    }

    /**
     * Test for invalid airport
     */
    @Test
    public void testInvalidAirport() {
        // add all paths but in different order
        bd.addPath(new Path("D", "E", 3));
        bd.addPath(new Path("E", "A", 4));
        bd.addPath(new Path("C", "B", 3));
        bd.addPath(new Path("F", "A", 1));
        bd.addPath(new Path("F", "D", 1));
        bd.addPath(new Path("A", "B", 6));
        bd.addPath(new Path("A", "D", 5));
        bd.addPath(new Path("B", "E", 1));
        bd.addPath(new Path("A", "C", 2));
        bd.addPath(new Path("B", "C", 2));
        bd.addPath(new Path("C", "F", 1));

        // one invalid argument
        List<IPath> paths = bd.findShortestPath("a", "E");
        assertEquals(paths, null);

        // two invalid argument but the same
        paths = bd.findShortestPath("a", "a");
        assertEquals(paths, null);

        // two invalid argument but not the same
        paths = bd.findShortestPath("", "a");
        assertEquals(paths, null);
    }

    /**
     * Test for order of the path were correct
     */
    @Test
    public void testFindPath() {
        bd.addPath(new Path("A", "B", 6));
        bd.addPath(new Path("A", "C", 2));
        bd.addPath(new Path("A", "D", 5));
        bd.addPath(new Path("B", "E", 1));
        bd.addPath(new Path("B", "C", 2));
        bd.addPath(new Path("C", "B", 3));
        bd.addPath(new Path("C", "F", 1));
        bd.addPath(new Path("D", "E", 3));
        bd.addPath(new Path("E", "A", 4));
        bd.addPath(new Path("F", "A", 1));
        bd.addPath(new Path("F", "D", 1));
        List<IPath> paths = bd.findShortestPath("A", "E");

        assertEquals(paths.size(), 3);
        assertEquals(paths.get(0).toString(), "A\t=>\tC: 2 Miles");
        assertEquals(paths.get(1).toString(), "C\t=>\tB: 3 Miles");
        assertEquals(paths.get(2).toString(), "B\t=>\tE: 1 Miles");
    }

    /**
     * Test for whether the backend get all neighbor airport correctly
     */
    @Test
    public void testNeighborAirport() {
        bd.addPath(new Path("A", "B", 6));
        bd.addPath(new Path("A", "C", 2));
        bd.addPath(new Path("A", "D", 5));
        bd.addPath(new Path("B", "E", 1));
        bd.addPath(new Path("B", "C", 2));
        bd.addPath(new Path("C", "B", 3));
        bd.addPath(new Path("C", "F", 1));
        bd.addPath(new Path("D", "E", 3));
        bd.addPath(new Path("E", "A", 4));
        bd.addPath(new Path("F", "A", 1));
        bd.addPath(new Path("F", "D", 1));

        List<String> destinations = bd.findAllNeighborAirports("A");
        destinations.sort(null);
        assertEquals(destinations.toString().replace(" ", ""), "[B,C,D]");
    }

    /**
     * Test for whether the backend get all neighbor airport correctly
     */
    @Test
    public void test6() {
        try {
            for (IPath iPath : new FlightLoader().loadFlightPaths("flights.json")) {
                bd.addPath(iPath);
            }
        } catch (Exception e) {
            fail(e);
        }

        List<String> destinations = bd.findAllNeighborAirports("GSP");
        destinations.sort(null);
        assertEquals(destinations.toString().replace(" ", ""), "[ATL,BWI,DFW,DTW,EWR,HOU,IAD,IAH,LGA,MDW,ORD]");
    }

    /**
     * Test for whether the backend get all airport path correctly
     */
    @Test
    public void test7() {
        try {
            for (IPath iPath : new FlightLoader().loadFlightPaths("flights.json")) {
                bd.addPath(iPath);
            }
        } catch (Exception e) {
            fail(e);
        }

        List<IPath> paths = bd.findShortestPath("GSP", "MSN");
        assertEquals(paths.size(), 2);
        assertEquals(paths.get(0).toString(), "GSP\t=>\tORD: 578 Miles");
        assertEquals(paths.get(1).toString(), "ORD\t=>\tMSN: 108 Miles");
    }

    @Test
    /**
     * Test for AE's graph normal find path function
     */
    public void test8() {
        DijkstraGraph<String> graph = new DijkstraGraph<>();
        // create a graph
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        graph.insertEdge("A", "B", 6);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "D", 5);
        graph.insertEdge("B", "E", 1);
        graph.insertEdge("B", "C", 2);
        graph.insertEdge("C", "B", 3);
        graph.insertEdge("C", "F", 1);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("E", "A", 4);
        graph.insertEdge("F", "A", 1);
        graph.insertEdge("F", "D", 1);
        // see wether graph behave as expect
        List<String> paths = graph.shortestPath("A", "E");
        assertEquals(paths.size(), 4);
        assertEquals(paths.get(0), "A");
        assertEquals(paths.get(1), "C");
        assertEquals(paths.get(2), "B");
        assertEquals(paths.get(3), "E");

        paths = graph.shortestPath("A", "A");
        assertEquals(paths.size(), 1);
        assertEquals(paths.get(0), "A");
    }

    @Test
    /**
     * test for graph handling exceptions
     */
    public void test9() {

        DijkstraGraph<String> graph = new DijkstraGraph<>();
        // create a graph
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        graph.insertEdge("A", "B", 6);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "D", 5);
        graph.insertEdge("B", "E", 1);
        graph.insertEdge("B", "C", 2);
        graph.insertEdge("C", "B", 3);
        graph.insertEdge("C", "F", 1);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("E", "A", 4);
        graph.insertEdge("F", "A", 1);
        graph.insertEdge("F", "D", 1);
        // see wether graph behave as expect
        try {
            List<String> paths = graph.shortestPath("A", "G");
            fail();
        } catch (NoSuchElementException e) {
            // behave as intended
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * internal test class for temporary Path class used inside backend testers only
     */
    class Path implements IPath {
        String origin;
        String destination;
        int distance;

        public Path(String origin, String destination, int distance) {
            this.origin = origin;
            this.destination = destination;
            this.distance = distance;
        }

        @Override
        public String getOrigin() {
            return origin;
        }

        @Override
        public String getDestination() {
            return destination;
        }

        @Override
        public int getPathCost() {
            return distance;
        }

    }

}
