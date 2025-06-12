package um.edu.uy.entities;

import um.edu.uy.importer.MoviesMetadataLoader;
import um.edu.uy.importer.RatingsLoader;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.hash.HashTable;

public class UMovie {

    private HashTable<String, Movie> moviesPorId;
    private HashTable<String, Collection> collectionsPorId;
    private HashTable<String, Genre> genresPorId;

    // constructor
    public UMovie() {
        this.moviesPorId = new ClosedHashTableImpl<>(2000000, 1);
        this.collectionsPorId = new ClosedHashTableImpl<>(5000, 1);
        this.genresPorId = new ClosedHashTableImpl<>(100, 1);
    }

    // load data
    public void loadMovies() {
        MoviesMetadataLoader.loadMovies("dataset/movies_metadata.csv", moviesPorId, collectionsPorId, genresPorId);
    }

    public void loadRatings() {
        RatingsLoader.loadRatings("dataset/ratings_1mm.csv", moviesPorId);
    }


}
