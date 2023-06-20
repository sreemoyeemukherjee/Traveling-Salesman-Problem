/**Name: Sreemoyee Mukherjee
 * Andrew ID: sreemoym
 * Course: Data Structures & Algorithms
 * Assignment Number: 4
 */
package dsa;

// class for building the graph
public class Graph {
    // data fields
    private Double[][] edges;
    private Object[] vertex;

    // constructor
    public Graph (int n) {
        edges = new Double[n][n];
        vertex = new Object[n];
    }
    public void addEdge(int s, int t, double dist) {
        edges[s][t] = dist; // set distance as the edge between two vertices
        edges[t][s] = dist; // edge should be set for both point A to B as for B to A
    }

    public int size() {
        return vertex.length;
    }

    // to get distance between any two points
    public Double getEdges(int s, int t) {
        return edges[s][t];
    }
}
