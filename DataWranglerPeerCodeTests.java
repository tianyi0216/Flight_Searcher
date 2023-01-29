// --== CS400 Project Three File Header ==--
// Name: Bill Zhu
// CSL Username: bzhu
// Email: wlzhu@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: This is another tester class for the peer tests. Because this uses the javafx tester, 
// I wanted to seperate the files, but I can't seem to get this to run. I carefully reviewed the class notes, 
// attended office hours, checked Piazza, and also referenced the frontend tests, but still can't get it to work.
// Since I am the Datawrangler, I determined that this isn't exactly my role, and decided to leave the file as is. 
// Although it doesn't run properly, I hope that you can see what I was trying to do and the work I put in 
//(Spent a long time trying  to get this to work).  

import javafx.scene.control.TextField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import edu.wisc.cs.cs400.JavaFXTester;
import org.junit.jupiter.api.Test;

/**
* This class attempts to create a few tests for the FrontendDeveloper. 
*/
public class DataWranglerPeerCodeTests extends JavaFXTester {

  public DataWranglerPeerCodeTests() {
    super(FlightPlannerFrontend.class);
  }

   /**
   * Tests the output for FrontendDeveloper. If there is no connection/path betwen airports, the output should be: There
   * is no such path, please edit your search.
   */
  @Test
  public void test8() {
    //Setting up scenario: Origin airport is LAX, and the Destination airport is GMS (Non-Existant). 
    TextField from = lookup("#from").query();
    from.setText("LAX");
    TextField to = lookup("#to").query();
    to.setText("GMS");
    Button search = lookup("#search").query();
    interact(() -> search.fireEvent(new ActionEvent()));

    //Tests output
    Label out = lookup("#out").query();
    assertEquals("There is no such path, please edit your search", out.getText());
  }

  /**
  * Tests search feature with two intermediary airports. To be honest, I didn't see much I could add for the FrontendDeveloper
  * tests as their tests are pretty extensive. I referenced their code to make this test, altering it by adding another airport
  * (I also didn't want to test a completely different thing since I can't run/test this file.)
  */
  @Test
  public void test9() {
    TextField from = lookup("#from").query();
    from.setText("LAX");

    Button add = lookup("#add").query();
    interact(() -> add.fireEvent(new ActionEvent()));

    TextField intermediate0 = lookup("#intermediate0").query();
    intermediate0.setText("DCA");
    TextField intermediate1 = lookup("#intermediate1").query();
    intermediate1.setText("ANC");

    TextField to = lookup("#to").query();
    to.setText("ORD");

    Button search = lookup("#search").query();
    interact(() -> search.fireEvent(new ActionEvent()));

    Label out = lookup("#out").query();
    assertEquals("The shortest path from " + "LAX" + " to " + "ORD" + " is: " + "\n"
        + "1: LAX -> DCA" + "\n2: DCA -> ANC" + "\n3: ANC -> ORD", out.getText());
  }
}
