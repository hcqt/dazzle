package org.dazzle.utils.datatype;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** @author hcqt@qq.com */
public class Object2Double {

	private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Double.MAX_VALUE);
	private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Double.MIN_VALUE);

	/** @author hcqt@qq.com */
	public static final Double convert(final Object targetObject) {
		if(targetObject instanceof String) {
			CatchDataTypeException.isNumber(Double.class, targetObject);
			BigDecimal targetData = new BigDecimal((String)targetObject);
			CatchDataTypeException.isBetweenSpan(Double.class, targetData, MAX_VALUE, MIN_VALUE);
			return Double.valueOf(targetData.setScale(0, RoundingMode.HALF_UP).toString());
		}
		else if(targetObject instanceof Byte) {
			return Double.valueOf((Byte) targetObject);
		}
		else if(targetObject instanceof Integer) {
			return Double.valueOf((Integer) targetObject);
		}
		else if(targetObject instanceof Short) {
			return Double.valueOf((Short) targetObject);
		}
		else if(targetObject instanceof Long) {
			return Double.valueOf((Long) targetObject);
		}
		else if(targetObject instanceof Float) {
			return Double.valueOf((Float) targetObject);
		}
		else if(targetObject instanceof BigDecimal) {
			BigDecimal targetData = (BigDecimal) targetObject;
			CatchDataTypeException.isBetweenSpan(Double.class, targetData, MAX_VALUE, MIN_VALUE);
			return Double.valueOf(targetData.doubleValue());
		} 
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Double.class, targetObject);
		}
	}

}
