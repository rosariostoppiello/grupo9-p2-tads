package um.edu.uy.entities;

import um.edu.uy.importer.MoviesMetadataLoader;
import um.edu.uy.importer.RatingsLoader;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.hash.HashTable;

public class UMovie {

    private HashTable<String, Movie> moviesById;
    private HashTable<String, Collection> collectionsById;
    private HashTable<String, Genre> genresById;
    private HashTable<String, Language> languagesByName;

    // constructor
    public UMovie() {
        this.moviesById = new ClosedHashTableImpl<>(2000000, 1);
        this.collectionsById = new ClosedHashTableImpl<>(5000, 1);
        this.genresById = new ClosedHashTableImpl<>(100, 1);
    }

    // load data
    public void loadMovies() {
        MoviesMetadataLoader.loadMovies("dataset/movies_metadata.csv", moviesById, collectionsById, genresById);
    }

    public void loadRatings() {
        RatingsLoader.loadRatings("dataset/ratings_1mm.csv", moviesById);
    }


}
