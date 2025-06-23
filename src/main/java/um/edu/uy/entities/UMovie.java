package um.edu.uy.entities;

import um.edu.uy.importer.CreditsLoader;
import um.edu.uy.importer.MoviesMetadataLoader;
import um.edu.uy.importer.RatingsLoader;

import um.edu.uy.queries.*;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.hash.HashTable;

public class UMovie {

    private HashTable<String, Movie> moviesById;
    private HashTable<String, Collection> collectionsById;
    private HashTable<String, Genre> genresById;
    private HashTable<String, Language> languagesByName;

    private HashTable<String, Actor> actorsById;
    private HashTable<String, Director> directorsById;

    // constructor
    public UMovie() {
        this.moviesById = new ClosedHashTableImpl<>(150000, 1);
        this.collectionsById = new ClosedHashTableImpl<>(1699, 1);
        this.genresById = new ClosedHashTableImpl<>(37, 1);
        this.languagesByName = new ClosedHashTableImpl<>(71,1);

        this.actorsById = new ClosedHashTableImpl<>(500000, 1);
        this.directorsById = new ClosedHashTableImpl<>(50000, 1);
    }

    // load data
    public void loadMovies() {
        MoviesMetadataLoader.loadMovies("movies_metadata.csv", moviesById, collectionsById, genresById, languagesByName);
    }

    public void loadRatings() {
        RatingsLoader.loadRatings("ratings_1mm.csv", moviesById);
    }

    public void loadCredits() {
        CreditsLoader.loadCredits("credits.csv", actorsById, directorsById);
    }

    // queries
    public void query(int num) {

        switch (num) {
            case 1:
                QueryTop5MoviesByLanguage query1 = new QueryTop5MoviesByLanguage();
                query1.queryTop5MoviesByLanguage(moviesById);
                break;
            case 2:
                QueryTop10MoviesByRating query2 = new QueryTop10MoviesByRating();
                query2.queryTop10MoviesByRating(moviesById);
                break;
            case 3:
                QueryTop5CollectionsByRevenue query3 = new QueryTop5CollectionsByRevenue();
                query3.queryTop5CollectionsByRevenue(moviesById, collectionsById);
                break;
            case 4:
                QueryTop10DirectorsByRating query4 = new QueryTop10DirectorsByRating();
                query4.queryTop10DirectorsByRating(moviesById, directorsById);
                break;
            case 5:
                QueryActorWithMostRatingsEveryMonth query5 = new QueryActorWithMostRatingsEveryMonth();
                query5.queryActorWithMostRatingsEveryMonth(moviesById, actorsById);
                break;
            case 6:
                QueryUsersWithMostRatingsByGenre query6 = new QueryUsersWithMostRatingsByGenre();
                query6.queryUsersWithMostRatingsByGenre(genresById);
                break;
        }
    }

    // getters and setters
    public HashTable<String, Movie> getMoviesById() {
        return moviesById;
    }

    public void setMoviesById(HashTable<String, Movie> moviesById) {
        this.moviesById = moviesById;
    }

    public HashTable<String, Collection> getCollectionsById() {
        return collectionsById;
    }

    public void setCollectionsById(HashTable<String, Collection> collectionsById) {
        this.collectionsById = collectionsById;
    }

    public HashTable<String, Genre> getGenresById() {
        return genresById;
    }

    public void setGenresById(HashTable<String, Genre> genresById) {
        this.genresById = genresById;
    }

    public HashTable<String, Language> getLanguagesByName() {
        return languagesByName;
    }

    public void setLanguagesByName(HashTable<String, Language> languagesByName) {
        this.languagesByName = languagesByName;
    }

    public HashTable<String, Actor> getActorsById() {
        return actorsById;
    }

    public void setActorsById(HashTable<String, Actor> actorsById) {
        this.actorsById = actorsById;
    }

    public HashTable<String, Director> getDirectorsById() {
        return directorsById;
    }

    public void setDirectorsById(HashTable<String, Director> directorsById) {
        this.directorsById = directorsById;
    }
}