// --== CS400 File Header Information ==--
// Name: Tianyi Xu
// Email: txu223@wisc.edu
// Team: BL
// TA: Sujitha
// Lecturer: Gary
// Notes to Grader: N/A

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A graph that uses dijkstra algorithm to calculate the shortest path between two vertices.
 * Utilizes a HashMap to store vertices and a node to store vertices's information - differs
 * from week 2 implementation. Also allows for storing of multiple edges with same beginning 
 * and destination for certain special type of datas.
 * 
 * @param <T> the data type stored in each vertex
 * @author Tianyi Xu
 */
public class DijkstraGraph<T> implements IDijkstraGraph<T> {
    protected HashMap<T, Node<T>> nodes; // hashmap maps all data to a corresponding node
    protected int edgeSize; // variable track the number of edges

    /**
     * Constructor for graph, initialize a HashMap for all vertices and 0 for number of edges
     */
    public DijkstraGraph(){
        nodes = new HashMap<T, Node<T>>();
        edgeSize = 0;
    }

    /**
     * Insert a new vertex into the graph.
     * 
     * @param data the data item stored in the new vertex
     * @return true if the data can be inserted as a new vertex, false if it is already in the graph
     * @throws NullPointerException if data is null
     */
    @Override
    public boolean insertVertex(T data) {
        if(data == null)
            throw new NullPointerException();

        if(nodes.keySet().contains(data)) return false;

        // put to the graph if not already in
        Node<T> newNode = new Node<T>(data);
        nodes.put(data, newNode);
        return true;
    }

    /**
     * Remove a vertex from the graph.
     * Also removes all edges adjacent to the vertex from the graph (all edges that have the vertex
     * as a source or a destination vertex).
     * 
     * @param data the data item stored in the vertex to remove
     * @return true if a vertex with *data* has been removed, false if it was not in the graph
     * @throws NullPointerException if data is null
     */
    @Override
    public boolean removeVertex(T data) {
        if(data == null)
            throw new NullPointerException();

        // check if data is in the graph
        Node<T> nodeToRemove = nodes.get(data);

        if(nodeToRemove == null) return false;

        // remove all edges connected to vertex
        for(T value: nodes.keySet()){
            //get all destination of current vertex
            Set<Node<T>> destinations = nodes.get(value).connections.keySet(); 
            // loop checks if current vertex is connected to the one removed
            if(destinations.contains(nodeToRemove)){
                // change edge size by removing the nodes's amount
                int numOfEdgesToRemove = nodes.get(value).connections.get(nodeToRemove).size(); 
                nodes.get(value).connections.remove(nodeToRemove); // remove the connection
                this.edgeSize -= numOfEdgesToRemove;
            }
        }
        
        // number of edges node to remove have, need to loop as this graph allows duplicate path
        int edgeToRemoveNums = 0;
        for(List<Integer> paths : nodeToRemove.connections.values()){
            edgeToRemoveNums += paths.size();
        }
        this.edgeSize -= edgeToRemoveNums;
        
        return nodes.remove(data) != null;
    }

    /**
     * Insert a new directed edge with a positive edges weight into the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @param weight the weight for the edge (has to be a positive integer)
     * @return true if the edge could be inserted, false if the edge with the same weight was
     * already in the graph with the graph
     * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not
     * in the graph, or weight is < 0
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     */
    @Override
    public boolean insertEdge(T source, T target, int weight) {
        if(source == null || target == null)
            throw new NullPointerException();
        
        if(weight < 0)
            throw new IllegalArgumentException();

        // source node and target node stored here
        Node<T> sourceNode = nodes.get(source);
        Node<T> targetNode = nodes.get(target);

        // check if they exist
        if(sourceNode == null || targetNode == null)
            throw new IllegalArgumentException();
        
        // inserting new edge
        // check if there is already a existed edge that has same weight
        Set<Node<T>> destination = sourceNode.connections.keySet();
        if(destination.contains(targetNode)){
            if(sourceNode.connections.get(targetNode).contains(weight))
                return false;
        }
        
        // path the current source node connects to target node
        List<Integer> pathList = sourceNode.connections.get(targetNode);

        // create the new pathlist based on currrent status
        if(pathList == null){
            pathList = new ArrayList<Integer>();
            pathList.add(weight);
        } else {
            pathList.add(weight);
        }
        // add to the hashmap and increment edge counts
        sourceNode.connections.put(targetNode, pathList);
        edgeSize++;
        return true;
    }

