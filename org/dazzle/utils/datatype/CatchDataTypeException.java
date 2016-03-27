package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.RegexUtil;
import org.dazzle.utils.ResMsgUtils;

/** @author hcqt@qq.com */
public class CatchDataTypeException {

	/** @author hcqt@qq.com */
	public static final <T> DataTypeException returnCouldNotConvertException(final Class<T> clazz, final Object targetObject) {
		return new DataTypeException(
				"SYS_COMMON_DATA_TYPE_CONVERT_9j2gh", 
				ResMsgUtils.resolve("类型为{0}的数据“{1}”无法转换为类型“{2}”", targetObject.getClass(), targetObject, clazz.getName()), 
				targetObject, 
				clazz.getName());
	}

	/** @author hcqt@qq.com */
	public static final <T> void isNumber(final Class<T> clazz, final Object targetObject) {
		if(null == RegexUtil.find("^(\\-|\\+)?\\d+(\\.\\d+)?$", targetObject.toString())) {
			throw new DataTypeException(
					"SYS_COMMON_DATA_TYPE_CONVERT_j8Nt2", 
					ResMsgUtils.resolve("类型为{0}的数据“{1}”不是数字，无法转换为类型“{2}”", targetObject.getClass(), targetObject, clazz.getName()), 
					targetObject, 
					clazz.getName());
		}
	}

	/** @author hcqt@qq.com */
	public static final <T> void isDecimal(final Class<T> clazz, final BigDecimal targetData) {
		BigDecimal bigDecimal = targetData.setScale(0, BigDecimal.ROUND_CEILING);
		if(targetData.compareTo(bigDecimal) != 0) {
			throw new DataTypeException(
					"SYS_COMMON_DATA_TYPE_CONVERT_5bhsq", 
					ResMsgUtils.resolve("类型为{0}的数据“{1}”为小数，无法转换为类型“{2}”", targetData.getClass(), targetData, clazz.getName()), 
					targetData, 
					clazz.getName());
		}
	}

	/** @author hcqt@qq.com */
	public static final <T> void isBetweenSpan(final Class<T> clazz, final BigDecimal targetData, final BigDecimal max_value, final BigDecimal min_value) {
		if(targetData.compareTo(max_value) == 1 || targetData.compareTo(min_value) == -1) {
			throw new DataTypeException(
					"SYS_COMMON_DATA_TYPE_CONVERT_n54di", 
					ResMsgUtils.resolve("数据“{0}”不在数据类型“{1}”可以容纳的数值范围中", targetData, clazz.getName()), 
					targetData, 
					clazz.getName());
		}
	}

}
