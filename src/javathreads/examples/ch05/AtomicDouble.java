package javathreads.examples.ch05;

import java.lang.*;
import java.util.concurrent.atomic.*;

/**
 * thread 间 不会共享保存在寄存器的数据
 * 使用volatile关键字能够确保变量不会保持在寄存器中
 * 这能够保证变量是真正地共享于thread之间
 * 
 * 对于 synchronized 
 * 当 JVM 进入 synchronized method 或块的时候
 * 它必须重新加载本来已经cached到自有寄存器上的数据
 * 在 vm 离开 同步块 之前，必须把自有寄存器存入主存储器中
 * */
public class AtomicDouble extends Number {
    private AtomicReference<Double> value;

    public AtomicDouble() {
        this(0.0);
    }

    public AtomicDouble(double initVal) {
        value = new AtomicReference<Double>(new Double(initVal));
    }

    public double get() {
        return value.get().doubleValue();
    }

    public void set(double newVal) {
        value.set(new Double(newVal));
    }

    public boolean compareAndSet(double expect, double update) {
        Double origVal, newVal;

        newVal = new Double(update);
        while (true) {
            origVal = value.get();

            if (Double.compare(origVal.doubleValue(), expect) == 0) {
                if (value.compareAndSet(origVal, newVal))
                    return true; 
            } else {
                return false;
            }
        }
    }

    public boolean weakCompareAndSet(double expect, double update) {
        return compareAndSet(expect, update);
    }

    public double getAndSet(double setVal) {
        Double origVal, newVal;

        newVal = new Double(setVal);
        while (true) {
            origVal = value.get();

            if (value.compareAndSet(origVal, newVal))
                return origVal.doubleValue();
        }
    }

    public double getAndAdd(double delta) {
        Double origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new Double(origVal.doubleValue() + delta);
            if (value.compareAndSet(origVal, newVal))
                return origVal.doubleValue();
        }
    }

    public double addAndGet(double delta) {
        Double origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new Double(origVal.doubleValue() + delta);
            if (value.compareAndSet(origVal, newVal))
                return newVal.doubleValue();
        }
    }

    public double getAndIncrement() {
        return getAndAdd((double) 1.0);
    }

    public double getAndDecrement() {
        return getAndAdd((double) -1.0);
    }

    public double incrementAndGet() {
        return addAndGet((double) 1.0);
    }

    public double decrementAndGet() {
        return addAndGet((double) -1.0);
    }

    public double getAndMultiply(double multiple) {
        Double origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new Double(origVal.doubleValue() * multiple);
            if (value.compareAndSet(origVal, newVal))
                return origVal.doubleValue();
        }
    }

    public double multiplyAndGet(double multiple) {
        Double origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new Double(origVal.doubleValue() * multiple);
            if (value.compareAndSet(origVal, newVal))
                return newVal.doubleValue();
        }
    }

    public int intValue() {
        return DoubleValue().intValue();
    }

    public long longValue() {
        return DoubleValue().longValue();
    }

    public float floatValue() {
        return DoubleValue().floatValue();
    }

    public double doubleValue() {
        return DoubleValue().doubleValue();
    }

    public byte byteValue() {
        return (byte)intValue();
    }

    public short shortValue() {
        return (short)intValue();
    }

    public Double DoubleValue() {
        return value.get();
    }

    public boolean isNaN() {
        return DoubleValue().isNaN();
    }

    public boolean isInfinite() {
        return DoubleValue().isInfinite();
    }

    public String toString() {
        return DoubleValue().toString();
    }

    public int hashCode() {
        return DoubleValue().hashCode();
    }

    public boolean equals(Object obj) {
        Double origVal = DoubleValue();

        return ((obj instanceof Double)
                && (origVal.equals((Double)obj)))
               ||
               ((obj instanceof AtomicDouble)
                && 
(origVal.equals(((AtomicDouble)obj).DoubleValue())));
    }

    public int compareTo(Double aValue) {
        return Double.compare(doubleValue(), aValue.doubleValue());
    }

    public int compareTo(AtomicDouble aValue) {
        return Double.compare(doubleValue(), aValue.doubleValue());
    }
}
