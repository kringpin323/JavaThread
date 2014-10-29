package javathreads.examples.ch15;

import java.util.concurrent.*;

// �����־��ܿ������������̳߳ص� ѭ�����Ƶ���
public class PoolLoopHandler implements Runnable {
	
	// ��̬�ڲ������޷����õ�����Χ��ķǾ�̬��Ա
	// �����˵�����б���̬�ȣ���������Ƕ�����
	// ��ˣ���̬�ڲ���ֻ�ܷ�������Χ��ľ�̬��Ա������֮����Ǿ�̬�ڲ���û���κ�����
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

	// ���е��̹߳���12���̹߳���������ݽṹ
	static protected ThreadPoolExecutor threadpool;
	static protected int maxThreads = 1;
	// 12���̶߳����Լ��� ���ĸ�����
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
		// �̳߳ؽ���Ҫʹ�õ� 12 ���߳�
		getThreadPool(numThreads);
		// ����ÿһ�ε� ��Χ
		setRange(start, end);
	}

	// �����µ�ѭ����Χ
	public synchronized void setRange(int start, int end) {
		startLoop = start;
		endLoop = end;
		reset();
	}

	// ֮ǰ������У�ѭ�������������ܱ���ظ�ѭ�������
	// Ҳ���ܸı�ѭ���ķ�Χ
	// ����ѭ�������ص�ѭ���Ŀ�ʼ
	public synchronized void reset() {
		curLoop = startLoop;
	}

	// ��������������̹߳���Ĳ���
	protected synchronized LoopRange loopGetRange() {
		// curLoop,endLoop���̶߳��е�
		if (curLoop >= endLoop)
			return null;
		// 
		LoopRange ret = new LoopRange();
		
		ret.start = curLoop;
		curLoop += (endLoop-startLoop)/numThreads+1;
		ret.end = (curLoop<endLoop)?curLoop:endLoop;
		// ��һ�� ��̬�ڲ��� ��ʵ���ڼ�¼���߳�Ӧ�ô�������ݷ�Χ
		return ret;
	}

	public void loopDoRange(int start, int end) {
	}

	public void loopProcess() {
		reset();
		// 12��threadִ��
		FutureTask t[] = new FutureTask[numThreads];
		for (int i = 0; i < numThreads; i++) {
			// ����ֵ�� object ��null��������ô���أ�
			// ���ǲ��÷���
			t[i] = new FutureTask(this, null);
			// ִ�У�����
			// ִ�� run()
			threadpool.execute(t[i]);
		}
		for (int i = 0; i < numThreads; i++) {
			try {
				// �ȵ�������ɲŽ�����Ч����ʵ�� join���
				t[i].get();
			} catch (ExecutionException ee) {
			} catch (InterruptedException ie) {
			}
		}
	}

	public void run() {
		// ������ƾ�̬�ڲ��࣬����һ�����ã������̳߳�����ָ��ͬһ������ PermSpace
		LoopRange str;
		while ((str = loopGetRange()) != null) {
			// ����� �� ���ڷ��ص� str ������ָ�� �� ���������ڵ����̣߳�������� race condition
			loopDoRange(str.start, str.end);
		}
	}
}
