// --== CS400 Project Three File Header ==--
// Name: Bill Zhu
// CSL Username: bzhu
// Email: wlzhu@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader:

import java.util.List;

/**
 * This class serves as a placeholder for the FlightApp file in order to facilitate the testing of
 * the DataWrangler's files
 * 
 * @author williamzhu
 */
public class DWFlightAppPlaceholder {
  public static void main(String[] args) throws Exception {
    FlightLoader loader = new FlightLoader();
    List<IPath> flights = loader.loadFlightPaths("test.json");
  }
}
