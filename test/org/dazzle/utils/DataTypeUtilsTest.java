package org.dazzle.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.dazzle.common.exception.BaseException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/** @author hcqt@qq.com */
public class DataTypeUtilsTest {
	
	/** @author hcqt@qq.com */
	@Test
	public void cvtTest0() {
		cvtTest();
		cvtCollectionTest();
	}

	/** @author hcqt@qq.com */
	@Ignore
	@Test
	public void cvtTest() {
		Object[][] clazzAndObj = {
				{Integer.class,100},
				{Integer.class,Integer.valueOf(100)},
				{Integer.class,(short)100},
				{Integer.class,(byte)100},
				{Integer.class,Short.valueOf((short)100)},
				{Integer.class,Byte.valueOf((byte)100)},
				{Integer.class,"100"},
				{Integer.class,100l},
				{Integer.class,100L},
				{Integer.class,100.000F},
				{Integer.class,100.000f},
				{Integer.class,100.000D},
				{Integer.class,100.000d},
				{Integer.class,new Date(0)},
				{Integer.class,'a'},
				{Integer.class,"a"},
				
				{Integer[].class,100},
				{Integer[].class,Integer.valueOf(100)},
				{Integer[].class,(short)100},
				{Integer[].class,(byte)100},
				{Integer[].class,Short.valueOf((short)100)},
				{Integer[].class,Byte.valueOf((byte)100)},
				{Integer[].class,"100"},
				{Integer[].class,100l},
				{Integer[].class,100L},
				{Integer[].class,100.000F},
				{Integer[].class,100.000f},
				{Integer[].class,100.000D},
				{Integer[].class,100.000d},
				{Integer[].class,new Date(0)},
				{Integer[].class,'a'},
				{Integer[].class,"a"},
				
				{int.class,100},
				{int.class,Integer.valueOf(100)},
				{int.class,(short)100},
				{int.class,(byte)100},
				{int.class,Short.valueOf((short)100)},
				{int.class,Byte.valueOf((byte)100)},
				{int.class,"100"},
				{int.class,100l},
				{int.class,100L},
				{int.class,100.000F},
				{int.class,100.000f},
				{int.class,100.000D},
				{int.class,100.000d},
				{int.class,new Date(0)},
				{int.class,'a'},
				{int.class,"a"},
				
				{int[].class,100},
				{int[].class,Integer.valueOf(100)},
				{int[].class,(short)100},
				{int[].class,(byte)100},
				{int[].class,Short.valueOf((short)100)},
				{int[].class,Byte.valueOf((byte)100)},
				{int[].class,"100"},
				{int[].class,100l},
				{int[].class,100L},
				{int[].class,100.000F},
				{int[].class,100.000f},
				{int[].class,100.000D},
				{int[].class,100.000d},
				{int[].class,new Date(0)},
				{int[].class,'a'},
				{int[].class,"a"},
				
				{
					Integer[].class,
					new Object[]{
							100,
							Integer.valueOf(100),
							(short)100,
							(byte)100,
							Short.valueOf((short)100),
							Byte.valueOf((byte)100),
							"100",
							100l,
							100L,
							100.000F,
							100.000f,
							100.000D,
							100.000d,
							new Date(0),
							'a',
							"a",
					}
				},
				
				{
					int[].class,
					new Object[]{
							100,
							Integer.valueOf(100),
							(short)100,
							(byte)100,
							Short.valueOf((short)100),
							Byte.valueOf((byte)100),
							"100",
							100l,
							100L,
							100.000F,
							100.000f,
							100.000D,
							100.000d,
							new Date(0),
							'a',
							"a",
					}
				},
				
				{
					byte[].class,
					new Object[]{
							100,
							Integer.valueOf(100),
							(short)100,
							(byte)100,
							Short.valueOf((short)100),
							Byte.valueOf((byte)100),
							"100",
							100l,
							100L,
							100.000F,
							100.000f,
							100.000D,
							100.000d,
							new Date(0),
							'a',
							"a",
					}
				},
				
				{
					short[].class,
					new Object[]{
							100,
							Integer.valueOf(100),
							(short)100,
							(byte)100,
							Short.valueOf((short)100),
							Byte.valueOf((byte)100),
							"100",
							100l,
							100L,
							100.000F,
							100.000f,
							100.000D,
							100.000d,
							new Date(0),
							'a',
							"a",
					}
				},
				
				{
					long[].class,
					new Object[]{
							100,
							Integer.valueOf(100),
							(short)100,
							(byte)100,
							Short.valueOf((short)100),
							Byte.valueOf((byte)100),
							"100",
							100l,
							100L,
							100.000F,
							100.000f,
							100.000D,
							100.000d,
							new Date(0),
							'a',
							"a",
					}
				},
				
				{
					char[].class,
					new Object[]{
							100,
							Integer.valueOf(100),
							(short)100,
							(byte)100,
							Short.valueOf((short)100),
							Byte.valueOf((byte)100),
							"100",
							100l,
							100L,
							100.000F,
							100.000f,
							100.000D,
							100.000d,
							new Date(0),
							'a',
							"a",
					}
				},
				
				{
					char[].class,
					new ArrayList<Object>(){
						private static final long serialVersionUID = 506175955173995847L;
						{
							add(100);
							add(Integer.valueOf(100));
							add((short)100);
							add((byte)100);
							add(Short.valueOf((short)100));
							add(Byte.valueOf((byte)100));
							add("100");
							add(100l);
							add(100L);
							add(100.000F);
							add(100.000f);
							add(100.000D);
							add(100.000d);
							add(new Date(0));
							add('a');
							add("a");
						}
					},
				},
				
		};
		boolean flag = true;
		for (int i = 0, j = 1; i < clazzAndObj.length; i++, j++) {
			Class<?> destClazz = (Class<?>) clazzAndObj[i][0];
			Object obj = clazzAndObj[i][1];
			try {
				Object retObj = DataTypeUtils.convert(destClazz, obj);
				System.out.println(""
						+ "案例=========>"+j+":\r\n"
						+ "原始数据类型        ->"+obj.getClass()+"\r\n"
						+ "原始数值                ->"+DTU.toStr(obj)+"\r\n"
						+ "预期数据类型        ->"+destClazz+"\r\n"
						+ "转换是否发生异常->否\r\n"
						+ "转换后的数据类型->"+retObj.getClass()+"\r\n"
						+ "转换后的数据值    ->"+DTU.toStr(retObj)+"\r\n"
						+ "测试结果是否符合预期->"+((destClazz == retObj.getClass() || retObj.getClass().getName().toUpperCase().contains(destClazz.getName().toUpperCase())) ? "是" : "否")+"\r\n"
						);
				if(!((destClazz == retObj.getClass() || retObj.getClass().getName().toUpperCase().contains(destClazz.getName().toUpperCase())))) {
					flag = false;
				}
			} catch (BaseException e) {
				flag = false;
				System.out.println(""
						+ "案例============>"+j+":\r\n"
						+ "原始数据类型                ->"+obj.getClass()+"\r\n"
						+ "原始数值                        ->"+obj+"\r\n"
						+ "预期数据类型                ->"+destClazz+"\r\n"
						+ "转换是否发生异常        ->是\r\n"
						+ "转换发生异常编码        ->"+e.getCode()+"\r\n"
						+ "转换发生异常信息        ->"+e.getMessage()+"\r\n"
						+ "测试结果是否符合预期->否\r\n"
						);
			}
		}
		Assert.assertTrue(flag);
	}
	
