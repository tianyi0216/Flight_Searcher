// --== CS400 Project Three File Header ==--
// Name: Bill Zhu
// CSL Username: bzhu
// Email: wlzhu@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader:

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Instances of classes that implement this interface can be used to load a list of flight paths
 * from a specified json source file. The following json attributes are used to load these
 * attributes: - Origin: - Destination - Distance
 */
public interface IFlightLoader {

  /**
   * This method loads the list of flight paths described within a json file.
   * 
   * @param filepath is relative to executable's working directory
   * @return a list of flight paths that were read from specified file
   */
  public List<IPath> loadFlightPaths(String filepath) throws FileNotFoundException;
}
