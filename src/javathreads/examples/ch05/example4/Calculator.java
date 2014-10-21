package javathreads.examples.ch05.example4;

import java.util.*;

public abstract class Calculator {
	// ���� �� static�ľֲ�����������������thread�䱻����
	private static ThreadLocal<HashMap> results = new ThreadLocal<HashMap>() {
		// һ��������¶���override ���method�������ض��� ThreadLocal���ݽṹ
		protected HashMap initialValue() {
			return new HashMap();
		}
	};

	public Object calculate(Object param) {
		// ����˵ results �ǹ���ģ����� results ����� HashMapȴ�ǹ����������������������HashMap��ȷ
		// �� ThreadLocal ���������ö��ԣ��������̼߳�Ľ������߳��ǰ�ȫ�ˣ�����ȴû�����̼߳��ͬ��
		HashMap hm = results.get();
		Object o = hm.get(param);
		if (o != null)
 			return o;  // �������̵߳� HashMap���������������ֱ�ӷ������������
		// ���û�У� ���� �����
		o = doLocalCalculate(param);
		// ��д��
		hm.put(param, o);
		return o;
	}

	protected abstract Object doLocalCalculate(Object param);
}
