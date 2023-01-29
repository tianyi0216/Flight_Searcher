// --== CS400 File Header Information ==--
// Name: Tianyi Xu
// Email: txu223@wisc.edu
// Team: BL
// TA: Sujitha
// Lecturer: Gary
// Notes to Grader: N/A

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class test the DijkstraGraph implemented by Algorithm Engineer. 
 * All test are in Junit and test all methods/feature by the Graph
 * 
 * @author Tianyi Xu
 */
public class AlgorithmEngineerTests {
    DijkstraGraph<String> test; // graph used for testing

    /**
     * initialize a graph object before each test
     */
    @BeforeEach
    public void createGraph(){
        test = new DijkstraGraph<String>();
    }

    /**
     * Test the constructor of the Dijkstra graph along with simple methods of the graph.
     * Test if correctly initialize a graph object with all fields. And test methods such as 
     * isEmpty, getEdgeCount and getVertexCount.
     */
    @Test
    public void test1(){
        // case 1, test the constructor calls initialization of test
        assertTrue(test != null);
        assertTrue(test.nodes != null);
        assertEquals(test.edgeSize, 0);

        // case 2, test isEmpty for graph, should be true and edgecount should be 0
        assertTrue(test.isEmpty());
        assertEquals(test.getEdgeCount(), 0);
        assertEquals(test.getVertexCount(), 0);

        // add vertices to test getVertexCount()
        test.insertVertex("AAA");
        assertEquals(test.getVertexCount(), 1);
        
        test.insertVertex("JFK");
        test.insertVertex("ORD");
        assertEquals(test.getVertexCount(), 3);

        // add edges to test for getEdgeCount
        test.insertEdge("AAA","JFK", 3);
        assertEquals(test.getEdgeCount(), 1);

        test.insertEdge("JFK", "ORD", 4);
        test.insertEdge("ORD", "JFK", 1);
        assertEquals(test.getEdgeCount(), 3);

        // test isempty and size now
        assertTrue(!test.isEmpty());
        assertEquals(test.edgeSize, 3);
        assertEquals(test.nodes.size(), 3);
    }

    /**
     * Test if the insertVertex, edge and remove vertex, edge method of the class correctly functions.
     * Test cases include add vertex, edges normally, add duplicate vertex and correctly allows for
     * duplicate origin, destination vertices.
     * All method also need to correctly modify vertex and edge count.
     */
    @Test
    public void test2(){
        // case 1, test insert one vertex and see if it exists
        test.insertVertex("JFK");
        assertTrue(test.nodes.get("JFK") != null);

        // case 2, insert duplicate and should be false
        assertTrue(test.insertVertex("JFK") == false);

        // insert few more vertex for testing
        test.insertVertex("LAX");
        test.insertVertex("ORD");
        test.insertVertex("IDK");
        assertEquals(test.nodes.size(), 4);

        // case 3, test insertEdge method, getweight should also return 3
        test.insertEdge("LAX", "ORD", 3);
        assertEquals(test.edgeSize, 1);
        assertEquals(test.getWeight("LAX", "ORD"), 3); 
        
        // case 4, insert multiple edges and test geitweight
        test.insertEdge("LAX", "JFK", 1);
        test.insertEdge("JFK", "IDK", 5);
        assertEquals(test.edgeSize, 3);
        assertEquals(test.getWeight("LAX", "JFK"), 1);
        assertEquals(test.getWeight("JFK", "IDK"), 5);

        // case 5, test same origin and destination insertion
        test.insertEdge("LAX", "JFK", 3);
        // size increased, but distance still 1
        assertEquals(test.edgeSize, 4);
        assertEquals(test.getWeight("LAX", "JFK"), 1);

        // case 6, test insert duplicate Edge weight, locations, should return false
        assertEquals(test.insertEdge("LAX", "JFK", 3), false);
        
        // case 7, test default, removeEdge should remove shorter one
        test.removeEdge("LAX", "JFK");
        assertEquals(test.edgeSize, 3);
        assertEquals(test.getWeight("LAX", "JFK"), 3);

        // case 8, add back and remove longer one
        test.insertEdge("LAX", "JFK", 1);
        test.removeEdge("LAX", "JFK", 3);
        assertEquals(test.edgeSize, 3);
        assertEquals(test.getWeight("LAX", "JFK"), 1);

        // case 9, add a long vertex and test removeVertex, should not change edgeSize
        test.insertVertex("ABC");
        assertEquals(test.nodes.size(), 5);
        test.removeVertex("ABC");
        assertEquals(test.nodes.size(), 4);
        assertEquals(test.edgeSize, 3); // edge size should still be 3

        // case 10, remove a vertex with one edge connecting to but no outgoing edge
        test.removeVertex("IDK");
        assertEquals(test.nodes.size(), 3);
        assertEquals(test.edgeSize, 2); // should be 2 now
        assertTrue(!test.containsEdge("JFK", "IDK"));

        // case 11, remove a vertex with outgoing edge, should remove all edge connecting to
        // result edgeSize should be 0
        test.removeVertex("LAX");
        assertEquals(test.nodes.size(), 2);
        assertEquals(test.edgeSize, 0);
        assertTrue(!test.containsEdge("LAX", "JFK"));
        assertTrue(!test.containsEdge("LAX", "ORD"));
    }

