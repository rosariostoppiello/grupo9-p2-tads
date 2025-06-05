package um.edu.uy.tads.tree.heap;

public interface MyBinaryHeapTree<K extends Comparable<K>,T> {

    void agregar(K key, T value);

    DatoHeap<K,T> eliminar();

    int tamanio();

    String toString();

}
