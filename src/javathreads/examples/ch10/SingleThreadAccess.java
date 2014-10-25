package javathreads.examples.ch10;

import java.util.concurrent.*;
import java.io.*;

public class SingleThreadAccess {

    private ThreadPoolExecutor tpe;
    
    // ���߳���� ���� SingleThreadAccess ����ִ�е�task���Ե��ò�����thread��ȫ�Ե�class
    // �⻹�ж��̣߳��������ðɣ����ǵ������ǵ��̡߳�����
    public SingleThreadAccess() {
    	// ֻ��һ�� thread������thread����ʧʱ���� 50000 ��
        tpe = new ThreadPoolExecutor(
        	1, 1, 50000L, TimeUnit.SECONDS,
        	new LinkedBlockingQueue<Runnable>());
    }

    // �첽ִ��
    public void invokeLater(Runnable r) {
        tpe.execute(r);
    }

    // ͬ��ִ�У�Ϊʲô˵ͬ������Ϊ task.get()��δ�õ� ��Ҫ�Ĵ�֮ǰ������������Ȼ������null�����Ե�Runnable rδ����֮ǰ����������task.get()��
    // ����Ĳ�ͬ�ǽ�����һ���߳�ִ�оͷ��ء�
    public void invokeAndWait(Runnable r) throws InterruptedException, ExecutionException {
        FutureTask task = new FutureTask(r, null);
        tpe.execute(task);
        task.get();
    }

    public void shutdown() {
        tpe.shutdown();
    }
}
