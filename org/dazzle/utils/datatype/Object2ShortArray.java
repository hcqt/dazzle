package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2ShortArray {

	/** @author hcqt@qq.com */
	public static final Short[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<Short> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(Short.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new Short[0]);
		}
	}

}
