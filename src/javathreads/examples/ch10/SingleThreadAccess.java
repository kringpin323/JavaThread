package javathreads.examples.ch10;

import java.util.concurrent.*;
import java.io.*;

public class SingleThreadAccess {

    private ThreadPoolExecutor tpe;
    
    // 单线程入口 ，由 SingleThreadAccess 对象执行的task可以调用不具有thread安全性的class
    // 这还叫多线程？。。。好吧，不是单例，是单线程。。。
    public SingleThreadAccess() {
    	// 只有一个 thread，而且thread的消失时间是 50000 秒
        tpe = new ThreadPoolExecutor(
        	1, 1, 50000L, TimeUnit.SECONDS,
        	new LinkedBlockingQueue<Runnable>());
    }

    // 异步执行
    public void invokeLater(Runnable r) {
        tpe.execute(r);
    }

    // 同步执行，为什么说同步，因为 task.get()在未得到 他要的答案之前，会阻塞，当然这里是null，所以当Runnable r未结束之前都会阻塞在task.get()中
    // 上面的不同是交给另一个线程执行就返回。
    public void invokeAndWait(Runnable r) throws InterruptedException, ExecutionException {
        FutureTask task = new FutureTask(r, null);
        tpe.execute(task);
        task.get();
    }

    public void shutdown() {
        tpe.shutdown();
    }
}
