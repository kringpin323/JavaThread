package javathreads.examples.ch09.example1;

import javathreads.examples.ch09.*;

public class ThreadTest {

    public static void main(String[] args) {
//        int nThreads = Integer.parseInt(args[0]);
    	int nThreads = 10;
//        long n = Long.parseLong(args[1]);
//    	long n = 99L;
    	// CPU 密集型真不是开玩笑的，当这个值是 99 的时候，所有 4核全是 100% 运行
    	long n = 42L;
        Thread t[] = new Thread[nThreads];

        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new Task(n, "Task " + i));
            t[i].start();
        }
        for (int i = 0; i < t.length; i++) {
            try {
                t[i].join();// 等t[i]运行完毕，本线程再运行，就是一个一个来
            } catch (InterruptedException ie) {}
        }
        // 一定是所有线程都终结才会执行这句话
        System.out.println("Main going to finish!");
    }
}