    /**
     * Remove an edge from the graph. If there are multiple edges between source and target,
     * remove the least cost path one.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if the edge could be removed, false if it was not in the graph
     * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not in the graph
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     */
    @Override
    public boolean removeEdge(T source, T target) {
        if(source == null || target == null)
            throw new NullPointerException();

        // source node and target node for the vertex is store here
        Node<T> sourceNode = nodes.get(source);
        Node<T> targetNode = nodes.get(target);

        // throw IllegalArgumentException if source and/or target were null
        if(sourceNode == null || targetNode == null)
            throw new IllegalArgumentException();

        // check if edge exists
        Set<Node<T>> destinations = sourceNode.connections.keySet();
        if(!destinations.contains(targetNode)) return false; 

        // get the list of edge
        List<Integer> pathList = sourceNode.connections.get(targetNode);
        if(pathList.size() == 0) return false; // implementation issue, could have pathList size 0
        int minIndex = 0; // find the minimum path distacne's index
        int min = pathList.get(0);
        for(int i = 0; i < pathList.size(); i++){
            if(pathList.get(i) < min)
                minIndex = i; 
        }
        pathList.remove(minIndex); // remove from the pathList and decrement the size.
        this.edgeSize--;
        return true;
    }

    /**
     * Method overloading, remove an edge with specific weight user desired.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if the edge could be removed, false if it was not in the graph
     * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not in the graph
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     */
    @Override
    public boolean removeEdge(T source, T target, int weight) {
        if(source == null || target == null)
            throw new NullPointerException();

        // the source and target node that contains corresponding vertices
        Node<T> sourceNode = nodes.get(source);
        Node<T> targetNode = nodes.get(target);

        // throw IllegalArgumentException if both source and target were null
        if(sourceNode == null || targetNode == null)
            throw new IllegalArgumentException();

        // check if edge exists
        Set<Node<T>> destinations = sourceNode.connections.keySet();
        if(!destinations.contains(targetNode)) return false;

        // check if given weight exists
        List<Integer> pathList = sourceNode.connections.get(targetNode);
        if(pathList.size() == 0) return false; // if size is 0, also return false,
        if(!pathList.contains(weight)) return false; // if edge not in path, also not be able to remove
        
        // find the index where the path is stored at and remove it, decrement edgecount.
        int edgeIndex = 0;
        for(int i = 0; i < pathList.size(); i++){
            if(pathList.get(i) == weight)
                edgeIndex = i;
        }

        pathList.remove(edgeIndex);
        this.edgeSize--;
        return true;
    }

    /**
     * Check if the graph contains a vertex with data item *data*.
     * 
     * @param data the data item to check check for
     * @return true if data item is stored in a vertex of the graph, false otherwise
     * @throws NullPointerException if *data* is null
     */
    @Override
    public boolean containsVertex(T data) {
        if(data == null) throw new NullPointerException();

        return nodes.get(data) != null;
    }

    /**
     * Check there exist an edge between source and target in the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if there exists an edge between two, false if there does not exist an edge
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     */
    @Override
    public boolean containsEdge(T source, T target) {
        if(source == null || target == null)
            throw new NullPointerException();

        // source node and target node to check for
        Node<T> sourceNode = nodes.get(source);
        Node<T> targetNode = nodes.get(target);
        
        if(sourceNode == null || targetNode == null) return false;

        // see if destination of sourceCode has target or not
        Set<Node<T>> destinations = sourceNode.connections.keySet();
        if(!destinations.contains(targetNode)) return false;

        List<Integer> pathList = sourceNode.connections.get(targetNode);
        if(pathList.size() == 0) return false; // edge cases when pathList exist but has no path in

        return true;
    }

    /**
     * Check there exist an edge with given weight between source and target in the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if there exists such edge between two, false if there does not exist such an edge
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     */
    @Override
    public boolean containsEdge(T source, T target, int weight) {
        if(source == null || target == null)
            throw new NullPointerException();

        // sourcenode and target contains source vertex and target vertex
        Node<T> sourceNode = nodes.get(source);
        Node<T> targetNode = nodes.get(target);
        
        // check if the vertex is in the graph
        if(sourceNode == null || targetNode == null) return false;

        Set<Node<T>> destinations = sourceNode.connections.keySet();
        if(!destinations.contains(targetNode)) return false;
        
        // check for weights
        List<Integer> pathList = sourceNode.connections.get(targetNode);
        if(pathList.size() == 0) return false; // if it is 0, return false;
        if(!pathList.contains(weight)) return false; // false if no specific weight found
        return true;
    }

    /**
     * Return the edge with lowest weight between two vertexes.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return the weight of the edge (0 or positive integer)
     * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not in the graph
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     * @throws NoSuchElementException if edge is not in the graph
     */
    @Override
    public int getWeight(T source, T target) {
        if(source == null || target == null)
            throw new NullPointerException();

        // sourceNode and targetNode containing the vertexes
        Node<T> sourceNode = nodes.get(source);
        Node<T> targetNode = nodes.get(target);

        // check if source and target in the graph
        if(sourceNode == null || targetNode == null)
            throw new IllegalArgumentException();

        // check to see if can reach target vertex from source, throw exceptions if cannot
        Set<Node<T>> destinations = sourceNode.connections.keySet();
        if(!destinations.contains(targetNode))
            throw new NoSuchElementException();

        List<Integer> pathList = sourceNode.connections.get(targetNode);
        if(pathList.size() == 0) throw new NoSuchElementException();

        // call helper function to find shortest edge
        int minWeight = shortest(pathList);
        return minWeight;
    }

