package um.edu.uy.queries;

import um.edu.uy.entities.Genre;
import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;
import um.edu.uy.tads.hash.ClosedHashTableImpl;
import um.edu.uy.tads.hash.Element;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.tree.heap.HeapData;
import um.edu.uy.tads.tree.heap.MyBinaryHeapTree;
import um.edu.uy.tads.tree.heap.MyBinaryHeapTreeImpl;

// query 6
public class QueryUsersWithMostRatingsByGenre {

    public void queryUsersWithMostRatingsByGenre(HashTable<String, Genre> genresById) {
        // find top 10 genres
        MyBinaryHeapTree<Integer, Genre> top10GenresHeap = new MyBinaryHeapTreeImpl<>(false, 11);

        for (Element<String, Genre> element : genresById) {
            Genre genre = element.getValue();
            Integer totalRatings = genre.getTotalRatingsCount();

            if (top10GenresHeap.sizeHeap() < 10) {
                top10GenresHeap.add(totalRatings, genre);
            } else {
                HeapData<Integer, Genre> minElement = top10GenresHeap.delete();
                if (totalRatings > minElement.getKey()) {
                    top10GenresHeap.add(totalRatings, genre);
                } else {
                    top10GenresHeap.add(minElement.getKey(), minElement.getData());
                }
                minElement = null;
            }
        }

        HeapData<Integer, Genre>[] top10Array = new HeapData[top10GenresHeap.sizeHeap()];
        int count = top10GenresHeap.sizeHeap();

        for (int i = 0; i < count; i++) {
            top10Array[i] = top10GenresHeap.delete();
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

        for (int movieIndex = 0; movieIndex < genre.getMoviesGenre().size(); movieIndex++) {
            Movie movie = genre.getMoviesGenre().find(movieIndex);

            for (Rating rating : movie.getRatings()) {
                String userId = rating.getUserId();
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
        Element<String, Integer> elem = userCounts.find(userId);
        if (elem == null) {
            try {
                userCounts.insert(userId, 1);
                return 1;
            } catch (ElementAlreadyExistsException e) {
                elem = userCounts.find(userId);
                return elem != null ? elem.getValue() : 1;
            }
        } else {
            Integer currentCount = elem.getValue();
            Integer newCount = currentCount + 1;
            userCounts.delete(userId);
            try {
                userCounts.insert(userId, currentCount + 1);
                return newCount;
            } catch (ElementAlreadyExistsException e) {
                elem = userCounts.find(userId);
                return elem != null ? elem.getValue() : newCount;
            }
        }
    }
}