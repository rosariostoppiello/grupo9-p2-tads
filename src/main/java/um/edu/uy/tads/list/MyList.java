package um.edu.uy.tads.list;

import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public interface MyList<T extends Comparable<T>> extends Iterable<T> {

    T find(int index);
    void add(T obj, Integer index);
    void delete(int index);

    Boolean elementExistsIn(T obj);

    void addFirst(T value);
    void addLast(T value);

    Boolean isEmpty();
    int size();

    void swap(T obj, int direccion);
    MyLinkedListImpl<T> addList(MyLinkedListImpl<T> listaAgregar);
    MyLinkedListImpl<T> addUpListsWithoutIntersection(MyLinkedListImpl<T> lista2);
    void addOrdered(T elemento);
    void printList();

}