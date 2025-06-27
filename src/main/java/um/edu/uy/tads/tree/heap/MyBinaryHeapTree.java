package um.edu.uy.tads.tree.heap;

public interface MyBinaryHeapTree<K extends Comparable<K>,T> {

    void add(K key, T value);

    HeapData<K,T> delete();

    int sizeHeap();

    String toString();

}
