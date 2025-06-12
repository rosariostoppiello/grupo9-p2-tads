package um.edu.uy.entities;

import um.edu.uy.tads.list.MyList;

public class Collection {

    private String collectionId;

    private String collectionName;

    private MyList<Movie> moviesCollection;

    public Collection(String collectionId, String collectionName, MyList<Movie> moviesCollection) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.moviesCollection = moviesCollection;
    }

    public void addMovieToCollection(Movie movie) {
        moviesCollection.addLast(movie);
    }

    public MyList<Movie> getMoviesCollection() {
        return moviesCollection;
    }

    public void setMoviesCollection(MyList<Movie> moviesCollection) {
        this.moviesCollection = moviesCollection;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
