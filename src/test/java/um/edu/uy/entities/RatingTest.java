package um.edu.uy.entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    private Rating rating;
    private Rating otherRating;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2023, 6, 15);
        rating = new Rating("user123", "movie456", 4.5f, testDate);
        otherRating = new Rating("user789", "movie321", 3.0f, LocalDate.of(2023, 7, 20));
    }

    @Test
    void testConstructor() {
        assertNotNull(rating);
        assertEquals("user123", rating.getUserId());
        assertEquals("movie456", rating.getMovieId());
        assertEquals(4.5f, rating.getRating(), 0.001f);
        assertEquals(testDate, rating.getDate());
    }

}