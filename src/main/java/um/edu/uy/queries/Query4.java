package um.edu.uy.queries;

import um.edu.uy.entities.Director;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.HashTable;

public class Query4 {

    public void queryTop10DirectorsByRating(HashTable<String, Movie> movies,
                                            HashTable<String, Director> directors) {
        // Una película tiene un director. En general, los directores tienen cierto renombre y sus
        //películas suelen tener una calidad similar. Considerando todas las películas de cada
        //director, indicar el Top 10 de los directores que mejor calificación tienen. Únicamente
        //se deben considerar directores con más de 1 película y más de 100 evaluaciones.
        //Se deberá utilizar la mediana como métrica de comparación.
        //Datos esperados del resultado:
        //● Nombre del director.
        //● Cantidad de películas.
        //● Mediana de su calificación.
    }
}
