package um.edu.uy.entities;

import java.util.LinkedList;

public class Director extends Person {

    private LinkedList<Movie> movies;

    public Director(String personId, String name, String creditId) {
        super(personId, name, creditId);
    }
}