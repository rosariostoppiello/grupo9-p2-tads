package um.edu.uy.tads.tree.bst;

import um.edu.uy.tads.list.*;
import um.edu.uy.tads.exceptions.ExceptionArbolVacio;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;
import um.edu.uy.tads.tree.TreeNode;

public class MyBinarySearchTreeImpl<K extends Comparable<K>, T> implements MyBinarySearchTree<K,T> {

    private TreeNode<K,T> root;

    @Override
    public T findBST(K key) {

        if (root == null) {
            throw new ExceptionArbolVacio("El árbol está vacío. No es posible encontrar.");
        } else {
            if (root.getKey().equals(key)) {
                return root.getData();
            }
            return findRecursivoBST(root, key);
        }
    }

    private T findRecursivoBST(TreeNode<K,T> nodoActual, K key) {

        // casos base --> frenarían la recursión si encuentro al nodo o si no existe
        if (nodoActual == null) {
            return null;
        } else if (nodoActual.getKey().equals(key)) {
            return nodoActual.getData();
        }

        // metodo recursivo
        T valor;
        if (nodoActual.getKey().compareTo(key) < 0) {
            valor = findRecursivoBST(nodoActual.getRightChild(), key);
            return valor;
        } else { // > 0
            valor = findRecursivoBST(nodoActual.getLeftChild(), key);
            return valor;
        }
    }

    public void insertBST(K key, T data) {

        // veo si hay que insertar el nodo en la raíz
        if (root == null) {
            root = new TreeNode<K,T>(key, data);
            return;
        }

        insertRecursivoBST(root, key, data);
    }

    public void insertRecursivoBST(TreeNode<K,T> nodoActual, K key, T data) {

        if (nodoActual == null) {
            return;
        }

        // metodo recursivo
        if (nodoActual.getKey().compareTo(key) > 0) {
            if (nodoActual.getLeftChild() == null) {
                nodoActual.setLeftChild(new TreeNode<>(key, data));
            } else {
                insertRecursivoBST(nodoActual.getLeftChild(), key, data);
            }
        } else if (nodoActual.getKey().compareTo(key) < 0) {
            if (nodoActual.getRightChild() == null) {
                nodoActual.setRightChild(new TreeNode<>(key, data));
            } else {
                insertRecursivoBST(nodoActual.getRightChild(), key, data);
            }
        } else {
        }

    }

    @Override
    public void deleteBST(K key) {
        if (root == null) {
            System.out.println("El árbol está vacío, no hay nada que borrar.");
            return;
        }

        TreeNode<K, T> nodo = deleteRecursivoBST(root, key);
    }

    private TreeNode<K, T> deleteRecursivoBST(TreeNode<K, T> nodoActual, K key) {
        if (nodoActual == null) return null;

        if (nodoActual.getKey().compareTo(key) > 0) {
            nodoActual.setLeftChild(deleteRecursivoBST(nodoActual.getLeftChild(), key));
        } else if (nodoActual.getKey().compareTo(key) < 0) {
            nodoActual.setRightChild(deleteRecursivoBST(nodoActual.getRightChild(), key));
        } else { // =
            // caso 1 --> sin hijos
            if (nodoActual.getLeftChild() == null && nodoActual.getRightChild() == null) {
                return null;
            }

            // caso 2 --> un solo hijo derecho
            if (nodoActual.getLeftChild() == null) {
                return nodoActual.getRightChild();
            }

            // caso 3 --> un solo hijo izquierdo
            if (nodoActual.getRightChild() == null) {
                return nodoActual.getLeftChild();
            }

            // caso 4 --> dos hijos
            TreeNode<K, T> nodoMinimo = encontrarMinimo(nodoActual.getRightChild());
            nodoActual.setKey(nodoMinimo.getKey());
            nodoActual.setData(nodoMinimo.getData());
            nodoActual.setRightChild(deleteRecursivoBST(nodoActual.getRightChild(), nodoMinimo.getKey())); // borro min
        }
        return nodoActual;
    }

    // metodo auxiliar para encontrar el nodo con la clave mínima en el subárbol derecho (este caso)
    private TreeNode<K, T> encontrarMinimo(TreeNode<K, T> nodo) {
        while (nodo.getLeftChild() != null) {
            nodo = nodo.getLeftChild();
        }
        return nodo;
    }

    @Override
    public MyList<K> inOrderBST() { // IZQ --> RAIZ --> DER
        MyList<K> lista = new MyLinkedListImpl<>();
        inOrderRecursivoBST(root, lista);
        return lista;
    }

    private void inOrderRecursivoBST(TreeNode<K,T> nodo, MyList<K> lista) {
        if (nodo == null) {
            return;
        }

        inOrderRecursivoBST(nodo.getLeftChild(), lista);
        lista.addLast(nodo.getKey()); // uso addlast xq el agregar me pide un index y no quiero eso, solo K
        inOrderRecursivoBST(nodo.getRightChild(), lista);
    }

    @Override
    public MyList<K> preOrderBST() { // RAIZ --> IZQ --> DER
        MyList<K> lista = new MyLinkedListImpl<>();
        preOrderRecursivoBST(root, lista);
        return lista;
    }

    private void preOrderRecursivoBST(TreeNode<K,T> nodo, MyList<K> lista) {
        if (nodo == null) {
            return;
        }
        lista.addLast(nodo.getKey());
        preOrderRecursivoBST(nodo.getLeftChild(), lista);
        preOrderRecursivoBST(nodo.getRightChild(), lista);
    }

    @Override
    public MyList<K> postOrderBST() { // IZQ --> DER --> RAIZ
        MyList<K> lista = new MyLinkedListImpl<>();
        postOrderRecursivoBST(root, lista);
        return lista;
    }

    private void postOrderRecursivoBST(TreeNode<K,T> nodo, MyList<K> lista) {
        if (nodo == null) {
            return;
        }
        postOrderRecursivoBST(nodo.getLeftChild(), lista);
        postOrderRecursivoBST(nodo.getRightChild(), lista);
        lista.addLast(nodo.getKey());
    }
}