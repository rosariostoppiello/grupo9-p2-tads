package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UMovieTest {

    private UMovie uMovie;

    @BeforeEach
    void setUp() {
        uMovie = new UMovie();
    }

    @Test
    void testConstructor() {
        assertNotNull(uMovie);
        assertNotNull(uMovie.getMoviesById());
        assertNotNull(uMovie.getCollectionsById());
        assertNotNull(uMovie.getGenresById());
        assertNotNull(uMovie.getLanguagesByName());
        assertNotNull(uMovie.getActorsById());
        assertNotNull(uMovie.getDirectorsById());
    }

    @Test
    void testQueryWithInvalidNumbers() {
        assertDoesNotThrow(() -> {
            uMovie.query(0);
            uMovie.query(7);
            uMovie.query(-1);
            uMovie.query(100);
        });
    }

}