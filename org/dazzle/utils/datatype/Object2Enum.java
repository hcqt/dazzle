package org.dazzle.utils.datatype;

import java.lang.reflect.Method;

import org.dazzle.common.exception.BaseException;
import org.dazzle.utils.DataTypeUtils;
import org.dazzle.utils.ResMsgUtils;

/** @author hcqt@qq.com */
public class Object2Enum {

	/** @author hcqt@qq.com */
	public static final <T> T convert(final Class<T> clazz, final Object targetObject) {
		T[] _elements = clazz.getEnumConstants();
		if(targetObject instanceof String) {
			for (T _enumElement : _elements) {
				try {
					Method method = _enumElement.getClass().getMethod("name");
					Object enumName = null;
					enumName = method.invoke(_enumElement);
					if(null == enumName) {
						continue;
					}
					if(enumName.toString().equals(targetObject.toString())) {
						return _enumElement;
					}
				} catch (Exception e) { }
			}
		} 
		// 尝试输入值是否可以转换为整型，如果可以，那么以枚举底层整型下标进行转换
		try {
			Integer index = DataTypeUtils.convert(Integer.class, targetObject);
			for (T _enumElement : _elements) {
				try {
					Method method = _enumElement.getClass().getMethod("ordinal");
					Object enumOrdinal = null;
					enumOrdinal = method.invoke(_enumElement);
					if(null == enumOrdinal) {
						continue;
					}
					if(enumOrdinal.toString().equals(index.toString())) {
						return _enumElement;
					}
				} catch (Exception e) { }
			}
		} catch (BaseException e) { }
		throw new BaseException(
				"SER_COMMON_META_DATA_54hdQ", 
				ResMsgUtils.resolve("无法把数据类型为“{0}”的数据“{1}”转换到枚举类型“{2}”", targetObject.getClass().getName(), targetObject, clazz), 
				targetObject.getClass().getName(), 
				targetObject, 
				clazz.getName());
	}

}
