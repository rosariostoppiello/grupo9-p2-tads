package um.edu.uy.queries;

import um.edu.uy.entities.Collection;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.Element;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.MyList;

public class QueryTop5CollectionsByRevenue {

    public void queryTop5CollectionsByRevenue(HashTable<String, Movie> moviesById,
                                              HashTable<String, Collection> collectionsById) {
        String[] top5Ids = new String[5];
        String[] top5Names = new String[5];
        int[] top5MovieCounts = new int[5];
        double[] top5Revenues = new double[5];
        String[] top5MovieIdLists = new String[5];

        processCollections(collectionsById, moviesById, top5Ids, top5Names, top5MovieCounts, top5Revenues, top5MovieIdLists);
        processIndMovies(moviesById, collectionsById, top5Ids, top5Names, top5MovieCounts, top5Revenues, top5MovieIdLists);

        results(top5Ids, top5Names, top5MovieCounts, top5Revenues, top5MovieIdLists);
    }

    private void processCollections(HashTable<String, Collection> collectionsById, HashTable<String, Movie> moviesById,
                                    String[] ids, String[] names, int[] counts, double[] revenues, String[] movieLists) {
        for (int i = 1; i <= 5000; i++) {

            Element<String, Collection> element = collectionsById.find(String.valueOf(i));
            if (element == null) continue;

            Collection collection = element.getValue();
            MyList<Movie> movies = collection.getMoviesCollection();

            double totalRevenue = 0;
            StringBuilder idList = new StringBuilder("[");

            for (int j = 0; j < movies.size(); j++) {
                Movie movie = movies.find(j);
                totalRevenue += movie.getRevenue();

                if (j > 0) idList.append(",");
                idList.append(movie.getMovieId());
            }
            idList.append("]");

            addTop5(ids, names, counts, revenues, movieLists,
                    collection.getCollectionId(), collection.getCollectionName(),
                    movies.size(), totalRevenue, idList.toString());
        }
    }

    private void processIndMovies(HashTable<String, Movie> moviesById, HashTable<String, Collection> collectionsById,
                                  String[] ids, String[] names, int[] counts, double[] revenues, String[] movieLists) {
        for (int i = 1; i <= 50000; i++) {
            Element<String, Movie> element = moviesById.find(String.valueOf(i));
            if (element == null) continue;

            Movie movie = element.getValue();
            String collectionId = movie.getBelongsToCollection();

            if (collectionId == null || collectionId.isEmpty() ||
                    collectionsById.find(collectionId) == null) {

                String movieList = "[" + movie.getMovieId() + "]";

                addTop5(ids, names, counts, revenues, movieLists,
                        movie.getMovieId(), movie.getTitle(),
                        1, movie.getRevenue(), movieList);
            }
        }
    }

    private void addTop5(String[] ids, String[] names, int[] counts, double[] revenues, String[] movieLists,
                         String id, String name, int count, double revenue, String movieList) {
        int position = -1;

        for (int i = 0; i < 5; i++) {
            if (ids[i] == null || revenue > revenues[i]) {
                position = i;
                break;
            }
        }
        if (position == -1) return;

        for (int i = 4; i > position; i--) {
            ids[i] = ids[i-1];
            names[i] = names[i-1];
            counts[i] = counts[i-1];
            revenues[i] = revenues[i-1];
            movieLists[i] = movieLists[i-1];
        }
        ids[position] = id;
        names[position] = name;
        counts[position] = count;
        revenues[position] = revenue;
        movieLists[position] = movieList;
    }

    private void results (String[] ids, String[] names, int[] counts, double[] revenues, String[] movieLists) {
        for (int i = 0; i < 5; i++) {
            if (ids[i] != null) {
                System.out.println(ids[i] + ", " + names[i] + ", " +
                        counts[i] + ", " + movieLists[i] + ", " +
                        revenues[i]);
            }
        }
    }
}