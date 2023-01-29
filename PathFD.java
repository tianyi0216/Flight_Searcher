// --== CS400 File Header Information ==--
// Name: Haozhe Wu
// CSL Username: haozhew
// Email: hwu435@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: <any optional extra notes to your grader>

/**
 * placeholder for FD
 */
public class PathFD implements IPath{
    @Override
    public String getOrigin() {
        return "msn";
    }

    @Override
    public String getDestination() {
        return "ord";
    }

    @Override
    public int getPathCost() {
        return 100;
    }
}
