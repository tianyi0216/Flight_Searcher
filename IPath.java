// --== CS400 Project Three File Header ==--
// Name: Bill Zhu
// CSL Username: bzhu
// Email: wlzhu@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader:

/*
 * Instances of classes that implement this interface represent a single airport and its respective
 * * information.
 */
public interface IPath {

  // constructor args (String name, String destination, int distance)
  // instance fields include destination airport and origin airport, as well as an integer variable
  // to store the distance between the two airports

  public String getOrigin(); // Retrieves name of airport

  public String getDestination(); // Retrieves name of destination airport

  public int getPathCost(); // Retrieves path distance
}
