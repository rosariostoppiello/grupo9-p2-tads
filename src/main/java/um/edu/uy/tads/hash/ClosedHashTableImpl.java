package um.edu.uy.tads.hash;

import um.edu.uy.tads.exceptions.ElementAlreadyExistsException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ClosedHashTableImpl<K extends Comparable<K>, T extends Comparable<T>> implements HashTable<K, T> {

    private Element<K, T>[] table;
    private int totalSize;
    private int elements;
    private final int typeResolution;
    private final Element<K, T> deletedElement; // tombstone !!

    public int elements() {
        return elements;
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTableImpl(int totalSize, int typeResolution) {
        this.totalSize = nextPrime(totalSize);
        this.table = new Element[this.totalSize];
        this.elements = 0;
        this.deletedElement = new Element<>(null, null);

        if (typeResolution != 0 && typeResolution != 1) {
            throw new RuntimeException("Number must be either 0 or 1.");
        } else {
            this.typeResolution = typeResolution;
        }
    }

    private int hashFuncion(K clave) {
        return Math.abs(clave.hashCode()) % totalSize;
    }

    @Override
    public void insert(K key, T value) throws ElementAlreadyExistsException {

        if (elements >= totalSize * 0.80) {
            reestructuration();
        }

        int pos = hashFuncion(key);
        int i = 0;

        if (typeResolution == 0) {
            while (table[(pos + i) % totalSize] != null && table[(pos + i) % totalSize] != deletedElement) {
                if (table[(pos + i) % totalSize].getKey().equals(key)) {
                    throw new ElementAlreadyExistsException("La clave ya existe en la tabla: " + key);
                }
                i++;
            }
            table[(pos + i) % totalSize] = new Element<>(key, value);
        } else if (typeResolution == 1) { // cuadrática
            while (table[(pos + (i * i)) % totalSize] != null && table[(pos + (i * i)) % totalSize] != deletedElement) {
                if (table[(pos + (i * i)) % totalSize].getKey().equals(key)) {
                    throw new ElementAlreadyExistsException("La clave ya existe en la tabla: " + key);
                }
                i++;
                if (i >= totalSize) break;
            }
            table[(pos + i * i) % totalSize] = new Element<>(key, value);
        }
        elements++;
    }

    @Override
    public Element find(K clave) {

        int pos = hashFuncion(clave);
        int i = 0;

        if (typeResolution == 0) {
            while (table[(pos + i) % totalSize] != null) { // chequeo que a donde me lleva la clave no sea null
                if (table[(pos + i) % totalSize] != deletedElement && table[(pos + i) % totalSize].getKey().equals(clave)) {
                    // si esa posición no tiene el atributo borrado=true y que la clave sea igual a la clave
                    return table[(pos + i) % totalSize];
                }
                i++; // ocurre que si no es igual la clave es porque hubo una colisión y moví el elemento de forma lineal
                if (i >= totalSize) break;
            }
        } else if (typeResolution == 1) {
            while (table[(pos + i * i) % totalSize] != null) { // chequeo que a donde me lleva la clave no sea null
                if (table[(pos + (i * i)) % totalSize] != deletedElement && table[(pos + (i * i)) % totalSize].getKey().equals(clave)) {
                    return table[(pos + i * i) % totalSize];
                }
                i++;
                if (i >= totalSize) break;

            }
        }
        return null; // si no lo encontró
    }

    @Override
    public void delete(K clave) {
        int pos = hashFuncion(clave);
        int i = 0;

        if (typeResolution == 0) {
            while (table[(pos + i) % totalSize] != null) {
                if (table[(pos + i) % totalSize] != deletedElement && table[(pos + i) % totalSize].getKey().equals(clave)) {
                    table[(pos + i) % totalSize] = deletedElement;
                    elements--;
                    return;
                }
                i++;
                if (i >= totalSize) break;
            }
        } else if (typeResolution == 1) {
            while (table[(pos + (i * i)) % totalSize] != null) {
                if (table[(pos + (i * i)) % totalSize] != deletedElement && table[(pos + (i * i)) % totalSize].getKey().equals(clave)) {
                    table[(pos + (i * i)) % totalSize] = deletedElement;
                    elements--;
                    return;
                }
                i++;
                if (i >= totalSize) break;
            }

        }
    }

    @SuppressWarnings("unchecked")
    private void reestructuration() {

        Element<K, T>[] tablaAnterior = table;
        int sizeAnterior = totalSize;

        totalSize = nextPrime(sizeAnterior * 2);
        table = new Element[totalSize];
        elements = 0;

        for (Element<K, T> elemento : tablaAnterior) {
            if (elemento != null && elemento != deletedElement) {
                try {
                    insert(elemento.getKey(), elemento.getValue());
                } catch (ElementAlreadyExistsException e) {
                    // no va a pasar
                }
            }
        }

    }

    private static final int[] PRIMES = {
            17, 29, 53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 98317, 196613,
            393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843, 50331653, 100663319,
            201326611, 402653189, 805306457, 1610612741};

    private int nextPrime(int number) {
        for (int prime : PRIMES) {
            if (prime > number) {
                return prime;

            }
        }
        throw new RuntimeException("No se ha encontrado un número primo mayor al doble del tamaño actual.");

    }

    @Override
    public Iterator<Element<K, T>> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<Element<K, T>> {

        private int currentIndex = 0;

        public HashTableIterator() {
            goToNextValidElement();
        }

        @Override
        public boolean hasNext() {
            return currentIndex < totalSize;
        }

        @Override
        public Element<K, T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay más elementos en la tabla hash.");
            }
            Element<K, T> elemento = table[currentIndex];
            currentIndex++;
            goToNextValidElement();
            return elemento;
        }

        private void goToNextValidElement() {
            while (currentIndex < totalSize && (table[currentIndex] == null || table[currentIndex] == deletedElement)) {
                currentIndex++;
            }
        }
    }

    @Override
    public void forEach(Consumer<? super Element<K, T>> action) {
        HashTable.super.forEach(action);
    }

    @Override
    public Spliterator<Element<K, T>> spliterator() {
        return HashTable.super.spliterator();
    }

}