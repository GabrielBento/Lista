package linkedlist;

import java.util.Arrays;
import java.util.Iterator;

// Exercício: implementar a interface Stack nesta estrutura
public class Vector<T extends Comparable<T>> implements Sequence<T>, Iterable<T>, Stack<T>, Queue<T> {

    /* Contém os elementos inseridos. É substituido quando sua capacidade chega
       ao limite. Ver métodos expand e add.
     */
    private T[] elements;
    // Capacidade inicial de elements.
    private int capacity = 4;
    /* Número de elementos presentes em elements. Deve ser sempre 
       <= elements.length
     */
    private int numElements = 0;

    public Vector() {
        elements = (T[]) new Comparable[capacity];
    }

    public Vector(int capacity) {
        this.capacity = capacity;
        elements = (T[]) new Comparable[capacity];
    }

    // Exercício 1 da lista de exercícios
    public T remove(T v) {
        for (int i = 0; i < numElements; i++) {
            if (elements[i].equals(v)) {
                // Henrique
                return remove(i);
            }
        }
        return null;
    }

    public void set(int i, T v) {
        if (i < 0 || i >= numElements) {
            throw new InvalidIndexException();
        }
        elements[i] = v;
    }

    public T get(int i) {
        if (i < 0 || i >= numElements) {
            throw new InvalidIndexException();
        }
        return elements[i];

    }

    // Insere um elemento no final
    @Override
    public void add(T v) {
        if (isFull()) {
            expand();
        }
        elements[numElements++] = v;
    }

    // Insere o valor v na posição i.
    @Override
    public void add(int i, T v) {
        if (i < 0 || i > numElements) {
            throw new InvalidIndexException();
        }
        // Se elements estiver cheio, cria-se um novo array de maior capacidade.
        if (isFull()) {
            expand();
        }
        // Realiza um shift para direita.
        for (int shift = numElements - 1; shift >= i; shift--) {
            elements[shift + 1] = elements[shift];
        }
        // Insere o elemento
        elements[i] = v;
        numElements++;
    }

    // Remove o i-ésimo valor (i inicia em 0).
    @Override
    public T remove(int i) {
        if (i < 0 || i >= numElements) {
            throw new InvalidIndexException();
        }

        T el = elements[i];
        // Realiza um shift para esquerda.
        for (int shift = i; shift < numElements - 1; shift++) {
            elements[shift] = elements[shift + 1];
        }
        numElements--;

        if (numElements < (capacity / 2)) {
            shrink();
        }
        return el;
    }

    private boolean isFull() {
        return numElements == capacity;
    }

    private void shrink() {
        T[] shrinkedElements = (T[]) new Object[capacity /= 2];
        for (int i = 0; i < numElements; i++) {
            shrinkedElements[i] = elements[i];
        }
        elements = shrinkedElements;
    }

    private void expand() {
        /* A implementação abaixo é meramente ilustrativa. Para uma forma mais
           eficiente, veja o comentário no final deste método.
         */
//        T[] expandedElements = (T[]) new Object[capacity *= 2];
//        for (int i = 0; i < numElements; i++) {
//            expandedElements[i] = elements[i];
//        }
//        elements = expandedElements;

        /* A forma mais recomendada de realizar esta operação é utilizar a 
           implementação já provida na biblioteca padrão conforme abaixo.
         */
        elements = Arrays.copyOf(elements, capacity *= 2);
    }

    @Override
    public String toString() {
        // Atenção: prático mas ineficiente.
        return Arrays.toString(Arrays.copyOfRange(elements, 0, numElements));
    }

    @Override
    public T remove() {
        return remove(numElements-1);
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(elements).iterator();
    }

    @Override
    public void enqueue(T e) {
        add(e);
    }

    @Override
    public T dequeue() {
        return remove(0);
    }

    // Exercício: implementar o método abaixo
    @Override
    public void push(T e) {
        add(e);
    }

    // Exercício: implementar o método abaixo
    @Override
    public T pop() {
        return remove();
    }

    public int count(T v) {
        int count = 0;
        for(T i: elements){
            if(v.equals(i))
                count++;
        }
        return count;
    }

    public void sort() {
        if(numElements > 0){
            for(int i = 0; i < numElements; i++){
                for(int a = 0; a < numElements-1; a++){
                    if(elements[a].compareTo(elements[a+1]) > 0){
                        T temp = elements[a];
                        elements[a] = elements[a+1];
                        elements[a+1] = temp;
                    }
                }
            }
        }
    }

    public int middle() {
        int meio = (int) Math.round((double) numElements / 2);;
        return meio;
    }

    public Vector<T> split() {
        Vector<T> t = new Vector<>();
        int v = numElements-middle();
        
        while (v != 0) { 
            t.add(remove());
            v--;         
        }
        t.reverse();
        
        return t;
    }

    public Vector<T> clone() {
        Vector<T> t = new Vector<>();
        for (int i = 0; i < numElements; i++) {
            t.add(elements[i]);
        }
        return t;
    }

    public void reverse() {

        T[] elements = Arrays.copyOf(this.elements, this.capacity);
        int a = numElements - 1;

        for (int i = 0; i < numElements; i++) {

            this.elements[i] = elements[a];

            a--;

        }

    }

    public static class InvalidIndexException extends RuntimeException {

        public InvalidIndexException() {
        }
    }

}
