package javathreads.examples.ch12;

import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;

// 单一 thread 化 的 nio服务器，字面理解是 ，一个thread处理多个 客户端 IO
public abstract class TCPNIOServer implements Runnable {
    protected ServerSocketChannel channel = null;
    private boolean done = false;
    // 追逐 集合点的socket与所有开放的客户端的socket
    // 有可用数据， selector会被通知
    protected Selector selector;
    protected int port = 8000;

    public void startServer() throws IOException {
        channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        ServerSocket server = channel.socket();
        server.bind(new InetSocketAddress(port));
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public synchronized void stopServer() throws IOException {
        done = true;
        channel.close();
    }

    protected synchronized boolean getDone() {
        return done;
    }

    public void run() {
        try {
            startServer();
        } catch (IOException ioe) {
            System.out.println("Can't start server:  " + ioe);
            return;
        }
        while (!getDone()) {
            try {
                selector.select();
            } catch (IOException ioe) {
                System.err.println("Server error: " + ioe);
                return;
            }
            // 所有未处理数据的socket组会通过selectedKeys()方法被返回
            Iterator it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                if (key.isReadable() || key.isWritable()) {
                    // Key represents a socket client
                	// 客户端 的 数据  socket
                    try {
                        handleClient(key);
                    } catch (IOException ioe) {
                        // Client disconnected
                        key.cancel();
                    }
                } else if (key.isAcceptable()) {
                    try {
                    	// 如果是集合点socket，调用 handleServer()
                        handleServer(key);
                    } catch (IOException ioe) {
                        // Accept error; treat as fatal
                        throw new IllegalStateException(ioe);
                    }
                } else System.out.println("unknown key state");
                it.remove();
            }
        }
    }

    // 如果是集合点 socket ，调用 handleServer 建立一个新的客户端连接
    protected void handleServer(SelectionKey key) throws IOException {
    	 // 这里 的 IO 不会被 block住
         SocketChannel sc = channel.accept();
         sc.configureBlocking(false);
         // 将客户端 socket 登记到 selector 中
         // 
         sc.register(selector, SelectionKey.OP_READ);
         registeredClient(sc);
     }

    // 客户端的 数据 socket ，调用 handleClient
    protected abstract void handleClient(SelectionKey key) throws IOException;
    protected abstract void registeredClient(SocketChannel sc) throws IOException;
}
