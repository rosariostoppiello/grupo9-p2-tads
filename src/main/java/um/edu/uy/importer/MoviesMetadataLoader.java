package um.edu.uy.importer;

import um.edu.uy.entities.Collection;
import um.edu.uy.entities.Genre;
import um.edu.uy.entities.Language;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.Element;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public class MoviesMetadataLoader {

    public static void loadMovies(String csvFilePath,
                                  HashTable<String, Movie> moviesById,
                                  HashTable<String, Collection> collectionsById,
                                  HashTable<String, Genre> genresById,
                                  HashTable<String, Language> languagesByName) {

        CSVParser.parseCSV(csvFilePath, line -> {
            processMovieLine(line, moviesById, collectionsById, genresById, languagesByName);
        });
    }

    private static void processMovieLine(String line,
                                         HashTable<String, Movie> moviesById,
                                         HashTable<String, Collection> collectionsById,
                                         HashTable<String, Genre> genresById,
                                         HashTable<String, Language> languagesByName) {
        String[] fields = CSVParser.splitCSV(line);

        if (fields.length < 19) { return; }

        try {
            String movieId = fields[5].trim();
            String title = fields[8].trim();
            String originalLanguage = fields[7].trim();
            String revenue = fields[13].trim();
            String belongsToCollectionStr = fields[1].trim();
            String genresStr = fields[3].trim();

            if (movieId.isEmpty() || !movieId.matches("\\d+")) { // skip if movieid is empty or not numeric
                return;
            }

            String collectionId = extractId(belongsToCollectionStr);
            Movie movie = new Movie(movieId, title, originalLanguage, revenue, collectionId);
            if (moviesById.find(movieId) == null) {
                moviesById.insert(movieId, movie);
            }

            if (collectionId != null && !collectionId.isEmpty()) { // if the movie is part of a collection
                addToCollection(collectionId, belongsToCollectionStr, movie, collectionsById);
            } else {
                addToCollection(movieId, null, movie, collectionsById);
            }

            addToGenres(genresStr, movie, genresById);
            if (!originalLanguage.isEmpty()) {
                addToLanguage(originalLanguage, movie, languagesByName);
            }
        } catch (Exception e) { // skip movie
        }
    }

    private static String extractId(String jsonStr) {
        if (jsonStr == null || jsonStr.trim().isEmpty() || jsonStr.equals("FALSE")) {
            return null;
        }

        int start = jsonStr.indexOf("'id':");
        if (start == -1) return null;

        start += 5;
        int end = jsonStr.indexOf(",", start);
        if (end == -1) end = jsonStr.indexOf("}", start);
        if (end == -1) return null;

        try {
            return jsonStr.substring(start, end).trim();
        } catch (Exception e) {
            return null;
        }
    }

    private static String extractName(String jsonStr) {
        int start = jsonStr.indexOf("'name': '");
        if (start == -1) return "Unknown";

        start += 9;
        int end = jsonStr.indexOf("'", start);
        return end != -1 ? jsonStr.substring(start, end) : "Unknown";
    }

    @SuppressWarnings("unchecked")
    private static void addToCollection(String collectionId, String collectionStr, Movie movie,
                                        HashTable<String, Collection> collectionsById) {
        Element<String, Collection> element = collectionsById.find(collectionId);
        Collection collection;

        if (element == null) {
            String name;
            if (collectionStr != null) {
                name = extractName(collectionStr);
            } else {
                name = movie.getTitle();
            }
            collection = new Collection(collectionId, name, new MyLinkedListImpl<>());
            collectionsById.insert(collectionId, collection);
        } else {
            collection = element.getValue();
        }
        collection.addMovieToCollection(movie);
    }

    private static void addToGenres(String genresStr, Movie movie, HashTable<String, Genre> genresById) {
        if (genresStr == null || genresStr.trim().isEmpty() || genresStr.equals("[]")) {
            return;
        }

        int startIndex = 0;
        while (true) {
            int idStart = genresStr.indexOf("{'id':", startIndex);
            if (idStart == -1) break;
            int blockEnd = genresStr.indexOf("}", idStart);
            if (blockEnd == -1) break;

            String genreBlock = genresStr.substring(idStart, blockEnd + 1);
            String genreId = extractId(genreBlock);
            if (genreId != null && !genreId.trim().isEmpty()) {
                String genreName = extractName(genreBlock);
                addToGenre(genreId, genreName, movie, genresById);
            }

            startIndex = blockEnd + 1;
        }
    }

    @SuppressWarnings("unchecked")
    private static void addToGenre(String genreId, String genreName, Movie movie, HashTable<String, Genre> genresById) {
        Element<String, Genre> element = genresById.find(genreId);
        Genre genre;

        if (element == null) {
            genre = new Genre(genreId, genreName, new MyLinkedListImpl<>());
            genresById.insert(genreId, genre);
        } else {
            genre = element.getValue();
        }
        genre.addMovieToGenre(movie);
    }

    @SuppressWarnings("unchecked")
    private static void addToLanguage(String languageCode, Movie movie, HashTable<String, Language> languagesByName) {
        if (languageCode == null || languageCode.trim().isEmpty() || languageCode.equals("\\N")) {
            return;
        }
        languageCode = languageCode.trim().toLowerCase();
        Element<String, Language> element = languagesByName.find(languageCode);
        Language language;

        if (element == null) { // if the language does not exist
            language = new Language(languageCode);
            language.setMoviesLanguage(new MyLinkedListImpl<>());
            languagesByName.insert(languageCode, language);
        } else {
            language = element.getValue();
        }

        if (language.getMoviesLanguage() != null) {
            language.addMovie(movie);
        }
    }
}