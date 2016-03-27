package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2FloatArray {

	/** @author hcqt@qq.com */
	public static final Float[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<Float> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(Float.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new Float[0]);
		}
	}

}
