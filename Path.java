// --== CS400 Project Three File Header ==--
// Name: Bill Zhu
// CSL Username: bzhu
// Email: wlzhu@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader:

/**
 * This class includes all the behaviors and data for the Path object. 
 * @author williamzhu
 */
public class Path implements IPath {
  private String origin; //The origin airport
  private String destination; //The destination airport
  private int distance; //The distance between the two airports

  /**
   * Constructor - initializes the instance variables
   * @param origin The origin airport
   * @param destination The destination airport
   * @param distance The distance between the two airports.
   */
  public Path(String origin, String destination, int distance) {
    this.origin = origin;
    this.destination = destination;
    this.distance = distance;
  }

  @Override
  /**
   * Getter method for the origin data field.
   */
  public String getOrigin() {
    return origin;
  }

  @Override
  /**
   * Getter method for the destination data field
   */
  public String getDestination() {
    return destination;
  }

  @Override
  /**
   * Getter method for the distance data field
   */
  public int getPathCost() {
    return distance;
  }
}
