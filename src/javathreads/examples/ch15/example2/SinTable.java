package javathreads.examples.ch15.example2;

public class SinTable implements Runnable {
    private class SinTableRange {
        public int start, end;
    }

    // �洢 ����ֵ�ĵط�
    private float lookupValues[];
    // �����߳� Ĭ�� 12 ��
    private Thread lookupThreads[];
    private int startLoop, endLoop, curLoop, numThreads;
 
    public SinTable() {
    	// ��������ʼ�� 12�� loopupThreads ���� ��� lookupValues
        lookupValues = new float [360 * 100];
        lookupThreads = new Thread[12];
        // curLoop
        startLoop = curLoop = 0;
        // loopupValues ��С
        endLoop = (360 * 100);
        numThreads = 12;
    }	
 
    // �����㻮��12����Χ���ָ����е�thread��
    private synchronized SinTableRange loopGetRange() {
    	//
        if (curLoop >= endLoop)
            return null;
        // �������� �� curLoop endLoop startLoop ���� main �� �� SinTable
        // ����� ret �������� 12 �߳�����
        // synchronized ͬ���� ����ͬ�� curLoop endLoop ��race condition ���
        SinTableRange ret = new SinTableRange();
        ret.start = curLoop;
        // �ָû�� �ֿ��� ��Χ
        curLoop += (endLoop-startLoop)/numThreads+1;
        ret.end = (curLoop<endLoop)?curLoop:endLoop;
        return ret;
    }
 
    // ������㣬 start ��end
    // ÿ�� thread �ϵ�stack ���� i��sinValue
    // ÿ�� thread �� ��stack
    // һ��ʼ����� Ϊʲô ʹ���� loopupValues�����Ĺ����������ȴ����ͬ��
    // ��Ϊ ÿ�� thread ���� �Լ� �� ��Χ�� start ��end ֮��û�н���
    // ������ʵ method ���洦��� lookupvalues ��ͬһ��������ȴ��ͬ�̴߳���ͬ��Χ���������ڲ���������±�����ͬ��
    private void loopDoRange(int start, int end) {
        for (int i = start; i < end; i += 1) {
            float sinValue = (float)Math.sin((i % 360)*Math.PI/180.0);
            lookupValues[i] = sinValue * (float)i / 180.0f;
        }
    }
 
    public void run() {
        SinTableRange str;
        // ���� 12 �������߳� �� stack
        while ((str = loopGetRange()) != null) {
        	// ����ÿ���߳�ӵ�� �Լ��� SinTableRange ����
            loopDoRange(str.start, str.end);
        }
    }
 
    public float[] getValues() {
        for (int i = 0; i < numThreads; i++) {
            lookupThreads[i] = new Thread(this);
            lookupThreads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            try {
            	// ���в�����������ٷ���
                lookupThreads[i].join();
            } catch (InterruptedException iex) {}
        }
        return lookupValues;
    }

    // main method �Ŀͻ��˵���û��
    public static void main(String args[]) {
        System.out.println("Starting Example 2 (Threaded Example)");

        SinTable st = new SinTable();
        float results[] = st.getValues();

        System.out.println("Results: "+ results[0]+ ", "+
                      results[1]+ ", "+ results[2]+ ", "+ "...");
        System.out.println("Done");
    }
}
