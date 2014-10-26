package javathreads.examples.ch12;

import java.net.*;
import java.io.*;

// 传统服务器 TCPServer class 实现
public class TCPServer implements Cloneable, Runnable {
    Thread runner = null;
    ServerSocket server = null;
    Socket data = null;

    private boolean done = false;

    // 由于两method都操作共享的数据所以将其设为同步
    public synchronized void startServer(int port) throws IOException {
        if (runner == null) {
            server = new ServerSocket(port);
            runner = new Thread(this);
            runner.start();
        }
    }

    public synchronized void stopServer() {
        done = true;
        runner.interrupt();
    }

    protected synchronized boolean getDone() {
        return done;
    }

    public void run() {
        if (server != null) {
            while (!getDone()) {
                try {
                	// 等待链接，当连接到来的时候，初始化另一个 server ，新的线程，再等待下一个连接
                	// 知道程序 调用 stopServer,这就是过去的堵塞IO的TCPServer实现
                	// TCPserver 只有一个
                    Socket datasocket = server.accept();
                    // 克隆一个 server
                    TCPServer newSocket = (TCPServer) clone();

                    newSocket.server = null;
                    newSocket.data = datasocket;
                    newSocket.runner =
                        new Thread(newSocket);
                    
                    // 克隆的server重新启动
                    newSocket.runner.start();
                } catch (Exception e) {}
            }
        } else {
        	// 模板模式，将 这个method留给子类实现
        	// 唯一可能的调用情况是：在startServer中 runner 不为 null 但是 server 为 null
        	// 就是上面的情况洛，也就是说 当 TCPServer启动以后，所有的 runner 都是新的面对客户端的runner
        	// 但是server都设为 null，所有客户端的处理线程都交给 子类实现run（data）
            run(data);
        }
    }
 
    public void run(Socket data) {
    }
}
