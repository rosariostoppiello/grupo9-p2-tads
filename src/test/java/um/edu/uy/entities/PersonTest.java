package um.edu.uy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// abstract --> concrete implementation only
public class PersonTest {

    private Person person;

    private static class TestPerson extends Person {
        public TestPerson(String personId, String name) {
            super(personId, name);
        }
    }

    @BeforeEach
    void setUp() {
        person = new TestPerson("123", "John Doe");
    }

    @Test
    void testConstructor() {
        assertNotNull(person);
        assertEquals("123", person.getPersonId());
        assertEquals("John Doe", person.getName());
    }
}
