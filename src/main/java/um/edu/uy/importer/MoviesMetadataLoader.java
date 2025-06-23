package um.edu.uy.importer;

import um.edu.uy.entities.Collection;
import um.edu.uy.entities.Genre;
import um.edu.uy.entities.Language;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.Elemento;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MoviesMetadataLoader {

    public static void loadMovies(String csvFilePath,
                                  HashTable<String, Movie> moviesById,
                                  HashTable<String, Collection> collectionsById,
                                  HashTable<String, Genre> genresById,
                                  HashTable<String, Language> languagesByName) {

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

            reader.readLine();
            String line;

            int counter = 0;
            while ((line = reader.readLine()) != null) {
                try {
                    counter++;
                    processMovieLine(line, moviesById, collectionsById, genresById, languagesByName);
                } catch (Exception ignored) { // skip malformed lines
                }
            }
            System.out.print(counter);

        } catch (IOException e) {
            System.err.println("Error loading movies metadata");
        }
    }

    private static void processMovieLine(String line,
                                         HashTable<String, Movie> moviesById,
                                         HashTable<String, Collection> collectionsById,
                                         HashTable<String, Genre> genresById,
                                         HashTable<String, Language> languagesByName) {
        String[] fields = splitCSV(line);

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
            if (moviesById.pertenece(movieId) == null) {
                moviesById.insertar(movieId, movie);
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
        } catch (Exception e) {
            // skip movie
        }
    }

    private static String[] splitCSV(String line) {
        if (!line.contains("\"")) {
            return line.split(",", -1);
        }
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    private static String extractId(String jsonStr) {
        if (jsonStr == null || jsonStr.trim().isEmpty() || jsonStr.equals("FALSE")) {
            return null;
        }

        int start = jsonStr.indexOf("'id':");
        if (start == -1) return null;

        start += 6;
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
        Elemento<String, Collection> element = collectionsById.pertenece(collectionId);
        Collection collection;

        if (element == null) {
            String name;
            if (collectionStr != null) {
                name = extractName(collectionStr);
            } else {
                name = movie.getTitle();
            }
            collection = new Collection(collectionId, name, new MyLinkedListImpl<>());
            collectionsById.insertar(collectionId, collection);
        } else {
            collection = element.getValor();
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
        Elemento<String, Genre> element = genresById.pertenece(genreId);
        Genre genre;

        if (element == null) {
            genre = new Genre(genreId, genreName, new MyLinkedListImpl<>());
            genresById.insertar(genreId, genre);
        } else {
            genre = element.getValor();
        }
        genre.addMovieToGenre(movie);
    }

    @SuppressWarnings("unchecked")
    private static void addToLanguage(String languageCode, Movie movie, HashTable<String, Language> languagesByName) {
        if (languageCode == null || languageCode.trim().isEmpty() || languageCode.equals("\\N")) {
            return;
        }
        languageCode = languageCode.trim().toLowerCase();
        Elemento<String, Language> element = languagesByName.pertenece(languageCode);
        Language language;

        if (element == null) { // if the language does not exist
            language = new Language(languageCode);
            language.setMoviesLanguage(new MyLinkedListImpl<>());
            languagesByName.insertar(languageCode, language);
        } else {
            language = element.getValor();
        }

        if (language.getMoviesLanguage() != null) {
            language.addMovie(movie);
        }
    }
}