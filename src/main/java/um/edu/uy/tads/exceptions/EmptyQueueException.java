package um.edu.uy.tads.exceptions;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException() {
        super("La cola está vacía.");
    }
}