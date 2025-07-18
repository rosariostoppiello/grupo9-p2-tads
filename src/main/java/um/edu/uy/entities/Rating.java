package um.edu.uy.entities;

import java.time.LocalDate;

public class Rating implements Comparable<Rating> {

    private String userId;
    private String movieId;
    private Float rating;
    private LocalDate date;

    public Rating(String userId, String movieId, Float rating, LocalDate date) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.date = date;
    }

    @Override
    public int compareTo(Rating o) {
        return this.rating.compareTo(o.rating);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
