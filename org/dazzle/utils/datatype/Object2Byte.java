package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2Byte {

	/** @author hcqt@qq.com */
	public static final <T> Byte convert(Object targetObject) {
		if(targetObject.getClass().isEnum()) {
			return convert(((Enum<?>)targetObject).ordinal());
		}
		CatchDataTypeException.isNumber(Byte.class, targetObject);
		if(DataTypeUtils.isChar(targetObject)) {
			return convert(Integer.valueOf((int) targetObject.toString().charAt(0)));
		}
		BigDecimal targetData = new BigDecimal(targetObject.toString());
		CatchDataTypeException.notDecimal(Byte.class, targetData);
		CatchDataTypeException.isBetweenSpan(Byte.class, targetData);
		if(targetObject instanceof String
				|| targetObject instanceof Long
				|| targetObject instanceof Byte
				|| targetObject instanceof Short
				|| targetObject instanceof Integer
				|| targetObject instanceof BigDecimal
				|| targetObject instanceof Float
				|| targetObject instanceof Double) {
			return Byte.valueOf(targetData.byteValue());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Byte.class, targetObject);
		}
	}

}
