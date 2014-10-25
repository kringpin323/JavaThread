package javathreads.examples.ch08.example6;

import java.util.*;
import java.util.concurrent.*;

public class FibonacciProducer implements Runnable {
    private Thread thr;
    // �洢Ҫ������������������ȳ�ǰ̫�࣬�����߽���blockingס����֮��һ��
    private BlockingQueue<Integer> queue;

    public FibonacciProducer(BlockingQueue<Integer> q) {
        // ��� BlockingQueue Ӧ���ǹ���ģ���Ϊ��������������֮�����Ϣ����
    	queue = q;
        thr = new Thread(this);
        // ��ʼ����
        thr.start();
    }

    public void run() {
        try {
            for(int x=0;;x++) {
                Thread.sleep(1000);
                queue.put(new Integer(x));// ����X
                System.out.println("Produced request " + x);
            }
        } catch (InterruptedException ex) {
        }
    }
}
