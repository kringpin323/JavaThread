package javathreads.examples.ch15.example8;

import javathreads.examples.ch15.*;

public class SinTable extends PoolLoopHandler {
    private float lookupValues[][];
    private int j;

    public SinTable() {
        super(0, 360, 12);
        lookupValues = new float[1000][];
        for (int j = 0; j < 1000; j++) {
            lookupValues[j] = new float[360];
        }
    }    

    public void loopDoRange(int start, int end) {
        float sinValue = 0.0f;
        // thread 外层而不是内层loop通常比较有益
        // 选择thread外层的理由是：创建，销毁，许多thread之间的同步化需要成本
        // 较大范围上同步thread会使用更少的同步化
        // 第二次循环或者称为内循环
        for (int i = start; i < end; i++) {
            sinValue = (float)Math.sin((i % 360)*Math.PI/180.0);
            lookupValues[j][i] = sinValue * (float)i / 180.0f;
            lookupValues[j][i] += lookupValues[j-1][i]*(float)j/180.0f;
        }
    }    

    public float[][] getValues() {
        for (int i = 0; i < 360; i++) {
            lookupValues[0][i] = 0;
        }
        // 第一次循环
        for (j = 1; j < 1000; j++) {
            loopProcess();
        }
        return lookupValues;
    }

    public static void main(String args[]) {
        System.out.println("Starting Example 8 (Inner Loop Example)");

        SinTable st = new SinTable();
        float results[][] = st.getValues();

        System.out.println("Results: "+ results[0][0]+ ", "+
                      results[0][1]+ ", "+ results[0][2]+ ", "+ "...");
        System.out.println("Results: "+ results[1][0]+ ", "+
                      results[1][1]+ ", "+ results[1][2]+ ", "+ "...");
        System.out.println("Results: "+ results[2][0]+ ", "+
                      results[2][1]+ ", "+ results[2][2]+ ", "+ "...");
        System.out.println("Done");
    }
}
