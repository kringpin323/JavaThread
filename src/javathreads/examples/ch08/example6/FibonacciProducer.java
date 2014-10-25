package javathreads.examples.ch08.example6;

import java.util.*;
import java.util.concurrent.*;

public class FibonacciProducer implements Runnable {
    private Thread thr;
    // 存储要被处理的请求，生产进度超前太多，消费者将被blocking住，反之，一样
    private BlockingQueue<Integer> queue;

    public FibonacciProducer(BlockingQueue<Integer> q) {
        // 这个 BlockingQueue 应该是共享的，作为生产者与消费者之间的信息传递
    	queue = q;
        thr = new Thread(this);
        // 开始生产
        thr.start();
    }

    public void run() {
        try {
            for(int x=0;;x++) {
                Thread.sleep(1000);
                queue.put(new Integer(x));// 生产X
                System.out.println("Produced request " + x);
            }
        } catch (InterruptedException ex) {
        }
    }
}
