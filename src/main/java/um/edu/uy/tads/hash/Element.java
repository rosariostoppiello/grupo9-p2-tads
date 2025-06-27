package um.edu.uy.tads.hash;

public class Element<K extends Comparable<K>, T> implements Comparable<Element<K, T>>  {
    private K key;
    private T value;

    public Element(K clave, T valor) {
        this.key = clave;
        this.value = valor;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public int compareTo(Element<K, T> o) {
        return this.key.compareTo(o.key);
    }
}
