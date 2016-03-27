package org.dazzle.utils.datatype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dazzle.utils.DataTypeUtils;

/** @author hcqt@qq.com */
public class Object2BigDecimalArray {

	/** @author hcqt@qq.com */
	public static final BigDecimal[] convert(final Object targetObject) {
		Object[] targetObjectArray = (Object[]) targetObject;
		List<BigDecimal> ret = new ArrayList<>();
		for (Object _targetObject : targetObjectArray) {
			ret.add(DataTypeUtils.convert(BigDecimal.class, _targetObject));
		}
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret.toArray(new BigDecimal[0]);
		}
	}

}
