package javathreads.examples.ch12;

import java.net.*;
import java.io.*;

// ��ͳ������ TCPServer class ʵ��
public class TCPServer implements Cloneable, Runnable {
    Thread runner = null;
    ServerSocket server = null;
    Socket data = null;

    private boolean done = false;

    // ������method������������������Խ�����Ϊͬ��
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
                	// �ȴ����ӣ������ӵ�����ʱ�򣬳�ʼ����һ�� server ���µ��̣߳��ٵȴ���һ������
                	// ֪������ ���� stopServer,����ǹ�ȥ�Ķ���IO��TCPServerʵ��
                	// TCPserver ֻ��һ��
                    Socket datasocket = server.accept();
                    // ��¡һ�� server
                    TCPServer newSocket = (TCPServer) clone();

                    newSocket.server = null;
                    newSocket.data = datasocket;
                    newSocket.runner =
                        new Thread(newSocket);
                    
                    // ��¡��server��������
                    newSocket.runner.start();
                } catch (Exception e) {}
            }
        } else {
        	// ģ��ģʽ���� ���method��������ʵ��
        	// Ψһ���ܵĵ�������ǣ���startServer�� runner ��Ϊ null ���� server Ϊ null
        	// �������������壬Ҳ����˵ �� TCPServer�����Ժ����е� runner �����µ���Կͻ��˵�runner
        	// ����server����Ϊ null�����пͻ��˵Ĵ����̶߳����� ����ʵ��run��data��
            run(data);
        }
    }
 
    public void run(Socket data) {
    }
}
