package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2Integer {

	/** @author hcqt@qq.com */
	public static final Integer convert(final Object targetObject) {
		if(targetObject.getClass().isEnum()) {
			return ((Enum<?>)targetObject).ordinal();
		}
		CatchDataTypeException.isNumber(Integer.class, targetObject);
		if(DataTypeUtils.isChar(targetObject)) {
			return Integer.valueOf((int) targetObject.toString().charAt(0));
		}
		BigDecimal targetData = new BigDecimal(targetObject.toString());
		CatchDataTypeException.notDecimal(Integer.class, targetData);
		CatchDataTypeException.isBetweenSpan(Integer.class, targetData);
		if(targetObject instanceof String
				|| targetObject instanceof Long
				|| targetObject instanceof Byte
				|| targetObject instanceof Short
				|| targetObject instanceof Integer
				|| targetObject instanceof BigDecimal
				|| targetObject instanceof Float
				|| targetObject instanceof Double) {
			return Integer.valueOf(targetData.intValue());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Integer.class, targetObject);
		}
	}

}
