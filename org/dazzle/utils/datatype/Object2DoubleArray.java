package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2DoubleArray {

	/** @author hcqt@qq.com */
	public static final Double[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<Double> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(Double.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new Double[0]);
		}
	}

}
