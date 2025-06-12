package um.edu.uy.importer;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import um.edu.uy.entities.Movie;
import um.edu.uy.entities.Rating;
import um.edu.uy.tads.hash.Elemento;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public class RatingsLoader {

    @SuppressWarnings("unchecked")
    public static void loadRatings(String csvFilePath, HashTable<String, Movie> moviesPorId) {
        try(FileReader reader = new FileReader(csvFilePath)) {
            //

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}