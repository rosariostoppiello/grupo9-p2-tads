package um.edu.uy.entities;

import um.edu.uy.tads.list.MyList;

public class Language implements Comparable<Language> {

    private String languageId;
    private MyList<Movie> moviesLanguage;

    public Language(String languageId) {
        this.languageId = languageId;
    }

    public void addMovie(Movie movie) {
        moviesLanguage.addLast(movie);
    }

    public MyList<Movie> getMoviesLanguage() {
        return moviesLanguage;
    }

    public void setMoviesLanguage(MyList<Movie> moviesLanguage) {
        this.moviesLanguage = moviesLanguage;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    @Override
    public int compareTo(Language o) {
        return this.languageId.compareTo(o.languageId);
    }
}
