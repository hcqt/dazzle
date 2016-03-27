package org.dazzle.utils.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;

/** @author hcqt@qq.com */
public class Object2Long {

	private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);
	private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);

	/** @author hcqt@qq.com */
	public static final Long convert(final Object targetObject) {
		if(targetObject instanceof String) {
			CatchDataTypeException.isNumber(Long.class, targetObject);
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			CatchDataTypeException.isDecimal(Long.class, targetData);
			CatchDataTypeException.isBetweenSpan(Long.class, targetData, MAX_VALUE, MIN_VALUE);
			return Long.valueOf(targetData.setScale(0, BigDecimal.ROUND_CEILING).toString());
		} 
		else if(targetObject instanceof Byte) {
			return Long.valueOf((Byte) targetObject);
		}
		else if(targetObject instanceof Short) {
			return Long.valueOf((Short) targetObject);
		}
		else if(targetObject instanceof Integer) {
			return Long.valueOf((Integer) targetObject);
		}
		else if(targetObject instanceof BigDecimal) {
			BigDecimal targetData = (BigDecimal) targetObject;
			CatchDataTypeException.isDecimal(Long.class, targetData);
			CatchDataTypeException.isBetweenSpan(Long.class, targetData, MAX_VALUE, MIN_VALUE);
			return Long.valueOf(targetData.setScale(0,BigDecimal.ROUND_CEILING).toString());
		}
		else if(targetObject instanceof BigInteger) {
			BigInteger targetData = (BigInteger)targetObject;
			CatchDataTypeException.isBetweenSpan(Long.class, new BigDecimal(targetData), MAX_VALUE, MIN_VALUE);
			return Long.valueOf(targetData.longValue());
		}
		else if(targetObject instanceof Float || targetObject instanceof Double) {
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			CatchDataTypeException.isDecimal(Long.class, targetData);
			CatchDataTypeException.isBetweenSpan(Long.class, targetData, MAX_VALUE, MIN_VALUE);
			return Long.valueOf(targetData.setScale(0,BigDecimal.ROUND_CEILING).toString());
		} 
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Long.class, targetObject);
		}
	}

}
