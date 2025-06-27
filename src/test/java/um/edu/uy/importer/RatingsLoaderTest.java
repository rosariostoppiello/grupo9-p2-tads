package um.edu.uy.importer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;

public class RatingsLoaderTest {

    private HashTable<String, Movie> moviesById;
    private String testCsvPath;
    private Movie testMovie1;
    private Movie testMovie2;
    private File csvFile;

    @BeforeEach
    void setUp() throws IOException {
        moviesById = new ClosedHashTableImpl<>(100, 1);

        testMovie1 = new Movie("100", "Test Movie 1", "en", "1000000", null);
        testMovie2 = new Movie("200", "Test Movie 2", "es", "2000000", null);

        try {
            moviesById.insert("100", testMovie1);
            moviesById.insert("200", testMovie2);
        } catch (ElementAlreadyExistsException e) {
        }

        csvFile = new File("test_ratings.csv");
        testCsvPath = csvFile.getPath();

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("userId,movieId,rating,timestamp\n");
            writer.write("user1,100,4.5,1600000000\n");
            writer.write("user2,100,3.0,1600000001\n");
            writer.write("user1,200,5.0,1600000002\n");
            writer.write("user3,999,2.0,1600000003\n");
        }
    }

    @AfterEach
    void tearDown() {
        if (csvFile.exists()) {
            csvFile.delete();
        }
    }

    @Test
    void testLoadRatings() {
        RatingsLoader.loadRatings(testCsvPath, moviesById);

        assertNotNull(testMovie1.getRatings());
        assertEquals(2, testMovie1.getRatings().size());

        Rating firstRating = testMovie1.getRatings().find(0);
        assertNotNull(firstRating);
        assertEquals("user1", firstRating.getUserId());
        assertEquals("100", firstRating.getMovieId());
        assertEquals(4.5f, firstRating.getRating(), 0.001);

        Rating secondRating = testMovie1.getRatings().find(1);
        assertNotNull(secondRating);
        assertEquals("user2", secondRating.getUserId());
        assertEquals(3.0f, secondRating.getRating(), 0.001);

        assertNotNull(testMovie2.getRatings());
        assertEquals(1, testMovie2.getRatings().size());

        Rating movie2Rating = testMovie2.getRatings().find(0);
        assertNotNull(movie2Rating);
        assertEquals("user1", movie2Rating.getUserId());
        assertEquals("200", movie2Rating.getMovieId());
        assertEquals(5.0f, movie2Rating.getRating(), 0.001);
    }
}
