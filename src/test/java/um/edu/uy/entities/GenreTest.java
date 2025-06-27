package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.uy.tads.list.linked.MyLinkedListImplTest;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {

    private Genre genre;
    private Genre genre2;
    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        genre = new Genre("1", "Action");
        genre2 = new Genre("2", "Comedy");
        movie1 = new Movie("100", "Die Hard", "en", "141600000", null);
        movie2 = new Movie("101", "Mad Max", "en", "378858340", null);

        // total ratings count
        movie1.getRatings().addLast(new Rating("user1", "100", 4.5f, java.time.LocalDate.now()));
        movie1.getRatings().addLast(new Rating("user2", "100", 5.0f, java.time.LocalDate.now()));
        movie2.getRatings().addLast(new Rating("user3", "101", 4.0f, java.time.LocalDate.now()));
    }

    @Test
    void testConstructor() {
        assertNotNull(genre);
        assertEquals("1", genre.getGenreId());
        assertEquals("Action", genre.getGenreName());
        assertNotNull(genre.getMoviesGenre());
        assertEquals(0, genre.getMoviesGenre().size());
    }

    @Test
    void testAddMovieToGenre() {
        genre.addMovieToGenre(movie1);
        assertEquals(1, genre.getMoviesGenre().size());
        assertEquals(movie1, genre.getMoviesGenre().find(0));
    }

    @Test
    void testGetTotalRatingsCount() {
        assertEquals(0, genre.getTotalRatingsCount());

        genre.addMovieToGenre(movie1); // has 2 ratings
        assertEquals(2, genre.getTotalRatingsCount());

        genre.addMovieToGenre(movie2); // has 1 rating
        assertEquals(3, genre.getTotalRatingsCount());
    }




}