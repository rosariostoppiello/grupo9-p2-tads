package um.edu.uy.tads.hash;
import um.edu.uy.tads.exceptions.ElementoYaExistenteException;

public interface HashTable<T> {
    public void insertar(String clave, T valor) throws ElementoYaExistenteException;
    public boolean pertenece(String clave);
    public void borrar(String clave);
}