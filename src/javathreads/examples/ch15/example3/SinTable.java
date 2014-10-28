package javathreads.examples.ch15.example3;

import javathreads.examples.ch15.*;

// 	以最小的成本计算将工作分配给thread，负载均衡
// 产生 了一个 小问题，如何逆向抽象？
// 正向可以比较容易看明白
public class SinTable extends LoopHandler {
	// 这个是共享变量而不是循环处理一部分是因为main客户端要使用它
    private float lookupValues[];

    public SinTable() {
        super(0, 360*100, 12);
        lookupValues = new float [360 * 100];
    }

    // 重写 loopDoRange
    public void loopDoRange(int start, int end) {
        for (int i = start; i < end; i++) {
            float sinValue = (float)Math.sin((i % 360)*Math.PI/180.0);
            lookupValues[i] = sinValue * (float)i / 180.0f;
        }
    }    

    public float[] getValues() {
    	// 循环处理
        loopProcess();
        return lookupValues;
    }

    public static void main(String args[]) {
        System.out.println("Starting Example 3 (Loop Handler Example)");

        SinTable st = new SinTable();
        float results[] = st.getValues();

        System.out.println("Results: "+ results[0]+ ", "+
                      results[1]+ ", "+ results[2]+ ", "+ "...");
        System.out.println("Done");
    }
}
