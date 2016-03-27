package org.dazzle.utils.datatype;

import java.math.BigDecimal;

/** @author hcqt@qq.com */
public class Object2BigDecimal {

	/** @author hcqt@qq.com */
	public static final BigDecimal convert(final Object targetObject) {
		if(targetObject instanceof String) {
			CatchDataTypeException.isNumber(BigDecimal.class, targetObject);
			return new BigDecimal((String) targetObject);
		}
		else if(targetObject instanceof Byte) {
			return new BigDecimal(targetObject.toString());
		}
		else if(targetObject instanceof Integer) {
			return new BigDecimal(targetObject.toString());
		}
		else if(targetObject instanceof Short) {
			return new BigDecimal(targetObject.toString());
		}
		else if(targetObject instanceof Long) {
			return new BigDecimal(targetObject.toString());
		}
		else if(targetObject instanceof Double) {
			return new BigDecimal(targetObject.toString());
		}
		else if(targetObject instanceof Float) {
			return new BigDecimal(targetObject.toString());
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(BigDecimal.class, targetObject);
		}
	}

}
