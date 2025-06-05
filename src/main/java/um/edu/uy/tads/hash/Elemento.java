package um.edu.uy.tads.hash;

public class Elemento<T> implements Comparable<Elemento<T>> {
    private String clave;
    private T valor;

    public Elemento(String clave, T valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public int compareTo(Elemento<T> o) {
        return this.clave.compareTo(o.clave);
    }
}
