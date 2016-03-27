package org.dazzle.utils.datatype;

import java.lang.reflect.Method;

import org.dazzle.common.exception.BaseException;
import org.dazzle.utils.ResMsgUtils;

/** @author hcqt@qq.com */
public class Object2Enum {

	/** @author hcqt@qq.com */
	public static final <T> T convert(final Class<T> clazz, final Object targetObject) {
		T[] _elements = clazz.getEnumConstants();
		for (T _enumElement : _elements) {
			Method method = null;
			try {
				method = _enumElement.getClass().getMethod("name");
				Object enumName = null;
				enumName = method.invoke(_enumElement);
				if(null != enumName) {
					if(enumName.toString().equals(targetObject.toString())) {
						return _enumElement;
					}
				}
			} catch (Exception e) { }
		}
		throw new BaseException(
				"SER_COMMON_META_DATA_54hdQ", 
				ResMsgUtils.resolve("无法把[数据类型{0}:数据{1}]转换到[枚举{2}]", targetObject.getClass().getName(), targetObject, clazz), 
				targetObject.getClass().getName(), 
				targetObject, 
				clazz.getName());
	}

}
