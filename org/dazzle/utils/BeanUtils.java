package org.dazzle.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dazzle.common.exception.BaseException;

/** @author hcqt@qq.com */
public class BeanUtils {

	/** @author hcqt@qq.com */
	BeanUtils() { super(); }

	/** @author hcqt@qq.com */
	public static final <T> Map<String, Object> bean2Map(
			T bean) {
		return bean2Map(bean, (String[]) null, false);
	}

	/** @author hcqt@qq.com */
	public static final <T> Map<String, Object> bean2Map(
			T bean, 
			String[] fields) {
		return bean2Map(bean, fields, false);
	}
	
	/** @author hcqt@qq.com */
	public static final <T> Map<String, Object> bean2Map(
			T bean, 
			String fields) {
		return bean2Map(bean, fields, false);
	}

	/** @author hcqt@qq.com */
	public static final <T> Map<String, Object> bean2Map(
			T bean, 
			Boolean needSequence) {
		return bean2Map(bean, (String[]) null, needSequence);
	}

	/** @author hcqt@qq.com */
	public static final <T> Map<String, Object> bean2Map(
			T bean, 
			String fields, 
			Boolean needSequence) {
		String[] fieldsArray = null;
		if(null != fields) {
			fieldsArray = fields.split(",");
			for (int i = 0; i < fieldsArray.length; i++) {
				if(null == fieldsArray[i] || fieldsArray[i].trim().isEmpty()) {
					continue;
				}
				fieldsArray[i] = fieldsArray[i].trim();
			}
		}
		return bean2Map(bean, fieldsArray, needSequence);
	}

	/** @author hcqt@qq.com */
	public static final <T> Map<String, Object> bean2Map(
			T bean, 
			String[] fields, 
			Boolean needSequence) {
		if (null == bean) {
			return null;
		}
		Map<String, Object> ret = null;
		if(null == needSequence) {
			needSequence = false;
		}
		if(needSequence) {
			ret = new LinkedHashMap<String, Object>();
		} else {
			ret = new HashMap<String, Object>();
		}
		Class<?> clazz = bean.getClass();
		List<String> fieldList = null;
		if(null == fields) {
			fieldList = getAllFieldName(clazz);
		} else {
			fieldList = Arrays.asList(fields);
		}
		for (String field : fieldList) {
			if(null == field) {
				continue;
			}
			ret.put(field, getFieldValue(bean, field));
		}
		return ret;
	}

	/** @author hcqt@qq.com */
	public static final <S, T> T bean2Bean(S obj, Class<T> clazz) {
		List<String> fields = getAllFieldName(clazz);
		Map<String, String> fieldMapping = new HashMap<String, String>(); 
		for (String field : fields) {
			fieldMapping.put(field, field);
		}
		return bean2Bean(fieldMapping, obj, clazz);
	}

	/** @author hcqt@qq.com */
	public static final <S, T> T bean2Bean(Map<String, String> fieldMapping, S obj, Class<T> clazz) {
		if(null == fieldMapping) {
			return null;
		}
		try {
			T ret = clazz.newInstance();
			for (Entry<String, String> entry : fieldMapping.entrySet()) {
				Object fieldNewValue = getFieldValue(obj, entry.getKey());
				setFieldValue(ret, entry.getValue(), fieldNewValue);
			}
			return ret;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new BaseException(
					"SYS_COMMON_MODEL2MODEL_8n3lk", 
					ResMsgUtils.resolve("创建目标对象[{0}]时发生不可预料的异常，详情——{1}", ExceptionUtils.out(e)), 
					e, 
					ExceptionUtils.out(e));
		}
	}

	/** @author hcqt@qq.com */
	private static final List<String> getAllFieldName (Class<?> clazz) {
		List<String> ret = new ArrayList<String>();
		if(null == clazz) {
			return ret;
		}
		for (Class<? extends Object> currentClass = clazz; currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
			Field[] fields = currentClass.getDeclaredFields();
			for (Field field : fields) {
				ret.add(field.getName());
			}
		}
		return ret;
	}

	/** @author hcqt@qq.com */
	public static final Object getFieldValue(
			Object instanceObject, 
			String field) {
		if(null == field) {
			return null;
		}
		Method method = null;
		for (Class<? extends Object> currentClass = instanceObject.getClass(); currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
			// 按照javaBean的get方法取值，若拿不到就直接从字段上取值
			try {
				method = currentClass.getDeclaredMethod(new StringBuilder().append("get")
						.append(field.substring(0, 1).toUpperCase())
						.append(field.substring(1)).toString());
				return method.invoke(instanceObject);
			} catch (Throwable e) {
				try {
					Field _field = currentClass.getDeclaredField(field);
					_field.setAccessible(true);
					return _field.get(instanceObject);
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) { }
			}
		}
		return null;
	}

	/** @author hcqt@qq.com */
	public static final void setFieldValue(
			Object instanceObject, 
			String field, 
			Object fieldNewValue) {
		if(null == field) {
			return;
		}
		Method method = null;
		for (Class<? extends Object> currentClass = instanceObject.getClass(); currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
			// 按照javaBean的set方法赋值，若拿不到就直接给字段上赋值
			try {
				method = currentClass.getDeclaredMethod(new StringBuilder().append("set")
						.append(field.substring(0, 1).toUpperCase())
						.append(field.substring(1)).toString());
				method.invoke(instanceObject, fieldNewValue);
			} catch (Throwable e) {
				try {
					Field _field = currentClass.getDeclaredField(field);
					_field.setAccessible(true);
					_field.set(instanceObject, fieldNewValue);
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) { }
			}
		}
	}

	/** @author hcqt@qq.com */
	@SuppressWarnings("rawtypes")
	public static final <T> T convertMap(Class<T> clazz, Map<String, Object> map){
		if(null == clazz || null == map){
			return null;
		}
		T t = null;
		try {
			t = clazz.newInstance();
			Method[] methods= clazz.getMethods();
			for(int i = 0; i < methods.length; i++){
				Method method = methods[i];
				String methodName = method.getName();
				if(methodName.indexOf("set") == 0){
					String key = methodName.substring(methodName.indexOf("set")+3);
					StringBuilder _key = new StringBuilder(key.substring(0 , 1).toLowerCase()).append(key.substring(1));
					
					Object obj = map.get(_key.toString());
					if(null != obj){
						Class[] paramTypes = method.getParameterTypes();
						if(obj.getClass().isAssignableFrom(paramTypes[0])){
							try {
								method.invoke(t, obj);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			return t;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** @author hcqt@qq.com */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final List<Object> convertListMap(Class clazz, List<Map<String, Object>> lists){
		if(null == clazz || null == lists){
			return null;
		}
		List<Object> _lists = new ArrayList<Object>();
		for(Map<String, Object> item : lists){
			Object obj = convertMap(clazz, item);
			if(null != obj){
				_lists.add(obj);
			}
		}
		if(_lists.size() > 0){
			return _lists;
		}
		return null;
	}

}