package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    private Movie movie;
    private Movie movie2;
    private Rating rating1;
    private Rating rating2;

    @BeforeEach
    void setUp() {
        movie = new Movie("100", "Avatar", "en", "2923706026", "1");
        movie2 = new Movie("200", "Titanic", "en", "2257844554", null);
        rating1 = new Rating("user1", "100", 4.5f, java.time.LocalDate.now());
        rating2 = new Rating("user2", "100", 5.0f, java.time.LocalDate.now());
    }

    @Test
    void testConstructor() {
        assertNotNull(movie);
        assertEquals("100", movie.getMovieId());
        assertEquals("Avatar", movie.getTitle());
        assertEquals("en", movie.getOriginalLanguage());
        assertEquals(2923706026.0, movie.getRevenue(), 0.001);
        assertEquals("1", movie.getBelongsToCollection());
        assertNotNull(movie.getRatings());
        assertEquals(0, movie.getRatings().size());
    }

    @Test
    void testCompareTo() {
        assertTrue(movie.compareTo(movie2) < 0);
        assertTrue(movie2.compareTo(movie) > 0);
        assertEquals(0, movie.compareTo(new Movie("100", "Different Title", "fr", "0", null)));
    }


}
