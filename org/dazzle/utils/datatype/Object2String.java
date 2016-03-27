package org.dazzle.utils.datatype;

import java.util.Date;

import org.dazzle.utils.DU;


/** @author hcqt@qq.com */
public class Object2String {

	/** @author hcqt@qq.com */
	public static final String convert(final Object targetObject) {
		String ret = null;
		if(null == targetObject) {
			ret = null;
		} else if(targetObject instanceof Date) {
			ret = DU.format((Date) targetObject, DEF_DATE_FMT);
		}
		if(null == ret) {
			ret = targetObject.toString();
		}
		return ret;
	}

	private static final String DEF_DATE_FMT = "yyyy-MM-dd HH:mm:ss"; 

}
