package javathreads.examples.ch15.example1;

public class SinTable {
	// 共享变量
    private float lookupValues[] = null;

    // 非常消耗CPU资源的计算，作为 范例
    public synchronized float[] getValues() {
        if (lookupValues == null) {
            lookupValues = new float [360 * 100];
            for (int i = 0; i < (360*100); i++) {
            	// 循环私有变量
                float sinValue = (float)Math.sin(
                                    (i % 360)*Math.PI/180.0);
                lookupValues[i] = sinValue * (float)i / 180.0f;
            }    
        }
        return lookupValues;
    }

    public static void main(String args[]) {
        System.out.println("Starting Example 1 (Control Example)");

        // 单线程处理模式，本来是不用 synchronized 但是为了后文方便
        SinTable st = new SinTable();
        float results[] = st.getValues();

        // 意思是输出整个数组
        System.out.println("Results: "+ results[0]+ ", "+
                      results[1]+ ", "+ results[2]+ ", "+ "...");
        System.out.println("Done");
    }
}
