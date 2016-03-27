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

/** @author hcqt@qq.com */
public class DataTypeUtils {
	
	/** @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	public static final <T> T convert(Class<T> clazz, final Object targetObject) {
		if(null == targetObject) {
			return null;
		}
		if(null == clazz) {
			return null;
		}
		if(clazz.isAssignableFrom(targetObject.getClass())) {
			return (T) targetObject;
		}
		
		if(isPrimitive(clazz)) {
			Class<?> boxingType = typeBoxing(clazz);
//			return (T) unBoxing(boxingType, convert(boxingType, targetObject));
			return (T) unBoxing(convert(boxingType, targetObject));
		}
		else if(clazz.isArray()) {
			return convertArray(clazz, targetObject);
		} 
		else if(clazz.isEnum()) {
			return Object2Enum.convert(clazz, targetObject);
		}
		else {
			T ret = null;
			if(String.class.isAssignableFrom(clazz)) {
				ret = (T) Object2String.convert(targetObject);
			}
			else if(isNumber(clazz)) {
				if(Long.class.isAssignableFrom(clazz)) {
					ret = (T) Object2Long.convert(targetObject);
				}
				else if(Integer.class.isAssignableFrom(clazz)) {
					ret = (T) Object2Integer.convert(targetObject);
				}
				else if(BigDecimal.class.isAssignableFrom(clazz)) {
					ret = (T) Object2BigDecimal.convert(targetObject);
				}
				else if(Double.class.isAssignableFrom(clazz)) {
					ret = (T) Object2Double.convert(targetObject);
				}
				else if(Float.class.isAssignableFrom(clazz)) {
					ret = (T) Object2Float.convert(targetObject);
				}
				else if(Short.class.isAssignableFrom(clazz)) {
					ret = (T) Object2Short.convert(targetObject);
				}
				else if(Byte.class.isAssignableFrom(clazz)) {
					ret = Object2Byte.convert(targetObject);
				} 
			}
			else if(Date.class.isAssignableFrom(clazz)) {
				ret = (T) Object2Date.convert(targetObject);
			}
			else if(Boolean.class.isAssignableFrom(clazz)) {
				ret = (T) Object2Boolean.convert(targetObject);
			}
			else if(Character.class.isAssignableFrom(clazz)) {
				ret = Object2Character.convert(targetObject);
			}
			else if(Collection.class.isAssignableFrom(clazz)) {
				return (T) toCollection(clazz, targetObject);
			}
			else {
				throw new BaseException(msg2Code, ResMsgUtils.resolve(msg2, clazz.getName()), clazz.getName());
			}
			if(null == ret) {
				throw new BaseException(msg3Code, ResMsgUtils.resolve(msg3, targetObject.getClass().getName(), targetObject, clazz.getName()), targetObject.getClass().getName(), targetObject, clazz.getName());
			} else {
				return ret;
			}
		}
	}
//	public static void main3(String[] args) {
//		System.out.println(String[].class.getSuperclass());
//		System.out.println(Object[].class.isAssignableFrom(String[].class));
//	}
//	public static void main6(String[] args) throws InstantiationException, IllegalAccessException {
//		System.out.println(RegexUtil[].class.getComponentType());
//	}
//	public static void main7(String[] args) {
//		Class<?> clazz = RegexUtil[].class;
//		Object obj = Arrays.asList(new RegexUtil(),new RegexUtil(),new RegexUtil());
//		Object c = convert(clazz, obj);;
//		System.out.println(c.getClass().getName());
//		System.out.println(c);
//		System.out.println(Arrays.toString((Object[])c));
//	}
//	public static void main(String[] args) {
//		Class<?> clazz = Integer[].class;
//		Object obj = Arrays.asList("2","3",1);
//		Object c = convert(clazz, obj);;
//		System.out.println(c.getClass().getName());
//		System.out.println(c);
//		System.out.println(Arrays.toString((Object[])c));
//	}
//	public static void main1(String[] args) {
//		Class<?> clazz = List.class;
//		Object obj = new String[]{"1","2"};
//		Object c = convert(clazz, obj);;
//		System.out.println(c.getClass().getName());
//		System.out.println(c);
//	}
//	public static void main(String[] args) {
//		Class<?> clazz = HashSet.class;
//		Object a = new int[]{1235421,124525,1235421};
//		Object obj = a;
//		Collection<?> b = toCollection(clazz, obj);
//		System.out.println(b.getClass().getName());
//		System.out.println(b);
//	}
//	public static void main(String[] args) {
//		Class<?> clazz = HashSet.class;
//		List<String> a = new ArrayList<>();
//		a.add("1");
//		a.add("1897878a");
//		a.add("1");
//		Object obj = a;
//		Collection<?> b = toCollection(clazz, obj);
//		System.out.println(b.getClass().getName());
//		System.out.println(b);
//	}
//	public static void main10(String[] args) {
//		Object a = new int[]{1235421,124525,1235421};
//		Object b = convertArray(a.getClass().getComponentType(), a);
//		System.out.println(b.getClass().getName());
//		System.out.println(b);
//	}

	/** @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	private static final <T> Collection<Object> toCollection(Class<T> clazz, Object targetObject) {
		Collection<Object> coll = null;
		if(clazz.isInterface()) {
			if(List.class.isAssignableFrom(clazz)) {
				coll = new ArrayList<>();
			} else if(Set.class.isAssignableFrom(clazz)) {
				coll = new HashSet<>();
			} else {
				throw new BaseException("dataTypeUtils_8h3kj", "暂不支持向类型{0}转换", clazz.getName());
			}
		} else {
			try {
				coll = (Collection<Object>) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new BaseException("dataTypeUtils_83hnk", "无法List类型{0}进行实例化", e, clazz.getName());
			}
		}
		Class<?> objClazz = targetObject.getClass();
		if(objClazz.isArray()) {
			coll.addAll(Arrays.asList((Object[]) boxing(targetObject)));
		}
		else if(targetObject instanceof Collection) {
			coll.addAll((Collection<? extends Object>) targetObject);
		}
		else {
			coll.add(targetObject);
		}
		return coll;
	}

	/** @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	private static final <T> T convertArray(Class<T> clazz, Object targetObject) {
		Object[] targetObjectArray = null;
		Class<?> objClazz = targetObject.getClass();
		if(objClazz.isArray()) {
			targetObjectArray = (Object[]) boxing(targetObject);
		} 
		else if(Collection.class.isAssignableFrom(objClazz)) {
			targetObjectArray = ((Collection<?>) targetObject).toArray();
		}
		else {
			targetObjectArray = new Object[]{ targetObject };
		}
		Class<?> everyClazz = clazz.getComponentType();
		Object[] newOjb = (Object[]) Array.newInstance(everyClazz, targetObjectArray.length);
		for (int i = 0; i < targetObjectArray.length; i++) {
			newOjb[i] = convert(everyClazz, targetObjectArray[i]);
		}
		return (T) newOjb;
	}

	/** @author hcqt@qq.com */
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

//	@SuppressWarnings("unchecked")
//	private static final <T> T convertArray(Class<T> clazz, Object targetObject) {
//		Object[] targetObjectArray = null;
//		if(targetObject.getClass().isArray()) {
//			targetObjectArray = (Object[]) targetObject;
//		} 
//		else if(List.class.isAssignableFrom(targetObject.getClass())) {
//			targetObjectArray = ((List<?>) targetObject).toArray();
//		}
//		else {
//			targetObjectArray = new Object[]{ targetObject };
//		}
//		Object[] newOjb = null;
//		Class<?> everyClazz = null;
//		if(String[].class.isAssignableFrom(clazz)) {
//			newOjb = new String[targetObjectArray.length];
//			everyClazz = String.class;
//		}
//		else if(isNumber(clazz)) {
//			if(Long[].class.isAssignableFrom(clazz)) {
//				newOjb = new Long[targetObjectArray.length];
//				everyClazz = Long.class;
//			}
//			else if(Integer[].class.isAssignableFrom(clazz)) {
//				newOjb = new Integer[targetObjectArray.length];
//				everyClazz = Integer.class;
//			}
//			else if(BigDecimal[].class.isAssignableFrom(clazz)) {
//				newOjb = new BigDecimal[targetObjectArray.length];
//				everyClazz = BigDecimal.class;
//			}
//			else if(Double[].class.isAssignableFrom(clazz)) {
//				newOjb = new Double[targetObjectArray.length];
//				everyClazz = Double.class;
//			}
//			else if(Float[].class.isAssignableFrom(clazz)) {
//				newOjb = new Float[targetObjectArray.length];
//				everyClazz = Float.class;
//			}
//			else if(Short[].class.isAssignableFrom(clazz)) {
//				newOjb = new Short[targetObjectArray.length];
//				everyClazz = Short.class;
//			}
//			else if(Byte[].class.isAssignableFrom(clazz)) {
//				newOjb = new Byte[targetObjectArray.length];
//				everyClazz = Byte.class;
//			} 
//		}
//		else if(Date[].class.isAssignableFrom(clazz)) {
//			newOjb = new Date[targetObjectArray.length];
//			everyClazz = Date.class;
//		}
//		else if(Boolean[].class.isAssignableFrom(clazz)) {
//			newOjb = new Boolean[targetObjectArray.length];
//			everyClazz = Boolean.class;
//		}
//		else if(Character[].class.isAssignableFrom(clazz)) {
//			newOjb = new Character[targetObjectArray.length];
//			everyClazz = Character.class;
//		}
//		else if(Object[].class.isAssignableFrom(clazz)) {
//			newOjb = new Object[targetObjectArray.length];
//			everyClazz = Object.class;
//		}
//		
//		for (int i = 0; i < targetObjectArray.length; i++) {
//			newOjb[i] = convert(everyClazz, targetObjectArray[i]);
//		}
//		return (T) newOjb;
//	}

