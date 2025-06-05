package um.edu.uy.tads.tree.heap;

public class MyBinaryHeapTreeImpl<K extends Comparable<K>, T> implements MyBinaryHeapTree<K,T> {

    private final DatoHeap<K, T>[] vectorHeap; // vector estático
    private int size;
    private final boolean max; // si es true es porque se ordena con los mayores más cerca de la raíz

    @SuppressWarnings("unchecked")
    public MyBinaryHeapTreeImpl(boolean max, int capacidad) {
        this.vectorHeap = new DatoHeap[capacidad];
        this.size = 0;
        this.max = max;
    }

    @Override
    public void agregar(K key, T value) {
        if (size+1 == vectorHeap.length) { // llegué al tamaño máximo
            throw new IllegalStateException("El heap está lleno");
        }

        vectorHeap[size] = new DatoHeap<>(key, value);
        bubbleUp(size);
        size++;
    }

    private void bubbleUp(int index) {
        int parentIndex = (index - 1) / 2;

        // mientras index > 0 y la comparación entre el elemento key y la key del padre no cumpla la condición
        while (index > 0 && compararMaxMin(vectorHeap[index].getKey(), vectorHeap[parentIndex].getKey())) {
            intercambiarElementos(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2; // busco padre del padre
        }
    }

    private boolean compararMaxMin(K a, K b) {
        return max ? a.compareTo(b) > 0 : a.compareTo(b) < 0;
        // esta fórma simplificada no fue mi primera opción claramente pero tras evaluar el código
        // se ve mucho más resumido hacer el if en tan solo una línea

        // si es max, devuelve true or false según a.compareTo(b) > 0 --> si a elem > padre es true
    }

    private void intercambiarElementos(int i, int j) {
        DatoHeap<K, T> temp = vectorHeap[i];
        vectorHeap[i] = vectorHeap[j];
        vectorHeap[j] = temp;
    }

    @Override
    public DatoHeap<K,T> eliminar() {
        if (size == 0) return null;
        DatoHeap<K,T> datoDevolver = vectorHeap[0];

        vectorHeap[0] = vectorHeap[size - 1]; // mover último al "root"
        size--;
        bubbleDown(0);

        return datoDevolver;
    }

    // en esencia es muy parecido al Bubble up pero inverso, con sus ecuaciones correspondientes
    private void bubbleDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int indexExtremoMaxMin = index; // depende de si es max o min y representa al extremo entre el padre e hijos

        // hijo izq
        if (leftChild < size && compararMaxMin(vectorHeap[leftChild].getKey(), vectorHeap[indexExtremoMaxMin].getKey())) {
            indexExtremoMaxMin = leftChild;
        }
        // hijo der
        if (rightChild < size && compararMaxMin(vectorHeap[rightChild].getKey(), vectorHeap[indexExtremoMaxMin].getKey())) {
            indexExtremoMaxMin = rightChild;
        }

        if (indexExtremoMaxMin != index) {
            intercambiarElementos(index, indexExtremoMaxMin);
            bubbleDown(indexExtremoMaxMin);
        }
    }

    @Override
    public int tamanio() {
        return size;
    }

    // misma base que en Complete BT pero variando la recorrida, antes debía hacer un proceso con
    // nodos y ahora es utilizando las ecuaciones que permiten pasar de un padre a los hijos
    @Override
    public String toString() {
        if (size == 0) return "Heap vacío";

        StringBuilder sb = new StringBuilder();
        arbolitoDesdeVector(sb,0, "", true);
        return sb.toString();
    }

    private void arbolitoDesdeVector(StringBuilder sb, int index, String prefijo, boolean esUltimoHijo) {
        if (index >= size) return;

        sb.append(prefijo);
        sb.append(esUltimoHijo ? "└── " : "├── ");
        sb.append(vectorHeap[index].toString()).append("\n");

        int hijoIzq = 2 * index + 1;
        int hijoDer = 2 * index + 2;

        boolean tieneHijoIzq = hijoIzq < size; // si no tiene hijo, el size es menor al index del hijo calculado
        boolean tieneHijoDer = hijoDer < size;

        if (tieneHijoIzq)
            arbolitoDesdeVector(sb, hijoIzq, prefijo + (esUltimoHijo ? "    " : "│   "), !tieneHijoDer);
        if (tieneHijoDer)
            arbolitoDesdeVector(sb, hijoDer, prefijo + (esUltimoHijo ? "    " : "│   "), true);
    }

}