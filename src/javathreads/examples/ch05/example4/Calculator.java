package javathreads.examples.ch05.example4;

import java.util.*;

public abstract class Calculator {
	// 由于 是 static的局部变量，对象本身是在thread间被共享
	private static ThreadLocal<HashMap> results = new ThreadLocal<HashMap>() {
		// 一般清情况下都会override 这个method，定义特定的 ThreadLocal数据结构
		protected HashMap initialValue() {
			return new HashMap();
		}
	};

	public Object calculate(Object param) {
		// 就是说 results 是共享的，但是 results 里面的 HashMap却非共享，从输出结果来看，输出的HashMap的确
		// 是 ThreadLocal 变量，作用而言，避免了线程间的交流，线程是安全了，但是却没有了线程间的同步
		HashMap hm = results.get();
		Object o = hm.get(param);
		if (o != null)
 			return o;  // 如果这个线程的 HashMap里面有这个东西，直接返回这个东西，
		// 如果没有， 会有 输出流
		o = doLocalCalculate(param);
		// 再写入
		hm.put(param, o);
		return o;
	}

	protected abstract Object doLocalCalculate(Object param);
}
