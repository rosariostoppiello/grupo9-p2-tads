package um.edu.uy.queries;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.Elemento;
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

        for (Elemento<String, Actor> actorElement : actorsById) {
            Actor actor = actorElement.getValor();
            String actorId = actorElement.getClave();
            String actorName = actor.getName();

            int[] ratingsPerMonth = new int[13];
            int[] moviesPerMonth = new int[13];


            for (int movieIndex = 0; movieIndex < actor.getActivityActor().largo(); movieIndex++) {
                String movieId = actor.getActivityActor().obtener(movieIndex);


                Elemento<String, Movie> movieElement = moviesById.pertenece(movieId);
                if (movieElement != null) {
                    Movie movie = movieElement.getValor();


                    boolean[] movieHasRatingsInMonth = new boolean[13];


                    for (int ratingIndex = 0; ratingIndex < movie.getRatings().largo(); ratingIndex++) {
                        Rating rating = movie.getRatings().obtener(ratingIndex);


                        LocalDate timestamp = rating.getDate();
                        int month = timestamp.getMonthValue(); // 1-12


                        ratingsPerMonth[month]++;


                        if (!movieHasRatingsInMonth[month]) {
                            movieHasRatingsInMonth[month] = true;
                            moviesPerMonth[month]++;
                        }
                    }
                }
            }

            for (int month = 1; month <= 12; month++) {
                if (ratingsPerMonth[month] > 0) {
                    if (bestActorByMonth[month] == null ||
                            ratingsPerMonth[month] > bestActorByMonth[month].ratingsCount) {

                        bestActorByMonth[month] = new ActorMonthData(
                                actorId,
                                actorName,
                                ratingsPerMonth[month],
                                moviesPerMonth[month]
                        );
                    }
                }
            }
        }

        for (int month = 1; month <= 12; month++) {
            if (bestActorByMonth[month] != null) {
                ActorMonthData best = bestActorByMonth[month];
                System.out.println(month + ", " + best.actorName + ", " +
                        best.moviesCount + ", " + best.ratingsCount);
            }
        }
    }
}