	/** @author hcqt@qq.com */
	@Ignore
	@Test
	public void cvtCollectionTest() {
		ArrayList<Object> arrayList = new ArrayList<Object>();
		arrayList.add(100);
		arrayList.add(Integer.valueOf(100));
		arrayList.add((short)100);
		arrayList.add((byte)100);
		arrayList.add(Short.valueOf((short)100));
		arrayList.add(Byte.valueOf((byte)100));
		arrayList.add("100");
		arrayList.add(100l);
		arrayList.add(100L);
		arrayList.add(100.000F);
		arrayList.add(100.000f);
		arrayList.add(100.000D);
		arrayList.add(100.000d);
		arrayList.add(new Date(0));
		arrayList.add('a');
		arrayList.add("a");
		Object[][] clazzAndObj = {
				{char[].class,arrayList},
				{Collection.class,arrayList},
				{List.class,arrayList},
				{Set.class,arrayList},
				{Iterable.class,arrayList},
				{Queue.class,arrayList},
		};
		boolean flag = true;
		for (int i = 0, j = 1; i < clazzAndObj.length; i++, j++) {
			Class<?> destClazz = (Class<?>) clazzAndObj[i][0];
			Object obj = clazzAndObj[i][1];
			try {
				Object retObj = DataTypeUtils.convert(destClazz, obj);
				System.out.println(""
						+ "案例=========>"+j+":\r\n"
						+ "原始数据类型        ->"+obj.getClass()+"\r\n"
						+ "原始数值                ->"+DTU.toStr(obj)+"\r\n"
						+ "预期数据类型        ->"+destClazz+"\r\n"
						+ "转换是否发生异常->否\r\n"
						+ "转换后的数据类型->"+retObj.getClass()+"\r\n"
						+ "转换后的数据值    ->"+DTU.toStr(retObj)+"\r\n"
						+ "测试结果是否符合预期->是\r\n"
						);
			} catch (BaseException e) {
				flag = false;
				System.out.println(""
						+ "案例============>"+j+":\r\n"
						+ "原始数据类型                ->"+obj.getClass()+"\r\n"
						+ "原始数值                        ->"+obj+"\r\n"
						+ "预期数据类型                ->"+destClazz+"\r\n"
						+ "转换是否发生异常        ->是\r\n"
						+ "转换发生异常编码        ->"+e.getCode()+"\r\n"
						+ "转换发生异常信息        ->"+e.getMessage()+"\r\n"
						+ "测试结果是否符合预期->否\r\n"
						);
			}
		}
		Assert.assertTrue(flag);
	}

