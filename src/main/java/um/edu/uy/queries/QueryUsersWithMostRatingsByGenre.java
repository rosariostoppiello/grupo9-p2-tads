package um.edu.uy.queries;

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
        MyBinaryHeapTree<Integer, Genre> top10GenresHeap = new MyBinaryHeapTreeImpl<>(false, 11);

        for (Elemento<String, Genre> element : genresById) {
            Genre genre = element.getValor();
            Integer totalRatings = genre.getTotalRatingsCount();

            if (top10GenresHeap.tamanio() < 10) {
                top10GenresHeap.agregar(totalRatings, genre);
            } else {
                DatoHeap<Integer, Genre> minElement = top10GenresHeap.eliminar();
                if (totalRatings > minElement.getKey()) {
                    top10GenresHeap.agregar(totalRatings, genre);
                } else {
                    top10GenresHeap.agregar(minElement.getKey(), minElement.getData());
                }
                minElement = null;
            }
        }

        DatoHeap<Integer, Genre>[] top10Array = new DatoHeap[top10GenresHeap.tamanio()];
        int count = top10GenresHeap.tamanio();

        for (int i = 0; i < count; i++) {
            top10Array[i] = top10GenresHeap.eliminar();
        }

        for (int i = count - 1; i >= 0; i--) {
            Genre genre = top10Array[i].getData();
            if (genre != null) {
                String topUser = findTopUser(genre);
                if (topUser != null) {
                    String[] parts = topUser.split(":");
                    System.out.println(parts[0] + ", " + genre.getGenreName() + ", " + parts[1]);

                    for (int j = 0; j < parts.length; j++) {
                        parts[j] = null;
                    }
                    parts = null;
                }
            }
            top10Array[i] = null;
        }
        top10Array = null;
        top10GenresHeap = null;
    }

    private String findTopUser(Genre genre) {
        HashTable<String, Integer> userCounts = new ClosedHashTableImpl<>(5000, 0);

        String currentTopUser = null;
        int currentMaxCount = 0;

        for (int movieIndex = 0; movieIndex < genre.getMoviesGenre().largo(); movieIndex++) {
            Movie movie = genre.getMoviesGenre().obtener(movieIndex);

            for (int ratingIndex = 0; ratingIndex < movie.getRatings().largo(); ratingIndex++) {
                String userId = movie.getRatings().obtener(ratingIndex).getUserId();

                int newCount = updateUserCount(userCounts, userId);
                if (newCount > currentMaxCount) {
                    currentMaxCount = newCount;
                    currentTopUser = userId;
                }
            }
        }
        userCounts = null;
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
            Integer newCount = currentCount + 1;
            userCounts.borrar(userId);
            try {
                userCounts.insertar(userId, currentCount + 1);
                return newCount;
            } catch (ElementoYaExistenteException e) {
                elem = userCounts.pertenece(userId);
                return elem != null ? elem.getValor() : newCount;
            }
        }
    }
}