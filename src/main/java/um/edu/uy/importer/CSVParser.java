package um.edu.uy.importer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVParser {

    public static void parseCSV(String csvFilePath, LineProcessor processor) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

            reader.readLine(); // skip header
            String line;
            StringBuilder currentRecord = new StringBuilder();
            boolean inQuotedField = false;
            int totalLinesRead = 0;
            int recordsProcessed = 0;
            int fieldCount = 0;

            while ((line = reader.readLine()) != null) {
                totalLinesRead++;

                if (currentRecord.length() > 0) {
                    currentRecord.append(' ').append(line);
                } else {
                    currentRecord.append(line);
                }

                fieldCount = 0;
                inQuotedField = false;
                boolean recordComplete = true;

                String currentLine = currentRecord.toString();

                for (int i = 0; i < currentLine.length(); i++) {
                    char c = currentLine.charAt(i);

                    if (c == '"') {
                        inQuotedField = !inQuotedField;
                    } else if (c == ',' && !inQuotedField) {
                        fieldCount++;
                    }
                }

                if (inQuotedField) {
                    recordComplete = false;
                }

                if (recordComplete) {
                    try {
                        processor.processLine(currentRecord.toString());
                        recordsProcessed++;
                    } catch (Exception e) { // skip malformed lines
                    }
                    currentRecord.setLength(0);
                }
            }

            if (currentRecord.length() > 0) {
                try {
                    processor.processLine(currentRecord.toString());
                    recordsProcessed++;
                } catch (Exception e) { // skip malformed lines
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading csv");
        }
    }

    public static String[] splitCSV(String line) {
        if (line.indexOf('"') == -1) {
            return line.split(",", -1);
        }

        String[] result = new String[19];
        int fieldIndex = 0;
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length() && fieldIndex < 19; i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    currentField.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result[fieldIndex++] = currentField.toString();
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        if (fieldIndex < 19) {
            result[fieldIndex] = currentField.toString();
        }

        return result;
    }

    public interface LineProcessor {
        void processLine(String line) throws Exception;
    }
}
