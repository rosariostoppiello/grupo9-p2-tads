package um.edu.uy.tads.list.linked;

import um.edu.uy.tads.exceptions.EmptyQueueException;
import um.edu.uy.tads.list.MyList;
import um.edu.uy.tads.stack.MyStack;
import um.edu.uy.tads.queue.MyQueue;

import java.util.EmptyStackException;

public class MyLinkedListImpl<T extends Comparable<T>> implements MyList<T>, MyQueue<T>, MyStack<T> {

    private Node<T> head;
    private Node<T> tail;
    private int largo;

    // ================================================================================================================
    // MyList<T>
    // ================================================================================================================

    public MyLinkedListImpl() {
        this.head = null;
        this.tail = null;
        this.largo = 0;
    }
   
    @Override
    public Boolean isEmpty() {
        return largo == 0;
    }

    @Override
    public int largo() {
        return largo;
    }

    @Override
    public void imprimirLista() {
        Node<T> actual = head;
        while (actual != null) {
            System.out.print(actual.getValor() + " -> ");
            actual = actual.getNext();
        }
        System.out.println("null");
    }
        
    @Override
    public T obtener(int index) {
        if (index < largo) {
            Node<T> nodoActual = this.head;
            for (int i = 0; i < index; i++) {
                nodoActual = nodoActual.getNext();
            }
            return nodoActual.getValor();
        } else return null;
    }
        
    @Override
    public void agregar(T obj, Integer index) {
        Node<T> nodoNuevo = new Node<>();
        nodoNuevo.setValor(obj);

        // esto en caso de que se desee agregar al final
        if (index == null) {
            if (isEmpty()) {
                head = nodoNuevo;
                tail = nodoNuevo;
            }
            else {
                tail.setNext(nodoNuevo);
                tail = nodoNuevo;
            }
        }

        // para agregar en una posición en particular
        else if (index == 0) { // si quiere insertar al inicio
            nodoNuevo.setNext(head);
            head = nodoNuevo;
            if (largo == 0) { // si la lista estaba vacía
                tail = nodoNuevo;
            }
        }
        else {
            if (index < largo) {
                Node<T> nodoActual = head;
                for (int i = 0; i < index - 1; i++) {
                    nodoActual = nodoActual.getNext();
                }
                Node<T> nodoSiguiente = nodoActual.getNext();
                nodoActual.setNext(nodoNuevo); // conecto nodo actual a nodo nuevo
                nodoNuevo.setNext(nodoSiguiente); // conecto nodo nuevo al siguiente nodo
            }
        }

    largo++; // aumento el tamaño de la lista
    }

    @Override
    public void eliminar(int index) {
        // index fuera de rango
        if (index < 0 || index >= largo) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites de la lista");
        }

