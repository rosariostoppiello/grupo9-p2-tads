package um.edu.uy.queries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import um.edu.uy.entities.Collection;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;

public class QueryTop5CollectionsByRevenueTest {

    private QueryTop5CollectionsByRevenue query;
    private HashTable<String, Movie> moviesById;
    private HashTable<String, Collection> collectionsById;

    @BeforeEach
    void setUp() {
        query = new QueryTop5CollectionsByRevenue();
        moviesById = new ClosedHashTableImpl<>(100, 1);
        collectionsById = new ClosedHashTableImpl<>(100, 1);

        try {
            Collection collection1 = new Collection("1", "Collection 1");
            Collection collection2 = new Collection("2", "Collection 2");

            collectionsById.insert("1", collection1);
            collectionsById.insert("2", collection2);

            Movie movie1 = new Movie("101", "Movie 1", "en", "1000000", "1");
            Movie movie2 = new Movie("102", "Movie 2", "en", "2000000", "1");
            Movie movie3 = new Movie("201", "Movie 3", "en", "3000000", "2");

            Movie movie4 = new Movie("301", " Movie title", "en", "4000000", null);
            moviesById.insert("101", movie1);
            moviesById.insert("102", movie2);
            moviesById.insert("201", movie3);
            moviesById.insert("301", movie4);

            collection1.addMovieToCollection(movie1);
            collection1.addMovieToCollection(movie2);
            collection2.addMovieToCollection(movie3);
        } catch (ElementAlreadyExistsException e) {
        }
    }

    @Test
    void testQueryTop5CollectionsByRevenue() {
        assertDoesNotThrow(() -> query.queryTop5CollectionsByRevenue(moviesById, collectionsById));
    }
}
