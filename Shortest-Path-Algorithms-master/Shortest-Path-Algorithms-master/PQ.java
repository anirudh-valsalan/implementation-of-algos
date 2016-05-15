/**
 * Priority Queue
 * @author : Team G12
 *
 * Anirudh K V
 * Malini K Bhaskaran
 * Neha Nirmala Srinivas
 * Saumya Ann George
 */

public interface PQ<T> {
    public void insert(T x);
    public T deleteMin();
    public T min();

    public void add(T x);
    public T remove();
    public T peek();
}
