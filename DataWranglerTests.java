// --== CS400 Project Three File Header ==--
// Name: Bill Zhu
// CSL Username: bzhu
// Email: wlzhu@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: The Peer Code tests are in another file named DataWranglerPeerCodeTests since 
// it uses javafx. As described in the file, it doesn't work on my computer despite hours of troubleshooting
// and attendence of office hours. Thus I don't really know if they work. I tried my best to convey what
// I wanted to test and wrote code referenced from the frontendDeveloper's tests. 

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
* Tester for DataWrangler classes. Peer review tests for Frontend Developer is in another file 
* named DataWranglerPeerCodeTests since it uses javafx. While these tests work, I can't seem to 
* get the Peer Code tests to work as I have documented in the file. 
*/
class DataWranglerTests {

  @Test
  /**
   * Tester for the Path class. Creates a path instance and checks if the data is correct.
   */
   void test1() {
    // Path instance
    Path path = new Path("Kentucky", "Fried", 9);

    // Checking that all the data is correct.
    assertEquals(path.getOrigin(), "Kentucky");
    assertEquals(path.getDestination(), "Fried");
    assertEquals(path.getPathCost(), 9);

  }

  /**
   * Tester for loading in a file with both correct and incorrect inputs. Test passes if the correct
   * input is properly executed and the incorrect inputs throw exceptions.
   */
  @Test
  public void test2() {
    try {
      // Initializing variable used in the test.
      List<IPath> file = new FlightLoader().loadFlightPaths("test.json");

      // Checking that the proper input was indeed correct.
      assertEquals(file.size(), 8);

      // Testing improper inputs which should throw exceptions.
      assertThrows(FileNotFoundException.class, () -> {
        new FlightLoader().loadFlightPaths("Mr._Piggy's_Fantastic_Adventures");
      });

      assertThrows(FileNotFoundException.class, () -> {
        new FlightLoader().loadFlightPaths("SomethingElse.json");
      });

    } catch (Exception e) {
      // Improper inputs did not throw an exception, thus failing this test.
      fail();
    }
  }

  /**
   * Tester for parsing a path from the JSON file. Parses one path and checks if it was done
   * correctly.
   */
  @Test
  public void test3() {
    try {
      // Initializing variable used in the test.
      List<IPath> file = new FlightLoader().loadFlightPaths("test.json");

      // Reference path - what the parsed path should be like.
      Path path = new Path("ANC", "SEA", 1448);

      // Checking parsed path's data.
      assertEquals(file.get(0).getOrigin(), path.getOrigin());
      assertEquals(file.get(0).getDestination(), path.getDestination());
      assertEquals(file.get(0).getPathCost(), path.getPathCost());

    } catch (Exception e) {
      // No exceptions should be thrown in this scenario, and thus if it does, the test failed.
      fail();
    }
  }

  /**
   * Tests the parsing of the JSON file by checking the number of paths parsed.
   */
  @Test
  public void test4() {
    try {
      // Initializing variable used in the test.
      List<IPath> file = new FlightLoader().loadFlightPaths("test.json");

      // Checking list size. Should have 8 paths from JSON file.
      assertEquals(file.size(), 8);

    } catch (Exception e) {
      // No exceptions should be thrown in this scenario, and thus if it does, the test failed.
      fail();
    }
  }

