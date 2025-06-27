package um.edu.uy.entities;

import um.edu.uy.tads.list.linked.MyLinkedListImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {

    private Collection collection;
    private Collection collection2;
    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        collection = new Collection("1", "Marvel Cinematic Universe", new MyLinkedListImpl<>());
        collection2 = new Collection("2", "DC Extended Universe", new MyLinkedListImpl<>());
        movie1 = new Movie("100", "Iron Man", "en", "585174222", null);
        movie2 = new Movie("101", "Thor", "en", "449326618", null);
    }

    @Test
    void testConstructor() {
        assertNotNull(collection);
        assertEquals("1", collection.getCollectionId());
        assertEquals("Marvel Cinematic Universe", collection.getCollectionName());
        assertNotNull(collection.getMoviesCollection());
        assertEquals(0, collection.getMoviesCollection().size());
    }

    @Test
    void testAddMultipleMoviesToCollection() {
        collection.addMovieToCollection(movie1);
        collection.addMovieToCollection(movie2);
        assertEquals(2, collection.getMoviesCollection().size());
        assertEquals(movie1, collection.getMoviesCollection().find(0));
        assertEquals(movie2, collection.getMoviesCollection().find(1));
    }

    @Test
    void testCompareTo() {
        assertTrue(collection.compareTo(collection2) < 0); // "1" < "2"
        assertTrue(collection2.compareTo(collection) > 0); // "2" > "1"
        assertEquals(0, collection.compareTo(new Collection("1", "Different Name", new MyLinkedListImpl<>())));
    }


}
