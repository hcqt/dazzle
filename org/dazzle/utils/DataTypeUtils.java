package org.dazzle.utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dazzle.common.exception.BaseException;
import org.dazzle.utils.datatype.Object2BigDecimal;
import org.dazzle.utils.datatype.Object2Boolean;
import org.dazzle.utils.datatype.Object2Byte;
import org.dazzle.utils.datatype.Object2Character;
import org.dazzle.utils.datatype.Object2Date;
import org.dazzle.utils.datatype.Object2Double;
import org.dazzle.utils.datatype.Object2Enum;
import org.dazzle.utils.datatype.Object2Float;
import org.dazzle.utils.datatype.Object2Integer;
import org.dazzle.utils.datatype.Object2Long;
import org.dazzle.utils.datatype.Object2Short;
import org.dazzle.utils.datatype.Object2String;

/**@see #convert(Class, Object) 
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
				ret = Object2Character.convert(srcObj);
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
			} else if(Set.class.isAssignableFrom(destClazz)) {
				coll = new HashSet<>();
			} else {
				throw new BaseException("dataTypeUtils_8h3kj", "暂不支持向类型{0}转换", destClazz.getName());
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
			if(null == it) {
				return true;
			}
		}
		return false;
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
		// 进行容错，如果不是数字，但依然有可能呢是char，char可以视作数字
		if(isChar(obj)) {
			return true;
		}
		// 进行容错，如果不是char，但依然有可能是字符串数字，那么用正则表达式判断是否是数字，并兼容正负号
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
		if(isChar(clazz)) {
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

}
