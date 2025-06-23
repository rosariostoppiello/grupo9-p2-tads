package um.edu.uy.queries;

import um.edu.uy.algorithms.QuickSort2Arrays;
import um.edu.uy.entities.Genre;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.MyList;

// query 6
public class QueryUsersWithMostRatingsByGenre {

    public void queryUsersWithMostRatingsByGenre(HashTable<String, Genre> genresById) {
        // find top 10 genres
        MyList<Genre> allGenres = genresById.allValues();

        Genre[] top10Genres = new Genre[10];
        Integer[] top10Counts = new Integer[10];
        int top10Size = 0;

        Genre genre;
        for (int genreIndex = 0; genreIndex < allGenres.largo(); genreIndex++) {
            genre = allGenres.obtener(genreIndex);
            int totalRatings = genre.getTotalRatingsCount();

            if (top10Size < 10) {
                top10Genres[top10Size] = genre;
                top10Counts[top10Size] = totalRatings;
                top10Size++;
            } else {
                int minIndex = 0;
                for (int i = 1; i < 10; i++) {
                    if (top10Counts[i] < top10Counts[minIndex]) {
                        minIndex = i;
                    }
                }
                if (totalRatings > top10Counts[minIndex]) {
                    top10Genres[minIndex] = genre;
                    top10Counts[minIndex] = totalRatings;
                }
            }
        }
        QuickSort2Arrays.quickSort(top10Genres, top10Counts);
        top10Counts = null;

        // find the user with the most ratings for each genre
        for (int g = 0; g < top10Size; g++) {
            if (top10Genres[g] != null) {
                genre = top10Genres[g];
                MyList<Movie> moviesInGenre = genre.getMoviesGenre();
                int maxPossibleUsers = moviesInGenre.largo();

                String[] userIds = new String[maxPossibleUsers];
                int[] userCounts = new int[maxPossibleUsers];
                int uniqueUsers = 0;

                Movie movie;
                for (int movieIndex = 0; movieIndex < moviesInGenre.largo(); movieIndex++) {
                    movie = moviesInGenre.obtener(movieIndex);
                    MyList<Rating> ratings = movie.getRatings();

                    for (int ratingIndex = 0; ratingIndex < ratings.largo(); ratingIndex++) {
                        String userId = ratings.obtener(ratingIndex).getUserId();

                        int userIndex = -1;
                        for (int key = 0; key < uniqueUsers; key++) {
                            if (userIds[key] != null && userIds[key].equals(userId)) {
                                userIndex = key;
                                break;
                            }
                        }

                        if (userIndex == -1) {
                            if(uniqueUsers < maxPossibleUsers) {
                                userIds[uniqueUsers] = userId;
                                userCounts[uniqueUsers] = 1;
                                uniqueUsers++;
                            }
                        } else {
                            userCounts[userIndex]++;
                        }
                    }
                }

                if (uniqueUsers > 0) {
                    int maxIndex = 0;
                    for (int users = 1; users < uniqueUsers; users++) {
                        if (userCounts[users] > userCounts[maxIndex]) {
                            maxIndex = users;
                        }
                    }

                    System.out.println(userIds[maxIndex] + ", " +
                            genre.getGenreName() + ", " +
                            userCounts[maxIndex]);
                }

                userIds = null;
                userCounts = null;
            }
        }
        top10Genres = null;
    }
}