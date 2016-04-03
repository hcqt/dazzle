package org.dazzle.utils.datatype;

/** @author hcqt@qq.com */
public class Object2Character {

	/** @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	public static final <T> T convert(final Object targetObject) {
		if(targetObject instanceof String) {
			String tmp = (String) targetObject;
			if(!tmp.isEmpty()) {
				return (T) Character.valueOf((tmp).charAt(0));
			}
		}
		throw CatchDataTypeException.returnCouldNotConvertException(Character.class, targetObject);
	}

}
