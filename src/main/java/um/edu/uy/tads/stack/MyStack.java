package um.edu.uy.tads.stack;

import java.util.EmptyStackException;

public interface MyStack<T> {

    void pop () throws EmptyStackException;
    T top() throws EmptyStackException;
    void push (T element);
    void makeEmptyStack ();

}
