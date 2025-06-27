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

public class QueryTop10MoviesByRatingTest {

    private QueryTop10MoviesByRating query;
    private HashTable<String, Movie> moviesById;

    @BeforeEach
    void setUp() {
        query = new QueryTop10MoviesByRating();
        moviesById = new ClosedHashTableImpl<>(100, 1);

        try {
            for (int i = 1; i <= 5; i++) {
                Movie movie = new Movie(String.valueOf(i), "Movie " + i, "en", String.valueOf(i * 1000000), null);

                for (int j = 0; j < 100; j++) {
                    movie.getRatings().addLast(new Rating("user" + j, String.valueOf(i), (float)(i % 5 + 1), LocalDate.now()));
                }
                moviesById.insert(String.valueOf(i), movie);
            }
        } catch (ElementAlreadyExistsException e) {
        }
    }
    @Test
    void testQueryTop10MoviesByRating() {
        assertDoesNotThrow(() -> query.queryTop10MoviesByRating(moviesById));
    }
}