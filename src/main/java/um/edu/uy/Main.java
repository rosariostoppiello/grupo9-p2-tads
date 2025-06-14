package um.edu.uy;

import um.edu.uy.entities.UMovie;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UMovie uMovieSystem = new UMovie();
        Scanner scanner = new Scanner(System.in);
        int option;

        while (true) {
            System.out.println("Menú principal: \n" +
                    "Seleccione la opción que desee: \n" +
                    "1. Carga de datos \n" +
                    "2. Ejecutar consultas \n" +
                    "3. Salir"
            );

            while (!scanner.hasNextInt()) {
                System.out.println("Opción inválida. Por favor, ingrese un número.");
                scanner.next();
            }
            option = scanner.nextInt();

            if (option == 1) {
                uMovieSystem.loadMovies(); // movies y collections
                uMovieSystem.loadRatings();
                // Credits
                System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: ");
                // falta poner tiempo de ejecución de la carga
            } else if (option == 2) {
                consultas:
                while (true) {
                    System.out.println("Menú consultas: \n" +
                            "1. Top 5 de las películas que más calificaciones por idioma.\n" +
                            "2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.\n" +
                            "3. Top 5 de las colecciones que más ingresos generaron.\n" +
                            "4. Top 10 de los directores que mejor calificación tienen.\n" +
                            "5. Actor con más calificaciones recibidas en cada mes del año.\n" +
                            "6. Usuarios con más calificaciones por género\n" +
                            "7. Salir"
                    );

                    while (!scanner.hasNextInt()) {
                        System.out.println("Opción inválida. Por favor, ingrese un número.");
                        scanner.next();
                    }
                    option = scanner.nextInt();

                    switch (option) {
                        case 1:
                            // Top 5 de las películas que más calificaciones por idioma
                            break;
                        case 2:
                            // Top 10 de las películas que mejor calificación media tienen por parte de los usuarios
                            break;
                        case 3:
                            // Top 5 de las colecciones que más ingresos generaron
                            break;
                        case 4:
                            // Top 10 de los directores que mejor calificación tienen
                            break;
                        case 5:
                            // Actor con más calificaciones recibidas en cada mes del año
                            break;
                        case 6:
                            // Usuarios con más calificaciones por género
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
    }

}