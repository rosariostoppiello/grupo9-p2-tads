package um.edu.uy;

import um.edu.uy.importer.RatingsLoader;

public class Main {
    public static void main(String[] args) {
        RatingsLoader.loadRatings("dataset-ratings_1mm.csv");
    }


}