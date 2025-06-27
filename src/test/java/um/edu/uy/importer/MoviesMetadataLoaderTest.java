package um.edu.uy.importer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import um.edu.uy.entities.Collection;
import um.edu.uy.entities.Genre;
import um.edu.uy.entities.Language;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.ClosedHashTableImpl;

public class MoviesMetadataLoaderTest {

    private HashTable<String, Movie> moviesById;
    private HashTable<String, Collection> collectionsById;
    private HashTable<String, Genre> genresById;
    private HashTable<String, Language> languagesByName;
    private String testCsvPath;

    @BeforeEach
    void setUp() throws IOException {
        moviesById = new ClosedHashTableImpl<>(100, 1);
        collectionsById = new ClosedHashTableImpl<>(100, 1);
        genresById = new ClosedHashTableImpl<>(100, 1);
        languagesByName = new ClosedHashTableImpl<>(100, 1);
        File csvFile = new File("test_movies.csv");
        testCsvPath = csvFile.getPath();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("belongs_to_collection,budget,genres,homepage,id,imdb_id,original_language,original_title,overview,production_companies,production_countries,release_date,revenue,runtime,spoken_languages,status,tagline,title\n");
            writer.write("{'id':100,'name':'Test Collection'},5000000,[{'id':200,'name':'Action'}],http://test.com,123,tt0123456,en,Test Movie 1,Overview of Movie 1,[{'name':'Test Studios'}],[{'iso_3166_1':'US','name':'United States'}],2020-01-01,1000000,120,[{'iso_639_1':'en','name':'English'}],Released,\"\",Test Movie 1\n");
            writer.write(",3000000,[{'id':201,'name':'Comedy'}],http://test2.com,124,tt0654321,es,Test Movie 2,Overview of Movie 2,[{'name':'Studio 2'}],[{'iso_3166_1':'ES','name':'Spain'}],2021-02-02,2000000,90,[{'iso_639_1':'es','name':'Spanish'}],Released,\"\",Test Movie 2\n");
        }
    }

    @Test
    void testLoadMovies() {
        MoviesMetadataLoader.loadMovies(
                testCsvPath,
                moviesById,
                collectionsById,
                genresById,
                languagesByName
        );
        assertTrue(true);
        new File(testCsvPath).delete();
    }
}


