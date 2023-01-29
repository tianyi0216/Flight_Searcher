// --== CS400 File Header Information ==--
// Name: Haozhe Wu
// CSL Username: haozhew
// Email: hwu435@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: <any optional extra notes to your grader>

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * The Frontend Application class
 */
public class FlightPlannerFrontend extends Application implements IFlightPlannerFrontend {

  TextField from = new TextField();
  Label start = new Label("Departure");
  TextField to = new TextField();
  Label end = new Label("Destination");

  TextField[] intermediates = new TextField[8];
  ContextMenu[] choiceLists = new ContextMenu[8];

  Button search = new Button("Search");
  Button add = new Button("+ intermediates");
  Button remove = new Button("-");
  Button clear = new Button("Clear");

  Label out = new Label("Welcome to Flight Planner");

  GridPane inputs = new GridPane();
  int numIntermediates = 0;
  List<IPath> file;

  FlightPlannerBackend FB = new FlightPlannerBackend();


  /**
   * Controls the window and all the aspects that are being updated inside it
   *
   * @param stage the scene
   * @throws Exception throw unexpected exceptions
   */
  @Override
  public void start(Stage stage) throws Exception {
    // Load the file
    try {
      file = new FlightLoader().loadFlightPaths("flights.json");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    for (IPath path : file) {
      FB.addPath(path);
    }

    // basic setting of the scene
    Group group = new Group();
    Scene scene = new Scene(group, 480, 480);
    stage.setScene(scene);
    stage.setTitle("Project3: Flight Planner");
    stage.show();

    // build the scene
    BorderPane wholeBorderPane = new BorderPane();
    group.getChildren().add(wholeBorderPane);

    inputs.setHgap(5);
    inputs.setVgap(10);
    wholeBorderPane.setLeft(inputs);
    wholeBorderPane.setAlignment(inputs, Pos.CENTER_LEFT);

    // Add all the buttons and labels to the input
    inputs.add(start, 0, 0);
    inputs.add(from, 1, 0);
    inputs.add(add, 1, 1);
    inputs.add(end, 0, 2);
    inputs.add(to, 1, 2);
    inputs.add(clear, 1, 3);
    inputs.add(remove, 2, 1);
    inputs.add(search, 1, 12);

    // build the output
    Rectangle outputBox = new Rectangle(200, 600);
    outputBox.setFill(Color.web("#f7ecd5"));

    Group outGroup = new Group(outputBox);
    outGroup.getChildren().add(out);
    out.setWrapText(true);

    out.setMaxWidth(200);
    wholeBorderPane.setRight(outGroup);

    // create intermediates
    for (int i = 0; i < choiceLists.length; i++) {
      intermediates[i] = new TextField();
      choiceLists[i] = new ContextMenu();
      intermediates[i].setContextMenu(choiceLists[i]);
      intermediates[i].setId("intermediate" + i);
    }

    // call help method
    setAllOnAction();

    // Set ID to help we do the test
    start.setId("start");
    end.setId("end");
    out.setId("out");
    from.setId("from");
    to.setId("to");
    add.setId("add");
    remove.setId("remove");
    search.setId("search");
    clear.setId("clear");

  }

  /**
   * Set all the buttons on action
   */
  protected void setAllOnAction() {
    search.setOnAction(e -> search());
    clear.setOnAction(e -> clear());
    remove.setOnAction(e -> removeCity());
    add.setOnAction(e -> addCity(inputs));
  }

  /**
   * Adds a slot for an additional city to be chosen
   *
   * @param g given GridPane
   */
  @Override
  public void addCity(GridPane g) {
    // the max intermediates is 8
    if (numIntermediates == 8) {
      return;
    }

    // else, move all the stuffs down and add a TextField
    int num = g.getRowIndex(add);
    g.setRowIndex(clear, num + 3);
    g.setRowIndex(to, num + 2);
    g.setRowIndex(end, num + 2);
    g.setRowIndex(add, num + 1);
    g.setRowIndex(remove, num + 1);
    g.add(intermediates[numIntermediates], 1, num);
    numIntermediates++;
  }

  /**
   * remove an intermediate TextField
   */
  public void removeCity() {
    // stop if there is no more intermediates
    if (numIntermediates == 0) {
      return;
    }

    // else, remove a TextField
    int num = inputs.getRowIndex(to) - 2;
    for (Node n : inputs.getChildren()) {
      if (inputs.getRowIndex(n) == num) {
        inputs.getChildren().remove(n);
        break;
      }
    }

    inputs.setRowIndex(end, num + 1);
    inputs.setRowIndex(add, num);
    inputs.setRowIndex(to, num + 1);
    inputs.setRowIndex(remove, num);
    inputs.setRowIndex(clear, num + 2);
    numIntermediates--;
  }

  /**
   * Calls a BD method to find the shortest path air route.
   */
  @Override
  public void search() {
    int totalStop = numIntermediates + 2;
    String fromSe = from.getText();
    String toSe = to.getText();
    String[] interSe = new String[numIntermediates];
    List<IPath> answer;

    // get the intermediates
    for (int i = 0; i < totalStop - 2; i++) {
      interSe[i] = intermediates[i].getText();
    }

    if (fromSe.equals("")) {
      out.setText("Please enter your Departure");
    } else {

      // Determine if there is a destination
      if (toSe.equals("")) {
        displayNeighbors(); // if there is no destination, display all the neighbors
      } else {

        // get all the path
        if (numIntermediates == 0) { // if there is no intermediates
          answer = FB.findShortestPath(fromSe, toSe);

        } else { // if there is some intermediates
          answer = FB.findShortestPath(fromSe, interSe[0]);

          // add all the intermediates
          for (int i = 1; i < numIntermediates; i++) {
            answer.addAll(FB.findShortestPath(interSe[i - 1], interSe[i]));
          }

          answer.addAll(FB.findShortestPath(interSe[numIntermediates - 1], toSe));
        }

        // output all the paths
        if (answer != null) {
          StringBuilder outSE =
              new StringBuilder("The shortest path from " + fromSe + " to " + toSe + " is: ");
          for (int i = 0; i < answer.size(); i++) {
            outSE.append("\n" + (i + 1) + ": " + answer.get(i).getOrigin() + " -> "
                + answer.get(i).getDestination());
          }

          out.setText(outSE.toString());

          // If there is no such paths
        } else {
          out.setText("There is no such path, please edit your search");
        }
      }
    }

  }

  /**
   * Clear all the data the user typed
   */
  @Override
  public void clear() {
    // remove all the intermediates
    inputs.getChildren().removeAll(intermediates);
    inputs.setRowIndex(end, 2);
    inputs.setRowIndex(to, 2);
    inputs.setRowIndex(add, 1);
    inputs.setRowIndex(clear, 3);
    inputs.setRowIndex(remove, 1);
    numIntermediates = 0;

    // set all the texts to default
    from.setText("");
    to.setText("");
    out.setText("Welcome to Flight Planner");

  }

  /**
   * Displays all adjacent airports to the airport furthest in the plan
   */
  @Override
  public void displayNeighbors() {
    String nameMemo;
    // if there is no intermediates, the from TextBox is the last one
    if (numIntermediates == 0) {
      nameMemo = from.getText();
    } else {
      // else, the last intermediate is the last one
      nameMemo = intermediates[numIntermediates - 1].getText();
    }

    // output the result
    List<String> neighbors = FB.findAllNeighborAirports(nameMemo);
    StringBuilder outSE = new StringBuilder(
        "There is no destination, but here are the neighbor airport of the last airport you typed: \n");
    for (String s : neighbors) {
      outSE.append(s + ", ");
    }
    out.setText(outSE.toString());

  }

  /**
   * The main method
   */
  public static void main(String[] args) {
    Application.launch();
  }

}
