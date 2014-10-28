package javathreads.examples.ch15.example4;

import javathreads.examples.ch15.*;

// ���Ǻܶ಻���ף���ô��
public class SinTable extends GuidedLoopHandler {
    private float lookupValues[];
    // ��֮ǰ�汾��ͬ�ĵط��������Ϊ�� �ش����
    private float sinValue;
 
    public SinTable() {
        super(0, 360*100, 100, 12);
        lookupValues = new float [360 * 100];
    }
 
    public void loopDoRange(int start, int end) {
    	// �Ѿ�������ÿ���߳� �Լ��Ĵ���Χ
        float sinValue = 0;
        for (int i = start; i < end; i++) {
            sinValue = (float)Math.sin((i % 360)*Math.PI/180.0);
            lookupValues[i] = sinValue * (float)i / 180.0f;
        }
        if (end == endLoop)
        	// ���̵߳� sinValue �� �߳� �� stack ��
        	// ����ͬ��������ǣ�ֻ��һ��thread���� end == endLoop������������� race condition����
        	// �����race condition �Ķ��壺����̲߳������ʹ������������������ݲ�һ��
            this.sinValue = sinValue;
    }
 
    public float[] getValues() {
        loopProcess();
        // ��ô���sinValue��ѭ��˽�б���
        // Ҳ�ǻش�������������Ҫ�洢��"����"�ı���ֵ
        // Ψһ������� ���class�� �̳��� Runnable��class
        // ��Ϊ�߳�ʹ�õ�ʱ��sinValue��ʵ��ֵ �洢�� thread stack ��
        // ��˺������̻߳�������
        // lookupValues[0] �� ����ģ�Ϊʲô��
        //������һ��ֵ��������������ʲô�ô���
        // û������
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