  /**
   * Tests the parsing of the JSON file by checking all the paths that should be included.
   */
  @Test
  public void test5() {
    try {
      // Initializing variables used in the test and reference variables.
      List<IPath> file = new FlightLoader().loadFlightPaths("test.json");
      Path path1 = new Path("ANC", "SEA", 1448);
      Path path2 = new Path("LAX", "PBI", 2330);
      Path path3 = new Path("SFO", "CLT", 2296);
      Path path4 = new Path("LAX", "MIA", 2342);
      Path path5 = new Path("SEA", "ANC", 1448);
      Path path6 = new Path("SFO", "MSP", 1589);
      Path path7 = new Path("LAS", "MSP", 1299);
      Path path8 = new Path("MKE", "ORD", 67);

      // Testing each path parsed from the JSON file.
      assertEquals(file.get(0).getOrigin(), path1.getOrigin());
      assertEquals(file.get(0).getDestination(), path1.getDestination());
      assertEquals(file.get(0).getPathCost(), path1.getPathCost());

      assertEquals(file.get(1).getOrigin(), path2.getOrigin());
      assertEquals(file.get(1).getDestination(), path2.getDestination());
      assertEquals(file.get(1).getPathCost(), path2.getPathCost());

      assertEquals(file.get(2).getOrigin(), path3.getOrigin());
      assertEquals(file.get(2).getDestination(), path3.getDestination());
      assertEquals(file.get(2).getPathCost(), path3.getPathCost());

      assertEquals(file.get(3).getOrigin(), path4.getOrigin());
      assertEquals(file.get(3).getDestination(), path4.getDestination());
      assertEquals(file.get(3).getPathCost(), path4.getPathCost());

      assertEquals(file.get(4).getOrigin(), path5.getOrigin());
      assertEquals(file.get(4).getDestination(), path5.getDestination());
      assertEquals(file.get(4).getPathCost(), path5.getPathCost());

      assertEquals(file.get(5).getOrigin(), path6.getOrigin());
      assertEquals(file.get(5).getDestination(), path6.getDestination());
      assertEquals(file.get(5).getPathCost(), path6.getPathCost());


      assertEquals(file.get(6).getOrigin(), path7.getOrigin());
      assertEquals(file.get(6).getDestination(), path7.getDestination());
      assertEquals(file.get(6).getPathCost(), path7.getPathCost());

      assertEquals(file.get(7).getOrigin(), path8.getOrigin());
      assertEquals(file.get(7).getDestination(), path8.getDestination());
      assertEquals(file.get(7).getPathCost(), path8.getPathCost());

      // Testing that path8 is the last path in the list.
      assertEquals(file.size(), 8);
      assertEquals(file.get(file.size() - 1).getOrigin(), path8.getOrigin());
      assertEquals(file.get(file.size() - 1).getDestination(), path8.getDestination());
      assertEquals(file.get(file.size() - 1).getPathCost(), path8.getPathCost());

    } catch (Exception e) {
      // No exceptions should be thrown in this scenario, and thus if it does, the test failed.
      fail();
    }
  }

  /**
   * Integration test with the AE. Tests that the Path class is being added to the graph properly. 
   */
  @Test
  public void test6() {
    //Initializing necessary variables
    DijkstraGraph<String> graph = new DijkstraGraph<String>();
    IPath path1 = new Path("HLK", "BDS", 203);
    IPath path2 = new Path("GNS", "RRE", 34);
    IPath path3 = new Path("DSE", "VSM", 10);
    IPath path4 = new Path("QOS", "SLE", 387);
    IPath path5 = new Path("GJE", "GNS", 383);

    //Setting up graph
    graph.insertVertex(path1.getOrigin());
    graph.insertVertex(path1.getDestination());
    graph.insertVertex(path2.getOrigin());
    graph.insertVertex(path2.getDestination());
    graph.insertVertex(path3.getOrigin());
    graph.insertVertex(path3.getDestination());
    graph.insertVertex(path4.getOrigin());
    graph.insertVertex(path4.getDestination());
    graph.insertVertex(path5.getOrigin());
    graph.insertVertex(path5.getDestination());

    //Inserting testing variables into graph
    graph.insertEdge(path1.getOrigin(), path1.getDestination(), path1.getPathCost());
    graph.insertEdge(path2.getOrigin(), path2.getDestination(), path2.getPathCost());
    graph.insertEdge(path3.getOrigin(), path3.getDestination(), path3.getPathCost());
    graph.insertEdge(path4.getOrigin(), path4.getDestination(), path4.getPathCost());
    graph.insertEdge(path5.getOrigin(), path5.getDestination(), path5.getPathCost());

    //Testing proper insertion 
    assertEquals(graph.getEdgeCount(), 5);
    assertEquals(graph.containsEdge(path1.getOrigin(), path1.getDestination()), true);
    assertEquals(graph.containsEdge(path2.getOrigin(), path2.getDestination()), true);
    assertEquals(graph.containsEdge(path3.getOrigin(), path3.getDestination()), true);
    assertEquals(graph.containsEdge(path4.getOrigin(), path4.getDestination()), true);
    assertEquals(graph.containsEdge(path5.getOrigin(), path5.getDestination()), true);
  }
  
   /**
   * Tests the parsing of the actual flights JSON file by checking the number of paths parsed.
   */
  @Test
  public void test7() {
    try {
      // Initializing variable used in the test.
      List<IPath> file = new FlightLoader().loadFlightPaths("flights.json");

      // Checking list size. Should have 4136 paths from JSON file.
      System.out.println(file.size());
      assertEquals(file.size(), 4136);

    } catch (Exception e) {
      // No exceptions should be thrown in this scenario, and thus if it does, the test failed.
      fail();
    }
  }
}
