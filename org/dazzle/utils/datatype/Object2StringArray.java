package org.dazzle.utils.datatype;

import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2StringArray {

	/** @author hcqt@qq.com */
	public static final String[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<String> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(String.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new String[0]);
		}
	}
public static void main(String[] args) {
	System.out.println(Number.class.isAssignableFrom(int.class));
	System.out.println(Object.class.isAssignableFrom(int.class));
	System.out.println(Object.class.isPrimitive());
}
}
