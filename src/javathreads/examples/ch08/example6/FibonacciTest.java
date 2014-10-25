
package javathreads.examples.ch08.example6;

import java.util.*;
import java.util.concurrent.*;

public class FibonacciTest {

    public static void main(String[] args) {
    	// ����һ�����͵ġ��н绺��������
    	// ��ͼ�����������з���Ԫ�ػᵼ�·����������������ͼ�ӿն����м���Ԫ�ؽ���������������
    	// ͨ�������ڴ� ���� ͨ�ŵ���������������
    	
    	// Race Condition 
    	// ����Ϊ����߳�ͬʱ������ͬ�Ĺ������ݣ���������ݵĲ�һ���ԡ�
    	// ����취��ͬ�����ݽṹ + synchronized �ؼ�������
        ArrayBlockingQueue<Integer> queue; 
        queue = new ArrayBlockingQueue<Integer>(10);
        new FibonacciProducer(queue);

//        int nThreads = Integer.parseInt(args[0]);
        int nThreads = 10;
        for (int i = 0; i < nThreads; i++)
            new FibonacciConsumer(queue);
    }
}
