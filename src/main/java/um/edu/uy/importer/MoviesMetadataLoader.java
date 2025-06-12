package um.edu.uy.importer;

import um.edu.uy.entities.Collection;
import um.edu.uy.entities.Genre;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.Elemento;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MoviesMetadataLoader {

    @SuppressWarnings("unchecked")
    public static void loadMovies(String csvFilePath,
                                  HashTable<String, Movie> moviesById,
                                  HashTable<String, Collection> collectionsById,
                                  HashTable<String, Genre> genresById) {

        // en vez de usar FileReader, uso BufferedReader para reducir la cantidad de operaciones I/O
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath), 65536)) {

            String headerLine = reader.readLine();
            if (headerLine == null) return;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}