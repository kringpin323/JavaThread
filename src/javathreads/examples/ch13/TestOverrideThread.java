package javathreads.examples.ch13;

import java.util.*;

// 注释与例程无关
// thread group 具有几个目的：
// 能够以单一 的method调用中断一组 thread
// 以自定义的安全管理器来确保无关联的thread不会互相干扰
public class TestOverrideThread implements Runnable {

    static class OverrideExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            alertAdministrator(e);
        }
    }

    public static void alertAdministrator(Throwable e) {
        // Use Java Mail to send the administrator's pager an email
        System.out.println("Adminstrator alert!");
        e.printStackTrace();
    }

    public static void main(String[] args) {
        Thread t = new Thread(new TestOverrideThread());
        t.setUncaughtExceptionHandler(new OverrideExceptionHandler());
        System.out.println(t.getUncaughtExceptionHandler());
        t.start();
    }

    public void run() {
        ArrayList al = new ArrayList();
        while (true) {
            al.add(new byte[1024]);
        }
    }
}
