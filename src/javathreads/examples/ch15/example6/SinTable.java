package javathreads.examples.ch15.example6;

import javathreads.examples.ch15.*;

public class SinTable extends GuidedLoopHandler {
    // ˽�У�ָ���
	private float lookupValues[];
    // ��Լ������������˼��ֻ��Ҫ�� = ����߳��֣�����Ҫ���ұ߳��ֵı���
    public float sumValue;

    // ��ʼ��
    public SinTable() {
        super(0, 360*100, 100, 12);
        // 360* 100 ���Ŀռ�����
        lookupValues = new float [360 * 100];
    }

    public void loopDoRange(int start, int end) {
        float sinValue = 0.0f;
        // ����thread stack frame �洢����
        float sumValue = 0.0f;
        for (int i = start; i < end; i++) {
            sinValue = (float)Math.sin((i % 360)*Math.PI/180.0);
            lookupValues[i] = sinValue * (float)i / 180.0f;
            // ѭ����ֵ ��Լ�����أ���� sumValue ���� thread �� stack �� ����һ�� frame ��
            sumValue += lookupValues[i];
        }
        // ÿ����Χ��һ��ͬ����������ÿ��ѭ����һ��ͬ������ͨ��ʹ�� ˽��ѭ������������ͬ����ʹ��
        synchronized (this) {
        	// ���еļ�Լ�������ǻش����
        	// �����ļ�Լ���������sumValue��public�ģ����������߳̿��Է��ʣ���ͬ������	        	
            this.sumValue += sumValue;
        }
    }

    public float[] getValues() {
        loopProcess();
        // ��������ˣ�Ȼ�� ���е��̶߳��Ǳ��̣߳���SinTable
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
