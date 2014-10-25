
package javathreads.examples.ch08.example6;

import java.util.*;
import java.util.concurrent.*;

public class Fibonacci {
	// 同步 collection
    private ConcurrentMap<Integer, Integer> values =
              new ConcurrentHashMap<Integer, Integer>();

    public int calculate(int x) {
        if (x < 0)
            throw new IllegalArgumentException("positive numbers only");
        if (x <= 1)
            return x;
        return calculate(x-1) + calculate(x-2);
    }

    public int calculateWithCache(int x) {
        Integer key = new Integer(x);
        // 就是说 没有这个key还是会返回一个null
        Integer result = values.get(key);

        if (result == null) {
            result = new Integer(calculate(x));
            values.putIfAbsent(key, result); // 这时候才算真的有这个key
        }
        // 每次计算得到结果，放入 hashMap缓存中，返回值
        return result.intValue();                
    }

    public int calculateOnlyWithCache(int x) {
        Integer v1 = values.get(new Integer(x-1));
        Integer v2 = values.get(new Integer(x-2));
        Integer key = new Integer(x);
        Integer result = values.get(key);
        
        if (result != null)
            return result.intValue();

        if ((v1 == null) || (v2 == null))
            throw new IllegalArgumentException("values not in cache");
        result = new Integer(v1.intValue() + v2.intValue());

        values.putIfAbsent(key, result);
        return result.intValue();
    }              

    public void calculateRangeInCache(int x, int y) {
        calculateWithCache(x++);
        calculateWithCache(x++);
        while (x <= y) {
            calculateOnlyWithCache(x++);
        }
    }
}
