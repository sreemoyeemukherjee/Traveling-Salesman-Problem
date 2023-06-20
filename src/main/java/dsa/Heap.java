/**Name: Sreemoyee Mukherjee
 * Andrew ID: sreemoym
 * Course: Data Structures & Algorithms
 * Assignment Number: 4
 */
package dsa;

// class for building a minimum Heap
public class Heap {
    // data fields
    private HeapElement[] heap;
    private int maxSize;
    private int currentHeapSize;

    // constructor
    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.heap = new HeapElement[maxSize];
        this.currentHeapSize = 0;
    }

    // generic getters and setters
    public HeapElement[] getHeap() {
        return heap;
    }

    public void setHeap(HeapElement[] heap) {
        this.heap = heap;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getCurrentHeapSize() {
        return currentHeapSize;
    }

    public void setCurrentHeapSize(int currentHeapSize) {
        this.currentHeapSize = currentHeapSize;
    }

    // inserting an element
    public void insert(Double weight, int vertex){
        // inserts a new key
        int i = currentHeapSize;
        heap[i] = new HeapElement();
        heap[i].weight = weight;
        currentHeapSize++;
        heap[i].vertex = vertex;
        // fixing the min heap property if it is violated
        while (i != 0 && heap[i].weight < heap[getParentIndex(i)].weight) {
            swap(heap, i, getParentIndex(i));
            i = getParentIndex(i);

        }
    }
    private int getParentIndex(int i){
        return (i-1)/2;
    }
    private void swap(HeapElement[] array, int i, int j) {
        HeapElement temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    // to delete the minimum element i.e. the root from the heap
    public HeapElement deleteMin() {
        if (currentHeapSize == 1) {
            currentHeapSize--;
            return heap[0];
        }

        // Store the minimum value,
        // and remove it from heap
        HeapElement root = heap[0];

        heap[0] = heap[currentHeapSize - 1];
        currentHeapSize--;
        reHeapify(0);

        return root;
    }

    // reheapification downward
    private void reHeapify(int index) {
        int l = getLeftChildIndex(index);
        int r = getRightChildIndex(index);

        int min = index;
        if (l < currentHeapSize && heap[l].weight < heap[min].weight) {
            min = l;
        }
        if (r < currentHeapSize && heap[r].weight < heap[min].weight) {
            min = r;
        }

        if (min != index) {
            swap(heap, index, min);
            reHeapify(min);
        }
    }
    private int getLeftChildIndex(int i){
        return (2*i)+1;
    }
    private int getRightChildIndex(int i){
        return (2*i)+2;
    }
}
