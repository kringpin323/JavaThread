package javathreads.examples.ch05.example4;

public class CalculatorTest extends Calculator implements Runnable {

    public static void main(String[] args) {
    	
//        int nThreads = Integer.parseInt(args[0]);
    	int nThreads = Integer.parseInt("5");
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(new CalculatorTest());
            t.start();
        }
    }

    public void run() {
        for (int i = 0; i < 30; i++) {
        	// 一共 5 个线程， 每个线程运行 calculate 30次 0,1,2,3,4  各有 6 个，各计算 6 次
            Integer p = new Integer(i % 5);
            calculate(p);
        }
    }

    protected Object doLocalCalculate(Object p) {
        System.out.println("Doing calculation of " + p + " in thread " + Thread.currentThread());
        return p;
    }
}
