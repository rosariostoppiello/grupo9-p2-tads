package um.edu.uy.entities;

import java.util.LinkedList;

public class Director extends Person implements Comparable<Director> {

    private LinkedList<Movie> movies;

    public Director(String personId, String name, String creditId) {
        super(personId, name, creditId);
    }

    @Override
    public int compareTo(Director o) {
        return this.personId.compareTo(o.personId);
    }
}