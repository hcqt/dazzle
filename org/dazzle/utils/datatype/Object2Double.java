package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2Double {

	/** @author hcqt@qq.com */
	public static final Double convert(final Object targetObject) {
		if(targetObject.getClass().isEnum()) {
			return Double.valueOf(((Enum<?>)targetObject).ordinal());
		}
		CatchDataTypeException.isNumber(Double.class, targetObject);
		if(DataTypeUtils.isChar(targetObject)) {
			return Double.valueOf((int) targetObject.toString().charAt(0));
		}
		BigDecimal targetData = new BigDecimal((String)targetObject);
		CatchDataTypeException.isBetweenSpan(Double.class, targetData);
		if(targetObject instanceof String
				|| targetObject instanceof Long
				|| targetObject instanceof Byte
				|| targetObject instanceof Short
				|| targetObject instanceof Integer
				|| targetObject instanceof BigDecimal
				|| targetObject instanceof Float
				|| targetObject instanceof Double) {
			return Double.valueOf(targetData.doubleValue());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Double.class, targetObject);
		}
	}

}
