package um.edu.uy;

import um.edu.uy.entities.UMovie;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UMovie uMovieSystem = new UMovie();
        Scanner scanner = new Scanner(System.in);
        int option;

        while (true) {
            System.out.println("Menú principal: ");
            System.out.println("Seleccione la opción que desee: ");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");

            while (!scanner.hasNextInt()) {
                System.out.println("Opción inválida. Por favor, ingrese un número.");
                scanner.next();
            }
            option = scanner.nextInt();

            if (option == 1) {
                long startTime = System.currentTimeMillis();
                System.out.println("Cargando datos...");
                uMovieSystem.loadMovies(); // movies, collections, languages, genres
                uMovieSystem.loadRatings();
                uMovieSystem.loadCredits();

                System.out.println(uMovieSystem.getGenresById().elementos()); // 20 - yo: 32 - 20
                System.out.println(uMovieSystem.getLanguagesByName().elementos()); // 89 - yo: 92 - 89
                System.out.println(uMovieSystem.getCollectionsById().elementos()); // 1644 - yo: 1695 - 42589
                System.out.println(uMovieSystem.getMoviesById().elementos()); // 45433 - yo: 45362 - 45433

                System.out.println(uMovieSystem.getParticipantsById().elementos()); // 19741 - yo: 161444 -
                System.out.println(uMovieSystem.getActorsById().elementos()); // 200979 - yo: 206049 -
                // 218034 total
                // ratings 1 000 000

                long endTime = System.currentTimeMillis();
                System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: " + (endTime-startTime) + " ms");

            } else if (option == 2) {
                consultas:
                while (true) {
                    System.out.println("Menú consultas: ");
                    System.out.println("1. Top 5 de las películas que más calificaciones por idioma.");
                    System.out.println("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.");
                    System.out.println("3. Top 5 de las colecciones que más ingresos generaron.");
                    System.out.println("4. Top 10 de los directores que mejor calificación tienen.");
                    System.out.println("5. Actor con más calificaciones recibidas en cada mes del año.");
                    System.out.println("6. Usuarios con más calificaciones por género");
                    System.out.println("7. Salir");

                    while (!scanner.hasNextInt()) {
                        System.out.println("Opción inválida. Por favor, ingrese un número.");
                        scanner.next();
                    }
                    option = scanner.nextInt();

                    long queryStart, queryEnd;

                    switch (option) {
                        case 1: // Top 5 de las películas que más calificaciones por idioma
                            uMovieSystem.query(1);
                            break;
                        case 2: // Top 10 de las películas que mejor calificación media tienen por parte de los usuarios
                            uMovieSystem.query(2);
                            break;
                        case 3: // Top 5 de las colecciones que más ingresos generaron
                            queryStart = System.currentTimeMillis();
                            uMovieSystem.query(3);
                            queryEnd = System.currentTimeMillis();
                            System.out.println("Tiempo de ejecución de la consulta: " + (queryEnd - queryStart) + " ms");
                            break;
                        case 4: // Top 10 de los directores que mejor calificación tienen
                            queryStart = System.currentTimeMillis();
                            uMovieSystem.query(4);
                            queryEnd = System.currentTimeMillis();
                            System.out.println("Tiempo de ejecución de la consulta: " + (queryEnd - queryStart) + " ms");
                            break;
                        case 5: // Actor con más calificaciones recibidas en cada mes del año
                            queryStart = System.currentTimeMillis();
                            uMovieSystem.query(5);
                            queryEnd = System.currentTimeMillis();
                            System.out.println("Tiempo de ejecución de la consulta: " + (queryEnd - queryStart) + " ms");
                            break;
                        case 6: // Usuarios con más calificaciones por género
                            queryStart = System.currentTimeMillis();
                            uMovieSystem.query(6);
                            queryEnd = System.currentTimeMillis();
                            System.out.println("Tiempo de ejecución de la consulta: " + (queryEnd - queryStart) + " ms");
                            break;
                        case 7:
                            System.out.println("Gracias por su(s) consulta(s)!");
                            break consultas;
                        default:
                            System.out.println("El número ingresado no se encuentra dentro de las opciones. Por favor, " +
                                    "elija un número entre los siguientes: 1, 2, 3, 4, 5, 6 o 7.");
                    }
                }
            } else if (option == 3) {
                System.out.println("Gracias por usar el sistema!");
                break;
            } else {
                System.out.println("El número ingresado no se encuentra dentro de las opciones. Por favor, " +
                        "elija un número entre los siguientes: 1, 2 o 3.");
            }
        }
        scanner.close();
    }

}