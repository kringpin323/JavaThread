
package javathreads.examples.ch08.example6;

import java.util.*;
import java.util.concurrent.*;

public class FibonacciTest {

    public static void main(String[] args) {
    	// 这是一个典型的“有界缓存区”，
    	// 试图向已满队列中放入元素会导致放入操作受阻塞；试图从空队列中检索元素将导致类似阻塞。
    	// 通过共享内存 进行 通信的生产者与消费者
    	
    	// Race Condition 
    	// 会因为多个线程同时访问相同的共享数据，而造成数据的不一致性。
    	// 解决办法：同步数据结构 + synchronized 关键字锁定
        ArrayBlockingQueue<Integer> queue; 
        queue = new ArrayBlockingQueue<Integer>(10);
        new FibonacciProducer(queue);

//        int nThreads = Integer.parseInt(args[0]);
        int nThreads = 10;
        for (int i = 0; i < nThreads; i++)
            new FibonacciConsumer(queue);
    }
}
