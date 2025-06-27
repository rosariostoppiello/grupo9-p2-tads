package um.edu.uy.tads.list.linked;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MyLinkedListImplTest {

    private MyLinkedListImpl<String> list;
    private MyLinkedListImpl<Integer> intList;

    @BeforeEach
    void setUp() {
        list = new MyLinkedListImpl<>();
        intList = new MyLinkedListImpl<>();
    }

    @Test
    void testConstructor() {
        assertNotNull(list);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(list.isEmpty());

        list.addLast("test");
        assertFalse(list.isEmpty());

        list.delete(0);
        assertTrue(list.isEmpty());
    }

    @Test
    void testAddFirst() {
        list.addFirst("first");
        assertEquals(1, list.size());
        assertEquals("first", list.find(0));

        list.addFirst("second");
        assertEquals(2, list.size());
        assertEquals("second", list.find(0));
        assertEquals("first", list.find(1));

        list.addFirst("third");
        assertEquals(3, list.size());
        assertEquals("third", list.find(0));
        assertEquals("second", list.find(1));
        assertEquals("first", list.find(2));
    }
}