        if (index == 0) { // eliminar primer elemento
            head = head.getNext();
            if (largo == 1) {
                tail = null;
            }
        } else {
            Node<T> nodoActual = head;
            for (int i = 0; i < index - 1; i++) {
                nodoActual = nodoActual.getNext();
            }
            Node<T> nodoAEliminar = nodoActual.getNext();
            Node<T> nodoPosteriorAlQueElimino = nodoAEliminar.getNext();
            nodoActual.setNext(nodoPosteriorAlQueElimino);
            nodoAEliminar.setNext(null);
            // ahora el nodo index-1 apunta al nodo index+1 por lo que el Garbage Collector
            // termina viendo que el nodo a eliminar no lo puedo acceder con nada y además
            // su next es null
            if (nodoAEliminar == tail) {
                tail = nodoActual;
            }
        }
        largo--;
    }

    @Override
    public Boolean elementoSeEncuentra(T obj) {
        Node<T> nodoActual = head;

        while (nodoActual != null) {
            if (nodoActual.getValor().equals(obj)) {
                return true;
            }
            nodoActual = nodoActual.getNext();
        }
        return false;
    }

    @Override
    public void addFirst(T value) {
        Node<T> nuevoNodo = new Node<>();
        nuevoNodo.setValor(value);
        nuevoNodo.setNext(head);
        head = nuevoNodo;

        if (largo == 0) { // caso lista vacía
            tail = nuevoNodo;
        }
        largo++;
    }
        
    @Override
    public void addLast(T value) {
        Node<T> nuevoNodo = new Node<>();
        nuevoNodo.setValor(value);
        nuevoNodo.setNext(null);

        if (largo == 0) { // caso lista vacía
            head = nuevoNodo;
            tail = nuevoNodo;
        } else {
            tail.setNext(nuevoNodo);
            tail = nuevoNodo; // esto no va a fuera a pesar de estar en ambos pues si lo pongo
                                // antes de ese setNext, pierdo el dato sobre qué era antes ultimo
        }
        largo++;
    }

    @Override
    public void intercambiar(T obj, int direccion) {
        if (head == null || head.getNext() == null) {
            return; // para intercambiar tiene que haber al menos dos elementos
        }

        Node<T> actual = head;
        Node<T> previo = null;
        Node<T> previoprevio = null;

        while (actual != null && !actual.getValor().equals(obj)) { // recorre hasta encontrar al elemento
            previoprevio = previo;
            previo = actual;
            actual = actual.getNext();
        }

        if (actual == null) { // si no lo encuentra y llega a pasar al último nodo da null
            System.out.println("No se encontró el elemento");
            return;
        }

        if (direccion == -1) { // el intercambio, si es -1 con el anterior
            if (previo == null) {
                return;
            }

            previo.setNext(actual.getNext());
            actual.setNext(previo);

            if (previoprevio != null) {
                previoprevio.setNext(actual);
            } else {
                head = actual; // si intercambiamos con el primer nodo, actual se convierte en head
            }
        }
        else if (direccion == 1) { // intercambiar con el siguiente
            Node<T> siguiente = actual.getNext();
            if (siguiente == null) {
                return; // no se puede intercambiar si es el último elemento
            }

            actual.setNext(siguiente.getNext());
            siguiente.setNext(actual);

            if (previo != null) {
                previo.setNext(siguiente);
            } else {
                head = siguiente;
            }

            if (actual == tail) {
                tail = siguiente;
            }
        }
    }

    @Override
    public MyLinkedListImpl<T> agregarLista(MyLinkedListImpl<T> listaAgregar) {
        MyLinkedListImpl<T> resultado = new MyLinkedListImpl<>();
        Node<T> actual = this.head;

        while (actual != null) {
            if (listaAgregar.elementoSeEncuentra(actual.getValor())) {
                resultado.addLast(actual.getValor());
            }
            actual = actual.getNext();
        }
        return resultado;
    }

    @Override
    public MyLinkedListImpl<T> juntarListasSinInterseccion(MyLinkedListImpl<T> lista2) {
        MyLinkedListImpl<T> resultado = new MyLinkedListImpl<>();

        Node<T> actual = head;
        while (actual != null) { // si no está en lista2, lo agrega a resultado
            if (!lista2.elementoSeEncuentra(actual.getValor())) {
                resultado.addLast(actual.getValor());
            }
            actual = actual.getNext();
        }

        Node<T> actual2 = lista2.head; // también lo hago desde la lista2
        while (actual2 != null) {
            if (!elementoSeEncuentra(actual2.getValor())) {
                resultado.addLast(actual2.getValor());
            }
            actual2 = actual2.getNext();
        }
        return resultado;
    }

    @Override
    public void addOrdered(T elemento) {
        Node<T> nodoNuevo = new Node<>();
        nodoNuevo.setValor(elemento);

        if (head == null || elemento.compareTo(head.getValor()) < 0) {
            nodoNuevo.setNext(head); // si no hay head al inicio o si es menor que el head
            head = nodoNuevo;
            if (tail == null) {
                tail = nodoNuevo;
            }
        } else {
            Node<T> nodoActual = head;
            while (nodoActual.getNext() != null && elemento.compareTo(nodoActual.getNext().getValor()) > 0 ) {
                nodoActual = nodoActual.getNext();
            }

            nodoNuevo.setNext(nodoActual.getNext()); // el next del
            nodoActual.setNext(nodoNuevo);

            if (nodoNuevo.getNext() == null) {
                tail = nodoNuevo;
            }
        }
        largo++;
    }

    // ================================================================================================================
    // MyQueue<T>
    // ================================================================================================================

    @Override
    public void enqueue(T element) { // este metodo mejora con el uso de tail
        Node<T> nodoAgrego = new Node<>(element);
        if (largo == 0) {
            head = nodoAgrego;
            tail = nodoAgrego;
        } else {
            tail.setNext(nodoAgrego); // agregar al final
            tail = nodoAgrego;
        }
        largo++;
    }

    @Override
    public T dequeue() throws EmptyQueueException { // saco al head
        if (largo == 0) {
            throw new EmptyQueueException();
        } else {
            Node<T> nodoBorrar = head;
            T valorBorrar = head.getValor();
            head = head.getNext();
            nodoBorrar.setNext(null); // esto no es necesario, pero es Garbage Collector Friendly (+ rendimiento)
            if (head == null) tail = null; // si la queue queda vacía, tail también se vuelve null
            largo--;
            return valorBorrar;
        }
    }

    @Override
    public T peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return head.getValor();
    }

    @Override
    public void makeEmpty() {
        while (head != null) {
            Node<T> temp = head;
            head = head.getNext();
            temp.setNext(null); // GC friendly como en dequeue
        }
        tail = null;
        largo = 0;
    }

    @Override // para devolver todos los elementos que tiene la queue como un String
    public String toString() { // mejor usar clase StringBuilder en vez de un for, pues no crea un objeto cada vez
        StringBuilder sb = new StringBuilder("[");
        Node<T> actual = head;
        while (actual != null) {
            sb.append(actual.getValor()); // append valor
            if (actual.getNext() != null) sb.append(", "); // pongo coma entre elementos
            actual = actual.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int tamanio() {
        return largo;
    }

    @Override
    public void enqueueWithPriority(T value) {
        addOrdered(value);
    }

    // ================================================================================================================
    // MyStack<T>
    // ================================================================================================================

    @Override
    public void pop() throws EmptyStackException {
        if (largo == 0) {
            throw new EmptyStackException();
        }
        head = head.getNext();
        largo --;
    }

    @Override
    public T top() throws EmptyStackException {
        if (largo == 0) {
            throw new EmptyStackException();
        }
        return head.getValor();
    }

    @Override
    public void push(T element) {
        Node<T> nodoAgrego = new Node<>(element);
        nodoAgrego.setNext(head);
        head = nodoAgrego;
        largo ++;
    }

    @Override
    public void makeEmptyStack() {
        while (!isEmpty()) {
            pop();
        }
    }

}
