package um.edu.uy.tads.queue;

import um.edu.uy.tads.exceptions.EmptyQueueException;

public interface MyQueue<T> {

    void enqueue (T element);
    T dequeue () throws EmptyQueueException;

    // metodos extra para practicar
    public T peek() throws EmptyQueueException;
    public void makeEmpty();
    public String toString();
    public int tamanio();
    void enqueueWithPriority(T value);
}