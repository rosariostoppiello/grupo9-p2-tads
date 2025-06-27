package um.edu.uy.tads.tree.heap;

public class HeapData<K extends Comparable<K>, T> {

    private K key;
    private T data;

    public HeapData(K key, T data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return key + (data != null ? ": " + data : "");
    }
}