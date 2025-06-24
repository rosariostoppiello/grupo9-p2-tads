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
import um.edu.uy.tads.tree.heap.DatoHeap;
import um.edu.uy.tads.tree.heap.MyBinaryHeapTree;
import um.edu.uy.tads.tree.heap.MyBinaryHeapTreeImpl;

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
                String topUser = findTopUserWithHeap(genre);
                if (topUser != null) {
                    String[] parts = topUser.split(":");
                    System.out.println(parts[0] + ", " + genre.getGenreName() + ", " + parts[1]);
                }
            }
        }
    }

    private String findTopUserWithHeap(Genre genre) {
        // MyBinaryHeapTree<Integer, String> topUsersHeap = new MyBinaryHeapTreeImpl<>(true, 20);

        HashTable<String, Integer> userCounts = new ClosedHashTableImpl<>(3000, 0);
        MyList<Movie> moviesInGenre = genre.getMoviesGenre();

        String currentTopUser = null;
        int currentMaxCount = 0;
        int totalRatingsProcessed = 0;

        int moviesToProcess = moviesInGenre.largo();

        for (int movieIndex = 0; movieIndex < moviesToProcess; movieIndex++) {
            Movie movie = moviesInGenre.obtener(movieIndex);
            MyList<Rating> ratings = movie.getRatings();

            for (int ratingIndex = 0; ratingIndex < ratings.largo(); ratingIndex++) {
                String userId = ratings.obtener(ratingIndex).getUserId();

                totalRatingsProcessed++;
                int newCount = updateUserCount(userCounts, userId);
                if (newCount > currentMaxCount) {
                    currentMaxCount = newCount;
                    currentTopUser = userId;
                }
            }
        }
        return currentTopUser != null ? currentTopUser + ":" + currentMaxCount : null;
    }

    private int updateUserCount(HashTable<String, Integer> userCounts, String userId) {
        Elemento<String, Integer> elem = userCounts.pertenece(userId);
        if (elem == null) {
            try {
                userCounts.insertar(userId, 1);
                return 1;
            } catch (ElementoYaExistenteException e) {
                elem = userCounts.pertenece(userId);
                return elem != null ? elem.getValor() : 1;
            }
        } else {
            Integer currentCount = elem.getValor();
            userCounts.borrar(userId);
            try {
                userCounts.insertar(userId, currentCount + 1);
                return currentCount + 1;
            } catch (ElementoYaExistenteException e) {
                return currentCount;
            }
        }
    }
}
