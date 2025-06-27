package um.edu.uy;

import um.edu.uy.entities.UMovie;
import java.util.Scanner;

public class Main {

    private static void redirectQueryUMovie(UMovie uMovieSystem, int queryNumber) {
        long queryStart = System.currentTimeMillis();
        uMovieSystem.query(queryNumber);
        long queryEnd = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (queryEnd - queryStart) + " ms");
    }

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
                uMovieSystem.loadData();
                long endTime = System.currentTimeMillis();
                System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: " + (endTime-startTime) + " ms");

            } else if (option == 2) {
                consultas:
                while (true) {
                    System.out.println("Menú consultas: ");
                    System.out.println("1. Top 5 de las películas con más calificaciones por idioma.");
                    System.out.println("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.");
                    System.out.println("3. Top 5 de las colecciones que más ingresos generaron.");
                    System.out.println("4. Top 10 de los directores que mejor calificación tienen.");
                    System.out.println("5. Actor con más calificaciones recibidas en cada mes del año.");
                    System.out.println("6. Usuarios con más calificaciones por género.");
                    System.out.println("7. Salir.");

                    while (!scanner.hasNextInt()) {
                        System.out.println("Opción inválida. Por favor, ingrese un número.");
                        scanner.next();
                    }
                    option = scanner.nextInt();

                    switch (option) {
                        case 1: case 2: case 3: case 4: case 5: case 6:
                            redirectQueryUMovie(uMovieSystem, option);
                            break;
                        case 7:
                            // System.out.println("Gracias por su(s) consulta(s)!");
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