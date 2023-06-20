/**Name: Sreemoyee Mukherjee
 * Andrew ID: sreemoym
 * Course: Data Structures & Algorithms
 * Assignment Number: 4
 */
package dsa;

// POJO for Minimum Spanning tree node
public class MSTNode {
    // data fields
    private int index;
    private Double weight;
    private MSTNode parent;

    // constructor
    public MSTNode(int index, Double weight, MSTNode parent) {
        this.index = index;
        this.weight = weight;
        this.parent = parent;
    }

    // generic getters and setters
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public MSTNode getParent() {
        return parent;
    }

    public void setParent(MSTNode parent) {
        this.parent = parent;
    }
}
