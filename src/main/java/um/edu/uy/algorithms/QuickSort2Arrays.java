package um.edu.uy.algorithms;

public class QuickSort2Arrays {

    public static <T extends Comparable<T>, K extends Comparable<K>> void quickSort(T[] array1, K[] array2) {
        if (array1.length <= 1) {
            return;
        }
        quickSortRecursivo(array1, array2, 0, array1.length - 1);
    }

    private static <T extends Comparable<T>, K extends Comparable<K>> void quickSortRecursivo(T[] array1, K[] array2, int low, int high) {
        if (low < high) {
            int pivotIndex = particion(array1, array2, low, high); // partir el array

            // ordenar elementos antes y después del pivot
            quickSortRecursivo(array1, array2, low, pivotIndex - 1);
            quickSortRecursivo(array1, array2, pivotIndex + 1, high);
        }
    }

    private static <T extends Comparable<T>, K extends Comparable<K>> int particion(T[] array1, K[] array2, int low, int high) {
        K pivot = array2[high];
        int indiceMayor = low - 1;

        for (int posicionElem = low; posicionElem < high; posicionElem++) {

            if (array2[posicionElem].compareTo(pivot) > 0 || array2[posicionElem].compareTo(pivot) == 0) {
                indiceMayor++;

                K temp2 = array2[indiceMayor];
                array2[indiceMayor] = array2[posicionElem];
                array2[posicionElem] = temp2;

                T temp1 = array1[indiceMayor];
                array1[indiceMayor] = array1[posicionElem];
                array1[posicionElem] = temp1;
            }
        }
        K temp2 = array2[indiceMayor + 1];
        array2[indiceMayor + 1] = array2[high];
        array2[high] = temp2;

        T temp1 = array1[indiceMayor + 1];
        array1[indiceMayor + 1] = array1[high];
        array1[high] = temp1;

        return indiceMayor + 1;
    }
}
