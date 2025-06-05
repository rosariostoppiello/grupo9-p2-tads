package um.edu.uy.tads.tree.bst;

import um.edu.uy.tads.list.*;

public interface MyBinarySearchTree <K extends Comparable<K>, T> {

    T findBST(K key);
    void insertBST(K key, T data);
    void deleteBST(K key);

    MyList<K> inOrderBST();
    MyList<K> preOrderBST();
    MyList<K> postOrderBST();

}
