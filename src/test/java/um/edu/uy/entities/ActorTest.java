package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActorTest {

    private Actor actor;
    private Actor actor2;

    @BeforeEach
    void setUp() {
        actor = new Actor("123", "Tom Ellis");
        actor2 = new Actor("124", "Lauren German");
    }

    @Test
    void testConstructor() {
        assertNotNull(actor);
        assertEquals("123", actor.getPersonId());
        assertEquals("Tom Ellis", actor.getName());
        assertNotNull(actor.getActivityActor());
        assertEquals(0, actor.getActivityActor().size());
    }

    @Test
    void testAddMovieId() {
        actor.addMovieId("movie1");
        assertEquals(1, actor.getActivityActor().size());
        assertTrue(actor.getActivityActor().elementExistsIn("movie1"));
    }

    @Test
    void testAddMovieIdNull() {
        actor.addMovieId(null);
        assertEquals(0, actor.getActivityActor().size());
    }

    @Test
    void testAddMovieIdDuplicate() {
        actor.addMovieId("movie1");
        actor.addMovieId("movie1");
        assertEquals(1, actor.getActivityActor().size());
    }

    @Test
    void testAddMultipleMovieIds() {
        actor.addMovieId("movie1");
        actor.addMovieId("movie2");
        actor.addMovieId("movie3");
        assertEquals(3, actor.getActivityActor().size());
    }

    @Test
    void testCompareTo() {
        assertTrue(actor.compareTo(actor2) < 0); // "123" < "456"
        assertTrue(actor2.compareTo(actor) > 0); // "456" > "123"
        assertEquals(0, actor.compareTo(new Actor("123", "Different Name")));
    }

}
