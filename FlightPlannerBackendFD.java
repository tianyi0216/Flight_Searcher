// --== CS400 File Header Information ==--
// Name: Haozhe Wu
// CSL Username: haozhew
// Email: hwu435@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: <any optional extra notes to your grader>


import java.util.ArrayList;
import java.util.List;

/**
 * placeholder for FD
 */
public class FlightPlannerBackendFD implements IFlightPlannerBackend {
    @Override
    public void addPath(IPath path) {

    }

    @Override
    public List<IPath> findShortestPath(String originAirportName, String destinationAirportName) {
        List<IPath> answer = new ArrayList<>();
        PathFD memo = new PathFD();
        answer.add(memo);
        return answer;
    }

    @Override
    public List<String> findAllNeighborAirports(String airport) {
        List<String> answer = new ArrayList<>();
        answer.add("aaaa");
        answer.add("bbbb");
        answer.add("cccc");
        answer.add("dddd");
        answer.add("aqwcf");
        answer.add("qwf");
        answer.add("qfqf");
        return answer;
    }

    @Override
    public List<String> getAllAirports() {
        return null;
    }
}