    /**
     * Returns the shortest path between startingVertex and destinationVertex.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     * 
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the destination vertex for the path
     * @return list of data item in vertices in order on the shortest path between vertex with 
     * data item startingVertex and vertex with data item destinationVertex, including both 
     * startingVertex and destinationVertex
     * @throws NoSuchElementException when no path from start to end can be found,
     * including when no vertex containing start or end can be found
     */
    @Override
    public List<T> shortestPath(T start, T end) {
        if(start == null || end == null)
            throw new NoSuchElementException();
        if(nodes.get(start) == null || nodes.get(end) == null)
            throw new NoSuchElementException();
        // variable to return that stores the path
        List<T> shortestPath = new ArrayList<T>();

        // case where start and end is same, then path will return itself
        if(start.equals(end)){
            shortestPath.add(start);
            return shortestPath;
        }

        // set each node's distance to origin to be infinity, use -1 to represent infinity
        // set to null to prepare for calculation
        for(Node<T> node: nodes.values()){
            node.minDistance = -1; 
            node.previousNode = null;
        }

        // startNode and end Node for starting vertex and end vertex
        Node<T> startNode = nodes.get(start);
        Node<T> endNode = nodes.get(end);

        // frontier is the priority queue that stores next node to visit
        PriorityQueue<Node<T>> frontier = new PriorityQueue<Node<T>>();
        // List of node that already visited and found a minDistance from source to the vertices
        List<Node<T>> visited = new ArrayList<Node<T>>();
        
        // begin from startNode(vertex), add to priorityQueue
        frontier.add(startNode);
        Node<T> current = startNode; // Node with vertex represent current Node
        current.minDistance = 0; // set its minDistance 0 - to itself

        // the loop that keeps working until all nodes are checked, or if priorityqueue returned null
        // in that case reached all possible vertex from starting vertex
        while(visited.size() != nodes.size()){
            // all possible destination for current vertex
            Set<Node<T>> destinations = current.connections.keySet();

            // for each of the destination vertex, do operations
            for(Node<T> node: destinations){
                // calculate min distance to start vertex by adding current edge distance plus previous distance
                int minDist = shortest(current.connections.get(node)) + current.minDistance;
                // update distance and previousNode if is infinity or smaller than Node's current minDistance
                if(node.minDistance == -1 || minDist < node.minDistance){
                    node.minDistance = minDist;
                    node.previousNode = current;
                    // if it is smaller, add that node to frontier
                    frontier.add(node);
                }           
            }
            // add to visited set if current has not been already
            if(!visited.contains(current)) visited.add(current);
            frontier.remove(current); // update current
            current = frontier.peek();
            if(current == null) break;
        }

        if(endNode.previousNode == null) throw new NoSuchElementException();
        // calculate path here
        Node<T> temp = endNode;
        // go back from endNode to ad dup all previous Node's Value to the path
        while(temp.previousNode != null){
            shortestPath.add(0, temp.value);
            temp = temp.previousNode;
        }
        shortestPath.add(0, temp.value);
        return shortestPath;
    }
    /**
     * Helper function to determine the minimum weights given all possible edges between two vertices
     * 
     * @param paths a list of all possible edge wrights between two possible vertices
     * @return the smallest weights edge between two vertices
     */
    protected int shortest(List<Integer> paths){
        // use weight at 0 as standard, compare value to update it 
        int minWeight = paths.get(0);
        for(Integer ints : paths){
            if(ints < minWeight)
                minWeight = ints;
        }
        return minWeight;
    }

