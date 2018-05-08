package linkedlist;

public interface Sequence <T> {
    public void add(T e);
    public T remove();
    
    public void add(int i, T e);
    public T remove(int i);
}
