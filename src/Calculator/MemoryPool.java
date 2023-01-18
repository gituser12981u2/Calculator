package Calculator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryPool<T> {


    private final int capacity;
    private final List<T> pool;

    public MemoryPool(int capacity) {
        this.capacity = capacity;
        this.pool = new ArrayList<>(capacity);
    }

    public T acquire() {
        if (pool.isEmpty()) {
            return new T();
        } else {
            return pool.remove(pool.size() - 1);
        }
    }

    public void release(T obj) {
        if (pool.size() < capacity) {
            pool.add(obj);
        }
    }
}
