package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageTest {

    private Language language;
    private Language language2;
    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        language = new Language("en");
        language2 = new Language("es");
        movie1 = new Movie("100", "Avatar", "en", "2923706026", null);
        movie2 = new Movie("101", "Titanic", "en", "2257844554", null);

        language.addMovie(movie1);
        language.addMovie(movie2);
    }

    @Test
    void testConstructor() {
        assertNotNull(language);
        assertEquals("en", language.getLanguageId());
    }

    @Test
    void testCompareTo() {
        assertTrue(language.compareTo(language2) < 0);
        assertTrue(language2.compareTo(language) > 0);
        assertEquals(0, language.compareTo(new Language("en")));
    }


}
