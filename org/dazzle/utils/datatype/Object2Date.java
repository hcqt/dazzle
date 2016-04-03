package org.dazzle.utils.datatype;

import java.util.Date;

import org.dazzle.common.exception.BaseException;
import org.dazzle.utils.DU;

/** @author hcqt@qq.com */
public class Object2Date {

	/** @author hcqt@qq.com */
	public static final Date convert(final Object targetObject) {
		if(targetObject instanceof String) {
			try { return DU.parse((String) targetObject, datePattern1); } catch (BaseException e) { }
		}
		try { 
			return new Date(Object2Long.convert(targetObject)); 
		} catch (BaseException e) { 
			throw CatchDataTypeException.returnCouldNotConvertException(Date.class, targetObject);
		}
	}

	private static final String datePattern1 = "yyyy-MM-dd HH:mm:ss";

}
