package um.edu.uy.queries;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.HashTable;

public class Query5 {
    public void queryActorWithMostRatingsEveryMonth(HashTable<String, Movie> moviesById,
                                                    HashTable<String, Actor> actorsById) {
        // Es sabido que hay épocas en las que ciertas películas son más vistas que otras.
        //Para promocionar mejor estas películas, se desea saber qué actor es más común en
        //ellas. Puntualmente, se desea saber cual es el actor con más calificaciones recibidas
        //en cada mes del año.
        //Datos esperados del resultado:
        //● Mes del año.
        //● Nombre del actor.
        //● Cantidad de películas (que fueron vistas en ese mes).
        //● Cantidad de calificaciones.
    }
}