    /**
     * Test the contains method for the graph, cases include see if it can correctly find a vertex or edge
     * from the graph and return true/false correctly.Also tests the getAllNeighborVertices and getAllVertices method
     * to see if it correctly returns all connecting vertices/vertices, cases include none connecting vertex, 1 or
     * multiple connecting vertices.
     */
    @Test
    public void test3(){
        // case 1, insert a vertex and see if contains works
        test.insertVertex("OPK");
        assertTrue(test.containsVertex("OPK"));

        // case 2, try contains on a non-existing vertex
        assertTrue(!test.containsVertex("LOL"));

        // case 3, insert multiple vertex and test contains
        test.insertVertex("AAA");
        test.insertVertex("BBB");
        test.insertVertex("CCC");
        assertTrue(test.containsVertex("AAA"));

        // case 4, try containsEdge
        test.insertEdge("OPK", "AAA", 3);
        test.insertEdge("AAA", "BBB", 2);
        assertTrue(test.containsEdge("OPK", "AAA"));

        // case 5, try containsEdge on a non-existed path
        assertTrue(!test.containsEdge("BBB", "CCC"));

        // case 6, try overloading method containsEdge
        // should find edge with cost 6, but not find cost 1 edge
        test.insertEdge("OPK", "AAA", 6);
        assertTrue(test.containsEdge("OPK", "AAA", 6));
        assertTrue(!test.containsEdge("OPK", "AAA", 1));

        // case 7 test neighboring vertices method, test on vertex have no connections
        // return list should have size 0
        assertEquals(test.allNeighborVertices("BBB").size() , 0);
        assertTrue(test.allNeighborVertices("BBB").isEmpty());

        // case 8, test neighboring vertices on a vertex have 1 connections only
        assertEquals(test.allNeighborVertices("AAA").size(), 1);
        assertTrue(test.allNeighborVertices("AAA").contains("BBB"));

        // case 9, add several edges and test method can find all connecting neighbors
        test.insertEdge("OPK", "BBB", 3);
        test.insertEdge("OPK", "CCC", 2);
        assertEquals(test.allNeighborVertices("OPK").size(), 3);
        assertTrue(test.allNeighborVertices("OPK").contains("BBB")
            && test.allNeighborVertices("OPK").contains("CCC") && 
            test.allNeighborVertices("OPK").contains("AAA"));

        // case 10, test getAllVertices Method, test if returns all vertices
        assertEquals(test.getAllVertices().size(), 4);
        assertTrue(test.getAllVertices().contains("AAA") && test.getAllVertices().contains("BBB")
            && test.getAllVertices().contains("CCC") && test.getAllVertices().contains("OPK"));
        
        // case 11, create an empty graph and test getAllVertices method
        DijkstraGraph<String> empty = new DijkstraGraph<String>();
        assertTrue(empty.getAllVertices().isEmpty());
        assertEquals(empty.getAllVertices().size(), 0);
    }

