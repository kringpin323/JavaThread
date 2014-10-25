package javathreads.examples.ch09.example1;

import javathreads.examples.ch09.*;

public class ThreadTest {

    public static void main(String[] args) {
//        int nThreads = Integer.parseInt(args[0]);
    	int nThreads = 10;
//        long n = Long.parseLong(args[1]);
//    	long n = 99L;
    	// CPU �ܼ����治�ǿ���Ц�ģ������ֵ�� 99 ��ʱ������ 4��ȫ�� 100% ����
    	long n = 42L;
        Thread t[] = new Thread[nThreads];

        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new Task(n, "Task " + i));
            t[i].start();
        }
        for (int i = 0; i < t.length; i++) {
            try {
                t[i].join();// ��t[i]������ϣ����߳������У�����һ��һ����
            } catch (InterruptedException ie) {}
        }
        // һ���������̶߳��ս�Ż�ִ����仰
        System.out.println("Main going to finish!");
    }
}
