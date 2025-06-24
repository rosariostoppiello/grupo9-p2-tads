package um.edu.uy.queries;

import um.edu.uy.entities.Movie;

import um.edu.uy.tads.hash.Elemento;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.tree.heap.DatoHeap;
import um.edu.uy.tads.tree.heap.MyBinaryHeapTree;
import um.edu.uy.tads.tree.heap.MyBinaryHeapTreeImpl;

// query 2 - 340 ms aprox.
public class QueryTop10MoviesByRating {

    public void queryTop10MoviesByRating(HashTable<String, Movie> movies) {
        MyBinaryHeapTree<Double, Movie> top10Heap = new MyBinaryHeapTreeImpl<>(false, 11);

        // heap insertions
        for (Elemento<String, Movie> elemento : movies) {
            Movie movie = elemento.getValor();

            if (!movie.getRatings().isEmpty()) {
                double sum = 0.0;
                int ratingsCount = movie.getRatings().largo();

                if (ratingsCount < 100) continue;

                for (int j = 0; j < ratingsCount; j++) {
                    sum +=movie.getRatings().obtener(j).getRating();
                }
                Double actualMean = sum / ratingsCount;

                if (top10Heap.tamanio() < 10) {
                    top10Heap.agregar(actualMean, movie);
                } else {
                    DatoHeap<Double, Movie> minElement = top10Heap.eliminar();
                    if (actualMean > minElement.getKey()) {
                        top10Heap.agregar(actualMean, movie);
                    } else {
                        top10Heap.agregar(minElement.getKey(), minElement.getData());
                    }
                }
            }
        }

        // temporal array
        DatoHeap<Double, Movie>[] tempArray = new DatoHeap[top10Heap.tamanio()];
        int count = top10Heap.tamanio();
        for (int i = 0; i < count; i++) {
            tempArray[i] = top10Heap.eliminar();
        }

        // print in reverse order
        for (int i = count - 1; i >= 0; i--) {
            System.out.println(tempArray[i].getData().getMovieId() + ", " +
                    tempArray[i].getData().getTitle() + " " +
                    tempArray[i].getKey());
        }

    }
}