package javathreads.examples.ch15.example2;

public class SinTable implements Runnable {
    private class SinTableRange {
        public int start, end;
    }

    // 存储 最后的值的地方
    private float lookupValues[];
    // 运行线程 默认 12 个
    private Thread lookupThreads[];
    private int startLoop, endLoop, curLoop, numThreads;
 
    public SinTable() {
    	// 构造器初始化 12个 loopupThreads 共享 这个 lookupValues
        lookupValues = new float [360 * 100];
        lookupThreads = new Thread[12];
        // curLoop
        startLoop = curLoop = 0;
        // loopupValues 大小
        endLoop = (360 * 100);
        numThreads = 12;
    }	
 
    // 将计算划分12个范围，分给所有的thread，
    private synchronized SinTableRange loopGetRange() {
    	//
        if (curLoop >= endLoop)
            return null;
        // 区别在于 ， curLoop endLoop startLoop 属于 main 中 的 SinTable
        // 而这个 ret 对象属于 12 线程自身
        // synchronized 同步是 用来同步 curLoop endLoop 的race condition 情况
        SinTableRange ret = new SinTableRange();
        ret.start = curLoop;
        // 分割还没有 分开的 范围
        curLoop += (endLoop-startLoop)/numThreads+1;
        ret.end = (curLoop<endLoop)?curLoop:endLoop;
        return ret;
    }
 
    // 具体计算， start ，end
    // 每个 thread 上的stack 都用 i，sinValue
    // 每个 thread 上 的stack
    // 一开始很奇怪 为什么 使用了 loopupValues这样的共享变量但是却不用同步
    // 因为 每个 thread 都有 自己 的 范围， start ，end 之间没有交集
    // 所以其实 method 里面处理的 lookupvalues 是同一个，但是却不同线程处理不同范围，这样就在并发的情况下避免了同步
    private void loopDoRange(int start, int end) {
        for (int i = start; i < end; i += 1) {
            float sinValue = (float)Math.sin((i % 360)*Math.PI/180.0);
            lookupValues[i] = sinValue * (float)i / 180.0f;
        }
    }
 
    public void run() {
        SinTableRange str;
        // 属于 12 个运行线程 的 stack
        while ((str = loopGetRange()) != null) {
        	// 现在每个线程拥有 自己的 SinTableRange 对象
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
            	// 所有并行运行完毕再返回
                lookupThreads[i].join();
            } catch (InterruptedException iex) {}
        }
        return lookupValues;
    }

    // main method 的客户端调用没变
    public static void main(String args[]) {
        System.out.println("Starting Example 2 (Threaded Example)");

        SinTable st = new SinTable();
        float results[] = st.getValues();

        System.out.println("Results: "+ results[0]+ ", "+
                      results[1]+ ", "+ results[2]+ ", "+ "...");
        System.out.println("Done");
    }
}
