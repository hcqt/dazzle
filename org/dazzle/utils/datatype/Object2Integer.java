package org.dazzle.utils.datatype;

import java.math.BigDecimal;

/** @author hcqt@qq.com */
public class Object2Integer {

	private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Integer.MAX_VALUE);
	private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Integer.MIN_VALUE);

	/** @author hcqt@qq.com */
	public static final Integer convert(final Object targetObject) {
		if(targetObject instanceof String) {
			CatchDataTypeException.isNumber(Integer.class, targetObject);
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			CatchDataTypeException.isDecimal(Integer.class, targetData);
			CatchDataTypeException.isBetweenSpan(Integer.class, targetData, MAX_VALUE, MIN_VALUE);
			return Integer.valueOf(targetData.setScale(0, BigDecimal.ROUND_CEILING).toString());
		}
		else if(targetObject instanceof Long) {
			BigDecimal tmp = BigDecimal.valueOf((Long) targetObject);
			CatchDataTypeException.isBetweenSpan(Integer.class, tmp, MAX_VALUE, MIN_VALUE);
			return Integer.valueOf(tmp.intValue());
		}
		else if(targetObject instanceof Byte) {
			return Integer.valueOf((Byte) targetObject);
		}
		else if(targetObject instanceof Short) {
			return Integer.valueOf((Short) targetObject);
		}
		else if(targetObject instanceof Integer) {
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			CatchDataTypeException.isBetweenSpan(Integer.class, targetData, MAX_VALUE, MIN_VALUE);
			return Integer.valueOf(((Integer) targetObject).intValue());
		}
		else if(targetObject instanceof BigDecimal) {
			BigDecimal targetData = (BigDecimal) targetObject;
			CatchDataTypeException.isDecimal(Integer.class, targetData);
			CatchDataTypeException.isBetweenSpan(Integer.class, targetData, MAX_VALUE, MIN_VALUE);
			return Integer.valueOf(targetData.setScale(0, BigDecimal.ROUND_CEILING).toString());
		}
		else if(targetObject instanceof Float || targetObject instanceof Double) {
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			CatchDataTypeException.isDecimal(Integer.class, targetData);
			CatchDataTypeException.isBetweenSpan(Integer.class, targetData, MAX_VALUE, MIN_VALUE);
			return Integer.valueOf(targetData.setScale(0, BigDecimal.ROUND_CEILING).toString());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Integer.class, targetObject);
		}
	}

}
