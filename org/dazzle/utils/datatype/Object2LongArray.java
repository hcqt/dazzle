package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2LongArray {

	/** @author hcqt@qq.com */
	public static final Long[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<Long> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(Long.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new Long[0]);
		}
	}

}
