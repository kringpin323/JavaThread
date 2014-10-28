package javathreads.examples.ch15;

// LoopHandler 使用静态或者块调度，
// 缺点是 ： 如果有一个线程所需要的时间比其他多，，其他线程在最后将会等待最后的那个线程完完成
// 所有循环管理的逻辑都移动到这里来
public class LoopHandler implements Runnable {
	protected class LoopRange {
		public int start, end;
	}
	protected Thread lookupThreads[];
	protected int startLoop, endLoop, curLoop, numThreads;

	public LoopHandler(int start, int end, int threads) {
		startLoop = curLoop = start;
		endLoop = end;
		numThreads = threads;
		lookupThreads = new Thread[numThreads];
	}

	protected synchronized LoopRange loopGetRange() {
		if (curLoop >= endLoop)
			return null;
		LoopRange ret = new LoopRange();
		ret.start = curLoop;
		curLoop += (endLoop-startLoop)/numThreads+1;
		ret.end = (curLoop<endLoop) ? curLoop : endLoop;
		return ret;
	}

	// 实际的计算内容，该部分不属于循环处理
	// client需要调用的 共享变量在 子类中
	public void loopDoRange(int start, int end) {
	}

	public void loopProcess() {
		for (int i = 0; i < numThreads; i++) {
			lookupThreads[i] = new Thread(this);
			lookupThreads[i].start();
		}
		for (int i = 0; i < numThreads; i++) {
			try {
				lookupThreads[i].join();
				lookupThreads[i] = null; // 这里请注意
			} catch (InterruptedException iex) {}
		}
	}

	public void run() {
		LoopRange str;
		while ((str = loopGetRange()) != null) {
			loopDoRange(str.start, str.end);
		}
	}
}
