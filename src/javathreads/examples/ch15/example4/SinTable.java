package javathreads.examples.ch15.example4;

import javathreads.examples.ch15.*;

// 还是很多不明白，怎么办
public class SinTable extends GuidedLoopHandler {
    private float lookupValues[];
    // 和之前版本不同的地方，这个设为了 回存变量
    private float sinValue;
 
    public SinTable() {
        super(0, 360*100, 100, 12);
        lookupValues = new float [360 * 100];
    }
 
    public void loopDoRange(int start, int end) {
    	// 已经定好了每个线程 自己的处理范围
        float sinValue = 0;
        for (int i = start; i < end; i++) {
            sinValue = (float)Math.sin((i % 360)*Math.PI/180.0);
            lookupValues[i] = sinValue * (float)i / 180.0f;
        }
        if (end == endLoop)
        	// 本线程的 sinValue 在 线程 的 stack 中
        	// 不用同步的情况是，只有一个thread会有 end == endLoop的情况，不会有 race condition发生
        	// 请记着race condition 的定义：多个线程并发访问共享数据区，导致数据不一致
            this.sinValue = sinValue;
    }
 
    public float[] getValues() {
        loopProcess();
        // 怎么理解sinValue是循环私有变量
        // 也是回春变量，我们需要存储的"最终"的变量值
        // 唯一的理解是 这个class是 继承了 Runnable的class
        // 作为线程使用的时候，sinValue的实际值 存储在 thread stack 中
        // 因此和其他线程互不干扰
        // lookupValues[0] 是 共享的，为什么？
        //　最后的一个值给回他，这样有什么好处？
        // 没想明白
        lookupValues[0] += sinValue;
        return lookupValues;
    }

    public static void main(String args[]) {
        System.out.println("Starting Example 4 (Storeback Variable Example)");

        SinTable st = new SinTable();
        float results[] = st.getValues();

        System.out.println("Results: "+ results[0]+ ", "+
                      results[1]+ ", "+ results[2]+ ", "+ "...");
        System.out.println("Done");
    }
}
