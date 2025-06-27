package um.edu.uy.queries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;

import java.time.LocalDate;
import java.time.Month;

public class QueryActorWithMostRatingsEveryMonthTest {

    private QueryActorWithMostRatingsEveryMonth query;
    private HashTable<String, Movie> moviesById;
    private HashTable<String, Actor> actorsById;

    @BeforeEach
    void setUp() {
        query = new QueryActorWithMostRatingsEveryMonth();
        moviesById = new ClosedHashTableImpl<>(100, 1);
        actorsById = new ClosedHashTableImpl<>(100, 1);

        try {
            Actor actor1 = new Actor("1", "Actor 1");
            Actor actor2 = new Actor("2", "Actor 2");

            actorsById.insert("1", actor1);
            actorsById.insert("2", actor2);

            Movie movie1 = new Movie("101", "Movie 1", "en", "1000000", null);
            Movie movie2 = new Movie("102", "Movie 2", "en", "2000000", null);

            moviesById.insert("101", movie1);
            moviesById.insert("102", movie2);

            actor1.addMovieId("101");
            actor2.addMovieId("102");
            for (int i = 0; i < 10; i++) {
                movie1.getRatings().addLast(new Rating("user" + i, "101", 4.5f,
                        LocalDate.of(2023, Month.JANUARY, i + 1)));
                movie2.getRatings().addLast(new Rating("user" + i, "102", 4.0f,
                        LocalDate.of(2023, Month.FEBRUARY, i + 1)));
            }
        } catch (ElementAlreadyExistsException e) {
        }
    }

    @Test
    void testQueryActorWithMostRatingsEveryMonth() {
        assertDoesNotThrow(() -> query.queryActorWithMostRatingsEveryMonth(moviesById, actorsById));
    }
}