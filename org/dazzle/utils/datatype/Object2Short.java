package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2Short {

	/** @author hcqt@qq.com */
	public static final Short convert(final Object targetObject) {
		if(targetObject.getClass().isEnum()) {
			return convert(((Enum<?>)targetObject).ordinal());
		}
		CatchDataTypeException.isNumber(Short.class, targetObject);
		if(DataTypeUtils.isChar(targetObject)) {
			return convert(Integer.valueOf((int) targetObject.toString().charAt(0)));
		}
		BigDecimal targetData = new BigDecimal(targetObject.toString());
		CatchDataTypeException.notDecimal(Short.class, targetData);
		CatchDataTypeException.isBetweenSpan(Short.class, targetData);
		if(targetObject instanceof String
				|| targetObject instanceof Long
				|| targetObject instanceof Byte
				|| targetObject instanceof Short
				|| targetObject instanceof Integer
				|| targetObject instanceof BigDecimal
				|| targetObject instanceof Float
				|| targetObject instanceof Double
				) {
			return Short.valueOf(targetData.shortValue());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Short.class, targetObject);
		}
	}

}
