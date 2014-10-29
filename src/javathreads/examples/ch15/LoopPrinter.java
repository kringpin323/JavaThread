package javathreads.examples.ch15;

import java.util.*;
import java.io.*;

// 双重循环，分离输出
public class LoopPrinter {
    private Vector pStorage[];
    private int growSize;

    public LoopPrinter(int initSize, int growSize) {
        pStorage = new Vector[initSize];
        this.growSize = growSize;
    }

    public LoopPrinter() {
        this(100, 0);
    }

    // 任何的带有print() println() 的方法都必须要被同步化
    // 两个目的
    // 1. 它让array大小可以在没有race condition 的状况下增加
    // 2. 让 method 能够在循环索引被分配给两个thread时运行，但已经不能保证输出顺序
    // 至于这个method的同步化大概是使用了共享状态下的 vector变量，具体不明
    private synchronized void enlargeStorage(int minSize) {
        int oldSize = pStorage.length;
        if (oldSize < minSize) {
            int newSize = (growSize > 0) ?
                oldSize + growSize : 2 * oldSize;
            if (newSize < minSize) {
                newSize = minSize;
            }    
            Vector newVec[] = new Vector[newSize];
            System.arraycopy(pStorage, 0, newVec, 0, oldSize);
            pStorage = newVec;
        }
    }
 
    public synchronized void print(int index, Object obj) {
        if (index >= pStorage.length) {
            enlargeStorage(index+1);
        }
        if (pStorage[index] == null) {
            pStorage[index] = new Vector();
        }
        pStorage[index].addElement(obj.toString());
    }
 
    public synchronized void println(int index, Object obj) {
        print(index, obj);
        print(index, "\n");
    }
 
    public synchronized void send2stream(PrintStream ps) {
        for (int i = 0; i < pStorage.length; i++) {
            if (pStorage[i] != null) {
            	// 这样的遍历方式
                Enumeration e = pStorage[i].elements();
                while (e.hasMoreElements()) {
                    ps.print(e.nextElement());
                }
            }    
        }
    }
}