    /**
     * Test if the dijkstra algorithm's get shortest path correctly return  apath between two
     * vertices. Test cases include vertices next to each other, vertices cannot find a path, 
     * vertices with a long path, two paths find the shorter one etc. Should correctly return 
     * a path of values in a list.
     */
    @Test
    public void test4(){
        // insert several vertex and edges for testing
        test.insertVertex("AAA");
        test.insertVertex("BBB");
        test.insertVertex("CCC");
        test.insertVertex("DDD");
        test.insertVertex("EEE");
        test.insertEdge("AAA", "BBB", 3);

        // case 1, test if two inputs are the same, should return list with 1 vertex
        assertEquals(test.shortestPath("AAA", "AAA").size(), 1);
        assertTrue(test.shortestPath("AAA", "AAA").contains("AAA"));

        // case 2, shortest path from AAA to BBB, should be 3
        assertEquals(test.shortestPath("AAA", "BBB").size(), 2);
        assertTrue(test.shortestPath("AAA", "BBB").contains("AAA") &&
        test.shortestPath("AAA", "BBB").contains("BBB"));

        // case 3, test shortest path for a unreachable vertex, should throw noSuchElementException
        assertThrows(NoSuchElementException.class, () -> {test.shortestPath("AAA", "CCC");});

        // add more edges to sert for shortest path
        test.insertEdge("BBB", "CCC", 3);
        test.insertEdge("CCC", "DDD", 3);

        // case 4, test an shortest path with multiple vertex
        assertEquals(test.shortestPath("AAA", "DDD").size(), 4);
        assertTrue(test.shortestPath("AAA", "DDD").contains("BBB") && 
            test.shortestPath("AAA", "DDD").contains("CCC"));
        
        // case 5, create a shorter path from AAA to DDD to see if this time returns this path
        test.insertEdge("AAA", "EEE", 4);
        test.insertEdge("EEE", "DDD", 1);
        assertEquals(test.shortestPath("AAA", "DDD").size(), 3);
        assertTrue(test.shortestPath("AAA", "DDD").contains("EEE"));

        // case 6, test insert a higher cost edge between EEE and DDD does not influence result
        test.insertEdge("EEE", "DDD", 999);
        assertEquals(test.shortestPath("AAA", "DDD").size(), 3);
        assertTrue(test.shortestPath("AAA", "DDD").contains("EEE"));
    }

    /**
     * Test if the dijkstra algorithm's getpathcost correctly return the shortest cost between two
     * vertices. Test cases include vertices next to each other, vertices cannot find a path, 
     * vertices with a long path, two paths find the shorter one etc. Should correctly return 
     * the shortest possible cost.
     */
    @Test
    public void test5(){
         // insert several vertex and edges for testing
         test.insertVertex("AAA");
         test.insertVertex("BBB");
         test.insertVertex("CCC");
         test.insertVertex("DDD");
         test.insertVertex("EEE");
         test.insertEdge("AAA", "BBB", 3);

        // case 1, test if two inputs are the same, should return cost 0
        assertEquals(test.getPathCost("AAA", "AAA"), 0);

         // case 2, test path cost for a unreachable vertex, should throw noSuchElementException
        assertThrows(NoSuchElementException.class, () -> {test.getPathCost("AAA", "CCC");});

        // case 3, test path cost for direct path between two vertex
        assertEquals(test.getPathCost("AAA", "BBB"), 3);

        // case 4, add more edge to test for path cost
        test.insertEdge("BBB", "CCC", 2);
        test.insertEdge("CCC", "DDD", 4);
        assertEquals(test.getPathCost("AAA", "DDD"), 9);

        // case 5, add a shorter route, should detect that route and have that shorter path
        test.insertEdge("AAA", "EEE", 5);
        test.insertEdge("EEE", "DDD", 1);
        assertEquals(test.getPathCost("AAA", "DDD"), 6);

        // case 6, insert a duplicate edge between E and D with higher weight, should not modify
        // the path cost between
        test.insertEdge("EEE", "DDD", 15);
        assertEquals(test.getPathCost("AAA", "DDD"), 6);
    }

