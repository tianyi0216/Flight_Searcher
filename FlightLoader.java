// --== CS400 Project Three File Header ==--
// Name: Bill Zhu
// CSL Username: bzhu
// Email: wlzhu@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader:

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that reads and parses the JSON file, returning a list of Path objects
 * 
 * @author williamzhu
 *
 */
public class FlightLoader implements IFlightLoader {

  /**
   * Parses the JSON file and returns a list of the path objects.
   */
  public List<IPath> loadFlightPaths(String filepath) throws FileNotFoundException {
    List<IPath> paths = new ArrayList<IPath>(); // List to return
    File flightPaths = new File(filepath); // File to look in
    Scanner fileReader = new Scanner(flightPaths); // Scans the file to look in.
    String line = ""; // Temporary storing variable for each line.

    // Edge case: No applicable file to look in. Throws an exception
    if (!flightPaths.exists()) {
      throw new FileNotFoundException("File does not exist.");
    }

    // Iterates through each line of the file.
    while (fileReader.hasNext()) {
      line = fileReader.nextLine();

      // Temporary variables to store parsed data in order to create a path file
      String origin = "";
      String destination = "";
      int distance = 0;

      // Edits out the useless beginning and ending lines
      if (line.equals("["))
        line = fileReader.nextLine();
      if (line.equals("]"))
        return paths;

      // Parses the line and extracts the important information (Origin airport, destination
      // airport, and distance between the two).
      for (String arg : line.split(",")) {
        String[] keyValuePair = arg.split(":");
        switch (keyValuePair[0]) {
          // Parses the origin airport data.
          case "{\"ORIGIN_AIRPORT\"":
            origin = keyValuePair[1].substring(1, keyValuePair[1].length() - 1);
            break;
          // Parses the destination airport data.
          case "\"DESTINATION_AIRPORT\"":
            destination = keyValuePair[1].substring(1, keyValuePair[1].length() - 1);
            break;
          // Parses the distance data.
          case "\"DISTANCE\"":
            Pattern distances = Pattern.compile("\\d+");
            Matcher distanceMatcher = distances.matcher(line);
            distanceMatcher.find();
            distance = Integer.parseInt(distanceMatcher.group());
            break;
        }
      }

      // Creates a path object with the temporary variables and adds it to the list.
      IPath path = new Path(origin, destination, distance);
      paths.add(path);
    }
    return paths;
  }
}
