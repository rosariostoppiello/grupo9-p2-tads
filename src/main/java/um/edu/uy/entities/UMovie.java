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
        this.moviesById = new ClosedHashTableImpl<>(2000000, 1);
        this.collectionsById = new ClosedHashTableImpl<>(5000, 1);
        this.genresById = new ClosedHashTableImpl<>(100, 1);
        this.actorsById = new ClosedHashTableImpl<>(150000, 1);
        this.participantsById = new ClosedHashTableImpl<>(150000, 1);
    }

    // load data
    public void loadMovies() {
        MoviesMetadataLoader.loadMovies("dataset/movies_metadata.csv", moviesById, collectionsById, genresById);
    }

    public void loadRatings() {
        RatingsLoader.loadRatings("dataset/ratings_1mm.csv", moviesById);
    }

    public void loadCredits() {
        CreditsLoader.loadCredits("credits.csv", actorsById, participantsById);
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
