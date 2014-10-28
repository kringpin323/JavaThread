package javathreads.examples.ch15;

// 自我调度
// 
public class SelfLoopHandler extends PoolLoopHandler {
   // 设定一个groupSize
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
        // 每次 分组 一定数目
        curLoop += groupSize; // 每个范围更少，通过线程池限制 线程数量为 12
        ret.end = (curLoop<endLoop)?curLoop:endLoop;
        return ret;
    }
}
