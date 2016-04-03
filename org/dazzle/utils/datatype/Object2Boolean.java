package org.dazzle.utils.datatype;

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
		throw CatchDataTypeException.returnCouldNotConvertException(Boolean.class, targetObject);
	}
}
