package um.edu.uy.tads.tree.heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyBinaryHeapTreeImplTest {
    // tests taken from the heap done in class

    private final MyBinaryHeapTree<Integer, String> miHeapMax = new MyBinaryHeapTreeImpl<>(true, 10);
    private final MyBinaryHeapTree<Integer, String> miHeapMin = new MyBinaryHeapTreeImpl<>(false, 10);

    @Test
    void probarMetodos() {
        // max
        miHeapMax.add(1, "A");
        miHeapMax.add(2, "B");
        miHeapMax.add(3, "C");
        miHeapMax.add(4, "D");
        miHeapMax.add(5, "E");
        miHeapMax.add(6, "F");

        assertEquals(6, miHeapMax.sizeHeap());

        String arbolillo = miHeapMax.toString();
        System.out.println(arbolillo);

        miHeapMax.delete();
        miHeapMax.delete();

        assertEquals(4, miHeapMax.sizeHeap());

        arbolillo = miHeapMax.toString();
        System.out.println(arbolillo);

        // min
        miHeapMin.add(1, "A");
        miHeapMin.add(2, "B");
        miHeapMin.add(3, "C");
        miHeapMin.add(4, "D");
        miHeapMin.add(5, "E");
        miHeapMin.add(6, "F");

        assertEquals(6, miHeapMin.sizeHeap());

        arbolillo = miHeapMin.toString();
        System.out.println(arbolillo);

        miHeapMin.delete();
        miHeapMin.delete();

        assertEquals(4, miHeapMin.sizeHeap());

        arbolillo = miHeapMin.toString();
        System.out.println(arbolillo);
    }
}
