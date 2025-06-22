package um.edu.uy.tads.hash;
import um.edu.uy.tads.exceptions.ElementoYaExistenteException;
import um.edu.uy.tads.list.linked.MyLinkedListImpl;

public interface HashTable<K extends Comparable<K>, T extends Comparable<T>> {
    public void insertar(K clave, T valor) throws ElementoYaExistenteException;
    public Elemento<K ,T> pertenece(K clave);
    public void borrar(K clave);
    public int elementos();
    public MyLinkedListImpl<T> allValues();
}