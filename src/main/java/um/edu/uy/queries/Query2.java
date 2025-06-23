package um.edu.uy.queries;

import um.edu.uy.algorithms.QuickSort2Arrays;
import um.edu.uy.entities.Movie;

import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.MyList;

public class Query2 {

    public void queryTop10MoviesByRating(HashTable<String, Movie> movies) {
        long queryStart = System.currentTimeMillis();

        MyList<Movie> allMovies = movies.allValues();

        Movie[] top10Movies = new Movie[10];
        Double[] top10Ratings = new Double[10];
        int top10Count = 0;

        Movie movie;
        for (int i = 0; i < allMovies.largo(); i++) {
            movie = allMovies.obtener(i);
            if (!movie.getRatings().isEmpty()) {
                double sum = 0.0;
                int ratingsCount = movie.getRatings().largo();

                if (ratingsCount < 50) continue; // so movies with 1-3 ratings do not take the top

                for (int j = 0; j < ratingsCount; j++) {
                    sum += movie.getRatings().obtener(j).getRating();
                }

                Double actualMean = sum / ratingsCount;

                insertTop10(top10Movies, top10Ratings, top10Count, movie, actualMean);
                if (top10Count < 10) top10Count++;
            }
        }

        QuickSort2Arrays.quickSort(top10Movies, top10Ratings);

        long queryEnd = System.currentTimeMillis();
        for (int i = 0; i < top10Count; i ++) {
            System.out.println(top10Movies[i].getMovieId() + ", " +
                    top10Movies[i].getTitle() + ", " +
                    top10Ratings[i]);
        }
        System.out.println("Tiempo de ejecución de la consulta:" + (queryEnd-queryStart) + "ms");
    }

    private void insertTop10(Movie[] top10Movies, Double[] top10Ratings, int count, Movie movie, Double rating) {
        if (count < 10) { // if there is less than 10 movies registered
            top10Movies[count] = movie;
            top10Ratings[count] = rating;
            return;
        }

        int worstIndex = 0;
        Double worstRating = top10Ratings[0];

        for (int i = 1; i < 10; i++) {
            if (top10Ratings[i] < worstRating) {
                worstRating = top10Ratings[i];
                worstIndex = i;
            }
        }

        if (rating > worstRating) {
            top10Movies[worstIndex] = movie;
            top10Ratings[worstIndex] = rating;
        }

    }

}
