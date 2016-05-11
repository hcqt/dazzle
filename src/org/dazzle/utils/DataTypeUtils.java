package org.dazzle.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.dazzle.common.exception.BaseException;

/**<a href="https://github.com/hcqt/dazzle">https://github.com/hcqt/dazzle</a>
 * @see #convert(Class, Object) 
 * @author hcqt@qq.com */
public class DataTypeUtils {

	/**把输入数据转换为预期的输出类型<br />
	 * 转换数据类型通常都是子类转换为父类或者转换为接口，这种情况至少占到程序编码90%以上，此方法内部默认此种判断为第一级判断，从而保证效率最优<br />
	 * 此工具类的其他代码专注于解决另外的10%复杂情况，旨在把常见的容错代码，封装于此，避免调用端编写复杂、重复、冗长、不健壮的代码<br />
	 * <h1>容错概述：</h1><br />
	 * 1.自动拆箱装箱<br />
	 * 2.数字与字符串向枚举转换<br />
	 * 3.数组与Collection自动互转<br />
	 * 4.除boolean外的七种基本数据类型互转时取值范围自适应<br />
	 * 5.字符串与时间与基本数据类型互转<br />
	 * 6.以及对上述容错情况自由排列组合后所产生的更复杂情况自动容错<br />
	 * 7.请格外注意，由于Boolean在Java中的通俗约定较之其他语言例如C非常不同，不存在其他数据类型与之兼容取值范围的习惯做法，因此并未容错，如果想要让数字类型转换为boolean，注意这里是未作支持的，现仅以最低兼容方式构造Boolean，即仅支持Java官方约定的通过字符串构造Boolean对象以及boolean基本值构造Boolean对象<br />
	 * <h1>常见情景：</h1><br />
	 * 1.比如一个int数组完全可以放入long数组或者List<Long>，用此工具方法自动处理，即可避免自己的代码篇幅冗长，或者代码编写质量不高导致程序健壮性不高的问题<br />
	 * 2.如果long数组数值并不是大数，当然也可以放入int数组，其他数值类型同理，反之亦然<br />
	 * 3.很多数据拿到的时候是Object类型，如果用强制类型转换，很容易出错，比如到底Object是数组还是集合类，都有可能，这种时候强制类型转换必然出错<br />
	 * 4.实现类自动转换为接口，比如HashMap、LinkedHashMap转换为Map
	 * <h1>示例：</h1><br />
	 * System.out.println(convert(byte[].class, "-128"));<br />
	 * System.out.println(convert(byte.class, "-128"));<br />
	 * System.out.println(convert(byte[].class, -128));<br />
	 * System.out.println(convert(byte.class, -128));<br />
	 * System.out.println(convert(Byte[].class, "-128"));<br />
	 * System.out.println(convert(Byte.class, "-128"));<br />
	 * System.out.println(convert(Byte[].class, -128));<br />
	 * System.out.println(convert(Byte.class, -128));<br />
	 * System.out.println(convert(byte[].class, "-128.00000"));<br />
	 * System.out.println(convert(byte.class, "-128.00000"));<br />
	 * System.out.println(convert(byte[].class, -128.00000));<br />
	 * System.out.println(convert(byte.class, -128.00000));<br />
	 * System.out.println(convert(Byte[].class, "-128.00000"));<br />
	 * System.out.println(convert(Byte.class, "-128.00000"));<br />
	 * System.out.println(convert(Byte[].class, -128.00000));<br />
	 * System.out.println(convert(Byte.class, -128.00000));<br />
	 * System.out.println(convert(float[].class, "-128.00000"));<br />
	 * System.out.println(convert(float.class, "-128.00000"));<br />
	 * System.out.println(convert(float[].class, -128.00000));<br />
	 * System.out.println(convert(float.class, -128.00000));<br />
	 * System.out.println(convert(Float[].class, "-128.00000"));<br />
	 * System.out.println(convert(Float.class, "-128.00000"));<br />
	 * System.out.println(convert(Float[].class, -128.00000));<br />
	 * System.out.println(convert(Float.class, -128.00000));<br />
	 * System.out.println(convert(BigDecimal.class, "-128"));<br />
	 * System.out.println(convert(BigDecimal.class, -128));<br />
	 * System.out.println(convert(BigDecimal.class, "-128.00000"));<br />
	 * System.out.println(convert(BigDecimal.class, -128.00000));<br />
	 * System.out.println(convert(Date.class, "0"));<br />
	 * System.out.println(convert(Date.class, "1451577600000"));<br />
	 * System.out.println(convert(Date[].class, new Object[]{"0","2016-01-01 00:00:00","1451577600000",1451577600000L}));<br />
	 * System.out.println(convert(Date[].class, Arrays.asList(new Object[]{"0","2016-01-01 00:00:00","1451577600000",1451577600000L})));<br />
	 * System.out.println(convert(List.class, new Date[]{new Date(0),new Date(1451577600000L)}));<br />
	 * System.out.println(convert(List.class, Arrays.asList(new Object[]{(byte)1,"1",1,1L})));<br />
	 * System.out.println(convert(Set.class, Arrays.asList(new Object[]{(byte)1,"1",1,1L})));<br />
	 * System.out.println(convert(Set.class, Arrays.asList(new Long[]{1L,1L,2L})));<br />
	 * System.out.println(convert(Set.class, new Long[]{1L,1L,2L}));<br />
	 * System.out.println(convert(BigDecimal[].class, new Object[]{(byte)1,1,1L,"1"}));<br />
	 * @param destClazz 预期将要输出为何种数据类型
	 * @param srcObj 输入任意类型的数据
	 * @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	public static final <T> T convert(Class<T> destClazz, final Object srcObj) {
		if(null == srcObj) {
			return null;
		}
		if(null == destClazz) {
			return null;
		}
		if(destClazz.isAssignableFrom(srcObj.getClass())) {
			return (T) srcObj;
		}
		
		if(isPrimitive(destClazz)) {
			Class<?> boxingType = typeBoxing(destClazz);
			return (T) unBoxing(convert(boxingType, srcObj));
		}
		else if(destClazz.isArray()) {
			return convertArray(destClazz, srcObj);
		} 
		else if(destClazz.isEnum()) {
			return Object2Enum.convert(destClazz, srcObj);
		}
		else {
			T ret = null;
			if(String.class.isAssignableFrom(destClazz)) {
				ret = (T) Object2String.convert(srcObj);
			}
			else if(isNumber(destClazz)) {
				if(Long.class.isAssignableFrom(destClazz)) {
					ret = (T) Object2Long.convert(srcObj);
				}
				else if(Integer.class.isAssignableFrom(destClazz)) {
					ret = (T) Object2Integer.convert(srcObj);
				}
				else if(BigDecimal.class.isAssignableFrom(destClazz)) {
					ret = (T) Object2BigDecimal.convert(srcObj);
				}
				else if(Double.class.isAssignableFrom(destClazz)) {
					ret = (T) Object2Double.convert(srcObj);
				}
				else if(Float.class.isAssignableFrom(destClazz)) {
					ret = (T) Object2Float.convert(srcObj);
				}
				else if(Short.class.isAssignableFrom(destClazz)) {
					ret = (T) Object2Short.convert(srcObj);
				}
				else if(Byte.class.isAssignableFrom(destClazz)) {
					ret = (T) Object2Byte.convert(srcObj);
				} 
			}
			else if(Date.class.isAssignableFrom(destClazz)) {
				ret = (T) Object2Date.convert(srcObj);
			}
			else if(Boolean.class.isAssignableFrom(destClazz)) {
				ret = (T) Object2Boolean.convert(srcObj);
			}
			else if(Character.class.isAssignableFrom(destClazz)) {
				ret = (T) Object2Character.convert(srcObj);
			}
			else if(Collection.class.isAssignableFrom(destClazz)) {
				return (T) toCollection(destClazz, srcObj);
			}
			else {
				throw new BaseException(msg2Code, msg2, destClazz.getName());
			}
			if(null == ret) {
				throw new BaseException(msg3Code, msg3, srcObj.getClass().getName(), srcObj, destClazz.getName());
			} else {
				return ret;
			}
		}
	}

	/** @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	private static final <T> Collection<Object> toCollection(Class<T> destClazz, Object srcObj) {
		Collection<Object> coll = null;
		if(destClazz.isInterface()) {
			if(List.class.isAssignableFrom(destClazz)) {
				coll = new ArrayList<>();
			} 
			else if(Set.class.isAssignableFrom(destClazz)) {
				coll = new HashSet<>();
			} 
			else if(Queue.class.isAssignableFrom(destClazz)) {
				coll = new LinkedList<>();
			} 
			else {
				throw new BaseException("dataTypeUtils_8h3kj", "暂不支持向集合类型{0}转换", destClazz.getName());
			}
		} else {
			try {
				coll = (Collection<Object>) destClazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new BaseException("dataTypeUtils_83hnk", "无法对类型{0}进行实例化", e, destClazz.getName());
			}
		}
		Class<?> objClazz = srcObj.getClass();
		if(objClazz.isArray()) {
			coll.addAll(Arrays.asList((Object[]) boxing(srcObj)));
		}
		else if(srcObj instanceof Collection) {
			coll.addAll((Collection<? extends Object>) srcObj);
		}
		else {
			coll.add(srcObj);
		}
		return coll;
	}

	/** @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	private static final <T> T convertArray(Class<T> destClazz, Object srcObj) {
		Object[] targetObjectArray = null;
		Class<?> objClazz = srcObj.getClass();
		if(objClazz.isArray()) {
			targetObjectArray = (Object[]) boxing(srcObj);
		} 
		else if(Collection.class.isAssignableFrom(objClazz)) {
			targetObjectArray = ((Collection<?>) srcObj).toArray();
		}
		else {
			targetObjectArray = new Object[]{ srcObj };
		}
		Class<?> everyClazz = destClazz.getComponentType();
		Object[] newOjb = (Object[]) Array.newInstance(everyClazz, targetObjectArray.length);
		for (int i = 0; i < targetObjectArray.length; i++) {
			newOjb[i] = convert(everyClazz, targetObjectArray[i]);
		}
		return (T) newOjb;
	}

	/**判断输入数据是否为null<br />数组与集合类内的每个元素都为null那么输入数据被视作null
	 * @author hcqt@qq.com */
	public static final boolean deepIsNull(Object obj) {
		if(null == obj) {
			return true;
		}
		Class<Object[]> arrClazz = Object[].class;
		Object[] newObj = convertArray(arrClazz, obj);
		for (Object it : newObj) {
			if(null != it) {
				return false;
			}
		}
		return true;
	}

