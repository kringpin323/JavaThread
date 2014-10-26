package javathreads.examples.ch10;

import java.util.*;
import java.text.*;

public class Task implements Runnable {
    long n;
    String id;

    private long fib(long n) {
        if (n == 0)
            return 0L;
        if (n == 1)
            return 1L;
        return fib(n - 1) + fib(n - 2);
    }

    public Task(long n, String id) {
        this.n = n;
        this.id = id;
    }

    // java 的 stack 大小是 1024K！ 
    // thread 在运行这个run method 的时候，会创建一个 stack frame
    // 存储所用到 的 d, df, startTime, endTime 
    // run() 调用 fib() , fib() method 所用 的stack frame 会被放在这个线程 的 stack中
    // 这个 stack frame 里面有fib() 的 局部变量的存储空间，
    // 每一次递归 就会加入新的 frame，fib() 返回， frame 从 stack 中去除。
    // 内存释放
    public void run() {
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
        long startTime = System.currentTimeMillis();
        d.setTime(startTime);
        System.out.println("Starting task " + id + " at " + df.format(d));
        fib(n);
        long endTime = System.currentTimeMillis();
        d.setTime(endTime);
        System.out.println("Ending task " + id + " at " + df.format(d) + " after " + (endTime - startTime) + " milliseconds");
    }
}
