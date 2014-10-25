
package javathreads.examples.ch08.example6;

import java.util.concurrent.*;

public class FibonacciConsumer implements Runnable {
    private Fibonacci fib = new Fibonacci();
    private Thread thr;
    private BlockingQueue<Integer> queue;

    public FibonacciConsumer(BlockingQueue<Integer> q) {
        // 果然是共享的 BlockingQueue 
    	queue = q;
        thr = new Thread(this);
        thr.start();
    }

    public void run() {
        int request, result;
        try {
            while (true) {
            	// 消费一个,如果这里没有，消费者将会被block住，将整个并发控制交给了 blockingQueue
                request = queue.take().intValue();
                // 消费的结果
                result = fib.calculateWithCache(request);
                // 输出消费的结果
                System.out.println("Calculated result of " + result + " from " + request);
            }
        } catch (InterruptedException ex) {

        }
    }
}
