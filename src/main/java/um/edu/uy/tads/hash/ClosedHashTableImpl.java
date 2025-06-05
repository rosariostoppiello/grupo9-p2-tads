package um.edu.uy.tads.hash;

import um.edu.uy.tads.hash.*;
import um.edu.uy.tads.exceptions.ElementoYaExistenteException;

// hashing de dispersión abierta --> tanto lineal como cuadrática
public class ClosedHashTableImpl<T> implements HashTable<T> {

    private Elemento<T>[] tabla;
    private int totalSize;
    private int elementos;
    private final int tipoResolucion; // 0 - lineal y 1 - cuadrática
    private final Elemento<T> elementoBorrado; // tombstone !!
    // del libro: "The tombstone indicates that a record once occupied the slot but does so no longer."

    @SuppressWarnings("unchecked")
    public ClosedHashTableImpl(int totalSize, int tipoResolucion) {
        this.totalSize = siguientePrimo(totalSize);
        this.tabla = new Elemento[this.totalSize];
        this.elementos = 0;
        this.elementoBorrado = new Elemento<>("Borrado", null);

        if (tipoResolucion != 0 && tipoResolucion != 1) {
            throw new RuntimeException("Se debe ingresar 0 o 1 para escoger el método de resolución de colisiones.");
        } else {
            this.tipoResolucion = tipoResolucion;
        }
    }

    // función hash
    private int hashFuncion(String clave) {
        return Math.abs(clave.hashCode()) % totalSize;
    }

    @Override
    public void insertar(String clave, T valor) throws ElementoYaExistenteException {

        if (elementos >= totalSize * 0.80) {
            reestructuracion();
        }

        int pos = hashFuncion(clave);
        int i = 0;

        if (tipoResolucion == 0) { // lineal
            // mientras que la posición sea diferente de null y el valor no haya sido borrado lógicamente
            while (tabla[(pos+i) % totalSize] != null && tabla[(pos+i) % totalSize] != elementoBorrado) {
                if (tabla[(pos+i) % totalSize].getClave().equals(clave)) {
                    throw new ElementoYaExistenteException("La clave ya existe en la tabla: " + clave);
                }
                i++;
            }
        } else if (tipoResolucion == 1) { // cuadrática
            while (tabla[(pos+(i*i)) % totalSize] != null && tabla[(pos+(i*i)) % totalSize] != elementoBorrado) {
                if (tabla[(pos+(i*i)) % totalSize].getClave().equals(clave)) {
                    throw new ElementoYaExistenteException("La clave ya existe en la tabla: " + clave);
                }
                i++;
                if (i >= totalSize) break; // evito bucle como en el ejercicio 3.4 de insertar en tablas
            }
        }

        // llego a acá si encuentro una posición null o que el valor no null que había fue borrado
        tabla[(pos+i) % totalSize] = new Elemento<>(clave, valor);
        elementos ++;

    }

    @Override
    public boolean pertenece(String clave) {

        int pos = hashFuncion(clave);
        int i = 0;

        if (tipoResolucion == 0) {
            while (tabla[(pos+i) % totalSize] != null) { // chequeo que a donde me lleva la clave no sea null
                if (tabla[(pos+i) % totalSize] != elementoBorrado && tabla[(pos+i) % totalSize].getClave().equals(clave)) {
                    // si esa posición no tiene el atributo borrado=true y que la clave sea igual a la clave
                    return true;
                }
                i++; // ocurre que si no es igual la clave es porque hubo una colisión y moví el elemento de forma lineal
                if (i >= totalSize) break;
            }
        } else if (tipoResolucion == 1) {
            while (tabla[(pos+i) % totalSize] != null) { // chequeo que a donde me lleva la clave no sea null
                if (tabla[(pos+(i*i)) % totalSize] != elementoBorrado && tabla[(pos+(i*i)) % totalSize].getClave().equals(clave)) {
                    return true;
                }
                i++;
                if (i >= totalSize) break;

            }
        }
        return false; // si no lo encontró
    }

    @Override
    public void borrar(String clave) {
        int pos = hashFuncion(clave);
        int i = 0;

        if (tipoResolucion == 0) {
            while (tabla[(pos + i) % totalSize] != null) {
                if (tabla[(pos + i) % totalSize] != elementoBorrado && tabla[(pos + i) % totalSize].getClave().equals(clave)) {
                    tabla[(pos + i) % totalSize] = elementoBorrado;
                    elementos--;
                    return;
                }
                i++;
                if (i >= totalSize) break;
            }
        } else if (tipoResolucion == 1) {
            while (tabla[(pos + (i * i)) % totalSize] != null) {
                if (tabla[(pos + (i * i)) % totalSize] != elementoBorrado && tabla[(pos + (i * i)) % totalSize].getClave().equals(clave)) {
                    tabla[(pos + (i * i)) % totalSize] = elementoBorrado;
                    elementos--;
                    return;
                }
                i++;
                if (i >= totalSize) break;
            }

        }
    }

    @SuppressWarnings("unchecked")
    private void reestructuracion() {

            Elemento<T>[] tablaAnterior = tabla;
            int sizeAnterior = totalSize;

            totalSize = siguientePrimo(sizeAnterior * 2);
            tabla = new Elemento[totalSize];
            elementos = 0;

            for (Elemento<T> elemento : tablaAnterior) {
                if (elemento != null && elemento != elementoBorrado) {
                    try {
                        insertar(elemento.getClave(), elemento.getValor());
                    } catch (ElementoYaExistenteException e) {
                        // no va a pasar
                    }
                }
            }

    }

    private static final int[] PRIMOS = {
            17, 29, 53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 98317, 196613,
            393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843, 50331653, 100663319,
            201326611, 402653189, 805306457, 1610612741}; // busqué en internet lista de primos

    private int siguientePrimo(int numero) {
            for (int primo : PRIMOS) {
                if (primo > numero) {
                    return primo;
                }
            }
            throw new RuntimeException("No se ha encontrado un número primo mayor al doble del tamaño actual.");

            // esto pensé en hacerlo con otro procedimiento que iba calculando los números primos pero es mucho
            // más costoso a efectos de este práctico prefiero utilizar una lista, pues no hay tantos números
            // primos a este nivel de cantidades relativamente bajas (llega hasta 1610612741).
    }
}