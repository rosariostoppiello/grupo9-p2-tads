package um.edu.uy.tads.hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClosedHashTableImplTest {

    private ClosedHashTableImpl<String, String> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new ClosedHashTableImpl<>(5, 0); // Resolución lineal
    }

    @Test
    void testConstructorResolucionLineal() {
        ClosedHashTableImpl<String, String> tabla = new ClosedHashTableImpl<>(5, 0);
        assertNotNull(tabla);
        assertEquals(0, tabla.elements());
    }

    @Test
    void testConstructorResolucionCuadratica() {
        ClosedHashTableImpl<String, String> tabla = new ClosedHashTableImpl<>(5, 1);
        assertNotNull(tabla);
        assertEquals(0, tabla.elements());
    }

    @Test
    void testInsertarYBuscarElementoResolucionCuadratica() {
        ClosedHashTableImpl<String, String> tabla = new ClosedHashTableImpl<>(5, 1);
        tabla.insert("clave1", "valor1");

        Element<String, String> found = tabla.find("clave1");
        assertNotNull(found);
        assertEquals("clave1", found.getKey());
        assertEquals("valor1", found.getValue());
        assertEquals(1, tabla.elements());
    }

}