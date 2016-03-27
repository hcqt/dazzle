package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2ByteArray {

	/** @author hcqt@qq.com */
	public static final Byte[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<Byte> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(Byte.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new Byte[0]);
		}
	}

}
