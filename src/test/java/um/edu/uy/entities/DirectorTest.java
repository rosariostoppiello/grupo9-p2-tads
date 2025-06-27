package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectorTest {

    private Director director;
    private Director director2;

    @BeforeEach
    void setUp() {
        director = new Director("100", "Christopher Nolan");
        director2 = new Director("200", "Quentin Tarantino");
    }

    @Test
    void testConstructor() {
        assertNotNull(director);
        assertEquals("100", director.getPersonId());
        assertEquals("Christopher Nolan", director.getName());
        assertNotNull(director.getMovieIds());
        assertEquals(0, director.getMovieIds().size());
        assertEquals(0, director.getMovieCount());
    }

    @Test
    void testAddMovieIdNull() {
        director.addMovieId(null);
        assertEquals(0, director.getMovieIds().size());
        assertEquals(0, director.getMovieCount());
    }

    @Test
    void testGetMovieCount() {
        assertEquals(0, director.getMovieCount());

        director.addMovieId("movie1");
        assertEquals(1, director.getMovieCount());

        director.addMovieId("movie2");
        assertEquals(2, director.getMovieCount());

        director.addMovieId("movie1"); // duplicate
        assertEquals(2, director.getMovieCount());
    }

}
