package javathreads.examples.ch12.example1;

import java.io.*;
import java.net.*;
import javathreads.examples.ch12.*;

public class TypeServer extends TCPServer {
    public void run(Socket data) {
        try {
            DataOutputStream dos =
                   new DataOutputStream(data.getOutputStream());
            dos.writeByte(TypeServerConstants.WELCOME); // 回复 welcome
            DataInputStream dis =
                  new DataInputStream(data.getInputStream());
            while (true) {
                byte b = dis.readByte(); // 写入，block住，直到客户机确实送出字符串的请求
                // 在独立的 thread 中运行客户机
                if (b != TypeServerConstants.GET_STRING_REQUEST) { // message passing 的线程间通信
                    System.out.println("Client sent unknown request " + b);
                    continue;
                }
                dos.writeByte(TypeServerConstants.GET_STRING_RESPONSE);
                dos.writeUTF("Thisisateststring");
                dos.flush(); // 刷新缓冲区，将数据传送给 客户端
            }
        } catch (Exception e) {
            System.out.println("Client terminating: " + e);
            return;
        }
    }

    public static void main(String[] args) throws IOException {
        TypeServer ts = new TypeServer();
        ts.startServer(Integer.parseInt(args[0]));
        System.out.println("Server ready and waiting...");
    }
}
