package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.RegexUtil;

/** @author hcqt@qq.com */
public class Object2Byte {

	/** @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	public static final <T> T convert(final Object targetObject) {
		if(targetObject instanceof String) {
			if(null != RegexUtil.find("^(\\-|\\+)?\\d+(\\.\\d+)?$", targetObject.toString())) {
				BigDecimal targetData = new BigDecimal((String)targetObject);
				BigDecimal bigDecimal = targetData.setScale(0,BigDecimal.ROUND_CEILING);
				if(targetData.compareTo(bigDecimal) == 0) {
					if(new BigDecimal(Byte.MAX_VALUE).compareTo(targetData) >=0 
							&& new BigDecimal(Byte.MIN_VALUE).compareTo(targetData) <=0) {
						return (T) Byte.valueOf(bigDecimal.toString());
					}
				}
			} 
		}
		else if(targetObject instanceof Short) {
			if(Byte.MAX_VALUE >= ((Short) targetObject)
					&& Byte.MIN_VALUE <= ((Short) targetObject)) {
				return (T) Byte.valueOf(targetObject.toString());
			}
		}
		else if(targetObject instanceof Integer) {
			if(Byte.MAX_VALUE >= ((Integer) targetObject)
					&& Byte.MIN_VALUE <= ((Integer) targetObject)) {
				return (T) Byte.valueOf(targetObject.toString());
			}
		}
		else if(targetObject instanceof Long) {
			if(Byte.MAX_VALUE >= ((Long) targetObject)
					&& Byte.MIN_VALUE <= ((Long) targetObject)) {
				return (T) Byte.valueOf(targetObject.toString());
			}
		}
		else if(targetObject instanceof BigDecimal) {
			BigDecimal targetData = (BigDecimal)targetObject;
			BigDecimal bigDecimal = targetData.setScale(0,BigDecimal.ROUND_CEILING);
			if(targetData.compareTo(bigDecimal) == 0) {
				if(new BigDecimal(Byte.MAX_VALUE).compareTo(targetData) >=0 
						&& new BigDecimal(Byte.MIN_VALUE).compareTo(targetData) <=0) {
					return (T) Byte.valueOf(bigDecimal.toString());
				}
			}
		}
		else if(targetObject instanceof Float) {
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			BigDecimal bigDecimal = targetData.setScale(0,BigDecimal.ROUND_CEILING);
			if(targetData.compareTo(bigDecimal) == 0) {
				if(new BigDecimal(Byte.MAX_VALUE).compareTo(targetData) >=0 
						&& new BigDecimal(Byte.MIN_VALUE).compareTo(targetData) <=0) {
					return (T) Byte.valueOf(bigDecimal.toString());
				}
			}
		}
		else if(targetObject instanceof Double) {
			BigDecimal targetData = new BigDecimal(targetObject.toString());
			BigDecimal bigDecimal = targetData.setScale(0,BigDecimal.ROUND_CEILING);
			if(targetData.compareTo(bigDecimal) == 0) {
				if(new BigDecimal(Byte.MAX_VALUE).compareTo(targetData) >=0 
						&& new BigDecimal(Byte.MIN_VALUE).compareTo(targetData) <=0) {
					return (T) Byte.valueOf(bigDecimal.toString());
				}
			}
		}
		return null;
	}

}
