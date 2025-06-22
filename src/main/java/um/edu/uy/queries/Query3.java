package um.edu.uy.queries;

import um.edu.uy.entities.Collection;
import um.edu.uy.entities.Movie;
import um.edu.uy.tads.hash.HashTable;

public class Query3 {
    public void queryTop5CollectionsByRevenue(HashTable<String, Movie> movies,
                                              HashTable<String, Collection> collections) {
        // Una película puede pertenecer a una saga (o colección de películas). Agrupando las
        //películas por saga, indicar el Top 5 de las colecciones que más ingresos generaron.
        //Si una película no es parte de una saga, ella misma se debe considerar como una
        //saga
        //Datos esperados del resultado:
        //● Id de la colección (o película).
        //● Título de la colección (o película).
        //● Cantidad de películas.
        //● Conjunto de Ids de películas que la componen.
        //● Ingresos generados.
    }
}
