package javathreads.examples.ch10.example2;

import javathreads.examples.ch10.*;

public class SingleThreadTest {

	// thread pool �ĺô��� �򻯳����߼�����ǿ������
	public static void main(String[] args) {
//		int nTasks = Integer.parseInt(args[0]);
		int nTasks = 4;
//		int fib = Integer.parseInt(args[1]);
		int fib = 40;
		SingleThreadAccess sta = new SingleThreadAccess();
		for (int i = 0; i < nTasks; i++)
			// �첽���������أ����������ǵ��̣߳�������ʵ���л��ˣ���ͷ��βֻ�������߳�
			// ��һ���� main �е����߳�
			// �ڶ����� SingleThreadAccess �̳߳������ ���߳�
			sta.invokeLater(new Task(fib, "Task " + i));
		sta.shutdown();
	}
}
