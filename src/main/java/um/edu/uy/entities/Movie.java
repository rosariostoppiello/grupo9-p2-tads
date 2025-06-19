package um.edu.uy.entities;

import um.edu.uy.tads.list.MyList;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public class Movie implements Comparable<Movie> {

    private String movieId;
    private String title;
    private String originalLanguage; // id language
    private double revenue;
    private String belongsToCollection; // guarda id colección
    private MyList<Rating> ratings;

    public Movie(String movieId, String title,
                 String originalLanguage, String revenue,
                 String belongsToCollection) {
        this.movieId = movieId;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.setRevenue(revenue);
        this.belongsToCollection = belongsToCollection;
        this.ratings = new MyLinkedListImpl<>();
    }

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

    public void setRevenue(String revenue) {
        if (revenue == null || revenue.trim().isEmpty() || revenue.equals("0")) {
            this.revenue = 0.0;
        } else {
            this.revenue = Double.parseDouble(revenue);
        }
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
