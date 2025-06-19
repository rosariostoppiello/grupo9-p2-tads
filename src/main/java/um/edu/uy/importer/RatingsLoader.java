package um.edu.uy.importer;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;

import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;

import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.hash.Elemento;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.MyList;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public class RatingsLoader {

    @SuppressWarnings("unchecked")
    public static void loadRatings(String csvFilePath, HashTable<String, Movie> moviesById) {

        // temporal hash table
        HashTable<String, Rating> userMovieRatings = new ClosedHashTableImpl<>(1500000, 1);

        try(BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            reader.readLine(); // skip header line

            String[] parts;
            Elemento<String,Movie> movieElem;
            Movie movie;
            MyList<Rating> ratings;
            Rating rating;

            String userId;
            String movieId;
            String userMovieKey;
            Elemento<String, Rating> existingRatingElem;

            while ((line = reader.readLine()) != null) {
                parts = splitLine(line);
                if (parts.length != 4) continue;

                userId = parts[0];
                movieId = parts[1];
                movieElem = moviesById.pertenece(movieId);

                if (movieElem != null) { // movie exists
                    movie = movieElem.getValor();

                    userMovieKey = userId + movieId;
                    existingRatingElem = userMovieRatings.pertenece(userMovieKey);

                    if (existingRatingElem != null) { // if the user had already rated that movie - overwrite
                        Rating existingRating = existingRatingElem.getValor();
                        existingRating.setRating(Float.parseFloat(parts[2]));
                        existingRating.setDate(Instant.ofEpochSecond(Long.parseLong(parts[3]))
                                .atZone(ZoneId.systemDefault()).toLocalDate());
                    } else {
                        rating = new Rating(
                                parts[0],
                                parts[1],
                                Float.parseFloat(parts[2]),
                                Instant.ofEpochSecond(Long.parseLong(parts[3]))
                                        .atZone(ZoneId.systemDefault()).toLocalDate());
                        userMovieRatings.insertar(userMovieKey, rating);
                        ratings = movie.getRatings();
                        if (ratings == null) {
                            ratings = new MyLinkedListImpl<>();
                            movie.setRatings(ratings);
                        }
                        ratings.addLast(rating);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error cargando datos" + e.getMessage());
            e.printStackTrace();
        } finally {
            userMovieRatings = null;
            System.gc(); // suggest garbage collection
        }
    }

    private static String[] splitLine(String line) {
        String[] result = new String[4];
        int start = 0;
        int pos = 0;
        int fieldIndex = 0;

        while (pos < line.length() && fieldIndex < 4) {
            if (line.charAt(pos) == ',') {
                result[fieldIndex] = line.substring(start,pos);
                fieldIndex++;
                start = pos + 1;
            }
            pos++;
        }

        if (fieldIndex < 4) {
            result[fieldIndex] = line.substring(start);
        }
        return result;
    }
}