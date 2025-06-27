package um.edu.uy.importer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CSVParserTest {

    private String csvPath;
    private File csvFile;

    @BeforeEach
    void setUp() throws IOException {
        csvFile = new File("test_csv.csv");
        csvPath = csvFile.getPath();
        try (FileWriter w = new FileWriter(csvFile)) {
            w.write("h1,h2,h3\n");
            w.write("v1,v2,v3\n");
            w.write("v4,\"q,v\",v6\n");
            w.write("v7,v8,v9\n");
        }
    }

    @AfterEach
    void tearDown() {
        csvFile.delete();
    }

    @Test
    void testParseCSV() {
        AtomicInteger cnt = new AtomicInteger();
        List<String> lines = new ArrayList<>();
        CSVParser.parseCSV(csvPath, line -> {
            lines.add(line);
            cnt.incrementAndGet();
        });
        assertEquals(3, cnt.get());
        assertTrue(lines.get(1).contains("q,v"));
    }
}
