package javathreads.examples.ch10.example1;

import java.util.concurrent.*;
import javathreads.examples.ch10.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
//        int nTasks = Integer.parseInt(args[0]);
    	int nTasks = 10;
//        long n = Long.parseLong(args[1]);
    	long n = 40L;
//        int tpSize = Integer.parseInt(args[2]);
    	int tpSize = 4; // 这个参数设置 BlockingQueue的 容量（感觉），设置threadPool同时拥有 4 个线程
//    	int maxSize = 10;
    	// coreSize最小值，maxsize最大值
    	// getPoolSize() 返回其中一个
    	// 第三，四个参数的含义是在 5 秒内 pool里面比最小值coreSize多的thread会被释放
    	// 线程池一般都会将thread的数目保持在 coreSize以上
    	// 如果时限为 0 ，不管threadPool大小， 空闲时候直接离开
    	// 接下来就是 当 thread 已经全部满了，或者是 .shutdown后
    	// 的 ThreadPoolExecutor的拒绝策略
    	// 四种拒绝策略请看 Java 线程 Page 197
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
            tpSize, tpSize, 50000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
        
        Task[] tasks = new Task[nTasks];
        for (int i = 0; i < nTasks; i++) {
            tasks[i] = new Task(n, "Task " + i);
            tpe.execute(tasks[i]);
        }
//        System.out.println(tpe.getPoolSize());
        tpe.shutdown(); // 关掉 ThreadPoolExecutor
        // 不是立即关闭，但是不再接受其他任何的 task，里面的task完成后就关闭
//        System.out.println("it is end! ");
    }
}
