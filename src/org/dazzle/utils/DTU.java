package org.dazzle.utils;

/** @author hcqt@qq.com */
public final class DTU extends DataTypeUtils {

	/** @author hcqt@qq.com */
	public static final <T> T cvt(Class<T> clazz, final Object targetObject) {
		return DataTypeUtils.convert(clazz, targetObject);
	}

	/** @author hcqt@qq.com */
	public static final boolean isNum(Object obj) {
		return DataTypeUtils.isNumber(obj);
	}

	/** @author hcqt@qq.com */
	public static final boolean isNum(Class<?> clazz) {
		return DataTypeUtils.isNumber(clazz);
	}

}
