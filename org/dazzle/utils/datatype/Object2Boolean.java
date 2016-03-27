package org.dazzle.utils.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;

/** @author hcqt@qq.com */
public class Object2Boolean {

	/** @author hcqt@qq.com */
	public static final Boolean convert(final Object targetObject) {
		if(targetObject instanceof String) {
			return Boolean.valueOf((String) targetObject);
		}
		else if(targetObject instanceof Boolean) {
			return (Boolean) targetObject;
		}
		else if(targetObject instanceof Integer) {
			if(0 == Integer.compare(0, (int) targetObject)) {
				return false;
			} else {
				return true;
			}
		}
		else if(targetObject instanceof Long){
			if(0 == Long.compare(0, (long) targetObject)) {
				return false;
			} else {
				return true;
			}
		}
		else if(targetObject instanceof Short){
			if(0 == Short.compare((short) 0, (short) targetObject)) {
				return false;
			} else {
				return true;
			}
		}
		else if(targetObject instanceof Byte){
			if(0 == Byte.compare((byte) 0, (byte) targetObject)) {
				return false;
			} else {
				return true;
			}
		}
		else if(targetObject instanceof Double){
			if(0 == Double.compare(0, (double) targetObject)) {
				return false;
			} else {
				return true;
			}
		}
		else if(targetObject instanceof Float){
			if(0 == Float.compare(0, (float) targetObject)) {
				return false;
			} else {
				return true;
			}
		}
		else if(targetObject instanceof Character){
			if(0 == Character.compare('0', ((Character) targetObject).charValue())) {
				return false;
			} else {
				return true;
			}
		}
		else if(targetObject instanceof BigDecimal){
			if(0 == ((BigDecimal) targetObject).compareTo(BigDecimal.ZERO)) {
				return false;
			} else {
				return true;
			}
		}
		else if(targetObject instanceof BigInteger){
			if(0 == ((BigInteger) targetObject).compareTo(BigInteger.ZERO)) {
				return false;
			} else {
				return true;
			}
		}
		else {
			throw CatchDataTypeException.returnCouldNotConvertException(Boolean.class, targetObject);
		}
	}
}
