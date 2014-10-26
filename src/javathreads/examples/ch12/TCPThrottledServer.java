package javathreads.examples.ch12;

import java.net.*;
import java.io.*;

// leader-follower 模式，
// 没看明白这里，在 serverThreads[i].start()启动后，server.accept()
// 里面的 server 怎么传递偷 子线程，因为初始化应该为空才对
public abstract class TCPThrottledServer implements Runnable {
    ServerSocket server = null;
    Thread[] serverThreads; // 有限线程数的 TCPServer
    volatile boolean done = false;  // shutdown server 的标志

    public synchronized void startServer(int port, int nThreads) throws IOException {
        server = new ServerSocket(port); // 

        // 必须追查所有的thread以便stopServer可以中断这些thread，但是这个只是抽象类，要看真正的实现才能理解
        serverThreads = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            serverThreads[i] = new Thread(this); // 每个 线程池里面的 Thread 都是本身这个 
            serverThreads[i].start(); // 但是貌似 shutdown 以后不能再开一个
        }
    }

    public synchronized void setDone() {
        done = true;
    }

    public void run() {
        while (!done) {
            try {
            	// 每次 client过来都会生成一个新的Socket
                Socket data;
                data = server.accept(); //  thread数量一定，循环使用，但太多的时间用于建立新连接
                run(data);
            } catch (IOException ioe) {
                System.out.println("Accept error " + ioe);
            }
        }
    }

    public void run(Socket data) {
    }
}
