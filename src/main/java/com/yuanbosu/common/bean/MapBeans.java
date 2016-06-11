package com.yuanbosu.common.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapBeans {

	public static final <T> T toBean(Class<T> type, Map<String, Object> map) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		T obj = type.newInstance();

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);

				descriptor.getWriteMethod().invoke(obj, new Object[] { value });
			}
		}

		return obj;
	}

	public static final Map<String, Object> toMap(Object bean, boolean nullAware) throws Exception {
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();

		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (propertyName.equals("class"))
				continue;
			Method readMethod = descriptor.getReadMethod();
			Object value = readMethod.invoke(bean, new Object[0]);
			if ((!nullAware) || (value != null)) {
				returnMap.put(propertyName, value);
			}
		}

		return returnMap;
	}

	public static final Map<String, String> describe(Object bean, boolean nullAware) throws Exception {
		Map<String, String> returnMap = new LinkedHashMap<String, String>();

		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (propertyName.equals("class"))
				continue;
			Method readMethod = descriptor.getReadMethod();
			Object value = readMethod.invoke(bean, new Object[0]);
			if ((nullAware) && (value == null))
				continue;
			returnMap.put(propertyName, value == null ? "" : value.toString());
		}

		return returnMap;
	}
}
