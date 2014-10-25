package javathreads.examples.ch10.example2;

import javathreads.examples.ch10.*;

public class SingleThreadTest {

	// thread pool 的好处： 简化程序逻辑，加强吞吐量
	public static void main(String[] args) {
//		int nTasks = Integer.parseInt(args[0]);
		int nTasks = 4;
//		int fib = Integer.parseInt(args[1]);
		int fib = 40;
		SingleThreadAccess sta = new SingleThreadAccess();
		for (int i = 0; i < nTasks; i++)
			// 异步，立即返回，但是由于是单线程，所以其实串行化了，由头到尾只有两个线程
			// 第一个是 main 中的主线程
			// 第二个是 SingleThreadAccess 线程池里面的 单线程
			sta.invokeLater(new Task(fib, "Task " + i));
		sta.shutdown();
	}
}
