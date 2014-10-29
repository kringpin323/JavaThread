package javathreads.examples.ch15;

import java.util.concurrent.*;

// 看名字就能看出来，加入线程池的 循环控制调度
public class PoolLoopHandler implements Runnable {
	
	// 静态内部类中无法引用到其外围类的非静态成员
	// 对这个说法持有保留态度，先用这个角度想想
	// 因此，静态内部类只能访问其外围类的静态成员，除此之外与非静态内部类没有任何区别。
	protected static class LoopRange {
		public int start, end;
	}

	protected static class PoolHandlerFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	}

	// 所有的线程共享，12个线程共享这个数据结构
	static protected ThreadPoolExecutor threadpool;
	static protected int maxThreads = 1;
	// 12个线程都有自己的 这四个变量
	protected int startLoop, endLoop, curLoop, numThreads;

	synchronized static void getThreadPool(int threads) {
		if (threadpool == null)
			threadpool = new ThreadPoolExecutor(
					1, 1,
					50000L, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>(),
					new PoolHandlerFactory());
		if (threads > maxThreads) {
			maxThreads = threads;
			threadpool.setMaximumPoolSize(maxThreads);
			threadpool.setCorePoolSize(maxThreads);
		}
	}

	public PoolLoopHandler(int start, int end, int threads) {
		numThreads = threads;
		// 线程池建立要使用的 12 个线程
		getThreadPool(numThreads);
		// 设置每一段的 范围
		setRange(start, end);
	}

	// 分配新的循环范围
	public synchronized void setRange(int start, int end) {
		startLoop = start;
		endLoop = end;
		reset();
	}

	// 之前的设计中，循环索引，决不能被设回给循环的起点
	// 也不能改变循环的范围
	// 重置循环索引回到循环的开始
	public synchronized void reset() {
		curLoop = startLoop;
	}

	// 看来这个是所有线程共享的部分
	protected synchronized LoopRange loopGetRange() {
		// curLoop,endLoop是线程独有的
		if (curLoop >= endLoop)
			return null;
		// 
		LoopRange ret = new LoopRange();
		
		ret.start = curLoop;
		curLoop += (endLoop-startLoop)/numThreads+1;
		ret.end = (curLoop<endLoop)?curLoop:endLoop;
		// 用一个 静态内部类 的实例在记录本线程应该处理的数据范围
		return ret;
	}

	public void loopDoRange(int start, int end) {
	}

	public void loopProcess() {
		reset();
		// 12个thread执行
		FutureTask t[] = new FutureTask[numThreads];
		for (int i = 0; i < numThreads; i++) {
			// 返回值的 object 是null，请问怎么返回？
			// 答案是不用返回
			t[i] = new FutureTask(this, null);
			// 执行，启动
			// 执行 run()
			threadpool.execute(t[i]);
		}
		for (int i = 0; i < numThreads; i++) {
			try {
				// 等到计算完成才结束，效果其实和 join差不多
				t[i].get();
			} catch (ExecutionException ee) {
			} catch (InterruptedException ie) {
			}
		}
	}

	public void run() {
		// 这个类似静态内部类，定义一个引用，所有线程持引用指向同一个，在 PermSpace
		LoopRange str;
		while ((str = loopGetRange()) != null) {
			// 这里的 话 由于返回的 str 的引用指向 的 对象都是属于单个线程，不会存在 race condition
			loopDoRange(str.start, str.end);
		}
	}
}
