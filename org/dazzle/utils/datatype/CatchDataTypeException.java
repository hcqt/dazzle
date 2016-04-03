package org.dazzle.utils.datatype;

import java.math.BigDecimal;

import org.dazzle.utils.DataTypeUtils;
import org.dazzle.utils.ResMsgUtils;

/** @author hcqt@qq.com */
class CatchDataTypeException {

	/**用于抛出无法转换数据类型的异常——输入的数据无法转换为预期的类型<br />该异常总是抛出，使用前你必须确定你是需要抛出此异常
	 * @param destClazz 预期的类型
	 * @param srcObj 输入的数据
	 * @author hcqt@qq.com */
	static final <T> DataTypeException returnCouldNotConvertException(Class<T> destClazz, Object srcObj) {
		return new DataTypeException(
				"SYS_COMMON_DATA_TYPE_CONVERT_9j2gh", 
				"类型为{0}的数据“{1}”无法转换为类型“{2}”", 
				srcObj == null ? "null" : srcObj.getClass(), 
				srcObj, 
				destClazz == null ? "null" : destClazz.getName());
	}

	/**检查输入数据是否是一个数字，不是则抛出异常
	 * @param destClazz 预期的类型
	 * @param srcObj 输入的数据
	 * @author hcqt@qq.com */
	static final <T> void isNumber(Class<T> destClazz, Object srcObj) {
		if(!DataTypeUtils.isNumber(srcObj)) {
			throw new DataTypeException(
					"SYS_COMMON_DATA_TYPE_CONVERT_j8Nt2", 
					"类型为{0}的数据“{1}”不是数字，无法转换为类型“{2}”", 
					srcObj.getClass(), 
					srcObj, 
					destClazz.getName());
		}
	}

//	/**如果不是小数，则抛出无法转换数据类型的异常
//	 * @author hcqt@qq.com */
//	static final <T> void isDecimal(Class<T> clazz, BigDecimal targetData) {
//		BigDecimal bigDecimal = targetData.setScale(0, BigDecimal.ROUND_CEILING);
//		if(targetData.compareTo(bigDecimal) != 0) {
//			throw new DataTypeException(
//					"SYS_COMMON_DATA_TYPE_CONVERT_5bhsq", 
//					ResMsgUtils.resolve("类型为{0}的数据“{1}”不是小数，无法转换为类型“{2}”", targetData.getClass(), targetData, clazz.getName()), 
//					targetData, 
//					clazz.getName());
//		}
//	}

	/**如果是小数，则抛出无法转换数据类型的异常
	 * @author hcqt@qq.com */
	static final <T> void notDecimal(Class<T> clazz, BigDecimal targetData) {
		BigDecimal bigDecimal = targetData.setScale(0, BigDecimal.ROUND_CEILING);
		if(targetData.compareTo(bigDecimal) != 0) {
			throw new DataTypeException(
					"SYS_COMMON_DATA_TYPE_CONVERT_5bhsq", 
					ResMsgUtils.resolve("类型为{0}的数据“{1}”为小数，无法转换为类型“{2}”", targetData.getClass(), targetData, clazz.getName()), 
					targetData, 
					clazz.getName());
		}
	}

//	/**检查目标数字是否介于最大值与最小值之间，符合指定数据类型的取值范围
//	 * @author hcqt@qq.com */
//	static final <T> void isBetweenSpan(final Class<T> clazz, final BigDecimal targetData, final BigDecimal max_value, final BigDecimal min_value) {
//		if(targetData.compareTo(max_value) == 1 || targetData.compareTo(min_value) == -1) {
//			throw new DataTypeException(
//					"SYS_COMMON_DATA_TYPE_CONVERT_n54di", 
//					ResMsgUtils.resolve("数据“{0}”不在数据类型“{1}”可以容纳的数值范围中", targetData, clazz.getName()), 
//					targetData, 
//					clazz.getName());
//		}
//	}

	private static final BigDecimal LongMax = BigDecimal.valueOf(Long.MAX_VALUE);
	private static final BigDecimal LongMin = BigDecimal.valueOf(Long.MIN_VALUE);
	
	private static final BigDecimal IntegerMax = BigDecimal.valueOf(Integer.MAX_VALUE);
	private static final BigDecimal IntegerMin = BigDecimal.valueOf(Integer.MIN_VALUE);
	
	private static final BigDecimal ShortMax = BigDecimal.valueOf(Short.MAX_VALUE);
	private static final BigDecimal ShortMin = BigDecimal.valueOf(Short.MIN_VALUE);
	
	private static final BigDecimal ByteMax = BigDecimal.valueOf(Byte.MAX_VALUE);
	private static final BigDecimal ByteMin = BigDecimal.valueOf(Byte.MIN_VALUE);
	
	private static final BigDecimal DoubleMax = new BigDecimal(Double.toString(Double.MAX_VALUE));// TODO 此数值疑似不准确
	private static final BigDecimal DoubleMin = new BigDecimal(Double.toString(-Double.MAX_VALUE));// TODO 此数值疑似不准确
	
	private static final BigDecimal FloatMax = new BigDecimal(Float.toString(Float.MAX_VALUE));// TODO 此数值疑似不准确
	private static final BigDecimal FloatMin = new BigDecimal(Float.toString(-Float.MAX_VALUE));// TODO 此数值疑似不准确

	/**@see #isBetweenSpan(Class, Object)
	 * @author hcqt@qq.com */
	static final <T> void isBetweenSpan(Class<T> destClazz, BigDecimal srcObj) {
		isBetweenSpan(destClazz, (Object) srcObj);
	}

	/**检查目标数字是否介于最大值与最小值之间，符合指定数据类型的取值范围
	 * @author hcqt@qq.com */
	static final <T> void isBetweenSpan(Class<T> destClazz, Object srcObj) {
		isNumber(destClazz, srcObj);
		BigDecimal max = null;
		BigDecimal min = null;
		if(Long.class.isAssignableFrom(destClazz)) {
			max = LongMax;
			min = LongMin;
		}
		else if(Integer.class.isAssignableFrom(destClazz)) {
			max = IntegerMax;
			min = IntegerMin;
		}
		else if(Double.class.isAssignableFrom(destClazz)) {
			max = DoubleMax;
			min = DoubleMin;
		}
		else if(Float.class.isAssignableFrom(destClazz)) {
			max = FloatMax;
			min = FloatMin;
		}
		else if(Short.class.isAssignableFrom(destClazz)) {
			max = ShortMax;
			min = ShortMin;
		}
		else if(Byte.class.isAssignableFrom(destClazz)) {
			max = ByteMax;
			min = ByteMin;
		}
		BigDecimal src = new BigDecimal(srcObj.toString());
		if((src != null && src.compareTo(max) == 1) || (src != null && src.compareTo(min) == -1)) {
			throw new DataTypeException(
					"SYS_COMMON_DATA_TYPE_CONVERT_n54di", 
					"类型为{0}的数值“{1}”不在数据类型“{2}”可以容纳的数值范围内",  
					srcObj.getClass().getName(), 
					srcObj, 
					destClazz.getName());
		}
	}

}
