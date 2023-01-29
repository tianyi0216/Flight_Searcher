// --== CS400 File Header Information ==--
// Name: Haozhe Wu
// CSL Username: haozhew
// Email: hwu435@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: <any optional extra notes to your grader>


import javafx.scene.layout.GridPane;

/**
 * The interface for Frontend, We will use JavaFX to develop the graphic user interface
 */
public interface IFlightPlannerFrontend {

    /**
     * Adds a slot for an additional city to be chosen
     *
     * @param g given GridPane
     */
    public void addCity(GridPane g);

    /**
     * Calls a BD method to find the shortest path air route.
     */
    public void search();

    /**
     * Clear all the data the user typed
     */
    public void clear();

    /*
     * Displays all adjacent airports to the airport furthest in the plan
     */
    public void displayNeighbors();
}