	/**与Java自己的toString不同在于，如果是数组，打印数组内容而不再是打印内存地址
	 * @author hcqt@qq.com */
	public static final String toStr(Object obj) {
		if(obj == null) {
			return null;
		}
		if(obj.getClass().isArray()) {
			if(isPrimitive(obj)) {
				if (obj instanceof byte[]) {
					return Arrays.toString((byte[])obj);
				}
				else if (obj instanceof short[]) {
					return Arrays.toString((short[])obj);
				}
				else if (obj instanceof int[]) {
					return Arrays.toString((int[])obj);
				}
				else if (obj instanceof long[]) {
					return Arrays.toString((long[])obj);
				}
				else if (obj instanceof double[]) {
					return Arrays.toString((double[])obj);
				}
				else if (obj instanceof float[]) {
					return Arrays.toString((float[])obj);
				}
				else if (obj instanceof char[]) {
					return Arrays.toString((char[])obj);
				}
				else if (obj instanceof boolean[]) {
					return Arrays.toString((boolean[])obj);
				}
				return Arrays.toString((Object[]) obj);
			} else {
				return Arrays.deepToString((Object[]) obj);
			}
		}
		return obj.toString();
	}

	/**判断输入的数据是否为基本数据类型<br />基本数据类型和基本数据类型的数组类型都是做是基本数据类型<br />null视作非基本数据类型
	 * @see #isPrimitive(Class)
	 * @author hcqt@qq.com */
	public static final boolean isPrimitive(Object obj) {
		if(null == obj) {
			return false;
		}
		return isPrimitive(obj.getClass());
	}

