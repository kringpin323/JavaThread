package javathreads.examples.ch15.example6;

import javathreads.examples.ch15.*;

public class SinTable extends GuidedLoopHandler {
    // 私有，指向堆
	private float lookupValues[];
    // 简约变量，估计意思：只需要在 = 号左边出现，不需要在右边出现的变量
    public float sumValue;

    // 初始化
    public SinTable() {
        super(0, 360*100, 100, 12);
        // 360* 100 个的空间数组
        lookupValues = new float [360 * 100];
    }

    public void loopDoRange(int start, int end) {
        float sinValue = 0.0f;
        // 利用thread stack frame 存储变量
        float sumValue = 0.0f;
        for (int i = start; i < end; i++) {
            sinValue = (float)Math.sin((i % 360)*Math.PI/180.0);
            lookupValues[i] = sinValue * (float)i / 180.0f;
            // 循环的值 简约到本地，这个 sumValue 放在 thread 的 stack 的 其中一个 frame 上
            sumValue += lookupValues[i];
        }
        // 每个范围作一次同步化而不是每个循环作一次同步化，通过使用 私有循环变量来减少同步的使用
        synchronized (this) {
        	// 所有的简约变量都是回存变量
        	// 真正的简约变量，这个sumValue是public的，所以其他线程可以访问，用同步处理	        	
            this.sumValue += sumValue;
        }
    }

    public float[] getValues() {
        loopProcess();
        // 计算完成了，然后 所有的线程都是本线程，是SinTable
        System.out.println(sumValue);
        return lookupValues;
    }

    public static void main(String args[]) {
        System.out.println("Starting Example 6 (Two-Stage Reduction Example)");
        System.out.print("Sum: ");

        SinTable st = new SinTable();
        float results[] = st.getValues();

        System.out.println("Results: "+ results[0]+ ", "+
                      results[1]+ ", "+ results[2]+ ", "+ "...");
        System.out.println("Done");
    }
}
