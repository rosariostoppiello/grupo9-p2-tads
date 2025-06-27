package um.edu.uy.queries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;

import java.time.LocalDate;

public class QueryTop5MoviesByLanguageTest {

    private QueryTop5MoviesByLanguage query;
    private HashTable<String, Movie> moviesById;

    @BeforeEach
    void setUp() {
        query = new QueryTop5MoviesByLanguage();
        moviesById = new ClosedHashTableImpl<>(100, 1);
        Movie movie1 = new Movie("1", "English Movie 1", "en", "1000000", null);
        Movie movie2 = new Movie("2", "English Movie 2", "en", "2000000", null);
        Movie movie3 = new Movie("3", "Spanish Movie", "es", "3000000", null);

        movie1.getRatings().addLast(new Rating("user1", "1", 4.5f, LocalDate.now()));
        movie2.getRatings().addLast(new Rating("user1", "2", 4.0f, LocalDate.now()));
        movie3.getRatings().addLast(new Rating("user1", "3", 3.5f, LocalDate.now()));

        try {
            moviesById.insert("1", movie1);
            moviesById.insert("2", movie2);
            moviesById.insert("3", movie3);
        } catch (ElementAlreadyExistsException e) {
        }
    }

    @Test
    void testQueryTop5MoviesByLanguage() {
        assertDoesNotThrow(() -> query.queryTop5MoviesByLanguage(moviesById));
    }
}
