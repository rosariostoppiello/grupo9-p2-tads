package um.edu.uy.entities;

import java.util.LinkedList;

public class Director extends Person implements Comparable<Director> {

    private LinkedList<String> movieIds;

    public Director(String personId, String name, String creditId) {
        super(personId, name, creditId);
        this.movieIds = new LinkedList<>();
    }
    public void addMovieId(String movieId) {
        if (movieId != null && !movieIds.contains(movieId)) {
            movieIds.add(movieId);
        }
    }

    public LinkedList<String> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(LinkedList<String> movieIds) {
        this.movieIds = movieIds;
    }

    public int getMovieCount() {
        return movieIds.size();
    }

    @Override
    public int compareTo(Director o) {
        return this.personId.compareTo(o.personId);
    }
}