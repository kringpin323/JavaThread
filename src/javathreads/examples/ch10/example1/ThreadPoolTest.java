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
    	int tpSize = 4; // ����������� BlockingQueue�� �������о���������threadPoolͬʱӵ�� 4 ���߳�
//    	int maxSize = 10;
    	// coreSize��Сֵ��maxsize���ֵ
    	// getPoolSize() ��������һ��
    	// �������ĸ������ĺ������� 5 ���� pool�������СֵcoreSize���thread�ᱻ�ͷ�
    	// �̳߳�һ�㶼�Ὣthread����Ŀ������ coreSize����
    	// ���ʱ��Ϊ 0 ������threadPool��С�� ����ʱ��ֱ���뿪
    	// ���������� �� thread �Ѿ�ȫ�����ˣ������� .shutdown��
    	// �� ThreadPoolExecutor�ľܾ�����
    	// ���־ܾ������뿴 Java �߳� Page 197
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
            tpSize, tpSize, 50000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
        
        Task[] tasks = new Task[nTasks];
        for (int i = 0; i < nTasks; i++) {
            tasks[i] = new Task(n, "Task " + i);
            tpe.execute(tasks[i]);
        }
//        System.out.println(tpe.getPoolSize());
        tpe.shutdown(); // �ص� ThreadPoolExecutor
        // ���������رգ����ǲ��ٽ��������κε� task�������task��ɺ�͹ر�
//        System.out.println("it is end! ");
    }
}