	/**判断输入的数据类型是否为基本数据类型<br />基本数据类型和基本数据类型的数组类型都是做是基本数据类型<br />null视作非基本数据类型<br />void为基本数据类型
	 * System.out.println(isPrimitive(Long[].class));  //false<br />
	 * System.out.println(isPrimitive(Long.class));    //false<br />
	 * System.out.println(isPrimitive(long[].class));  //true<br />
	 * System.out.println(isPrimitive(long.class));    //true<br />
	 * System.out.println(isPrimitive(void.class));    //true<br />
	 * System.out.println(isPrimitive(Object[].class));//false<br />
	 * System.out.println(isPrimitive(Object.class));  //false<br />
	 * @author hcqt@qq.com */
	public static final boolean isPrimitive(Class<?> clazz) {
		if(null == clazz) {
			return false;
		}
		if(clazz.isPrimitive()) {
			return true;
		}
		if(clazz.isArray()) {
			return int[].class.isAssignableFrom(clazz)
					|| long[].class.isAssignableFrom(clazz)
					|| double[].class.isAssignableFrom(clazz)
					|| float[].class.isAssignableFrom(clazz)
					|| short[].class.isAssignableFrom(clazz)
					|| byte[].class.isAssignableFrom(clazz)
					|| char[].class.isAssignableFrom(clazz)
					|| boolean[].class.isAssignableFrom(clazz);
		}
		return false;
	}

	/**判断输入的数据是否是char
	 * @see #isChar(Class)
	 * @author hcqt@qq.com */
	public static final boolean isChar(Object obj) {
		if(obj == null) {
			return false;
		}
		if(isChar(obj.getClass())) {
			return true;
		}
		return obj.toString().length() == 1;
	}

