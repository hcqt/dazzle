package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2Float {

	/** @author hcqt@qq.com */
	public static final Float convert(final Object targetObject) {
		if(targetObject.getClass().isEnum()) {
			return convert(((Enum<?>)targetObject).ordinal());
		}
		CatchDataTypeException.isNumber(Float.class, targetObject);
		if(DataTypeUtils.isChar(targetObject)) {
			return convert(Integer.valueOf((int) targetObject.toString().charAt(0)));
		}
		BigDecimal targetData = new BigDecimal(targetObject.toString());
		CatchDataTypeException.isBetweenSpan(Float.class, targetData);
		if(targetObject instanceof String
				|| targetObject instanceof Long
				|| targetObject instanceof Byte
				|| targetObject instanceof Short
				|| targetObject instanceof Integer
				|| targetObject instanceof BigDecimal
				|| targetObject instanceof Float
				|| targetObject instanceof Double) {
			return Float.valueOf(targetData.floatValue());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Float.class, targetObject);
		}
	}

}
