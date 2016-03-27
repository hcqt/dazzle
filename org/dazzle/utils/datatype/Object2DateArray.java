package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2DateArray {

	/** @author hcqt@qq.com */
	public static final Date[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<Date> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(Date.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new Date[0]);
		}
	}

}
