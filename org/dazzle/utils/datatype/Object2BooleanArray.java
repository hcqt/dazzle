package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2BooleanArray {

	/** @author hcqt@qq.com */
	public static final Boolean[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<Boolean> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(Boolean.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new Boolean[0]);
		}
	}

}