    /**
     * Returns the cost of the path (sum over edge weights) between startingVertex and destinationVertex.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     * 
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the destination vertex for the path
     * @return the cost of the shortest path between vertex with data item startingVertex and vertex
     * with data item destinationVertex, including both startingVertex and destinationVertex
     * @throws NoSuchElementException when no path from start to end can be found
     * including when no vertex containing start or end can be found
     */
    @Override
    public int getPathCost(T start, T end) {
        // check if inputs are valid
        if(start == null || end == null)
            throw new NoSuchElementException();
        if(nodes.get(start) == null || nodes.get(end) == null)
            throw new NoSuchElementException();

        // case if start equals end, then path is just 0 by default
        if(start.equals(end))
            return 0;

        // set each node's previous node to null to prepare for calculation
        // set minDistance to represent infinity
        for(Node<T> node: nodes.values()){
            node.minDistance = -1;
            node.previousNode = null;
        }

        // start node and end node contains vertex
        Node<T> startNode = nodes.get(start);
        Node<T> endNode = nodes.get(end);

        // dijkstra algorithm here, frontier and visited set
        PriorityQueue<Node<T>> frontier = new PriorityQueue<Node<T>>();
        List<Node<T>> visited = new ArrayList<Node<T>>();
        
        // set up starting node's distance and previous
        frontier.add(startNode);
        Node<T> current = startNode;
        current.minDistance = 0;

        // the loop that keeps working until all nodes are checked, or if priorityqueue returned null
        // in that case reached all possible vertex from starting vertex
        while(visited.size() != nodes.size()){
            // all possible destination for current vertex
            Set<Node<T>> destinations = current.connections.keySet();

            // for each of the destination vertex, do operations
            for(Node<T> node: destinations){
                // calculate min distance to start vertex by adding current edge distance plus previous distance
                int minDist = shortest(current.connections.get(node)) + current.minDistance;
                // update distance and previousNode if is infinity or smaller than Node's current minDistance
                if(node.minDistance == -1 || minDist < node.minDistance){
                    node.minDistance = minDist;
                    node.previousNode = current;
                    frontier.add(node); // add to froniter if smaller
                }
            }
            // add to visited if not been visited yet
            if(!visited.contains(current)) visited.add(current);
            // update current Node to next in priorityQueue
            frontier.remove(current);
            current = frontier.peek();
            if(current == null) break;
        }
        if(endNode.previousNode == null) throw new NoSuchElementException();
        return endNode.minDistance; // endNode's mindistance would be 
    }

    /**
     * Check if the graph is empty (does not contain any vertices or edges).
     * 
     * @return true if the graph does not contain any vertices or edges, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return nodes.size() == 0;
    }

    /**
     * Return the number of edges in the graph.
     * 
     * @return the number of edges in the graph
     */
    @Override
    public int getEdgeCount() {
        return edgeSize;
    }

    /**
     * Return the number of vertices in the graph
     * 
     * @return the number of vertices in the graph
     */
    @Override
    public int getVertexCount() {
        return nodes.size();
    }

    /**
     * Method to display all possible vertex to get to from given source vertex
     * @param source the source vertex to check where it can connect to
     * @return a list of vertex's value where the source can connect to
     * @throws NullPointerException is source is null
     */
    @Override
    public List<T> allNeighborVertices(T source) {
        if(source == null) throw new NullPointerException();
        List<T> neighborVertices = new ArrayList<T>(); // list of destination to return
        Node<T> sourceNode = nodes.get(source); // sourceNode containing the data

        // check if source vertex is in the graph or not
        if(sourceNode == null) return neighborVertices;

        // iterate through source vertex's connection nodes and add values to the list
        Set<Node<T>> connectedTo = sourceNode.connections.keySet();
        for(Node<T> node : connectedTo){
            neighborVertices.add(node.value);
        }
        return neighborVertices;
    }

    /**
     * Method that returns a list of all vertice's value in the list
     * 
     * @return a list of all vertice's value
     */
    @Override
    public List<T> getAllVertices() {
        List<T> allVertices = new ArrayList<T>();
        Set<T> allKeys = nodes.keySet();
        
        for(T keys: allKeys)
            allVertices.add(keys);
        return allVertices;
    }
    
    /**
     * The Node that stored vertex and their paths in a graph. Each Node store the value, a map
     * of their destinations and corresponding path weights.
     * Also contains field that is used when performing Dijkstra Algorithm.
     * @param <T> type of data stored in the Node
     * @author Tianyi Xu
     */
    static class Node<T> implements Comparable<Node<T>>{
        T value; // value of the data stoed in
        HashMap<Node<T>, List<Integer>> connections; // key is destination, value is cost
        int minDistance; // used for shortestPath, minimum distance to previous node
        Node<T> previousNode; // used for shortest path, previous Node for the shortest path

        /**
         * Constructor for the node, set value to data stored and create a new HashMap mapping
         * the destination and corresponding path
         * @param data
         */
        public Node(T data){
            value = data;
            connections = new HashMap<Node<T>, List<Integer>>();
        }
        /**
         * compareTo method compare two node, compare their minimum Distance. Used for Dijkstra's
         * shortest path.
         * @param o another node object to be compared with
         * @return the difference between their min Distance, in case of a tie, return compare to
         * of their string representation
         */
        @Override
        public int compareTo(DijkstraGraph.Node<T> o) {
            int compare = this.minDistance - o.minDistance != 0 ? this.minDistance - o.minDistance :
                this.value.toString().compareTo(o.value.toString());

            return compare;
        }
    }
}

