package um.edu.uy.entities;

import um.edu.uy.tads.list.MyList;

public class Genre {

    private String genreId;
    private String genreName;
    private MyList<Movie> moviesGenre;

    public Genre(String genreId, String genreName, MyList<Movie> moviesGenre) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.moviesGenre = moviesGenre;
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
}
