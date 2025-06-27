package um.edu.uy.queries;

import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.Element;
import um.edu.uy.tads.hash.HashTable;

public class QueryTop5MoviesByLanguage {
    public void queryTop5MoviesByLanguage(HashTable<String, Movie> movies) {
        String[] languages = {"en", "fr", "it", "es", "pt"};
        String[] languageNames = {"Inglés", "Francés", "Italiano", "Español", "Portugués"};

        for (int langIndex = 0; langIndex < languages.length; langIndex++) {
            String language = languages[langIndex];
            String languageName = languageNames[langIndex];

            Movie[] topMovies = new Movie[5];
            int[] ratingCounts = new int[5];

            topMoviesLanguage(movies, language, topMovies, ratingCounts);
            printResults(languageName, topMovies, ratingCounts, languageName);
        }
    }

    private void topMoviesLanguage(HashTable<String, Movie> movies, String language,
                                   Movie[] topMovies, int[] ratingCounts) {
        for (int i = 1; i <= 50000; i++) {
            Element<String, Movie> element = movies.find(String.valueOf(i));
            if (element == null) continue;

            Movie movie = element.getValue();

            if (movie.getOriginalLanguage() != null &&
                    movie.getOriginalLanguage().equals(language)) {

                int ratingCount = movie.getRatings() != null ? movie.getRatings().size() : 0;
                tryAddToTop5(movie, ratingCount, topMovies, ratingCounts);
            }
        }
    }

    private void tryAddToTop5(Movie movie, int ratingCount, Movie[] topMovies, int[] ratingCounts) {
        int position = -1;

        for (int i = 0; i < 5; i++) {
            if (topMovies[i] == null || ratingCount > ratingCounts[i]) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            for (int i = 4; i > position; i--) {
                topMovies[i] = topMovies[i-1];
                ratingCounts[i] = ratingCounts[i-1];
            }

            topMovies[position] = movie;
            ratingCounts[position] = ratingCount;
        }
    }

    private void printResults(String languageName, Movie[] topMovies, int[] ratingCounts, String fullLanguageName) {
        System.out.println(languageName + ":");

        for (int i = 0; i < 5; i++) {
            if (topMovies[i] != null) {
                System.out.println(topMovies[i].getMovieId() + ", " +
                        topMovies[i].getTitle() + "," +
                        ratingCounts[i] + "," +
                        fullLanguageName);
            }
        }
    }
}