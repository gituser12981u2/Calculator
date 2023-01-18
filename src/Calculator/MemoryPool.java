package Calculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MemoryPool<T> {
    private final int maxCapacity;
    private final long evictionTime;
    private ConcurrentHashMap<String, OperatorInfo> operators;
    private ExecutorService executor;
    private final Queue<Token> evictionQueue;
    private final long memoryThreshold = 100000000;

    public MemoryPool(int maxCapacity, long evictionTime) {
        this.maxCapacity = maxCapacity;
        this.evictionTime = evictionTime;
        this.evictionQueue = new ConcurrentLinkedQueue<>();
        this.executor = Executors.newSingleThreadExecutor();
        operators = new ConcurrentHashMap<>();
        executor = Executors.newSingleThreadExecutor();
        // Time interval retrofit to the size of the memory pool
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::evictEntries, 0, 1, TimeUnit.SECONDS);
    }

    public void addOperator(Token operatorToken) {
        executor.submit(() -> {
            synchronized (operators) {
                OperatorInfo info = operators.get(operatorToken.getValue());
                if (info == null) {
                    info = new OperatorInfo(operatorToken);
                    operators.put(operatorToken.getValue(), info);
                } else {
                    info.add();
                }
            }
        });
    }

    public Map<String, OperatorInfo> getOperators() {
        return operators;
    }

    public int size() {
        return operators.size();
    }

    public Token peek() {
        return evictionQueue.peek();
    }

    public Token poll() {
        Token token = evictionQueue.poll();
        operators.remove(token);
        return token;
    }

    public void evictEntries() {
        long currentTime = System.currentTimeMillis();
        while (size() > maxCapacity) {
            Token token = peek();
            if (token == null) {
                break;
            }
            OperatorInfo operatorInfo = operators.get(token.getValue());
            if (currentTime - operatorInfo.getIndex() > evictionTime) {
                poll();
            } else {
                break;
            }
        }
    }

    public void retrofit() {
        if (operators.size() > maxCapacity) {
            long currentTime = System.currentTimeMillis();
            for (Map.Entry<String, OperatorInfo> entry : operators.entrySet()) {
                OperatorInfo info = entry.getValue();
                long tokenTime = info.getLastAccessTime();
                if (currentTime - tokenTime > evictionTime) {
                    operators.remove(entry.getKey());
                }
            }
        }
    }

    private class OperatorInfo {
        private Token operator;
        private int quantity;
        private int index;
        private long lastAccessTime;

        public OperatorInfo(Token operator) {
            this.operator = operator;
            this.quantity = 1;
            this.index = operator.getIndex();
            this.lastAccessTime = System.currentTimeMillis();
        }

        public void add() {
            this.quantity++;
            this.lastAccessTime = System.currentTimeMillis();
        }

        public long getLastAccessTime() {
            return lastAccessTime;
        }

        public Token getOperator() {
            return operator;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getIndex() {
            return index;
        }
    }

    public void monitorMemoryUsage() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            long freeMemory = Runtime.getRuntime().freeMemory();
            if (freeMemory < memoryThreshold) {
                System.err.println("Abnormal memory usage detected. Free memory: " + freeMemory + " bytes");
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    public void saveStatsToFile() {
        File file = new File("memoryPoolStats.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println("Elements in HashMap: " + operators.size());
            writer.println("Size of pool: " + maxCapacity);
            int maxQuantity = 0;
            String maxElement = "";
            for (Map.Entry<String, OperatorInfo> entry : operators.entrySet()) {
                if (entry.getValue().getQuantity() > maxQuantity) {
                    maxQuantity = entry.getValue().getQuantity();
                    maxElement = entry.getKey();
                }
            }
            writer.println("Highest Quantity Element: " + maxElement + "(" + maxQuantity + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanup() {
        executor.shutdown();
        operators.clear();
        evictionQueue.clear();
    }
}