	/** @author hcqt@qq.com */
	@Test
	public void cvtExceptTest() {
		Object[][] a = {
				{Integer.class,Long.MAX_VALUE},
				{Integer.class,new Date(Long.MAX_VALUE)},
				{Integer.class,true},
				{Integer.class,100.1},
				{Integer.class,"xxx"},
		};
		for (int i = 0, j = 1; i < a.length; i++, j++) {
			Class<?> destClazz = (Class<?>) a[i][0];
			Object obj = a[i][1];
			try {
				Object retObj = DataTypeUtils.convert(destClazz, obj);
				System.out.println(""
						+ "案例============>"+j+":\r\n"
						+ "原始数据类型                ->"+obj.getClass()+"\r\n"
						+ "原始数值                        ->"+obj+"\r\n"
						+ "预期数据类型                ->"+destClazz+"\r\n"
						+ "转换是否发生异常        ->否\r\n"
						+ "转换后的数据类型        ->"+retObj.getClass()+"\r\n"
						+ "转换后的数据值            ->"+retObj+"\r\n"
						+ "测试结果是否符合预期->否\r\n"
						);
			} catch (BaseException e) {
				System.out.println(""
						+ "案例============>"+j+":\r\n"
						+ "原始数据类型                ->"+obj.getClass()+"\r\n"
						+ "原始数值                        ->"+obj+"\r\n"
						+ "预期数据类型                ->"+destClazz+"\r\n"
						+ "转换是否发生异常        ->是\r\n"
						+ "转换发生异常编码        ->"+e.getCode()+"\r\n"
						+ "转换发生异常信息        ->"+e.getMessage()+"\r\n"
						+ "测试结果是否符合预期->是\r\n"
						);
			}
		}
	}
//	public static void main(String[] args) {
//		System.out.println(BigDecimal.valueOf(Character.MAX_VALUE));
//		System.out.println(BigDecimal.valueOf(Character.MIN_VALUE));
//	}
//	public static void main(String[] args) {
//		System.out.println((int) Character.MAX_VALUE);
//		System.out.println((int) Character.MIN_VALUE);
//		System.out.println('1' > '2');
//		System.out.println('a' > 'b');
//		System.out.println('a' > 'A');
//	}
//public static void main(String[] args) {
//	System.out.println(int.class);
//	System.out.println(Integer.class);
//	System.out.println(int.class == Integer.class);
//	System.out.println(int.class.equals(Integer.class));
//	System.out.println(int.class.getGenericSuperclass());
//}
//	public static void main(String[] args) {
//	System.out.println(DU.format(new Date(0), null));
//	System.out.println(DU.format(new Date(Long.MAX_VALUE), null));
//	System.out.println(DU.format(new Date(Long.MIN_VALUE), null));
//	System.out.println(DU.parse("1970-01-01 00:00:00", null).getTime());
//	System.out.println(DU.parse("1969-01-01 00:00:00", null).getTime());
//	System.out.println(DU.parse("1900-01-01 00:00:00", null).getTime());
//}
//public static void main(String[] args) {
//String reg = "^( *)(\\d{1,4})( *)((-|/|\\.)?)( *)((\\d{1,2})?)( *)((-|/|\\.)?)( *)((\\d{1,2})?)( *)((( +)\\d{1,2})?)( *)((:|\\.)?)( *)((\\d{1,2})?)( *)((:|\\.)?)( *)((\\d{1,2})?)( *)((:|\\.)?)( *)((\\d{1,3})?)( *)$";
//System.out.println(" 2000  ".matches(reg));
//System.out.println(" 2000 - 00 ".matches(reg));
//System.out.println(" 2000 - 00 / 00".matches(reg));
//System.out.println(" 2000 - 00 / 00 ".matches(reg));
//System.out.println(" 2000 - 00 / 00  ".matches(reg));
//System.out.println(" 2000 - 00 / 0000  :  00 :  00  . 000".matches(reg));
//System.out.println(" 2000 - 00 / 00 00  :  00 :  00  . 000".matches(reg));
//System.out.println(" 2000 - 00 / 00  00  :  00 :  00  . 000".matches(reg));
//}
//public static void main(String[] args) {
//String reg = "^( *)(\\d{1,4})( *)(-|/|\\.)( *)(\\d{1,2})( *)(-|/|\\.)( *)(\\d{1,2})( *)((\\d{1,2})?)( *)((:|\\.)?)( *)((\\d{1,2})?)( *)((:|\\.)?)( *)((\\d{1,2})?)( *)((:|\\.)?)( *)((\\d{1,3})?)( *)$";
//System.out.println(" 2000 - 00 / 00  ".matches(reg));
//System.out.println(" 2000 - 00 / 00  0".matches(reg));
//System.out.println(" 2000 - 00 / 00  00".matches(reg));
//System.out.println(" 2000 - 00 / 00  00 : 00  ".matches(reg));
//System.out.println(" 2000 - 00 / 00  00  :  00 :  00  ".matches(reg));
//System.out.println(" 2000 - 00 / 00  00  :  00 :  00  . 000".matches(reg));
//System.out.println(" 2000 - 00 / 00  00:00:00".matches("^( *)(\\d{1,4})( *)(-|/)( *)(\\d{1,2})( *)(-|/)( *)(\\d{1,2})( *)[( +)(\\d{1,2})( *)[(:)( *)(\\d{1,2})( *)[(:)( *)(\\d{1,2})( *)]]]$"));
//System.out.println("2000-00-00".matches("^(\\d{1,4})(-|/)(\\d{1,2})(-|/)(\\d{1,2})[( +)(\\d{1,2})(:)(\\d{1,2})(:)(\\d{1,2})]$"));
//
//System.out.println("2000-0-00".matches("^(\\d{1,4})(-|/)(\\d{2})(-|/)(\\d{2})$"));
//System.out.println("2000-00/00".matches("^(\\d{1,4})(-|/)(\\d{2})(-|/)(\\d{2})$"));
//System.out.println("0002-00/00".matches("^(\\d{1,4})(-|/)(\\d{2})(-|/)(\\d{2})$"));
//System.out.println("2-00/00".matches("^(\\d{1,4})(-|/)(\\d{2})(-|/)(\\d{2})$"));
//System.out.println("0-00/00".matches("^(\\d{1,4})(-|/)(\\d{2})(-|/)(\\d{2})$"));
//System.out.println("-00/00".matches("^(\\d{1,4})(-|/)(\\d{2})(-|/)(\\d{2})$"));
//}
//public static void main(String[] args) {
//	System.out.println(null instanceof Date);
//	System.out.println(new java.sql.Date(0) instanceof Date);
//	System.out.println(new Date() instanceof Date);
//	System.out.println((Object)Integer.MAX_VALUE instanceof Date);
//}
}
