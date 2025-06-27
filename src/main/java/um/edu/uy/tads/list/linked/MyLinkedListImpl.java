package um.edu.uy.tads.list.linked;

import um.edu.uy.tads.list.MyList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedListImpl<T extends Comparable<T>> implements MyList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    // ================================================================================================================
    // MyList<T>
    // ================================================================================================================

    public MyLinkedListImpl() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
   
    @Override
    public Boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printList() {
        Node<T> actual = head;
        while (actual != null) {
            System.out.print(actual.getValue() + " -> ");
            actual = actual.getNext();
        }
        System.out.println("null");
    }
        
    @Override
    public T find(int index) {
        if (index < size) {
            Node<T> actualNode = this.head;
            for (int i = 0; i < index; i++) {
                actualNode = actualNode.getNext();
            }
            return actualNode.getValue();
        } else return null;
    }
        
    @Override
    public void add(T obj, Integer index) {
        Node<T> newNode = new Node<>();
        newNode.setValue(obj);

        if (index == null) {
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            }
            else {
                tail.setNext(newNode);
                tail = newNode;
            }
        }

        else if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
        }
        else {
            if (index < size) {
                Node<T> nodoActual = head;
                for (int i = 0; i < index - 1; i++) {
                    nodoActual = nodoActual.getNext();
                }
                Node<T> nodoSiguiente = nodoActual.getNext();
                nodoActual.setNext(newNode);
                newNode.setNext(nodoSiguiente);
            }
        }

    size++;
    }

    @Override
    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }

        if (index == 0) {
            head = head.getNext();
            if (size == 1) {
                tail = null;
            }
        } else {
            Node<T> actualNode = head;
            for (int i = 0; i < index - 1; i++) {
                actualNode = actualNode.getNext();
            }
            Node<T> nodeToDelete = actualNode.getNext();
            Node<T> nextNodeToTheNodeToDelete = nodeToDelete.getNext();
            actualNode.setNext(nextNodeToTheNodeToDelete);
            nodeToDelete.setNext(null);
            if (nodeToDelete == tail) {
                tail = actualNode;
            }
        }
        size--;
    }

    @Override
    public Boolean elementExistsIn(T obj) {
        Node<T> actualNode = head;

        while (actualNode != null) {
            if (actualNode.getValue().equals(obj)) {
                return true;
            }
            actualNode = actualNode.getNext();
        }
        return false;
    }

    @Override
    public void addFirst(T value) {
        Node<T> newNode = new Node<>();
        newNode.setValue(value);
        newNode.setNext(head);
        head = newNode;

        if (size == 0) {
            tail = newNode;
        }
        size++;
    }
        
    @Override
    public void addLast(T value) {
        Node<T> newNode = new Node<>();
        newNode.setValue(value);
        newNode.setNext(null);

        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void swap(T obj, int direction) {
        if (head == null || head.getNext() == null) {
            return;
        }

        Node<T> actual = head;
        Node<T> previous = null;
        Node<T> previousPrevious = null;

        while (actual != null && !actual.getValue().equals(obj)) {
            previousPrevious = previous;
            previous = actual;
            actual = actual.getNext();
        }

        if (actual == null) {
            System.out.println("No se encontró el elemento");
            return;
        }

        if (direction == -1) {
            if (previous == null) {
                return;
            }

            previous.setNext(actual.getNext());
            actual.setNext(previous);

            if (previousPrevious != null) {
                previousPrevious.setNext(actual);
            } else {
                head = actual;
            }
        }
        else if (direction == 1) {
            Node<T> siguiente = actual.getNext();
            if (siguiente == null) {
                return;
            }

            actual.setNext(siguiente.getNext());
            siguiente.setNext(actual);

            if (previous != null) {
                previous.setNext(siguiente);
            } else {
                head = siguiente;
            }

            if (actual == tail) {
                tail = siguiente;
            }
        }
    }

    @Override
    public MyLinkedListImpl<T> addList(MyLinkedListImpl<T> listToAdd) {
        MyLinkedListImpl<T> result = new MyLinkedListImpl<>();
        Node<T> actual = this.head;

        while (actual != null) {
            if (listToAdd.elementExistsIn(actual.getValue())) {
                result.addLast(actual.getValue());
            }
            actual = actual.getNext();
        }
        return result;
    }

    @Override
    public MyLinkedListImpl<T> addUpListsWithoutIntersection(MyLinkedListImpl<T> list2) {
        MyLinkedListImpl<T> result = new MyLinkedListImpl<>();

        Node<T> actual = head;
        while (actual != null) {
            if (!list2.elementExistsIn(actual.getValue())) {
                result.addLast(actual.getValue());
            }
            actual = actual.getNext();
        }

        Node<T> actual2 = list2.head;
        while (actual2 != null) {
            if (!elementExistsIn(actual2.getValue())) {
                result.addLast(actual2.getValue());
            }
            actual2 = actual2.getNext();
        }
        return result;
    }

    @Override
    public void addOrdered(T element) {
        Node<T> newNode = new Node<>();
        newNode.setValue(element);

        if (head == null || element.compareTo(head.getValue()) < 0) {
            newNode.setNext(head);
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else {
            Node<T> actualNode = head;
            while (actualNode.getNext() != null && element.compareTo(actualNode.getNext().getValue()) > 0 ) {
                actualNode = actualNode.getNext();
            }

            newNode.setNext(actualNode.getNext());
            actualNode.setNext(newNode);

            if (newNode.getNext() == null) {
                tail = newNode;
            }
        }
        size++;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyListIterator();
    }

    private class MyListIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay más elementos");
            }
            T data = current.getValue();
            current = current.getNext();
            return data;
        }
    }
}
