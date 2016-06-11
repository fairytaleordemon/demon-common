package com.yuanbosu.common.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

public class Beans {

	private static ConcurrentMap<String, BeanCopier> beanCopiers = new ConcurrentHashMap<String, BeanCopier>();
	private static ConcurrentMap<String, NullAwareBeanCopier> nullBeanCopiers = new ConcurrentHashMap<String, NullAwareBeanCopier>();

	public static void copy(Object from, Object to) throws Exception {
		BeanCopier copier = _createCopier(from, to, false);
		copier.copy(from, to, null);
	}

	@SuppressWarnings("unchecked")
	public static <T> T copyBean(Object from, Class<T> toClass) throws Exception {
		Object to = toClass.newInstance();
		copy(from, to);
		return (T) to;
	}

	@SuppressWarnings("unchecked")
	public static <T1, T2> List<T2> copyCollection(Collection<T1> fromCollection, Class<T2> toClass) throws Exception {
		List<T2> toList = new ArrayList<T2>();
		for (Iterator<T1> localIterator = fromCollection.iterator(); localIterator.hasNext();) {
			Object from = localIterator.next();
			Object to = toClass.newInstance();
			copy(from, to);
			toList.add((T2) to);
		}
		return toList;
	}

	public static final void copyNullAware(Object from, Object to) throws Exception {
		NullAwareBeanCopier copier = _createNullAwareCopier(from, to);
		copier.copy(from, to);
	}

	@SuppressWarnings("unchecked")
	public static <T> T copyBeanNullAware(Object from, Class<T> toClass) throws Exception {
		Object to = toClass.newInstance();
		copyNullAware(from, to);
		return (T) to;
	}

	@SuppressWarnings("unchecked")
	public static <T1, T2> List<T2> copyCollectionNullAware(Collection<T1> fromCollection, Class<T2> toClass)
			throws Exception {
		List<T2> toList = new ArrayList<T2>();
		for (Iterator<T1> localIterator = fromCollection.iterator(); localIterator.hasNext();) {
			Object from = localIterator.next();
			Object to = toClass.newInstance();
			copy(from, to);
			toList.add((T2) to);
		}
		return toList;
	}

	private static BeanCopier _createCopier(Object from, Object to, boolean useConverter) throws Exception {
		String fromName = from.getClass().getSimpleName();
		String toName = to.getClass().getSimpleName();
		String converterFlag = String.valueOf(useConverter);
		String key = fromName + "->" + toName + "|" + converterFlag;

		if (beanCopiers.containsKey(key)) {
			return (BeanCopier) beanCopiers.get(key);
		}
		beanCopiers.putIfAbsent(key, BeanCopier.create(from.getClass(), to.getClass(), useConverter));
		return (BeanCopier) beanCopiers.get(key);
	}

	private static NullAwareBeanCopier _createNullAwareCopier(Object from, Object to) throws Exception {
		Class<? extends Object> fromClass = from.getClass();
		Class<? extends Object> toClass = to.getClass();
		String key = fromClass.getSimpleName() + "->" + toClass.getSimpleName();

		if (nullBeanCopiers.containsKey(key))
			return (NullAwareBeanCopier) nullBeanCopiers.get(key);

		nullBeanCopiers.putIfAbsent(key, new NullAwareBeanCopier(fromClass, toClass));
		return (NullAwareBeanCopier) nullBeanCopiers.get(key);
	}

	public static void main(String[] args) throws Exception {
		CopyTest test1 = new CopyTest();
		CopyTest test2 = new CopyTest();

		test1.setName("fff");

		test1.setTimes(new Date());

		test2.setName("bbb");
		test2.setNumber(Integer.valueOf(2222));

		long time7 = System.currentTimeMillis();

		for (int i = 0; i < 1000; i++)
			;
		long time8 = System.currentTimeMillis();

		System.out.println(time8 - time7);
		System.out.println(test2.getName() + " - " + test2.getNumber() + " - " + test2.getTimes());

		System.out.println(MapBeans.toMap(test2, false));
	}

	class simpleConverter implements Converter {
		simpleConverter() {
		}

		@SuppressWarnings("rawtypes")
		public Object convert(Object value, Class target, Object context) {
			return null;
		}
	}
}