    /**
     * Testing DW's path class with AE, test cases include add Path object into graph to see
     * if it can be added properly using AE's insertVertex and insertEdge method. Test to see
     * if correctly update Vertex count and edge count and correctly added to graph.
     */
    @Test
    public void test6(){
        IPath pathA = new Path("AAA", "BBB", 15);
        IPath pathB = new Path("BBB", "DDD", 3);
        IPath pathC = new Path("CCC", "AAA", 10);
        IPath pathD = new Path("DDD", "CCC", 7);

        // case1, add vertices to see if correctly added
        test.insertVertex(pathA.getOrigin());
        test.insertVertex(pathB.getOrigin());
        test.insertVertex(pathC.getOrigin());
        test.insertVertex(pathD.getOrigin());

        // test to see if DW's path's getOrigin work as the vertex, and the containsvertex can find it
        assertEquals(test.getVertexCount(), 4);
        assertTrue(test.containsVertex("AAA"));
        assertTrue(test.containsVertex("BBB"));
        assertTrue(test.containsVertex("CCC"));
        assertTrue(test.containsVertex("DDD"));

        // test 2, add all the edges into the graph, test to see if able to extract path
        test.insertEdge(pathA.getOrigin(), pathA.getDestination(), pathA.getPathCost());
        test.insertEdge(pathB.getOrigin(), pathB.getDestination(), pathB.getPathCost());
        test.insertEdge(pathC.getOrigin(), pathC.getDestination(), pathC.getPathCost());
        test.insertEdge(pathD.getOrigin(), pathD.getDestination(), pathD.getPathCost());

        // test getEdgeCount work with DW's Path, Path's getOrigin/Destination/PathCost should
        // act like a normal edge. Also testing AE's containsEdge, getEdgeCount can find those/
        // return correct size
        assertEquals(test.getEdgeCount(), 4);
        assertTrue(test.containsEdge(pathA.getOrigin(), pathA.getDestination()));
        assertTrue(test.containsEdge(pathB.getOrigin(), pathB.getDestination()));
        assertTrue(test.containsEdge(pathC.getOrigin(), pathC.getDestination()));
        assertTrue(test.containsEdge(pathD.getOrigin(), pathD.getDestination()));
    }

    /**
     * Test if it can add a large data file into the graph. Call DW's loader to load all paths
     * and add all of them into a graph to see if it can be added
     * @throws FileNotFoundException if file is not found
     */
    @Test
    public void test7() throws FileNotFoundException{
        FlightLoader loader = new FlightLoader();
        List<IPath> paths = loader.loadFlightPaths("flights.json");
        
        // case 1, test if correctly loads all paths
        assertEquals(paths.size(), 4136);
        
        // add all into the test
        for(IPath path: paths){
            test.insertVertex(path.getOrigin());
            test.insertVertex(path.getDestination());
            test.insertEdge(path.getOrigin(), path.getDestination(), path.getPathCost());
        }

        // insert vertex and edgeSize should be able to insert 4135 path/edge
        assertEquals(test.edgeSize, 4135);
        assertEquals(test.nodes.size(), 312);
    }

