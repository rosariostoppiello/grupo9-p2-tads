package um.edu.uy.entities;

import um.edu.uy.tads.list.MyList;

public class Movie implements Comparable<Movie> {

    private String movieId;
    private String title;
    private String originalLanguage; // id language
    private double revenue;
    private String belongsToCollection; // guarda id colección
    private MyList<Rating> ratings;

    public String getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(String belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public MyList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(MyList<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public int compareTo(Movie o) {
        return this.movieId.compareTo(o.movieId);
    }
}
