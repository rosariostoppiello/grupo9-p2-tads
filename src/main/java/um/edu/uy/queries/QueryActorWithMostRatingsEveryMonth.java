package um.edu.uy.queries;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.hash.Element;
import um.edu.uy.tads.hash.HashTable;

import java.time.LocalDate;

// query 5
public class QueryActorWithMostRatingsEveryMonth {

    private static class ActorMonthData {
        String actorId;
        String actorName;
        int ratingsCount;
        int moviesCount;

        ActorMonthData(String id, String name, int ratings, int movies) {
            this.actorName = name;
            this.actorId = id;
            this.ratingsCount = ratings;
            this.moviesCount = movies;
        }
    }

    public void queryActorWithMostRatingsEveryMonth(HashTable<String, Movie> moviesById,
                                                    HashTable<String, Actor> actorsById) {
        ActorMonthData[] bestActorByMonth = new ActorMonthData[13];

        // optimization --> preload movies and precalculate ratings by month for each movie
        int movieCount = moviesById.elements();;
        Movie[] allMovies = new Movie[moviesById.elements()];
        int movieIndex = 0;

        for (Element<String, Movie> movieElement : moviesById) {
            allMovies[movieIndex++] = movieElement.getValue();
        }

        int[][] movieRatingsByMonth = new int[movieIndex][13];

        for (int i = 0; i < movieIndex; i++) {
            Movie movie = allMovies[i];

            for (Rating rating : movie.getRatings()) {
                LocalDate timestamp = rating.getDate();
                int month = timestamp.getMonthValue();
                movieRatingsByMonth[i][month]++;
            }
        }

        HashTable<String, Integer> movieIdToIndex = new ClosedHashTableImpl<>(movieIndex * 2, 0);
        for (int i = 0; i < movieIndex; i++) {
            try {
                movieIdToIndex.insert(allMovies[i].getMovieId(), i);
            } catch (ElementAlreadyExistsException e) {}
        }

        // iteration by actor
        for (Element<String, Actor> actorElement : actorsById) {
            Actor actor = actorElement.getValue();
            String actorId = actorElement.getKey();
            String actorName = actor.getName();

            int[] ratingsPerMonth = new int[13];
            int[] moviesPerMonth = new int[13];

            for (String movieId : actor.getActivityActor()) {
                Element<String, Integer> indexElement = movieIdToIndex.find(movieId);
                if (indexElement != null) {
                    int arrayIndex = indexElement.getValue();
                    for (int month = 1; month <= 12; month++) {
                        int ratingsInMonth = movieRatingsByMonth[arrayIndex][month];
                        if (ratingsInMonth > 0) {
                            ratingsPerMonth[month] += ratingsInMonth;
                            moviesPerMonth[month]++;
                        }
                    }
                }
            }

            for (int month = 1; month <= 12; month++) {
                if (ratingsPerMonth[month] > 0) {
                    if (bestActorByMonth[month] == null || ratingsPerMonth[month] > bestActorByMonth[month].ratingsCount) {
                        bestActorByMonth[month] = new ActorMonthData(actorId, actorName, ratingsPerMonth[month],
                                moviesPerMonth[month]);
                    }
                }
            }

            for (int i = 1; i <= 12; i++) {
                ratingsPerMonth[i] = 0;
                moviesPerMonth[i] = 0;
            }
        }

        final String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        for (int month = 1; month <= 12; month++) {
            if (bestActorByMonth[month] != null) {
                ActorMonthData best = bestActorByMonth[month];
                String monthName = monthNames[month - 1];
                System.out.println(monthName + ", " + best.actorName + ", " +
                        best.ratingsCount + ", " + best.moviesCount);
            }
        }

        for (int i = 0; i < movieCount; i++) {
            allMovies[i] = null;
            for (int j = 0; j < 13; j++) {
                movieRatingsByMonth[i][j] = 0;
            }
            movieRatingsByMonth[i] = null;
        }
        allMovies = null;
        movieRatingsByMonth = null;
        movieIdToIndex = null;

        for (int i = 0; i < bestActorByMonth.length; i++) {
            bestActorByMonth[i] = null;
        }
        bestActorByMonth = null;

        System.gc();
    }
}