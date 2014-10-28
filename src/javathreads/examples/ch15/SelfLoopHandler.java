package javathreads.examples.ch15;

// ���ҵ���
// 
public class SelfLoopHandler extends PoolLoopHandler {
   // �趨һ��groupSize
	protected int groupSize;

    public SelfLoopHandler(int start, int end, int size, int threads) {
        super(start, end, threads);
        groupSize = size;
    }

    protected synchronized LoopRange loopGetRange() {
        if (curLoop >= endLoop)
            return null;
        LoopRange ret = new LoopRange();
        ret.start = curLoop;
        // ÿ�� ���� һ����Ŀ
        curLoop += groupSize; // ÿ����Χ���٣�ͨ���̳߳����� �߳�����Ϊ 12
        ret.end = (curLoop<endLoop)?curLoop:endLoop;
        return ret;
    }
}
