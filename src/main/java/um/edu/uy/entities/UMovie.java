package um.edu.uy.entities;

import um.edu.uy.importer.CreditsLoader;
import um.edu.uy.importer.MoviesMetadataLoader;
import um.edu.uy.importer.RatingsLoader;

import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.hash.HashTable;

public class UMovie {

    private HashTable<String, Movie> moviesById;
    private HashTable<String, Collection> collectionsById;
    private HashTable<String, Genre> genresById;
    private HashTable<String, Language> languagesByName;
    private HashTable<String, Actor> actorsById;
    private HashTable<String, Participant> participantsById;

    // constructor
    public UMovie() {
        this.moviesById = new ClosedHashTableImpl<>(150000, 1);
        this.collectionsById = new ClosedHashTableImpl<>(1699, 1);
        this.genresById = new ClosedHashTableImpl<>(37, 1);
        this.languagesByName = new ClosedHashTableImpl<>(71,1);

        this.actorsById = new ClosedHashTableImpl<>(2000000, 1);
        this.participantsById = new ClosedHashTableImpl<>(2000000, 1);
    }

    // load data
    public void loadMovies() {
        MoviesMetadataLoader.loadMovies("movies_metadata.csv", moviesById, collectionsById, genresById, languagesByName);
    }

    public void loadRatings() {
        RatingsLoader.loadRatings("ratings_1mm.csv", moviesById);
    }

    public void loadCredits() {
        CreditsLoader.loadCredits("creditscsv", actorsById, participantsById);
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

    public HashTable<String, Participant> getParticipantsById() {
        return participantsById;
    }

    public void setParticipantsById(HashTable<String, Participant> participantsById) {
        this.participantsById = participantsById;
    }
}