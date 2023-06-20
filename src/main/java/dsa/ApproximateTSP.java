/**Name: Sreemoyee Mukherjee
 * Andrew ID: sreemoym
 * Course: Data Structures & Algorithms
 * Assignment Number: 4
 */
package dsa;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// Driver class to read csv file and compute Hamiltonian cycle and optimum cycle using Prim's algorithm
public class ApproximateTSP {
    // data fields
    static List<String> records = new LinkedList<>();   // to hold crime records
    static int totalVertices = 0;
    static Graph graph = new Graph(totalVertices);
    static Double cycleLength = 0.0;    // to store Hamiltonian cycle length
    static List<Integer> hamiltonianCycle = new LinkedList<>();
    static Double optimalLength = 0.0;  // to store optimum cycle length
    static List<Integer> optimalCycle = new LinkedList<>();

    public static void main(String[] args){
        String allLines = "";
        // reading the crime file
        try {
            Scanner sc = new Scanner(new File("CrimeLatLonXY1990.csv"));
            while (sc.hasNext()){
                allLines = allLines + sc.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String data[] = allLines.split("\n");
        Scanner sc = new Scanner(System.in);
        // take input date range from user
        System.out.println("Enter start date");
        String startDate =  sc.nextLine();
        System.out.println("Enter end date");
        String endDate = sc.nextLine();
        System.out.println("Crime records between " + startDate + " and " + endDate);
        // ignore first line as it contains headers
        for (int i=1; i<data.length; i++){
            String date = data[i].split(",")[5];
            if(date.compareTo(startDate)>=0 && date.compareTo(endDate)<=0){
                System.out.println(data[i]);
                records.add(data[i]);
                totalVertices++;
            }
        }
        String root = records.get(0);   // first record is set as the root
        graph = new Graph(totalVertices);
        fillGraph(graph);
        prim(root);
        optimalCycleLength();
        toKML();
    }
    // to fill the graph with distances between crime locations
    public static void fillGraph(Graph graph) {
        // computing distance between two records and adding the distance to graph
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j       ++) {
                if(i==j){
                    graph.addEdge(i,j,0.0); // distance from one crime location to itself is zero
                    continue;
                }
                Double x1 = Double.parseDouble(records.get(i).split(",")[0]);
                Double x2 = Double.parseDouble(records.get(j).split(",")[0]);
                Double y1 = Double.parseDouble(records.get(i).split(",")[1]);
                Double y2 = Double.parseDouble(records.get(j).split(",")[1]);
                Double dist = getDistance(x1, x2, y1, y2);
                graph.addEdge(i,j,dist);
            }
        }
    }
    // computes Pythagorean distance between two points on the x-y plane
    public static Double getDistance(Double x1, Double x2, Double y1, Double y2){
        Double sum = Math.pow((x1-x2),2) + Math.pow((y1-y2),2);
        Double dist = Math.sqrt(sum)*0.00018939;
        return dist;
    }
    // Prim's algorithm
    public static void prim(String root){
        List<MSTNode> tree = new LinkedList<>();
        boolean flag1 = true;
        boolean[] markDeleted = new boolean[records.size()];    // to mark crimes deleted once already visited
        // initialising each with distance infinity and parent null
        for (int i = 0; i<totalVertices; i++){
            MSTNode node = new MSTNode(i, Double.POSITIVE_INFINITY, null);
            tree.add(node);
        }
        tree.get(0).setWeight(0.0); // set root as first node visited in tree
        int parent = 0;
        hamiltonianCycle.add(0);    // add root to cycle
        markDeleted[0] = true;      // mark root as visited
        Heap heap = new Heap(totalVertices);
        while (flag1){  // while there are still unvisited nodes
            for (int i = 0; i < totalVertices; i++){
                if(parent != i && !markDeleted[i]) {    // unless it is self node or the node has already been visited
                    heap.insert(graph.getEdges(parent, i), i);
                }
            }
            HeapElement min = heap.deleteMin(); // get minimum distance
            int u = min.vertex;
            tree.get(u).setWeight(min.weight);
            tree.get(u).setParent(tree.get(parent));
            markDeleted[u] = true;
            cycleLength+= min.weight;
            hamiltonianCycle.add(u);
            parent = u;
            // update distances to nodes adjacent to 'u'
            for (int i=0; i<totalVertices; i++){
                Double dist = graph.getEdges(u,i);
                // if node has not already been visited by Prim's algorithm and new distance is lesser
                // than current distance stored in node
                if(graph.getEdges(u,i)!=0 && !markDeleted[i] && dist < tree.get(i).getWeight()){
                    tree.get(i).setParent(tree.get(parent));
                    tree.get(i).setWeight(dist);
                }
            }
            boolean flag2 = false;
            for (boolean b: markDeleted){
                if (!b){    // if there exists unvisited nodes
                    flag2 = true;
                    break;
                }
            }
            if(!flag2)
                flag1 = false;
            heap = new Heap(totalVertices);
        }
        hamiltonianCycle.add(0);    // adding last node as root to complete the cycle
        // Creates a FileWriter
        FileWriter file = null;
        try {
            file = new FileWriter("result.txt", true);
            // Creates a PrintWriter
            PrintWriter out = new PrintWriter(file, true);
            out.println();  // to put line break between the two inputs, also to leave space in 1st line for andrewID
            out.println("TestCase");
            out.println("Hamiltonian Cycle");
            System.out.println("Hamiltonian Cycle (not necessarily optimum): ");
            // printing the Hamiltonian cycle
            for (Integer i: hamiltonianCycle){
                System.out.print(i + "\t");
                out.print(i + "\t");
            }
            out.println("\nLength");
            // to get distance between last node and root i.e. starting point
            cycleLength+= graph.getEdges(hamiltonianCycle.get(hamiltonianCycle.size()-2), hamiltonianCycle.get(hamiltonianCycle.size()-1));
            System.out.println("\nLength Of cycle:  " + cycleLength + " miles");
            out.print(cycleLength);
            out.close();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // initialise used to search minimum distance in the array
    // currently this task is being done using Heap
    public static int deleteMin(Graph tempGraph, int index){
        int minIndex = 0;
        Double min = Double.POSITIVE_INFINITY;
        for(int i=0; i<totalVertices; i++){
            // if node is not a self node and distance is lesser than min
            // then replace min with this new distance
            if(tempGraph.getEdges(index,i)<min && tempGraph.getEdges(index,i)!=0){
                min = tempGraph.getEdges(index, i);
                minIndex = i;   // storing the index of the node to which the distance is smallest
            }
        }
        return minIndex;
    }
    // to find the optimal distance by trying every permutation using brute-force
    public static Double findOptimal(int[] tour, int start, Double tourLength) {
        Double min = 0.0;
        if (start == tour.length - 1) {
            // base case: we have reached the end of the array, so we print it
            for (int i = 0; i < tour.length - 1; i++){
                min += graph.getEdges(tour[i], tour[i + 1]);

            }
            // storing the cycle for which the length is optimum
            if(min <= tourLength){
                optimalCycle = new LinkedList<>();
                tourLength = min;
                for (int i: tour)
                    optimalCycle.add(i);
            }
        } else {
            // recursive case: for each element in the array, swap it with the first element,
            // then recursively print all permutations of the remaining elements
            for (int i = start; i < tour.length - 1; i++) {
                swap(tour, start, i);
                tourLength = findOptimal(tour, start + 1, tourLength);
                swap(tour, start, i);
            }
        }
        return tourLength;
    }

    // to swap two elements in an array
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    // to find and print the optimum cycle and its cycle length
    public static void optimalCycleLength() {
        int[] tour = new int[hamiltonianCycle.size()];
        for (int i = 0; i < hamiltonianCycle.size(); i++) {
            tour[i] = hamiltonianCycle.get(i);
        }
        System.out.println("Looking at every permutation to find the optimal solution");
        // calling the recursive method to return the optimum distance
        optimalLength = findOptimal(tour, 1, Double.POSITIVE_INFINITY);
        // Creates a FileWriter
        FileWriter file = null;
        try {
            file = new FileWriter("result.txt", true);
            // Creates a PrintWriter
            PrintWriter out = new PrintWriter(file, true);
            out.println();
            out.println("Optimum path");
            System.out.println("The best permutation");
            for (Integer i : optimalCycle){
                System.out.print(i + "\t");
                out.print(i + "\t");
            }
            out.println("\nLength");
            System.out.println("\nOptimal Cycle length = " + optimalLength + " miles");
            out.println(optimalLength);
            out.close();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // post-condition: creates the PGHCrimes.kml file and stores a KML representation of the two paths (Hamiltonian cycle and optimum cycle).
    public static void toKML(){
        Double offset = 0.001;  // offset to separate overlapping paths
        try {
            FileWriter myWriter = new FileWriter("PGHCrimes.kml");
            myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                    " <kml xmlns=\"http://earth.google.com/kml/2.2\"> " +
                    "<Document> <name>Pittsburgh TSP</name><description>TSP on Crime</description>" +
                    "<Style id=\"style6\"> <LineStyle> <color>73FF0000</color> <width>5</width> </LineStyle> </Style> " +
                    "<Style id=\"style5\"> <LineStyle> <color>507800F0</color> <width>5</width> </LineStyle> </Style> " +
                    "<Placemark> <name>TSP Path</name> <description>TSP Path</description>" +
                    " <styleUrl>#style6</styleUrl> <LineString> <tessellate>1</tessellate> <coordinates> " );
            for (Integer i: hamiltonianCycle){
                myWriter.write((Double.parseDouble(records.get(i).split(",")[8]) + offset)
                        + "," + (Double.parseDouble(records.get(i).split(",")[7]) + offset) + ",0.000000\n");
            }
            myWriter.write("</coordinates> </LineString> </Placemark> " +
                    "<Placemark> <name>Optimal Path</name> <description>Optimal Path</description> " +
                    "<styleUrl>#style5</styleUrl> <LineString> <tessellate>1</tessellate> <coordinates> ");
            for (Integer i: optimalCycle){
                myWriter.write(records.get(i).split(",")[8] + "," + records.get(i).split(",")[7] + ",0.000000\n");
            }
            myWriter.write("</coordinates> </LineString> </Placemark> </Document> </kml> ");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
