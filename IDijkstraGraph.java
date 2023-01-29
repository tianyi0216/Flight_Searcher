// --== CS400 Project Two File Header ==--
// Name: Tianyi Xu
// CSL Username: tianyi
// Email: txu223@wisc.edu
// Lecture #: 002 @1:00pm
// Notes to Grader: Since Everything in P3's proposal can be done by GraphADT, AE's does not 
// have any completely new method but extends the function mainly to support multiple edges. As
// well as different implementation on Dijkstra graph. For example, the graph will support data
// that has multiple edges with same destination such as flight's time as path cost rather than 
// distance, or different flight path results in different distance between same origin/targets.
// It also prevents data from lost when using the graph.

import java.util.HashMap;
import java.util.List;

/**
 * This class will implement GraphADT and is used to compute the shortest path
 * between two vertex stored in the graph using Dijkstra algorithm. It extend the function
 * of a graph ADT in that it will use HashMap to map data to paths to maximize time complexity
 * and also allows for multiple paths between two vertexes with different weight. It also allows
 * for removing/checking edges with a specific weight and slightly modifies removeEdge and getWeight
 */
public interface IDijkstraGraph<T> extends GraphADT<T>  {
    // HashMap<T, Node<T>> nodes; nodes will be stored in hashMap mapped to data value
    
    // constructor - default constructor takes no argument

    // Most method has the same function as those in GraphADT, except will need to modify 
    // implementation to work with HashMap implementation.

     // Remove the lowest cost edge if there exists multiple paths between source and target
    public boolean removeEdge(T source, T target);

    // get weight will also return the minimum weight if there exists multiple paths between
    // source and target
    public int getWeight(T source, T target);

    /**
     * Method overloading that allows user to remove edge with a certain weight
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @param weight the weight of edge to be removed
     * @return true if the edge could be removed, false if it was not in the graph
     */
    public boolean removeEdge(T source, T target, int weight);

    /**
     * Method overloading that allows user to check if edge with given weight is in the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @param weight the weight of the edge contained
     * @return true if the edge is in the graph, false if it is not in the graph
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     */
    public boolean containsEdge(T source, T target, int weight);

    /**
     * Method to return all possible vertex to get to from given source vertex
     * @param source the source vertex to check where it can connect to
     * @return a list of vertex's value where the source can connect to
     */
    public List<T> allNeighborVertices(T source);

    /**
     * Method to return all vertices in the graph
     * 
     * @return a list of vertice's value in the graph
     */
    public List<T> getAllVertices();

    // Node<T> class - the node class uses to store the value and their connections/path 
    // to other vertex - example below
    // storing node and connections in a hashmap. 
    /* 
    static class Node<T> // optional implements Comparable<Node<T>>{
        T value;  // the data value stores here
        HashMap<Node<T>, List<Integer>> connections; // key is destination, value is cost
    }*/
    
}

