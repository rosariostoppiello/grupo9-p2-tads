package um.edu.uy.tads.tree.heap;

public class MyBinaryHeapTreeImpl<K extends Comparable<K>, T> implements MyBinaryHeapTree<K,T> {

    private final HeapData<K, T>[] vectorHeap;
    private int size;
    private final boolean max;

    @SuppressWarnings("unchecked")
    public MyBinaryHeapTreeImpl(boolean max, int capacity) {
        this.vectorHeap = new HeapData[capacity];
        this.size = 0;
        this.max = max;
    }

    @Override
    public void add(K key, T value) {
        if (size+1 == vectorHeap.length) {
            throw new IllegalStateException("The heap is full");
        }

        vectorHeap[size] = new HeapData<>(key, value);
        bubbleUp(size);
        size++;
    }

    private void bubbleUp(int index) {
        int parentIndex = (index - 1) / 2;

        while (index > 0 && compareMaxMin(vectorHeap[index].getKey(), vectorHeap[parentIndex].getKey())) {
            swapElements(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private boolean compareMaxMin(K a, K b) {
        return max ? a.compareTo(b) > 0 : a.compareTo(b) < 0;
    }

    private void swapElements(int i, int j) {
        HeapData<K, T> temp = vectorHeap[i];
        vectorHeap[i] = vectorHeap[j];
        vectorHeap[j] = temp;
    }

    @Override
    public HeapData<K,T> delete() {
        if (size == 0) return null;
        HeapData<K,T> datoDevolver = vectorHeap[0];

        vectorHeap[0] = vectorHeap[size - 1];
        size--;
        bubbleDown(0);

        return datoDevolver;
    }

    private void bubbleDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int indexExtremeMaxMin = index;

        if (leftChild < size && compareMaxMin(vectorHeap[leftChild].getKey(), vectorHeap[indexExtremeMaxMin].getKey())) {
            indexExtremeMaxMin = leftChild;
        }
        // hijo der
        if (rightChild < size && compareMaxMin(vectorHeap[rightChild].getKey(), vectorHeap[indexExtremeMaxMin].getKey())) {
            indexExtremeMaxMin = rightChild;
        }

        if (indexExtremeMaxMin != index) {
            swapElements(index, indexExtremeMaxMin);
            bubbleDown(indexExtremeMaxMin);
        }
    }

    @Override
    public int sizeHeap() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0) return "Heap vacío";

        StringBuilder sb = new StringBuilder();
        diagramFromArray(sb,0, "", true);
        return sb.toString();
    }

    private void diagramFromArray(StringBuilder sb, int index, String prefix, boolean isLastChild) {
        if (index >= size) return;

        sb.append(prefix);
        sb.append(isLastChild ? "└── " : "├── ");
        sb.append(vectorHeap[index].toString()).append("\n");

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        boolean hasLeftChild = leftChild < size;
        boolean hasRightChild = rightChild < size;

        if (hasLeftChild)
            diagramFromArray(sb, leftChild, prefix + (isLastChild ? "    " : "│   "), !hasRightChild);
        if (hasRightChild)
            diagramFromArray(sb, rightChild, prefix + (isLastChild ? "    " : "│   "), true);
    }

}