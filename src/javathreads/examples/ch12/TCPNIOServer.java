package javathreads.examples.ch12;

import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;

// ��һ thread �� �� nio����������������� ��һ��thread������ �ͻ��� IO
public abstract class TCPNIOServer implements Runnable {
    protected ServerSocketChannel channel = null;
    private boolean done = false;
    // ׷�� ���ϵ��socket�����п��ŵĿͻ��˵�socket
    // �п������ݣ� selector�ᱻ֪ͨ
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
            // ����δ�������ݵ�socket���ͨ��selectedKeys()����������
            Iterator it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                if (key.isReadable() || key.isWritable()) {
                    // Key represents a socket client
                	// �ͻ��� �� ����  socket
                    try {
                        handleClient(key);
                    } catch (IOException ioe) {
                        // Client disconnected
                        key.cancel();
                    }
                } else if (key.isAcceptable()) {
                    try {
                    	// ����Ǽ��ϵ�socket������ handleServer()
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

    // ����Ǽ��ϵ� socket ������ handleServer ����һ���µĿͻ�������
    protected void handleServer(SelectionKey key) throws IOException {
    	 // ���� �� IO ���ᱻ blockס
         SocketChannel sc = channel.accept();
         sc.configureBlocking(false);
         // ���ͻ��� socket �Ǽǵ� selector ��
         // 
         sc.register(selector, SelectionKey.OP_READ);
         registeredClient(sc);
     }

    // �ͻ��˵� ���� socket ������ handleClient
    protected abstract void handleClient(SelectionKey key) throws IOException;
    protected abstract void registeredClient(SocketChannel sc) throws IOException;
}
