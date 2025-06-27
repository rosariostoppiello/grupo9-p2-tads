package um.edu.uy.entities;

import um.edu.uy.tads.list.MyList;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public class Genre implements Comparable<Genre> {

    private String genreId;
    private String genreName;
    private MyList<Movie> moviesGenre;

    public Genre(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.moviesGenre = new MyLinkedListImpl<>();
    }

    public int getTotalRatingsCount() {
        int totalRatings = 0;
        Movie movie;
        for (int i = 0; i < moviesGenre.size(); i++) {
            movie = moviesGenre.find(i);
            totalRatings += movie.getRatings().size();
        }
        return totalRatings;
    }

    public void addMovieToGenre(Movie movie) {
        moviesGenre.addLast(movie);
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public MyList<Movie> getMoviesGenre() {
        return moviesGenre;
    }

    public void setMoviesGenre(MyList<Movie> moviesGenre) {
        this.moviesGenre = moviesGenre;
    }

    @Override
    public int compareTo(Genre o) {
        return this.genreId.compareTo(o.genreId);
    }

}
