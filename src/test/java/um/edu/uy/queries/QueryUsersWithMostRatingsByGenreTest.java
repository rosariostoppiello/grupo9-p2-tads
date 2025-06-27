package um.edu.uy.queries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import um.edu.uy.entities.Genre;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;

import java.time.LocalDate;

public class QueryUsersWithMostRatingsByGenreTest {

    private QueryUsersWithMostRatingsByGenre query;
    private HashTable<String, Genre> genresById;

    @BeforeEach
    void setUp() {
        query = new QueryUsersWithMostRatingsByGenre();
        genresById = new ClosedHashTableImpl<>(100, 1);

        try {
            Genre actionGenre = new Genre("1", "Action");
            Genre comedyGenre = new Genre("2", "Comedy");
            Movie actionMovie1 = new Movie("101", "Action Movie 1", "en", "1000000", null);
            Movie actionMovie2 = new Movie("102", "Comedy Movie 2", "en", "2000000", null);
            Movie comedyMovie = new Movie("201", "Horror Movie", "en", "3000000", null);

            actionGenre.addMovieToGenre(actionMovie1);
            actionGenre.addMovieToGenre(actionMovie2);
            comedyGenre.addMovieToGenre(comedyMovie);
            for (int i = 0; i < 5; i++) {

                actionMovie1.getRatings().addLast(new Rating("user1", "101", 4.5f, LocalDate.now()));
                actionMovie2.getRatings().addLast(new Rating("user1", "102", 4.0f, LocalDate.now()));

                comedyMovie.getRatings().addLast(new Rating("user2", "201", 3.5f, LocalDate.now()));
            }

            genresById.insert("1", actionGenre);
            genresById.insert("2", comedyGenre);
        } catch (ElementAlreadyExistsException e) {
        }
    }

    @Test
    void testQueryUsersWithMostRatingsByGenre() {
        assertDoesNotThrow(() -> query.queryUsersWithMostRatingsByGenre(genresById));
    }
}