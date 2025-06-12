package um.edu.uy.tads.hash;

public class Elemento<K extends Comparable<K>, T> implements Comparable<Elemento<K, T>>  {
    private K clave;
    private T valor;

    public Elemento(K clave, T valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    @Override
    public int compareTo(Elemento<K, T> o) {
        return this.clave.compareTo(o.clave);
    }
}
