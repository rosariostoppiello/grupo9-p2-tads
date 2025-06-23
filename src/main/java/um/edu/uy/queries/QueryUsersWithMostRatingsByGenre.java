package um.edu.uy.queries;

import um.edu.uy.algorithms.QuickSort2Arrays;
import um.edu.uy.entities.Genre;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.exceptions.ElementoYaExistenteException;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.hash.Elemento;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.MyList;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

// query 6
public class QueryUsersWithMostRatingsByGenre {

    public void queryUsersWithMostRatingsByGenre(HashTable<String, Genre> genresById) {
        // find top 10 genres
        MyList<Genre> allGenres = genresById.allValues();
        int totalGenres = allGenres.largo();

        Genre[] genreArray = new Genre[totalGenres];
        Integer[] countArray = new Integer[totalGenres];

        for (int i = 0; i < totalGenres; i++) {
            genreArray[i] = allGenres.obtener(i);
            countArray[i] = genreArray[i].getTotalRatingsCount();
        }

        QuickSort2Arrays.quickSort(genreArray, countArray);
        int top10Size = Math.min(10, totalGenres);

        for (int g = 0; g < top10Size; g++) {
            Genre genre = genreArray[g];
            if (genre != null) {
                String topUser = findTopUserForGenre(genre);
                if (topUser != null) {
                    String[] parts = topUser.split(":");
                    System.out.println(parts[0] + ", " + genre.getGenreName() + ", " + parts[1]);
                }
            }
        }
    }



    private String findTopUserForGenre(Genre genre) {
        HashTable<String, Integer> userCounts = new ClosedHashTableImpl<>(50000,1);
        MyList<Movie> moviesInGenre = genre.getMoviesGenre();

        for (int movieIndex = 0; movieIndex < moviesInGenre.largo(); movieIndex++) {
            Movie movie = moviesInGenre.obtener(movieIndex);
            MyList<Rating> ratings = movie.getRatings();

            for (int ratingIndex = 0; ratingIndex < ratings.largo(); ratingIndex++) {
                String userId = ratings.obtener(ratingIndex).getUserId();

                Elemento<String, Integer> elem = userCounts.pertenece(userId);
                if (elem == null) {
                    try {
                        userCounts.insertar(userId, 1);
                    } catch (ElementoYaExistenteException e) {
                    }
                } else {
                    Integer currentCount = elem.getValor();
                    userCounts.borrar(userId);
                    try {
                        userCounts.insertar(userId, currentCount + 1);
                    } catch (ElementoYaExistenteException e) {
                    }
                }
            }
        }
        MyLinkedListImpl<String> allUserIds = userCounts.allKeys();
        if (allUserIds.largo() == 0) return null;

        String topUserId = allUserIds.obtener(0);
        int maxCount = userCounts.pertenece(topUserId).getValor();

        for (int i = 1; i < allUserIds.largo(); i++) {
            String userId = allUserIds.obtener(i);
            int count = userCounts.pertenece(userId).getValor();
            if (count > maxCount) {
                maxCount = count;
                topUserId = userId;
            }
        }

        return topUserId + ":" + maxCount;
    }

}