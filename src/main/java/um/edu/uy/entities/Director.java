package um.edu.uy.entities;

import um.edu.uy.tads.list.MyList;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public class Director extends Person implements Comparable<Director> {

    private MyList<String> movieIds;

    public Director(String personId, String name) {
        super(personId, name);
        this.movieIds = new MyLinkedListImpl<>();
    }

    public void addMovieId(String movieId) {
        if (movieId != null && !movieIds.elementoSeEncuentra(movieId)) {
            movieIds.addLast(movieId);
        }
    }

    public MyList<String> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(MyList<String> movieIds) {
        this.movieIds = movieIds;
    }

    public int getMovieCount() {
        return movieIds.largo();
    }

    @Override
    public int compareTo(Director o) {
        return this.personId.compareTo(o.personId);
    }
}