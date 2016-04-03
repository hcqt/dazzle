package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2Long {

	/** @author hcqt@qq.com */
	public static final Long convert(final Object targetObject) {
		if(targetObject.getClass().isEnum()) {
			return Long.valueOf(((Enum<?>)targetObject).ordinal());
		}
		CatchDataTypeException.isNumber(Long.class, targetObject);
		if(DataTypeUtils.isChar(targetObject)) {
			return Long.valueOf((int) targetObject.toString().charAt(0));
		}
		BigDecimal targetData = new BigDecimal(targetObject.toString());
		CatchDataTypeException.notDecimal(Long.class, targetData);
		CatchDataTypeException.isBetweenSpan(Long.class, targetData);
		if(targetObject instanceof String
				|| targetObject instanceof Long
				|| targetObject instanceof Byte
				|| targetObject instanceof Short
				|| targetObject instanceof Integer
				|| targetObject instanceof BigDecimal
				|| targetObject instanceof Float
				|| targetObject instanceof Double
				) {
			return Long.valueOf(targetData.longValue());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Long.class, targetObject);
		}
	}

}