	/**判断输入的数据类型是否是char
	 * @author hcqt@qq.com */
	public static final boolean isChar(Class<?> clazz) {
		if(clazz == null) {
			return false;
		}
		if(char.class.isAssignableFrom(clazz)) {
			return true;
		}
		if(Character.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}

	/**判断输入的数据是否是数字<br />支持基本数据类型和装箱数据类型的判断<br />支持数组数据类型的判断<br />
	 * null视作不是数字<br />存在容错机制，如果数据类型不是数字，继续判断toString字符串是否为数字，并兼容正负号<br />
	 * @see #isNumber(Class)
	 * @author hcqt@qq.com */
	public static final boolean isNumber(Object obj) {
		if(null == obj) {
			return false;
		}
		if(isNumber(obj.getClass())) {
			return true;
		}
		// 进行容错，如果数据类型对不上，但依然有可能是字符串数字，那么用正则表达式判断是否是数字，并兼容正负号
		return obj.toString().matches("^(\\-|\\+)?\\d+(\\.\\d+)?$");
	}

	/**判断输入的数据类型是否是数字<br />支持基本数据类型和装箱数据类型的判断<br />支持数组数据类型的判断<br />null视作不是数字<br />
	 * System.out.println(isNumber(Long[].class));//true<br />
	 * System.out.println(isNumber(Long.class));  //true<br />
	 * System.out.println(isNumber(long.class));  //true<br />
	 * System.out.println(isNumber(long.class));  //true<br />
	 * System.out.println(isNumber(void.class));  //false<br />
	 * System.out.println(isNumber(Object[].class));//false<br />
	 * System.out.println(isNumber(Object.class));//false<br />
	 * @author hcqt@qq.com */
	public static final boolean isNumber(Class<?> clazz) {
		if(null == clazz) {
			return false;
		}
		if(Number.class.isAssignableFrom(clazz)) {
			return true;
		}
		if(Number[].class.isAssignableFrom(clazz)) {
			return true;
		}
		if(clazz.isArray()) {
			return int[].class.isAssignableFrom(clazz)
					|| long[].class.isAssignableFrom(clazz)
					|| double[].class.isAssignableFrom(clazz)
					|| float[].class.isAssignableFrom(clazz)
					|| short[].class.isAssignableFrom(clazz)
					|| byte[].class.isAssignableFrom(clazz)
					|| char[].class.isAssignableFrom(clazz);
		}
		if(isPrimitive(clazz)) {
			return int.class.isAssignableFrom(clazz)
					|| long.class.isAssignableFrom(clazz)
					|| double.class.isAssignableFrom(clazz)
					|| float.class.isAssignableFrom(clazz)
					|| short.class.isAssignableFrom(clazz)
					|| byte.class.isAssignableFrom(clazz)
					|| char.class.isAssignableFrom(clazz);
		}
		return false;
	}

	/**判断对象是否是日期；<br />
	 * 进行类型判断以及内容判断；<br />
	 * 内容判断为判断字符串格式是否是时间格式；<br />
	 * 字符串判断：<br />日期部分兼容减号、斜杠、点号分隔；<br />时间部分兼容冒号、点号；<br />兼容空格；<br />
	 * @author hcqt@qq.com */
	public static final boolean isDate(Object obj) {
		if(null == obj) {
			return false;
		}
		if(isDate(obj.getClass())) {
			return true;
		}
		// 进行容错，如果数据类型对不上，但依然有可能是字符串日期，那么用正则表达式判断是否日期，日期部分兼容减号、斜杠、点号分隔；时间部分兼容冒号、点号；兼容空格；
		return obj.toString().matches("^"
				+ "( *)(\\d{1,4})( *)" //年
				+ "((-|/|\\.)?)( *)((\\d{1,2})?)( *)" //月
				+ "((-|/|\\.)?)( *)((\\d{1,2})?)( *)" //日
				+ "((( +)\\d{1,2})?)( *)" //时
				+ "((:|\\.)?)( *)((\\d{1,2})?)( *)" //分
				+ "((:|\\.)?)( *)((\\d{1,2})?)( *)" //秒
				+ "((:|\\.)?)( *)((\\d{1,3})?)( *)" //毫秒
				+ "$");
	}

	/**判断Class是否是日期
	 * @author hcqt@qq.com */
	public static final boolean isDate(Class<?> clazz) {
		if(null == clazz) {
			return false;
		}
		return Date.class.isAssignableFrom(clazz);
	}

	/**对输入数据进行装箱<br />如果输入数据是基本数据类型则返回装箱数据类型的数据<br />可以对基本数据类型的数组进行装箱<br />
	 * @see #typeBoxing(Class)
	 * @author hcqt@qq.com */
	public static final Object boxing(Object srcObj) {
		if(null == srcObj) {
			return null;
		}
		Class<?> objClazz = srcObj.getClass();
		if(!isPrimitive(objClazz)) {
			return srcObj;
		}
		if(!objClazz.isArray()) {
			if(int.class.isAssignableFrom(objClazz)) {
				return Integer.valueOf((int) srcObj);
			} 
			else if(long.class.isAssignableFrom(objClazz)) {
				return Long.valueOf((long) srcObj);
			}
			else if(double.class.isAssignableFrom(objClazz)) {
				return Double.valueOf((double) srcObj);
			}
			else if(float.class.isAssignableFrom(objClazz)) {
				return Float.valueOf((float) srcObj);
			}
			else if(short.class.isAssignableFrom(objClazz)) {
				return Short.valueOf((short) srcObj);
			}
			else if(byte.class.isAssignableFrom(objClazz)) {
				return Byte.valueOf((byte) srcObj);
			}
			else if(char.class.isAssignableFrom(objClazz)) {
				return Character.valueOf((char) srcObj);
			}
			else if(boolean.class.isAssignableFrom(objClazz)) {
				return Boolean.valueOf((boolean) srcObj);
			}
		} else {
			if(int[].class.isAssignableFrom(objClazz)) {
				int[] obj = (int[]) srcObj;
				Integer[] ret = new Integer[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			} 
			else if(long[].class.isAssignableFrom(objClazz)) {
				long[] obj = (long[]) srcObj;
				Long[] ret = new Long[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(double[].class.isAssignableFrom(objClazz)) {
				double[] obj = (double[]) srcObj;
				Double[] ret = new Double[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(float[].class.isAssignableFrom(objClazz)) {
				float[] obj = (float[]) srcObj;
				Float[] ret = new Float[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(short[].class.isAssignableFrom(objClazz)) {
				short[] obj = (short[]) srcObj;
				Short[] ret = new Short[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(byte[].class.isAssignableFrom(objClazz)) {
				byte[] obj = (byte[]) srcObj;
				Byte[] ret = new Byte[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(char[].class.isAssignableFrom(objClazz)) {
				char[] obj = (char[]) srcObj;
				Character[] ret = new Character[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(boolean[].class.isAssignableFrom(objClazz)) {
				boolean[] obj = (boolean[]) srcObj;
				Boolean[] ret = new Boolean[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
		}
		return srcObj;
	}

	/**对输入数据进行拆箱<br />如果输入数据是包装数据类型则返回拆箱数据类型的数据<br />可以对包装数据类型的数组进行拆箱<br />
	 * @see #typeUnBoxing(Class)
	 * @author hcqt@qq.com */
	public static final Object unBoxing(Object obj) {
		if(null == obj) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		if(isPrimitive(clazz)) {
			return obj;
		}
		if(!clazz.isArray()) {
			if(Integer.class.isAssignableFrom(clazz)) {
				return (int) obj;
			} 
			else if(Long.class.isAssignableFrom(clazz)) {
				return (long) obj;
			}
			else if(Double.class.isAssignableFrom(clazz)) {
				return (double) obj;
			}
			else if(Float.class.isAssignableFrom(clazz)) {
				return (float) obj;
			}
			else if(Short.class.isAssignableFrom(clazz)) {
				return (short) obj;
			}
			else if(Byte.class.isAssignableFrom(clazz)) {
				return (byte) obj;
			}
			else if(Character.class.isAssignableFrom(clazz)) {
				return (char) obj;
			}
			else if(Boolean.class.isAssignableFrom(clazz)) {
				return (boolean) obj;
			}
		} else {
			if(Integer[].class.isAssignableFrom(clazz)) {
				Integer[] tmp = (Integer[]) obj;
				int[] newObj = new int[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (int) unBoxing(tmp[i]);
				}
				return newObj;
			} 
			else if(Long[].class.isAssignableFrom(clazz)) {
				Long[] tmp = (Long[]) obj;
				long[] newObj = new long[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (long) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Double[].class.isAssignableFrom(clazz)) {
				Double[] tmp = (Double[]) obj;
				double[] newObj = new double[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (double) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Float[].class.isAssignableFrom(clazz)) {
				Float[] tmp = (Float[]) obj;
				float[] newObj = new float[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (float) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Short[].class.isAssignableFrom(clazz)) {
				Short[] tmp = (Short[]) obj;
				short[] newObj = new short[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (short) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Byte[].class.isAssignableFrom(clazz)) {
				Byte[] tmp = (Byte[]) obj;
				byte[] newObj = new byte[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (byte) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Character[].class.isAssignableFrom(clazz)) {
				Character[] tmp = (Character[]) obj;
				char[] newObj = new char[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (char) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Boolean[].class.isAssignableFrom(clazz)) {
				Boolean[] tmp = (Boolean[]) obj;
				boolean[] newObj = new boolean[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (boolean) unBoxing(tmp[i]);
				}
				return newObj;
			}
		}
		return obj;
	}

	/**对输入数据类型进行装箱<br />把八种基本数据类型装箱为包装类型<br />支持对基本数据类型的数组类型装箱为包装类型<br />
	 * void类型以及非基本数据类型不进行装箱原样返回<br />输入null返回null不作处理<br />
	 * System.out.println(typeBoxing(Long[].class));// 原样返回=>class [Ljava.lang.Long;<br />
	 * System.out.println(typeBoxing(Long.class));// 原样返回=>class java.lang.Long<br />
	 * System.out.println(typeBoxing(long[].class));// 装箱=>class [Ljava.lang.Long;<br />
	 * System.out.println(typeBoxing(long.class));// 装箱=>class java.lang.Long<br />
	 * System.out.println(typeBoxing(void.class));// 原样返回=>void<br />
	 * System.out.println(typeBoxing(Object[].class));// 原样返回=>class [Ljava.lang.Object;<br />
	 * System.out.println(typeBoxing(Object.class));// 原样返回=>class java.lang.Object<br />
	 * @see #typeUnBoxing(Class)
	 * @author hcqt@qq.com */
	public static final Class<?> typeBoxing(Class<?> clazz) {
		if(null == clazz) {
			return null;
		}
		if(!isPrimitive(clazz)) {
			return clazz;
		}
		if(!clazz.isArray()) {
			if(int.class.isAssignableFrom(clazz)) {
				return Integer.class;
			} 
			else if(long.class.isAssignableFrom(clazz)) {
				return Long.class;
			}
			else if(double.class.isAssignableFrom(clazz)) {
				return Double.class;
			}
			else if(float.class.isAssignableFrom(clazz)) {
				return Float.class;
			}
			else if(short.class.isAssignableFrom(clazz)) {
				return Short.class;
			}
			else if(byte.class.isAssignableFrom(clazz)) {
				return Byte.class;
			}
			else if(char.class.isAssignableFrom(clazz)) {
				return Character.class;
			}
			else if(boolean.class.isAssignableFrom(clazz)) {
				return Boolean.class;
			}
		} else {
			if(int[].class.isAssignableFrom(clazz)) {
				return Integer[].class;
			} 
			else if(long[].class.isAssignableFrom(clazz)) {
				return Long[].class;
			}
			else if(double[].class.isAssignableFrom(clazz)) {
				return Double[].class;
			}
			else if(float[].class.isAssignableFrom(clazz)) {
				return Float[].class;
			}
			else if(short[].class.isAssignableFrom(clazz)) {
				return Short[].class;
			}
			else if(byte[].class.isAssignableFrom(clazz)) {
				return Byte[].class;
			}
			else if(char[].class.isAssignableFrom(clazz)) {
				return Character[].class;
			}
			else if(boolean[].class.isAssignableFrom(clazz)) {
				return Boolean[].class;
			}
		}
		return clazz;
	}

	/**对输入数据类型进行拆箱<br />把八种基本数据类型的包装类拆箱为基本类型<br />支持对包装数据类型的数组类型拆箱为基本数据类型的数组类型<br />
	 * void类型和基本数据类型不进行拆箱原样返回<br />输入null返回null不作处理<br />
	 * System.out.println(typeUnBoxing(Long[].class));// 拆箱=>class [J<br />
	 * System.out.println(typeUnBoxing(Long.class));// 拆箱=>long<br />
	 * System.out.println(typeUnBoxing(long[].class));// 原样返回=>class [J<br />
	 * System.out.println(typeUnBoxing(long.class));// 原样返回=>long<br />
	 * System.out.println(typeUnBoxing(void.class));// 原样返回=>void<br />
	 * System.out.println(typeUnBoxing(Object[].class));// 原样返回=>class [Ljava.lang.Object;<br />
	 * System.out.println(typeUnBoxing(Object.class));// 原样返回=>class java.lang.Object<br />
	 * @see #typeBoxing(Class)
	 * @author hcqt@qq.com */
	public static final Class<?> typeUnBoxing(Class<?> clazz) {
		if(null == clazz) {
			return null;
		}
		if(isPrimitive(clazz)) {
			return clazz;
		}
		if(!clazz.isArray()) {
			if(Integer.class.isAssignableFrom(clazz)) {
				return int.class;
			} 
			else if(Long.class.isAssignableFrom(clazz)) {
				return long.class;
			}
			else if(Double.class.isAssignableFrom(clazz)) {
				return double.class;
			}
			else if(Float.class.isAssignableFrom(clazz)) {
				return float.class;
			}
			else if(Short.class.isAssignableFrom(clazz)) {
				return short.class;
			}
			else if(Byte.class.isAssignableFrom(clazz)) {
				return byte.class;
			}
			else if(Character.class.isAssignableFrom(clazz)) {
				return char.class;
			}
			else if(Boolean.class.isAssignableFrom(clazz)) {
				return boolean.class;
			}
		} else {
			if(Integer[].class.isAssignableFrom(clazz)) {
				return int[].class;
			} 
			else if(Long[].class.isAssignableFrom(clazz)) {
				return long[].class;
			}
			else if(Double[].class.isAssignableFrom(clazz)) {
				return double[].class;
			}
			else if(Float[].class.isAssignableFrom(clazz)) {
				return float[].class;
			}
			else if(Short[].class.isAssignableFrom(clazz)) {
				return short[].class;
			}
			else if(Byte[].class.isAssignableFrom(clazz)) {
				return byte[].class;
			}
			else if(Character[].class.isAssignableFrom(clazz)) {
				return char[].class;
			}
			else if(Boolean[].class.isAssignableFrom(clazz)) {
				return boolean[].class;
			}
		}
		return clazz;
	}

	public static final String msg2Code = "dataTypeUtils_i4nCf";
	public static final String msg2 = "指定的数据类型“{0}”暂不支持，无法转换";
	
	public static final String msg3Code = "dataTypeUtils_0nFck";
	public static final String msg3 = "无法将数据类型为“{0}”的数据“{1}”转换为类型“{2}”";

	/** @author hcqt@qq.com */
	static final class DataTypeException extends BaseException {

		private static final long serialVersionUID = 4654330042965245163L;

		/** @author hcqt@qq.com */
		public DataTypeException() {
			super();
		}

		/** @author hcqt@qq.com */
		public DataTypeException(String code, String message, Object... msgArg) {
			super(code, message, msgArg);
		}

		/** @author hcqt@qq.com */
		public DataTypeException(String code, String message, Throwable cause, Object... msgArg) {
			super(code, message, cause, msgArg);
		}

	}

	/** @author hcqt@qq.com */
	private static final class CatchDataTypeException {

		/**用于抛出无法转换数据类型的异常——输入的数据无法转换为预期的类型<br />该异常总是抛出，使用前你必须确定你是需要抛出此异常
		 * @param destClazz 预期的类型
		 * @param srcObj 输入的数据
		 * @author hcqt@qq.com */
		private static final <T> DataTypeException returnCouldNotConvertException(Class<T> destClazz, Object srcObj) {
			return new DataTypeException(
					"SYS_COMMON_DATA_TYPE_CONVERT_9j2gh", 
					"类型为{0}的数据“{1}”无法转换为类型“{2}”", 
					srcObj == null ? "null" : srcObj.getClass(), 
					srcObj, 
					destClazz == null ? "null" : destClazz.getName());
		}

//		/**检查输入数据是否是一个数字，不是则抛出异常
//		 * @param destClazz 预期的类型
//		 * @param srcObj 输入的数据
//		 * @author hcqt@qq.com */
//		private static final <T> void isNumber(Class<T> destClazz, Object srcObj) {
//			if(!DataTypeUtils.isNumber(srcObj)) {
//				throwNotNumberException(destClazz, srcObj);
//			}
//		}
//
//		/**抛出异常——输入数据不是数字
//		 * @param destClazz 预期的类型
//		 * @param srcObj 输入的数据
//		 * @author hcqt@qq.com */
//		private static final <T> DataTypeException throwNotNumberException(Class<T> destClazz, Object srcObj) {
//			return new DataTypeException(
//					"SYS_COMMON_DATA_TYPE_CONVERT_j8Nt2", 
//					"类型为{0}的数据“{1}”不是数字，无法转换为类型“{2}”", 
//					srcObj.getClass(), 
//					srcObj, 
//					destClazz.getName());
//		}
//
//		/**如果不是小数，则抛出无法转换数据类型的异常
//		 * @author hcqt@qq.com */
//		static final <T> void isDecimal(Class<T> clazz, BigDecimal targetData) {
//			BigDecimal bigDecimal = targetData.setScale(0, BigDecimal.ROUND_CEILING);
//			if(targetData.compareTo(bigDecimal) != 0) {
//				throw new DataTypeException(
//						"SYS_COMMON_DATA_TYPE_CONVERT_5bhsq", 
//						ResMsgUtils.resolve("类型为{0}的数据“{1}”不是小数，无法转换为类型“{2}”", targetData.getClass(), targetData, clazz.getName()), 
//						targetData, 
//						clazz.getName());
//			}
//		}

		/**如果是小数，则抛出无法转换数据类型的异常
		 * @author hcqt@qq.com */
		private static final <T> void notDecimal(Class<T> clazz, BigDecimal targetData) {
			BigDecimal bigDecimal = targetData.setScale(0, BigDecimal.ROUND_CEILING);
			if(targetData.compareTo(bigDecimal) != 0) {
				throw new DataTypeException(
						"SYS_COMMON_DATA_TYPE_CONVERT_5bhsq", 
						ResMsgUtils.resolve("类型为{0}的数据“{1}”为小数，无法转换为类型“{2}”", targetData.getClass(), targetData, clazz.getName()), 
						targetData, 
						clazz.getName());
			}
		}

//		/**检查目标数字是否介于最大值与最小值之间，符合指定数据类型的取值范围
//		 * @author hcqt@qq.com */
//		static final <T> void isBetweenSpan(final Class<T> clazz, final BigDecimal targetData, final BigDecimal max_value, final BigDecimal min_value) {
//			if(targetData.compareTo(max_value) == 1 || targetData.compareTo(min_value) == -1) {
//				throw new DataTypeException(
//						"SYS_COMMON_DATA_TYPE_CONVERT_n54di", 
//						ResMsgUtils.resolve("数据“{0}”不在数据类型“{1}”可以容纳的数值范围中", targetData, clazz.getName()), 
//						targetData, 
//						clazz.getName());
//			}
//		}

		private static final BigDecimal LongMax = BigDecimal.valueOf(Long.MAX_VALUE);
		private static final BigDecimal LongMin = BigDecimal.valueOf(Long.MIN_VALUE);
		
		private static final BigDecimal IntegerMax = BigDecimal.valueOf(Integer.MAX_VALUE);
		private static final BigDecimal IntegerMin = BigDecimal.valueOf(Integer.MIN_VALUE);
		
		private static final BigDecimal ShortMax = BigDecimal.valueOf(Short.MAX_VALUE);
		private static final BigDecimal ShortMin = BigDecimal.valueOf(Short.MIN_VALUE);
		
		private static final BigDecimal ByteMax = BigDecimal.valueOf(Byte.MAX_VALUE);
		private static final BigDecimal ByteMin = BigDecimal.valueOf(Byte.MIN_VALUE);
		
		private static final BigDecimal CharMax = BigDecimal.valueOf(Character.MAX_VALUE);
		private static final BigDecimal CharMin = BigDecimal.valueOf(Character.MIN_VALUE);
		
		private static final BigDecimal DoubleMax = new BigDecimal(Double.toString(Double.MAX_VALUE));// TODO 此数值疑似不准确
		private static final BigDecimal DoubleMin = new BigDecimal(Double.toString(-Double.MAX_VALUE));// TODO 此数值疑似不准确
		
		private static final BigDecimal FloatMax = new BigDecimal(Float.toString(Float.MAX_VALUE));// TODO 此数值疑似不准确
		private static final BigDecimal FloatMin = new BigDecimal(Float.toString(-Float.MAX_VALUE));// TODO 此数值疑似不准确

		/**@see #isBetweenSpan(Class, Object)
		 * @author hcqt@qq.com */
		private static final <T> void isBetweenSpan(Class<T> destClazz, BigDecimal srcObj) {
			isBetweenSpan(destClazz, (Object) srcObj);
		}

		/**检查目标数字是否介于最大值与最小值之间，符合指定数据类型的取值范围
		 * @author hcqt@qq.com */
		private static final <T> void isBetweenSpan(Class<T> destClazz, Object srcObj) {
			if(!DataTypeUtils.isNumber(srcObj)) {
				CatchDataTypeException.returnCouldNotConvertException(destClazz, srcObj);
			}
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
			else if(Character.class.isAssignableFrom(destClazz)) {
				max = CharMax;
				min = CharMin;
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

	/** @author hcqt@qq.com */
	private static final class Object2Byte {

		/** @author hcqt@qq.com */
		private static final <T> Byte convert(Object targetObject) {
			if(targetObject.getClass().isEnum()) {
				return convert(((Enum<?>)targetObject).ordinal());
			}
			else if(isNumber(targetObject)) {
				BigDecimal targetData = new BigDecimal(targetObject.toString());
				CatchDataTypeException.notDecimal(Byte.class, targetData);
				CatchDataTypeException.isBetweenSpan(Byte.class, targetData);
				if(targetObject instanceof String
						|| targetObject instanceof Long
						|| targetObject instanceof Byte
						|| targetObject instanceof Short
						|| targetObject instanceof Integer
						|| targetObject instanceof BigDecimal
						|| targetObject instanceof Float
						|| targetObject instanceof Double) {
					return Byte.valueOf(targetData.byteValue());
				}
				else {
					throw CatchDataTypeException.returnCouldNotConvertException(Byte.class, targetObject);
				}
			} 
			else if(isChar(targetObject)) {
				return Byte.valueOf(Integer.valueOf((int) targetObject.toString().charAt(0)).byteValue());
			}
			else if(isDate(targetObject)) {
				Date d = DataTypeUtils.convert(Date.class, targetObject);
				return convert(d.getTime());
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Byte.class, targetObject);
		}

	}

	/** @author hcqt@qq.com */
	private static final class Object2Short {

		/** @author hcqt@qq.com */
		private static final Short convert(final Object targetObject) {
			if(targetObject.getClass().isEnum()) {
				return convert(((Enum<?>)targetObject).ordinal());
			}
			else if(isNumber(targetObject)) {
				BigDecimal targetData = new BigDecimal(targetObject.toString());
				CatchDataTypeException.notDecimal(Short.class, targetData);
				CatchDataTypeException.isBetweenSpan(Short.class, targetData);
				if(targetObject instanceof String
						|| targetObject instanceof Long
						|| targetObject instanceof Byte
						|| targetObject instanceof Short
						|| targetObject instanceof Integer
						|| targetObject instanceof BigDecimal
						|| targetObject instanceof Float
						|| targetObject instanceof Double
						) {
					return Short.valueOf(targetData.shortValue());
				}
				else {
					throw CatchDataTypeException.returnCouldNotConvertException(Short.class, targetObject);
				}
			} 
			else if(isChar(targetObject)) {
				return Short.valueOf(Integer.valueOf((int) targetObject.toString().charAt(0)).shortValue());
			}
			else if(isDate(targetObject)) {
				Date d = DataTypeUtils.convert(Date.class, targetObject);
				return convert(d.getTime());
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Short.class, targetObject);
		}

	}

	/** @author hcqt@qq.com */
	private static final class Object2Integer {

		/** @author hcqt@qq.com */
		private static final Integer convert(final Object targetObject) {
			if(targetObject.getClass().isEnum()) {
				return ((Enum<?>)targetObject).ordinal();
			}
			else if(isNumber(targetObject)) {
				BigDecimal targetData = new BigDecimal(targetObject.toString());
				CatchDataTypeException.notDecimal(Integer.class, targetData);
				CatchDataTypeException.isBetweenSpan(Integer.class, targetData);
				if(targetObject instanceof String
						|| targetObject instanceof Long
						|| targetObject instanceof Byte
						|| targetObject instanceof Short
						|| targetObject instanceof Integer
						|| targetObject instanceof BigDecimal
						|| targetObject instanceof Float
						|| targetObject instanceof Double) {
					return Integer.valueOf(targetData.intValue());
				}
				else {
					throw CatchDataTypeException.returnCouldNotConvertException(Integer.class, targetObject);
				}
			} 
			else if(isChar(targetObject)) {
				return Integer.valueOf((int) targetObject.toString().charAt(0));
			}
			else if(isDate(targetObject)) {
				Date d = DataTypeUtils.convert(Date.class, targetObject);
				return convert(d.getTime());
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Integer.class, targetObject);
		}

	}

	/** @author hcqt@qq.com */
	private static final class Object2Long {

		/** @author hcqt@qq.com */
		private static final Long convert(final Object targetObject) {
			if(targetObject.getClass().isEnum()) {
				return Long.valueOf(((Enum<?>)targetObject).ordinal());
			}
			else if(isNumber(targetObject)) {
				BigDecimal targetData = new BigDecimal(targetObject.toString());
				CatchDataTypeException.notDecimal(Long.class, targetData);
				CatchDataTypeException.isBetweenSpan(Long.class, targetData);
				if(targetObject instanceof String
						|| targetObject instanceof Long
						|| targetObject instanceof Byte
						|| targetObject instanceof Short
						|| targetObject instanceof Integer
						|| targetObject instanceof BigDecimal
						|| targetObject instanceof Float
						|| targetObject instanceof Double
						) {
					return Long.valueOf(targetData.longValue());
				}
				else {
					throw CatchDataTypeException.returnCouldNotConvertException(Long.class, targetObject);
				}
			} 
			else if(isChar(targetObject)) {
				return Long.valueOf((int) targetObject.toString().charAt(0));
			}
			else if(isDate(targetObject)) {
				Date d = DataTypeUtils.convert(Date.class, targetObject);
				return Long.valueOf(d.getTime());
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Long.class, targetObject);
		}

	}

	/** @author hcqt@qq.com */
	private static final class Object2Double {

		/** @author hcqt@qq.com */
		private static final Double convert(final Object targetObject) {
			if(targetObject.getClass().isEnum()) {
				return Double.valueOf(((Enum<?>)targetObject).ordinal());
			}
			else if(isNumber(targetObject)) {
				BigDecimal targetData = new BigDecimal((String)targetObject);
				CatchDataTypeException.isBetweenSpan(Double.class, targetData);
				if(targetObject instanceof String
						|| targetObject instanceof Long
						|| targetObject instanceof Byte
						|| targetObject instanceof Short
						|| targetObject instanceof Integer
						|| targetObject instanceof BigDecimal
						|| targetObject instanceof Float
						|| targetObject instanceof Double) {
					return Double.valueOf(targetData.doubleValue());
				}
				else {
					throw CatchDataTypeException.returnCouldNotConvertException(Double.class, targetObject);
				}
			} 
			else if(isChar(targetObject)) {
				return Double.valueOf((int) targetObject.toString().charAt(0));
			}
			else if(isDate(targetObject)) {
				Date d = DataTypeUtils.convert(Date.class, targetObject);
				return convert(d.getTime());
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Double.class, targetObject);
		}

	}

	/** @author hcqt@qq.com */
	private static final class Object2Float {

		/** @author hcqt@qq.com */
		private static final Float convert(final Object targetObject) {
			if(targetObject.getClass().isEnum()) {
				return convert(((Enum<?>)targetObject).ordinal());
			}
			else if(isNumber(targetObject)) {
				BigDecimal targetData = new BigDecimal(targetObject.toString());
				CatchDataTypeException.isBetweenSpan(Float.class, targetData);
				if(targetObject instanceof String
						|| targetObject instanceof Long
						|| targetObject instanceof Byte
						|| targetObject instanceof Short
						|| targetObject instanceof Integer
						|| targetObject instanceof BigDecimal
						|| targetObject instanceof Float
						|| targetObject instanceof Double) {
					return Float.valueOf(targetData.floatValue());
				}
				else {
					throw CatchDataTypeException.returnCouldNotConvertException(Float.class, targetObject);
				}
			} 
			else if(isChar(targetObject)) {
				return Float.valueOf((int) targetObject.toString().charAt(0));
			}
			else if(isDate(targetObject)) {
				Date d = DataTypeUtils.convert(Date.class, targetObject);
				return convert(d.getTime());
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Float.class, targetObject);
		}

	}

	/** @author hcqt@qq.com */
	private static final class Object2BigDecimal {

		/** @author hcqt@qq.com */
		private static final BigDecimal convert(final Object targetObject) {
			if(targetObject.getClass().isEnum()) {
				return BigDecimal.valueOf(((Enum<?>)targetObject).ordinal());
			}
			else if(isNumber(targetObject)) {
				try {
					return new BigDecimal(targetObject.toString());
				} catch(Exception e) {
					throw CatchDataTypeException.returnCouldNotConvertException(BigDecimal.class, targetObject);
				}
			} 
			else if(isChar(targetObject)) {
				return BigDecimal.valueOf((int) targetObject.toString().charAt(0));
			}
			else if(isDate(targetObject)) {
				Date d = DataTypeUtils.convert(Date.class, targetObject);
				return BigDecimal.valueOf(d.getTime());
			}
			throw CatchDataTypeException.returnCouldNotConvertException(BigDecimal.class, targetObject);
		}

	}

	/** @author hcqt@qq.com */
	private static final class Object2Character {

		/** @author hcqt@qq.com */
		private static final Character convert(final Object targetObject) {
			if(isChar(targetObject)) {
				return Character.valueOf(targetObject.toString().charAt(0));
			}
			else if(isNumber(targetObject)) {
				BigDecimal b = DataTypeUtils.convert(BigDecimal.class, targetObject);
				CatchDataTypeException.notDecimal(Character.class, b);
				CatchDataTypeException.isBetweenSpan(Character.class, b);
				return Character.valueOf((char) b.intValue());
			} 
			else if(targetObject.getClass().isEnum()) {
				Integer i = DataTypeUtils.convert(Integer.class, targetObject);
				return convert(i);
			}
			else if(isDate(targetObject)) {
				Date d = DataTypeUtils.convert(Date.class, targetObject);
				return convert(d.getTime());
			}
			else if(targetObject instanceof String) {
				String s = (String) targetObject;
				if(s.length() != 1) {
					throw CatchDataTypeException.returnCouldNotConvertException(Character.class, targetObject);
				}
				return Character.valueOf((s).charAt(0));
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Character.class, targetObject);
		}
		
	}

	/** @author hcqt@qq.com */
	private static final class Object2Boolean {

		/** @author hcqt@qq.com */
		private static final Boolean convert(final Object targetObject) {
			if(targetObject instanceof String) {
				return Boolean.valueOf((String) targetObject);// TODO 异常处理
			}
			else if(targetObject instanceof Boolean) {
				return (Boolean) targetObject;
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Boolean.class, targetObject);
		}
	}

	/** @author hcqt@qq.com */
	private static final class Object2Enum {

		/** @author hcqt@qq.com */
		private static final <T> T convert(final Class<T> clazz, final Object targetObject) {
			T[] _elements = clazz.getEnumConstants();
			if(targetObject instanceof String) {
				for (T _enumElement : _elements) {
					try {
						Method method = _enumElement.getClass().getMethod("name");
						Object enumName = null;
						enumName = method.invoke(_enumElement);
						if(null == enumName) {
							continue;
						}
						if(enumName.toString().equals(targetObject.toString())) {
							return _enumElement;
						}
					} catch (Exception e) { }
				}
			} 
			// 尝试输入值是否可以转换为整型，如果可以，那么以枚举底层整型下标进行转换
			try {
				Integer index = DataTypeUtils.convert(Integer.class, targetObject);
				for (T _enumElement : _elements) {
					try {
						Method method = _enumElement.getClass().getMethod("ordinal");
						Object enumOrdinal = null;
						enumOrdinal = method.invoke(_enumElement);
						if(null == enumOrdinal) {
							continue;
						}
						if(enumOrdinal.toString().equals(index.toString())) {
							return _enumElement;
						}
					} catch (Exception e) { }
				}
			} catch (BaseException e) { }
			throw new DataTypeException(
					"SER_COMMON_META_DATA_54hdQ", 
					ResMsgUtils.resolve("无法把数据类型为“{0}”的数据“{1}”转换到枚举类型“{2}”", targetObject.getClass().getName(), targetObject, clazz), 
					targetObject.getClass().getName(), 
					targetObject, 
					clazz.getName());
		}

	}

	/** @author hcqt@qq.com */
	private static final class Object2String {

		/** @author hcqt@qq.com */
		private static final String convert(final Object targetObject) {
			String ret = null;
			if(null == targetObject) {
				ret = null;
			} 
			else if(isDate(targetObject)) {
				Date d = Object2Date.convert(targetObject);
				ret = DU.format(d, DEF_DATE_FMT);
			}
			if(null == ret) {
				ret = targetObject.toString();
			}
			return ret;
		}

		private static final String DEF_DATE_FMT = "yyyy-MM-dd HH:mm:ss"; 

	}

	/** @author hcqt@qq.com */
	private static final class Object2Date {

		/** @author hcqt@qq.com */
		private static final Date convert(final Object targetObject) {
			if(isDate(targetObject)) {
				if(targetObject instanceof Date) {
					return (Date) targetObject;
				}
				else if(targetObject instanceof String) {
					try { return DU.parse((String) targetObject, datePattern1); } 
					catch (BaseException e) { }
				}
			}
			else if(isNumber(targetObject)) {
				try {  return new Date(DataTypeUtils.convert(Long.class, targetObject)); } 
				catch (BaseException e) { }
			}
			throw CatchDataTypeException.returnCouldNotConvertException(Date.class, targetObject);
		}

		private static final String datePattern1 = "yyyy-MM-dd HH:mm:ss";

	}

}
