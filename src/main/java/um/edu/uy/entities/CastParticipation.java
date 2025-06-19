package um.edu.uy.entities;

public class CastParticipation {
    private String castId;
    private String movieId;
    private String creditId;

    public CastParticipation (String castId, String movieId, String creditId) {
        this.castId = castId;
        this.movieId = movieId;
        this.creditId = creditId;

    }

    public String getCastId () {
        return castId;
    }

    public void setCastId (String castId) {
        this.castId = castId;
    }

    public String getMovieId () {
        return movieId;
    }

    public void setMovieId (String movieId) {
        this.movieId = movieId;
    }

    public String getCreditId () {
        return creditId;
    }

    public void setCreditId (String creditId) {
        this.creditId = creditId;
    }
}
