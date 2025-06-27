package um.edu.uy.queries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import um.edu.uy.entities.Director;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;

import java.time.LocalDate;

public class QueryTop10DirectorsByRatingTest {

    private QueryTop10DirectorsByRating query;
    private HashTable<String, Movie> moviesById;
    private HashTable<String, Director> directorsById;

    @BeforeEach
    void setUp() {
        query = new QueryTop10DirectorsByRating();
        moviesById = new ClosedHashTableImpl<>(100, 1);
        directorsById = new ClosedHashTableImpl<>(100, 1);

        try {
            Director director1 = new Director("1", "Director 1");
            Director director2 = new Director("2", "Director 2");

            directorsById.insert("1", director1);
            directorsById.insert("2", director2);

            Movie movie1 = new Movie("101", "Movie 1", "en", "1000000", null);
            Movie movie2 = new Movie("102", "Movie 2", "en", "2000000", null);
            Movie movie3 = new Movie("201", "Movie 3", "en", "3000000", null);
            Movie movie4 = new Movie("202", "Movie 4", "en", "4000000", null);
            moviesById.insert("101", movie1);
            moviesById.insert("102", movie2);
            moviesById.insert("201", movie3);
            moviesById.insert("202", movie4);


            director1.addMovieId("101");
            director1.addMovieId("102");
            director2.addMovieId("201");
            director2.addMovieId("202");


            for (int i = 0; i < 150; i++) {
                movie1.getRatings().addLast(new Rating("user" + i, "101", 4.5f, LocalDate.now()));
                movie2.getRatings().addLast(new Rating("user" + i, "102", 4.0f, LocalDate.now()));
                movie3.getRatings().addLast(new Rating("user" + i, "201", 3.5f, LocalDate.now()));
                movie4.getRatings().addLast(new Rating("user" + i, "202", 5.0f, LocalDate.now()));
            }
        } catch (ElementAlreadyExistsException e) {

        }
    }

    @Test
    void testQueryTop10DirectorsByRating() {
        assertDoesNotThrow(() -> query.queryTop10DirectorsByRating(moviesById, directorsById));
    }
}
