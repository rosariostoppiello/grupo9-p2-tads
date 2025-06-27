package um.edu.uy.tads.hash;
import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;

public interface HashTable<K extends Comparable<K>, T extends Comparable<T>> extends Iterable<Element<K, T>> {
    public void insert(K key, T value) throws ElementAlreadyExistsException;
    public Element<K ,T> find(K clave);
    public void delete(K clave);
    public int elements();
}