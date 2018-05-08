package linkedlist;

import java.util.Iterator;

// Exercício: implementar a interface Queue nesta estrutura
public class LinkedList<T extends Comparable<T>> implements Sequence<T>, Iterable<T>, Stack<T>, Queue<T> {

    @Override
    public void push(T e) {
        add(length(), e);
    }

    @Override
    public T pop() {
        return remove(length());
    }

    // Exercício: implementar o método abaixo
    @Override
    public void enqueue(T e) {
        push(e);
    }

    // Exercício: implementar o método abaixo
    @Override
    public T dequeue() {
        return remove();
    }

    /* Cada Node (Nó) contém um valor v, e aponta para outros dois Nodes que
       representam informação anteriores (prev) e posteriores (next) a ele.    
     */
    class Node<E> {

        private E v;
        private Node<E> prev;
        private Node<E> next;

        Node(E v, Node<E> prev, Node<E> next) {
            this.v = v;
            this.prev = prev;
            this.next = next;
        }

        /**
         * @return the v
         */
        public E getV() {
            return v;
        }

        /**
         * @param v the v to set
         */
        public void setV(E v) {
            this.v = v;
        }

        /**
         * @return the prev
         */
        public Node<E> getPrev() {
            return prev;
        }

        /**
         * @param prev the prev to set
         */
        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        /**
         * @return the next
         */
        public Node<E> getNext() {
            return next;
        }

        /**
         * @param next the next to set
         */
        public void setNext(Node<E> next) {
            this.next = next;
        }
        
        
    }

    // A head (cabeça) representa o primeiro valor da lista
    Node<T> head;

    Node<T> n;
    
    Node<T> curr;

    // Retorna o valor contido no Node head
    public T head() {
        return head.getV();
    }

    // Insere o valor v no início da lista. 
    @Override
    public void add(T v) {
        n = new Node<>(v, null, head);
        head = n;
        if (head.getNext() != null) {
            head.next.setPrev(n);
        }
    }

    @Override
    public void add(int i, T v) {
        Node<T> currPrev = null;
        curr = head;
        int count = 0;
        while (count < i) {
            count++;
            currPrev = curr;
            curr = curr.getNext();
        }
        Node<T> n = new Node<>(v, currPrev, curr);
        if (currPrev != null) {
            currPrev.setNext(n);
        } else {
            head = n;
        }
        if (curr != null) {
            curr.setPrev(n);
        }
    }

    // Conta quantos Nodes existem na lista iniciando na head.
    public int length() {
        curr = head;
        int count = 0;
        while (curr != null) {
            count++;
            curr = curr.getNext();
        }
        return count;
    }

    // Remove o primeiro elemento da lista.
    @Override
    public T remove() {
        T ret = head.getV();

        head = head.getNext();
        head.setPrev(null);
        return ret;
    }

    // Exercício: implementar a operação abaixo.
    @Override
    public T remove(int i) {
        curr = head;
        
        for (int e = 0; e < i; e++) {
            curr = curr.getNext();
        }
       
        return remove(curr.getV());
    }
    
    // Exercício 1 da lista de exercícios
    public T remove(T e) {
        System.out.println("integer");
        curr = head;
        if (curr.getPrev() == null && curr.getNext() == null) {
            head = null;
            return null;
        }
        while (curr != null) {
            if (curr.getV().equals(e)) {
                if (curr.getPrev() != null) {
                    curr.getPrev().setNext(curr.getNext());
                }
                if (curr.getNext() != null) {
                    curr.getNext().setPrev(curr.getPrev());
                }
                if (curr.getPrev() == null && curr.getNext() != null) {
                    head = curr.getNext();
                }

                return curr.getV();
            }
            curr = curr.getNext();
        }
        return null;
    }

    
    
    public int count(T v){
        int count = 0;
        curr = head;

        while (curr != null) {
            if (curr.getV().equals(v)) {
                count++;
            }
            curr = curr.getNext();
        }
        return count;
    }
    
    
    public void sort(){
        if (length() > 0) {
            Node<T> atual = head;
            for (int i = 0; i < length() - 1; i++) {
                while (atual != null) {
                    if (atual.getNext() != null && atual.getV().compareTo(atual.getNext().getV()) > 0) {
                        T temp = atual.getV();
                        atual.setV(atual.getNext().getV());
                        atual.getNext().setV(temp);
                    }
                    atual = atual.getNext();
                }
                atual = head;
            }
        }
    }
    
    public int middle(){
        int meio = (int) Math.round((double) length() / 2);;
        return meio;
    }
    
    public LinkedList<T> split(){
      LinkedList<T> t = new LinkedList<>();
        int v = length()-middle();
        
        while(v != 0){
           t.add(remove(length()-1));
            v--;
        }
      
        return t;
    }
    
    public LinkedList<T> clone(){
        LinkedList<T> t = new LinkedList<>();
        Node<T> node = head;
        while (node != null) {
            t.push(node.getV());
            node = node.getNext();
        }
        return t;
    }
    
    public void reverse(){
        Node<T> prev = null, prox;
        curr = head;

        while (curr != null) {
            prox = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = prox;
        }
        head = prev;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T v = curr.getV();
                curr = curr.getNext();
                return v;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        curr = head;
        sb.append('[');
        while (curr != null) {
            sb.append(curr.getV());
            curr = curr.getNext();
            if (curr != null) {
                sb.append(", ");
            } else {
                sb.append(']');
            }
        }
        return sb.toString();
    }

    public String prettyToString() {
        StringBuilder sb = new StringBuilder();
        curr = head;
        while (curr != null) {
            if (curr.getPrev() == null) {
                sb.append('-');
            } else {
                sb.append('=');
            }
            sb.append('[');
            sb.append(curr.getV());
            sb.append(']');
            if (curr.getNext() == null) {
                sb.append('-');
            } else {
                sb.append('=');
            }
            curr = curr.getNext();
        }
        return sb.toString();
    }
}
