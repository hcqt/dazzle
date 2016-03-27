package org.dazzle.utils.datatype;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** @author hcqt@qq.com */
public class Object2Float {

	private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Float.MAX_VALUE);
	private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Float.MIN_VALUE);

	/** @author hcqt@qq.com */
	public static final Float convert(final Object targetObject) {
		if(targetObject instanceof String) {
			CatchDataTypeException.isNumber(Float.class, targetObject);
			BigDecimal targetData = new BigDecimal((String)targetObject);
			CatchDataTypeException.isBetweenSpan(Float.class, targetData, MAX_VALUE, MIN_VALUE);
			return Float.valueOf(targetData.setScale(0, RoundingMode.HALF_UP).toString());
		}
		else if(targetObject instanceof Byte) {
			return Float.valueOf((Byte) targetObject);
		}
		else if(targetObject instanceof Integer) {
			return Float.valueOf((Integer) targetObject);
		}
		else if(targetObject instanceof Short) {
			return Float.valueOf((Short) targetObject);
		}
		else if(targetObject instanceof Long) {
			return Float.valueOf((Long) targetObject);
		}
		else if(targetObject instanceof Float) {
			return Float.valueOf((Float) targetObject);
		}
		else if(targetObject instanceof Double) {
			return new Float((Double) targetObject);
		}
		else if(targetObject instanceof BigDecimal) {
			BigDecimal targetData = (BigDecimal) targetObject;
			CatchDataTypeException.isBetweenSpan(Float.class, targetData, MAX_VALUE, MIN_VALUE);
			return Float.valueOf(targetData.floatValue());
		} 
		else if(targetObject instanceof Boolean) {
			if((Boolean) targetObject) {
				return 1F;
			} else {
				return 0F;
			}
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Float.class, targetObject);
		}
	}

}