	/** @author hcqt@qq.com */
	public static final boolean isNumber(Object obj) {
		if(null == obj) {
//			StackTraceElement a = Thread.currentThread().getStackTrace()[1];
//			throw new BaseException("dataTypeUtils_jk3hb", "调用{0}.{1}()要求参数不能为空", a.getClassName(), a.getMethodName());
			return false;
		}
		return isNumber(obj.getClass());
	}

	/** @author hcqt@qq.com */
	public static final boolean isPrimitive(Object obj) {
		if(null == obj) {
			return false;
		}
		return isPrimitive(obj.getClass());
	}

	/** @author hcqt@qq.com */
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

	/** @author hcqt@qq.com */
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
					|| byte[].class.isAssignableFrom(clazz);
		}
		if(isPrimitive(clazz)) {
			return int.class.isAssignableFrom(clazz)
					|| long.class.isAssignableFrom(clazz)
					|| double.class.isAssignableFrom(clazz)
					|| float.class.isAssignableFrom(clazz)
					|| short.class.isAssignableFrom(clazz)
					|| byte.class.isAssignableFrom(clazz);
		}
		return false;
	}

	/** @author hcqt@qq.com */
	public static final Object unBoxing(Object object) {
		if(null == object) {
			return null;
		}
		Class<?> clazz = object.getClass();
		if(isPrimitive(clazz)) {
			return object;
		}
		if(!clazz.isArray()) {
			if(Integer.class.isAssignableFrom(clazz)) {
				return (int) object;
			} 
			else if(Long.class.isAssignableFrom(clazz)) {
				return (long) object;
			}
			else if(Double.class.isAssignableFrom(clazz)) {
				return (double) object;
			}
			else if(Float.class.isAssignableFrom(clazz)) {
				return (float) object;
			}
			else if(Short.class.isAssignableFrom(clazz)) {
				return (short) object;
			}
			else if(Byte.class.isAssignableFrom(clazz)) {
				return (byte) object;
			}
			else if(Character.class.isAssignableFrom(clazz)) {
				return (char) object;
			}
			else if(Boolean.class.isAssignableFrom(clazz)) {
				return (boolean) object;
			}
		} else {
			if(Integer[].class.isAssignableFrom(clazz)) {
				Integer[] tmp = (Integer[]) object;
				int[] newObj = new int[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (int) unBoxing(tmp[i]);
				}
				return newObj;
			} 
			else if(Long[].class.isAssignableFrom(clazz)) {
				Long[] tmp = (Long[]) object;
				long[] newObj = new long[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (long) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Double[].class.isAssignableFrom(clazz)) {
				Double[] tmp = (Double[]) object;
				double[] newObj = new double[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (double) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Float[].class.isAssignableFrom(clazz)) {
				Float[] tmp = (Float[]) object;
				float[] newObj = new float[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (float) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Short[].class.isAssignableFrom(clazz)) {
				Short[] tmp = (Short[]) object;
				short[] newObj = new short[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (short) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Byte[].class.isAssignableFrom(clazz)) {
				Byte[] tmp = (Byte[]) object;
				byte[] newObj = new byte[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (byte) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Character[].class.isAssignableFrom(clazz)) {
				Character[] tmp = (Character[]) object;
				char[] newObj = new char[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (char) unBoxing(tmp[i]);
				}
				return newObj;
			}
			else if(Boolean[].class.isAssignableFrom(clazz)) {
				Boolean[] tmp = (Boolean[]) object;
				boolean[] newObj = new boolean[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					newObj[i] = (boolean) unBoxing(tmp[i]);
				}
				return newObj;
			}
		}
		throw new BaseException("dataTypeUtils_8hjkl", "无法把数据{0}拆箱为类型{1}的简单类型", object, clazz.getName());
	}
//public static void main14(String[] args) {
//	Integer[] i = {1,2};
//	Object a = unBoxing(i);
//	System.out.println(a.getClass().getName());
//	System.out.println(a);
//}
//	private static final Object unBoxing(Class<?> clazz, Object object) {
//		if(!clazz.isArray()) {
//			if(Integer.class.isAssignableFrom(clazz)) {
//				return (int) object;
//			} 
//			else if(Long.class.isAssignableFrom(clazz)) {
//				return (long) object;
//			}
//			else if(Double.class.isAssignableFrom(clazz)) {
//				return (double) object;
//			}
//			else if(Float.class.isAssignableFrom(clazz)) {
//				return (float) object;
//			}
//			else if(Short.class.isAssignableFrom(clazz)) {
//				return (short) object;
//			}
//			else if(Byte.class.isAssignableFrom(clazz)) {
//				return (byte) object;
//			}
//			else if(Character.class.isAssignableFrom(clazz)) {
//				return (char) object;
//			}
//			else if(Boolean.class.isAssignableFrom(clazz)) {
//				return (boolean) object;
//			}
//		} else {
//			if(Integer[].class.isAssignableFrom(clazz)) {
//				Integer[] tmp = (Integer[]) object;
//				int[] newObj = new int[tmp.length];
//				for (int i = 0; i < tmp.length; i++) {
//					newObj[i] = (int) unBoxing(Integer.class, tmp[i]);
//				}
//				return newObj;
//			} 
//			else if(Long[].class.isAssignableFrom(clazz)) {
//				Long[] tmp = (Long[]) object;
//				long[] newObj = new long[tmp.length];
//				for (int i = 0; i < tmp.length; i++) {
//					newObj[i] = (long) unBoxing(Long.class, tmp[i]);
//				}
//				return newObj;
//			}
//			else if(Double[].class.isAssignableFrom(clazz)) {
//				Double[] tmp = (Double[]) object;
//				double[] newObj = new double[tmp.length];
//				for (int i = 0; i < tmp.length; i++) {
//					newObj[i] = (double) unBoxing(Double.class, tmp[i]);
//				}
//				return newObj;
//			}
//			else if(Float[].class.isAssignableFrom(clazz)) {
//				Float[] tmp = (Float[]) object;
//				float[] newObj = new float[tmp.length];
//				for (int i = 0; i < tmp.length; i++) {
//					newObj[i] = (float) unBoxing(Float.class, tmp[i]);
//				}
//				return newObj;
//			}
//			else if(Short[].class.isAssignableFrom(clazz)) {
//				Short[] tmp = (Short[]) object;
//				short[] newObj = new short[tmp.length];
//				for (int i = 0; i < tmp.length; i++) {
//					newObj[i] = (short) unBoxing(Short.class, tmp[i]);
//				}
//				return newObj;
//			}
//			else if(Byte[].class.isAssignableFrom(clazz)) {
//				Byte[] tmp = (Byte[]) object;
//				byte[] newObj = new byte[tmp.length];
//				for (int i = 0; i < tmp.length; i++) {
//					newObj[i] = (byte) unBoxing(Byte.class, tmp[i]);
//				}
//				return newObj;
//			}
//			else if(Character[].class.isAssignableFrom(clazz)) {
//				Character[] tmp = (Character[]) object;
//				char[] newObj = new char[tmp.length];
//				for (int i = 0; i < tmp.length; i++) {
//					newObj[i] = (char) unBoxing(Character.class, tmp[i]);
//				}
//				return newObj;
//			}
//			else if(Boolean[].class.isAssignableFrom(clazz)) {
//				Boolean[] tmp = (Boolean[]) object;
//				boolean[] newObj = new boolean[tmp.length];
//				for (int i = 0; i < tmp.length; i++) {
//					newObj[i] = (boolean) unBoxing(Boolean.class, tmp[i]);
//				}
//				return newObj;
//			}
//		}
//		throw new BaseException("dataTypeUtils_8hjkl", "无法把数据{0}拆箱为类型{1}的简单类型", object, clazz.getName());
//	}

	/** @author hcqt@qq.com */
	public static final Object boxing(Object targetObject) {
		if(null == targetObject) {
			return null;
		}
		Class<?> objClazz = targetObject.getClass();
		if(!isPrimitive(objClazz)) {
			return targetObject;
		}
		if(!objClazz.isArray()) {
			if(int.class.isAssignableFrom(objClazz)) {
				return Integer.valueOf((int) targetObject);
			} 
			else if(long.class.isAssignableFrom(objClazz)) {
				return Long.valueOf((long) targetObject);
			}
			else if(double.class.isAssignableFrom(objClazz)) {
				return Double.valueOf((double) targetObject);
			}
			else if(float.class.isAssignableFrom(objClazz)) {
				return Float.valueOf((float) targetObject);
			}
			else if(short.class.isAssignableFrom(objClazz)) {
				return Short.valueOf((short) targetObject);
			}
			else if(byte.class.isAssignableFrom(objClazz)) {
				return Byte.valueOf((byte) targetObject);
			}
			else if(char.class.isAssignableFrom(objClazz)) {
				return Character.valueOf((char) targetObject);
			}
			else if(boolean.class.isAssignableFrom(objClazz)) {
				return Boolean.valueOf((boolean) targetObject);
			}
		} else {
			if(int[].class.isAssignableFrom(objClazz)) {
				int[] obj = (int[]) targetObject;
				Integer[] ret = new Integer[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			} 
			else if(long[].class.isAssignableFrom(objClazz)) {
				long[] obj = (long[]) targetObject;
				Long[] ret = new Long[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(double[].class.isAssignableFrom(objClazz)) {
				double[] obj = (double[]) targetObject;
				Double[] ret = new Double[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(float[].class.isAssignableFrom(objClazz)) {
				float[] obj = (float[]) targetObject;
				Float[] ret = new Float[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(short[].class.isAssignableFrom(objClazz)) {
				short[] obj = (short[]) targetObject;
				Short[] ret = new Short[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(byte[].class.isAssignableFrom(objClazz)) {
				byte[] obj = (byte[]) targetObject;
				Byte[] ret = new Byte[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(char[].class.isAssignableFrom(objClazz)) {
				char[] obj = (char[]) targetObject;
				Character[] ret = new Character[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
			else if(boolean[].class.isAssignableFrom(objClazz)) {
				boolean[] obj = (boolean[]) targetObject;
				Boolean[] ret = new Boolean[obj.length];
				for (int i = 0; i < obj.length; i++) {
					ret[i] = obj[i];
				}
				return ret;
			}
		}
		throw new BaseException("dataTypeUtils_83h3k", "无法获取类型为{0}的数据{1}进行装箱", objClazz, targetObject);
	}

	/** @author hcqt@qq.com */
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
		throw new BaseException("dataTypeUtils_23ygd", "无法获取类型{0}的装箱类型", clazz.getName());
	}
//public static void main13(String[] args) {
//	System.out.println(typeUnBoxing(Integer[].class));
//}

	/** @author hcqt@qq.com */
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
		throw new BaseException("dataTypeUtils_23ygd", "无法获取类型{0}的装箱类型", clazz.getName());
	}

	public static final String msg2Code = "dataTypeUtils_i4nCf";
	public static final String msg2 = "指定的数据类型{0}暂不支持，无法转换";
	
	public static final String msg3Code = "dataTypeUtils_0nFck";
	public static final String msg3 = "无法将数据类型{0}:数据{1}转换为类型{2}";

}
