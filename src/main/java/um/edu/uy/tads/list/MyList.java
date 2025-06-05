package um.edu.uy.tads.list;

import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public interface MyList<T extends Comparable<T>> {

    T obtener(int index);
    void agregar(T obj, Integer index);
    void eliminar(int index);

    Boolean elementoSeEncuentra(T obj);

    void addFirst(T value);
    void addLast(T value);

    Boolean isEmpty();
    int largo();

    void intercambiar(T obj, int direccion);
    MyLinkedListImpl<T> agregarLista(MyLinkedListImpl<T> listaAgregar);
    MyLinkedListImpl<T> juntarListasSinInterseccion(MyLinkedListImpl<T> lista2);
    void addOrdered(T elemento);
    void imprimirLista();

}