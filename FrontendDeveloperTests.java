// --== CS400 File Header Information ==--
// Name: Haozhe Wu
// CSL Username: haozhew
// Email: hwu435@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: <any optional extra notes to your grader>

import javafx.scene.control.TextField;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import edu.wisc.cs.cs400.JavaFXTester;
import org.junit.jupiter.api.Test;


/**
 * Test class for FlightPlannerFrontend.java
 */
public class FrontendDeveloperTests extends JavaFXTester {
  // this JavaFXTester class implements the TestFX FXRobot class, documented here:
  // https://testfx.github.io/TestFX/docs/javadoc/testfx-core/javadoc/org.testfx/org/testfx/api/FxRobot.html

  /**
   * specify the Application being tested
   */

  public FrontendDeveloperTests() {
    super(FlightPlannerFrontend.class);
  }


  /**
   * Test the basic scene
   */
  @Test
  public void test1() {
    Label start = lookup("#start").query();
    assertEquals("Departure", start.getText());

    Label end = lookup("#end").query();
    assertEquals("Destination", end.getText());

    Label out = lookup("#out").query();
    assertEquals("Welcome to Flight Planner", out.getText());

  }

  /**
   * Test all the variables before any change or inputs
   */
  @Test
  public void test2() {
    FlightPlannerFrontend FP = new FlightPlannerFrontend();
    assertEquals(0, FP.numIntermediates);

    TextField from = lookup("#from").query();
    assertEquals("", from.getText());

    TextField to = lookup("#to").query();
    assertEquals("", to.getText());

    // click search, because there is nothing in the from, the out should be "Please enter your
    // Departure"
    Button search = lookup("#search").query();
    interact(() -> search.fireEvent(new ActionEvent()));

    Label out = lookup("#out").query();
    assertEquals("Please enter your Departure", out.getText());
  }

  /**
   * Search the shortest path from an airport to another airport Because I used the BD placeholder
   * here, so the only path in the whole class is msn -> ord
   * <p>
   * After all the tests, test the clear()
   */
  @Test
  public void test3() {
    TextField from = lookup("#from").query();
    from.setText("LAX");
    TextField to = lookup("#to").query();
    to.setText("DCA");

    Button search = lookup("#search").query();
    interact(() -> search.fireEvent(new ActionEvent()));

    Label out = lookup("#out").query();
    assertEquals(
        "The shortest path from " + "LAX" + " to " + "DCA" + " is: " + "\n" + "1: LAX -> DCA",
        out.getText());

    // test the clear(), all the thing would back to default
    Button clear = lookup("#clear").query();
    interact(() -> clear.fireEvent(new ActionEvent()));
    assertEquals("", from.getText());
    assertEquals("", to.getText());
    assertEquals("Welcome to Flight Planner", out.getText());
  }

  /**
   * add an intermediate, and do a search
   */
  @Test
  public void test4() {
    TextField from = lookup("#from").query();
    from.setText("LAX");

    Button add = lookup("#add").query();
    interact(() -> add.fireEvent(new ActionEvent()));

    TextField intermediate0 = lookup("#intermediate0").query();
    intermediate0.setText("DCA");

    TextField to = lookup("#to").query();
    to.setText("ORD");

    Button search = lookup("#search").query();
    interact(() -> search.fireEvent(new ActionEvent()));

    Label out = lookup("#out").query();
    assertEquals("The shortest path from " + "LAX" + " to " + "ORD" + " is: " + "\n"
        + "1: LAX -> DCA" + "\n2: DCA -> ORD", out.getText());
  }

  /**
   * fill the to leave the to blank and do the search, the out should display all the neighbors of
   * to
   * <p>
   * then add an intermediate fill the from and intermediate but leave the to blank,
   * <p>
   * the out should display the intermediate's the neighbors
   * <p>
   * ** because I used the placeholder, so the neighbors should always be "aaaa, bbbb, cccc, dddd,
   * aqwcf, qwf, qfqf" (Not anymore)
   */
  @Test
  public void test5() {
    TextField from = lookup("#from").query();
    from.setText("MSN");

    Button search = lookup("#search").query();
    interact(() -> search.fireEvent(new ActionEvent()));

    Label out = lookup("#out").query();
    assertEquals(
        "There is no destination, but here are the neighbor airport of the last airport you typed: \n"
            + "ATL, CVG, DEN, DFW, DTW, EWR, MCO, MSP, ORD, SLC, ",
        out.getText());

    Button add = lookup("#add").query();
    interact(() -> add.fireEvent(new ActionEvent()));
    TextField intermediate0 = lookup("#intermediate0").query();
    intermediate0.setText("DCA");
    interact(() -> search.fireEvent(new ActionEvent()));

    assertEquals(
        "There is no destination, but here are the neighbor airport of the last airport you typed: \n"
            + "ATL, AUS, BDL, BNA, BOS, CAK, CHS, CLE, CLT, DAL, DEN, DFW, DTW, EWR, FLL, HOU, IAH, IND, JAX,"
            + " JFK, LAS, LAX, LGA, MCI, MCO, MDW, MIA, MKE, MSP, MSY, ORD, PBI, PDX, PHL, PHX, PIT, RSW, SEA, "
            + "SFO, SJU, SLC, STL, TPA, ",
        out.getText());
  }

}
