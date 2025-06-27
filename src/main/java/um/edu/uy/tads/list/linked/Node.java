package um.edu.uy.tads.list.linked;

public class Node<T> {

    private Node<T> next;
    private T value;

    public Node(T valor) {
        this.next = null;
        this.value = valor;
    }

    public Node() {
        this.value = null;
        this.next = null;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
