package um.edu.uy.tads.hash;
import um.edu.uy.tads.exceptions.ElementoYaExistenteException;

public interface HashTable<K, T> {
    public void insertar(K clave, T valor) throws ElementoYaExistenteException;
    public Elemento pertenece(K clave);
    public void borrar(K clave);
}