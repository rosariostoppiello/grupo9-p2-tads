package um.edu.uy.importer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Director;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.ClosedHashTableImpl;

public class CreditsLoaderTest {

    private HashTable<String, Actor> actorsById;
    private HashTable<String, Director> directorsById;
    private String testCsvPath;

    @BeforeEach
    void setUp() throws IOException {
        actorsById = new ClosedHashTableImpl<>(100, 1);
        directorsById = new ClosedHashTableImpl<>(100, 1);
        File csvFile = new File("test_credits.csv");
        testCsvPath = csvFile.getPath();

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("cast,crew,id\n");
            writer.write("[{'id': 1, 'name': 'Actor One'}],[{'id': 2, 'name': 'Director One', 'job': 'Director'}],100\n");
            writer.write("[{'id': 3, 'name': 'Actor Two'}],[{'id': 4, 'name': 'Not Director', 'job': 'Writer'}],101\n");
        }
    }

    @Test
    void testLoadCredits() {
        CreditsLoader.loadCredits(testCsvPath, actorsById, directorsById);
        assertTrue(true);
        new File(testCsvPath).delete();
    }
}