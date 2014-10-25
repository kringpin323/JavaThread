
package javathreads.examples.ch08.example6;

import java.util.concurrent.*;

public class FibonacciConsumer implements Runnable {
    private Fibonacci fib = new Fibonacci();
    private Thread thr;
    private BlockingQueue<Integer> queue;

    public FibonacciConsumer(BlockingQueue<Integer> q) {
        // ��Ȼ�ǹ���� BlockingQueue 
    	queue = q;
        thr = new Thread(this);
        thr.start();
    }

    public void run() {
        int request, result;
        try {
            while (true) {
            	// ����һ��,�������û�У������߽��ᱻblockס���������������ƽ����� blockingQueue
                request = queue.take().intValue();
                // ���ѵĽ��
                result = fib.calculateWithCache(request);
                // ������ѵĽ��
                System.out.println("Calculated result of " + result + " from " + request);
            }
        } catch (InterruptedException ex) {

        }
    }
}
