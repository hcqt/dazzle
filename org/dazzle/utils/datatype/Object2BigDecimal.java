package org.dazzle.utils.datatype;

import java.math.BigDecimal;

/** @author hcqt@qq.com */
public class Object2BigDecimal {

	/** @author hcqt@qq.com */
	public static final BigDecimal convert(final Object targetObject) {
		if(targetObject.getClass().isEnum()) {
			return BigDecimal.valueOf(((Enum<?>)targetObject).ordinal());
		}
		CatchDataTypeException.isNumber(BigDecimal.class, targetObject);
		try {
			return new BigDecimal(targetObject.toString());
		} catch(Exception e) {
			throw CatchDataTypeException.returnCouldNotConvertException(BigDecimal.class, targetObject);
		}
	}

}
