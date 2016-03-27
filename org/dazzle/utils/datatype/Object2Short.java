package org.dazzle.utils.datatype;

import java.math.BigDecimal;

/** @author hcqt@qq.com */
public class Object2Short {

	private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Short.MAX_VALUE);
	private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Short.MIN_VALUE);

	/** @author hcqt@qq.com */
	public static final Short convert(final Object targetObject) {
		if(targetObject instanceof String) {
			CatchDataTypeException.isNumber(Short.class, targetObject);
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			CatchDataTypeException.isDecimal(Short.class, targetData);
			CatchDataTypeException.isBetweenSpan(Short.class, targetData, MAX_VALUE, MIN_VALUE);
			return Short.valueOf(targetData.setScale(0, BigDecimal.ROUND_CEILING).toString());
		}
		else if(targetObject instanceof Byte) {
			return Short.valueOf((Byte) targetObject);
		}
		else if(targetObject instanceof BigDecimal) {
			BigDecimal targetData = (BigDecimal) targetObject;
			CatchDataTypeException.isDecimal(Short.class, targetData);
			CatchDataTypeException.isBetweenSpan(Short.class, targetData, MAX_VALUE, MIN_VALUE);
			return Short.valueOf(targetData.setScale(0, BigDecimal.ROUND_CEILING).toString());
		}
		else if(targetObject instanceof Float || targetObject instanceof Double) {
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			CatchDataTypeException.isDecimal(Short.class, targetData);
			CatchDataTypeException.isBetweenSpan(Short.class, targetData, MAX_VALUE, MIN_VALUE);
			return Short.valueOf(targetData.setScale(0, BigDecimal.ROUND_CEILING).toString());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Short.class, targetObject);
		}
	}

}
