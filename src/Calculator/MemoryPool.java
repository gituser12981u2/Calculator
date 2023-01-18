package Calculator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryPool<Token> {


    private final int capacity;
    private final List<Token> pool;

    public MemoryPool(int capacity) {
        this.capacity = capacity;
        this.pool = new ArrayList<>(capacity);
    }

    public Token acquire() {
        if (pool.isEmpty()) {
            return new Token();
        } else {
            return pool.remove(pool.size() - 1);
        }
    }

    public void release(Token obj) {
        if (pool.size() < capacity) {
            pool.add(obj);
        }
    }
}