    /**
     * Code Review for BD, test if BD's addPath, findNeighborPath and getAllAirport
     * method works with AE's code. Cases include no path added, add path to 
     * test get all airport and get neighbroing, get neighborairport on airport has 
     * no connections etc. 
     */
    @Test
    public void test8(){
        // hardcode somepath to add
        IPath a = new Path("AAA", "BBB", 3);
        IPath b = new Path("BBB", "CCC", 3);
        IPath c = new Path("AAA", "DDD", 3);
        IPath d = new Path("DDD", "AAA", 3);
        IPath e = new Path("AAA", "CCC", 3);

        FlightPlannerBackend backend = new FlightPlannerBackend();

        // case 1 ,test get Airport on empty backend, should return empty string
        assertEquals(backend.getAllAirports().size(), 0);
        assertTrue(backend.getAllAirports().isEmpty());

        // test findPath, should return null
        assertEquals(backend.findShortestPath("AAA", "BBB"), null);
        backend.addPath(a);
        backend.addPath(b);
        backend.addPath(c);
        backend.addPath(d);
        backend.addPath(e);

        // case 1, should add 5 edge and 4 vertex into graph, check edgeSize
        assertEquals(backend.graph.getEdgeCount(), 5);
        assertEquals(backend.graph.getVertexCount(), 4);

        // case 2, test the getAllAirportMethod, should find 4 airports and contains all of them
        assertEquals(backend.getAllAirports().size(), 4);
        assertTrue(backend.getAllAirports().contains("AAA"));
        assertTrue(backend.getAllAirports().contains("BBB"));
        assertTrue(backend.getAllAirports().contains("CCC"));
        assertTrue(backend.getAllAirports().contains("DDD"));

        // case 3, test getNeighboring airport on A and B
        assertEquals(backend.findAllNeighborAirports("AAA").size(), 3);
        assertTrue(backend.findAllNeighborAirports("AAA").contains("BBB"));
        assertTrue(backend.findAllNeighborAirports("AAA").contains("CCC"));
        assertTrue(backend.findAllNeighborAirports("AAA").contains("DDD"));

        // B should find 1 airport connecting to it.
        assertEquals(backend.findAllNeighborAirports("BBB").size(), 1);
        assertTrue(backend.findAllNeighborAirports("BBB").contains("CCC"));

        // case 4, test getNeighbroing Airport on airport has no connection, should
        // return an empty list
        assertEquals(backend.findAllNeighborAirports("CCC").size(), 0);
        assertTrue(backend.findAllNeighborAirports("CCC").isEmpty());
    }

    /**
     * Code Review for BD, test BD's findShortestPath method to see if it works with AE's method.
     * Test case include finding path when no airport exists, find direct path, find shorter path
     * between multiplt path etc. Test to see if return correct list of path and contains correct 
     * vertex.
     */
    @Test
    public void test9(){
        FlightPlannerBackend backend = new FlightPlannerBackend();
        // case 1, find path when no airport is in, should return null
        assertEquals(backend.findShortestPath("AAA", "BBB"), null);

        // insert several paths for testing
        IPath a = new Path("AAA", "BBB", 4);
        IPath b = new Path("BBB", "CCC", 1);
        IPath c = new Path("AAA", "DDD", 5);
        IPath d = new Path("DDD", "AAA", 8);
        IPath e = new Path("AAA", "CCC", 7);
        backend.addPath(a);
        backend.addPath(b);
        backend.addPath(c);
        backend.addPath(d);
        backend.addPath(e);

        // case 2, find the path between AAA and BBB, should return a string of 1 path
        assertEquals(backend.findShortestPath("AAA", "BBB").size(), 1);

        // case 3, find the path between AAA and CCC, should choose the shorter one 
        // rather than direct path
        assertEquals(backend.findShortestPath("AAA","CCC").size(), 2);
        assertTrue(backend.findShortestPath("AAA", "CCC").toString().contains("BBB") && 
            backend.findShortestPath("AAA", "CCC").toString().contains("CCC"));

        // case 4, find a path where cannot be able to get, should return null
        assertEquals(backend.findShortestPath("BBB", "AAA"), null);
    }
}

