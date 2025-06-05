package um.edu.uy.tads.list.linked;

public class Node<T> {

    private Node<T> next;
    private T valor;

    public Node(T valor) {
        this.next = null;
        this.valor = valor;
    }

    public Node() {
        this.valor = null;
        this.next = null;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

}
