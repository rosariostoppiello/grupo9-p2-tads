package um.edu.uy.queries;

import um.edu.uy.entities.Director;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.Elemento;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.MyList;

import java.util.Arrays;

public class QueryTop10DirectorsByRating {

    public void queryTop10DirectorsByRating(HashTable<String, Movie> moviesById, HashTable<String, Director> directorsById) {
        System.out.println("Top 10 directores por rating:");

        Director[] top10 = new Director[10];
        Float[] medians = new Float[10];

        for (int id = 1; id <= 50000; id++) {
            Elemento<String, Director> elem = directorsById.pertenece(String.valueOf(id));
            if (elem == null) continue;

            Director director = elem.getValor();
            if (director.getMovieIds().largo() <= 1) continue;

            Float[] ratings = getAllRatings(director, moviesById);
            if (ratings.length <= 100) continue;

            float median = getMedian(ratings);
            updateTop10(top10, medians, director, median);
        }

        for (int i = 0; i < 10 && top10[i] != null; i++) {
            System.out.println(top10[i].getName() + ", " +
                    top10[i].getMovieIds().largo() + ", " +
                    String.format("%.2f", medians[i]));
        }
    }

    private Float[] getAllRatings(Director director, HashTable<String, Movie> moviesById) {
        MyList<String> movieIds = director.getMovieIds();

        int totalRatings = 0;
        for (int i = 0; i < movieIds.largo(); i++) {
            Elemento<String, Movie> elem = moviesById.pertenece(movieIds.obtener(i));
            if (elem != null) {
                Movie movie = elem.getValor();
                MyList<Rating> movieRatings = movie.getRatings();
                if (movieRatings != null) {
                    totalRatings += movieRatings.largo();
                }
            }
        }

        Float[] allRatings = new Float[totalRatings];
        int pos = 0;

        for (int i = 0; i < movieIds.largo(); i++) {
            Elemento<String, Movie> elem = moviesById.pertenece(movieIds.obtener(i));
            if (elem != null) {
                Movie movie = elem.getValor();
                MyList<Rating> ratings = movie.getRatings();
                if (ratings != null) {
                    for (int j = 0; j < ratings.largo(); j++) {
                        allRatings[pos++] = ratings.obtener(j).getRating();
                    }
                }
            }
        }

        return allRatings;
    }

    private float getMedian(Float[] ratings) {
        Arrays.sort(ratings);
        int length = ratings.length;

        if (length % 2 == 0) {
            return (ratings[length/2 - 1] + ratings[length/2]) / 2.0f;
        } else {
            return ratings[length/2];
        }
    }

    private void updateTop10(Director[] directors, Float[] medians, Director newDir, float newMedian) {
        int pos = -1;
        for (int i = 0; i < 10; i++) {
            if (directors[i] == null || newMedian > medians[i]) {
                pos = i;
                break;
            }
        }

        if (pos == -1) return;

        for (int i = 9; i > pos; i--) {
            directors[i] = directors[i-1];
            medians[i] = medians[i-1];
        }

        directors[pos] = newDir;
        medians[pos] = newMedian;
    }
}