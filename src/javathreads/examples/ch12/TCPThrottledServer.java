package javathreads.examples.ch12;

import java.net.*;
import java.io.*;

// leader-follower ģʽ��
// û����������� serverThreads[i].start()������server.accept()
// ����� server ��ô����͵ ���̣߳���Ϊ��ʼ��Ӧ��Ϊ�ղŶ�
public abstract class TCPThrottledServer implements Runnable {
    ServerSocket server = null;
    Thread[] serverThreads; // �����߳����� TCPServer
    volatile boolean done = false;  // shutdown server �ı�־

    public synchronized void startServer(int port, int nThreads) throws IOException {
        server = new ServerSocket(port); // 

        // ����׷�����е�thread�Ա�stopServer�����ж���Щthread���������ֻ�ǳ����࣬Ҫ��������ʵ�ֲ������
        serverThreads = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            serverThreads[i] = new Thread(this); // ÿ�� �̳߳������ Thread ���Ǳ������ 
            serverThreads[i].start(); // ����ò�� shutdown �Ժ����ٿ�һ��
        }
    }

    public synchronized void setDone() {
        done = true;
    }

    public void run() {
        while (!done) {
            try {
            	// ÿ�� client������������һ���µ�Socket
                Socket data;
                data = server.accept(); //  thread����һ����ѭ��ʹ�ã���̫���ʱ�����ڽ���������
                run(data);
            } catch (IOException ioe) {
                System.out.println("Accept error " + ioe);
            }
        }
    }

    public void run(Socket data) {
    }
}
