package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2IntegerArray {

	/** @author hcqt@qq.com */
	public static final Integer[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<Integer> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(Integer.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new Integer[0]);
		}
	}

}
