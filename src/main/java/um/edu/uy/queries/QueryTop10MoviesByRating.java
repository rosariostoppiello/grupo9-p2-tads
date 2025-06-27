package um.edu.uy.queries;

import um.edu.uy.entities.Movie;

import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.Element;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.tree.heap.HeapData;
import um.edu.uy.tads.tree.heap.MyBinaryHeapTree;
import um.edu.uy.tads.tree.heap.MyBinaryHeapTreeImpl;

import java.util.Arrays;

public class QueryTop10MoviesByRating {

    public void queryTop10MoviesByRating(HashTable<String, Movie> movies) {
        MyBinaryHeapTree<Double, Movie> top10Heap = new MyBinaryHeapTreeImpl<>(false, 11);

        for (Element<String, Movie> elemento : movies) {
            Movie movie = elemento.getValue();

            if (!movie.getRatings().isEmpty()) {
                double sum = 0.0;
                int ratingsCount = movie.getRatings().size();

                if (ratingsCount < 100) continue;

                for (Rating rating : movie.getRatings()) {
                    sum += rating.getRating();
                }
                Double actualMean = sum / ratingsCount;

                if (top10Heap.sizeHeap() < 10) {
                    top10Heap.add(actualMean, movie);
                } else {
                    HeapData<Double, Movie> minElement = top10Heap.delete();
                    if (actualMean > minElement.getKey()) {
                        top10Heap.add(actualMean, movie);
                    } else {
                        top10Heap.add(minElement.getKey(), minElement.getData());
                    }
                    minElement = null;
                }
            }
        }

        HeapData<Double, Movie>[] tempArray = new HeapData[top10Heap.sizeHeap()];
        int count = top10Heap.sizeHeap();
        for (int i = 0; i < count; i++) {
            tempArray[i] = top10Heap.delete();
        }

        for (int i = count - 1; i >= 0; i--) {
            System.out.println(tempArray[i].getData().getMovieId() + ", " +
                    tempArray[i].getData().getTitle() + " " +
                    tempArray[i].getKey());
        }

        Arrays.fill(tempArray, null);
        tempArray = null;
        top10Heap = null;

    }
}