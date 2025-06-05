package um.edu.uy.importer;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import um.edu.uy.entities.Rating;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class RatingsLoader {
    public static void loadRatings (String csvFilePath) {
        try(FileReader reader = new FileReader(csvFilePath)) {
            CsvToBean<Rating> csvToBean = new CsvToBeanBuilder<Rating>(reader)
                    .withType(Rating.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<Rating> iterator = csvToBean.iterator();

            while (iterator.hasNext()) {
                Rating rating = iterator.next();
                System.out.println(rating);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
