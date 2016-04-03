package org.dazzle.utils;

/**<a href="https://github.com/hcqt/dazzle">https://github.com/hcqt/dazzle</a>
 * @author hcqt@qq.com*/
public class StringUtils {

	/**@author hcqt@qq.com*/
	StringUtils(){ super(); };

	/**统计目标字符串在源字符串中共出现多少次
	 * @param src 源字符串
	 * @param dest 要统计的目标字符串
	 * @author hcqt@qq.com*/
	public static final int subStringCountNum(String src, String dest) {
		String tmp = null;
		int ret = 0;
		for (boolean flag = true; flag; ) {
			tmp = SU.subStringAfter(src, dest, 1);
			if(tmp != null) {
				src = tmp;
				ret++;
			} else {
				flag = false;
			}
		}
		return ret;
	}

	/**从字符串中删除指定后缀，如果要删除的后缀确实为后缀条件成立，则删除，否则原始字符串原封不动<br />
	 * 后缀的条件成立规则：如果指定的后缀后边依然存在字母，那么指定的后缀就不成立，反之就成立<br />
	 * 如果后缀条件成立，将会把后缀之后的字符串，连同后缀删除掉<br />
	 * 例如：<br />
	 * 1.原始字符串为“book.xml”，要删除的后缀为“.xml”，这时候条件成立<br />
	 * 2.如果原始字符串为“book.xml ”那么条件依然视作成立<br />
	 * 3.但如果原始字符串为“book.xmla”那么条件就不成立<br />
	 * @author hcqt@qq.com*/
	public static final void deleteSuffix(StringBuilder str, String suffix) {
		String after = subStringAfter(str.toString(), suffix, -1);
		if(null == after) {// 如果原始文本中不存在指定的字符串
			return;
		}
		if(after.matches("\\w+")) {// 如果原始文本的指定字符串后还有字母数字下划线
			return;
		}
		int start = indexOf(str.toString(), suffix, -1);
		int end = str.length();
		if(start <= -1) {
			start = 0;
		}
		if(end <= -1) {
			end = 0;
		}
		str.delete(start, end);
	}

	/**@see #deleteSuffix(StringBuilder, String)
	 * @author hcqt@qq.com*/
	public static final void deleteSuffixIgnoreCase(StringBuilder str, String suffix) {
		String after = subStringAfterIgnoreCase(str.toString(), suffix, -1);
		if(null == after) {// 如果原始文本中不存在指定的字符串
			return;
		}
		if(after.matches("\\w+")) {// 如果原始文本的指定字符串后还有字母数字下划线
			return;
		}
		int start = indexOfIgnoreCase(str.toString(), suffix, -1);
		int end = str.length();
		if(start <= -1) {
			start = 0;
		}
		if(end <= -1) {
			end = 0;
		}
		str.delete(start, end);
	}

	/**@see #deleteSuffix(StringBuilder, String)
	 * @author hcqt@qq.com*/
	public static final String deleteSuffix(String str, String suffix) {
		StringBuilder sb = new StringBuilder(str);
		deleteSuffix(sb, suffix);
		return sb.toString();
	}

	/**@see #deleteSuffix(StringBuilder, String)
	 * @author hcqt@qq.com*/
	public static final String deleteSuffixIgnoreCase(String str, String suffix) {
		StringBuilder sb = new StringBuilder(str);
		deleteSuffixIgnoreCase(sb, suffix);
		return sb.toString();
	}

	/**删除前缀，从原始字符串中删除指定的前缀，如果指定的前缀前边依然存在字母数字下划线，则前缀条件不成立，不会发生删除操作，前缀将会连同删除
	 * @see #deleteSuffix(StringBuilder, String)
	 * @author hcqt@qq.com*/
	public static final void deletePrefix(StringBuilder str, String prefix) {
		String before = subStringBefore(str.toString(), prefix, 1);
		if(null == before) {// 如果原始文本中不存在指定的字符串
			return;
		}
		if(before.matches("\\w+")) {// 如果原始文本的指定字符串后还有字母数字下划线
			return;
		}
		int start = 0;
		int end = indexOf(str.toString(), prefix, 1) + prefix.length();
		if(start <= -1) {
			start = 0;
		}
		if(end <= -1) {
			end = 0;
		}
		str.delete(start, end);
	}

	/**@see #deletePrefix(StringBuilder, String)
	 * @author hcqt@qq.com*/
	public static final void deletePrefixIgnoreCase(StringBuilder str, String prefix) {
		String before = subStringBeforeIgnoreCase(str.toString(), prefix, 1);
		if(null == before) {// 如果原始文本中不存在指定的字符串
			return;
		}
		if(before.matches("\\w+")) {// 如果原始文本的指定字符串后还有字母数字下划线
			return;
		}
		int start = 0;
		int end = indexOfIgnoreCase(str.toString(), prefix, 1) + prefix.length();
		if(start <= -1) {
			start = 0;
		}
		if(end <= -1) {
			end = 0;
		}
		str.delete(start, end);
	}
	
	/**@see #deletePrefix(StringBuilder, String)
	 * @author hcqt@qq.com*/
	public static final String deletePrefix(String str, String prefix) {
		StringBuilder sb = new StringBuilder(str);
		deletePrefix(sb, prefix);
		return sb.toString();
	}
	
	/**@see #deletePrefix(StringBuilder, String)
	 * @author hcqt@qq.com*/
	public static final String deletePrefixIgnoreCase(String str, String prefix) {
		StringBuilder sb = new StringBuilder(str);
		deletePrefixIgnoreCase(sb, prefix);
		return sb.toString();
	}

	/**在原始字符串中搜索指定的分隔字符串，并获取分隔字符串在原始字符串中的开始索引，如果未找到，返回-1<br />
	 * 例子：<br />
	 * System.out.println(indexOf("abc,abc,abc", "abc", 1));//0<br />
	 * System.out.println(indexOf("abc,abc,abc", "abc", 2));//4<br />
	 * System.out.println(indexOf("abc,abc,abc", "abc", 3));//8<br />
	 * System.out.println(indexOf("abc,abc,abc", "abc", -1));//8<br />
	 * System.out.println(indexOf("abc,abc,abc", "abc", -2));//4<br />
	 * System.out.println(indexOf("abc,abc,abc", "abc", -3));//0<br />
	 * System.out.println(indexOf("abc,abc,abc", "abc", 0));//-1<br />
	 * System.out.println(indexOf("abc,abc,abc", "aaa", 1));//-1<br />
	 * System.out.println(indexOf("abc,abc,abc", "aaa", 1000));//-1<br />
	 * @param str 原始字符串
	 * @param separator 分隔字符串
	 * @param num 指定分隔字符串在原始字符串第几次出现，正数为从左向右搜索分隔字符串，负数为从右向左搜索分隔字符串
	 * @author hcqt@qq.com*/
	public static final int indexOf(String str, String separator, Integer num) {
		if(null == str) {
			return -1;
		}
		if(null == separator) {
			return -1;
		}
		if(null == num) {
			num  = Integer.valueOf(0);
		}
		if(Integer.signum(num) == 0) {
			return -1;
		} 
		else if(Integer.signum(num) == 1) {
			int j = -1;
			for (int i = 0; i < num; i++) {
				if(j == -1) {
					j = str.indexOf(separator, 0);
				} else {
					j = str.indexOf(separator, j+1);
				}
				if(j == -1) {
					break;
				}
			}
			return j;
		} 
		else if(Integer.signum(num) == -1) {
			int j = -1;
			for (int i = 0; i < Math.abs(num); i++) {
				if(j == -1) {
					j = str.lastIndexOf(separator);
				} else {
					j = str.lastIndexOf(separator, j-1);
				}
				if(j == -1) {
					break;
				}
			}
			return j;
		}
		return -1;
	}

	/**是{@link #indexOf(String, String, Integer)}方法的忽略大小写版本
	 * @see #indexOf(String, String, Integer)
	 * @author hcqt@qq.com*/
	public static final int indexOfIgnoreCase(String str, String separator, Integer num) {
		if(null == str) {
			return -1;
		}
		if(null == separator) {
			return -1;
		}
		return indexOf(str.toUpperCase(), separator.toUpperCase(), num);
	}

	/**@see #subStringBefore(String, String, Integer, boolean)
	 * @author hcqt@qq.com*/
	public static final String subStringBefore(String str, String separator, Integer num) {
		return subStringBefore(str, separator, num, false);
	}

	/**@see #subStringBefore(String, String, Integer, boolean)
	 * @author hcqt@qq.com*/
	public static final String subStringAfter(String str, String separator, Integer num) {
		return subStringAfter(str, separator, num, false);
	}

	/**@see #subStringBefore(String, String, Integer, boolean)
	 * @author hcqt@qq.com*/
	public static final String subStringBeforeIgnoreCase(String str, String separator, Integer num) {
		return subStringBefore(str, separator, num, true);
	}

	/**@see #subStringBefore(String, String, Integer, boolean)
	 * @author hcqt@qq.com*/
	public static final String subStringAfterIgnoreCase(String str, String separator, Integer num) {
		return subStringAfter(str, separator, num, true);
	}

	/**在原始字符串中搜索分隔字符串，以分隔字符串第num次的位置为准，把分割字符串（不含）之前的字符串截取返回<br />
	 * 如果分隔字符串并不存在指定的检索次数，将会返回null<br />
	 * 示例：<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "abc", 1, true));//(注：打印为空白)<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "abc", 2, true));//abc,<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "abc", 3, true));//abc,abc,<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "abc", -1, true));//abc,abc,<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "abc", -2, true));//abc,<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "abc", -3, true));//(注：打印为空白)<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "abc", 0, true));//null<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "abc", 1000, true));//null<br />
	 * System.out.println(subStringBefore("abc,abc,abc", "aaa", 1, true));//null<br />
	 * @param str 原始字符串
	 * @param separator 分隔字符串
	 * @param num 指定分隔字符串在原始字符串第几次出现，正数为从左向右搜索分隔字符串，负数为从右向左搜索分隔字符串
	 * @param ignoreCase 是否以忽略大小写方式搜索字符串，注意，忽略大小写仅在搜索时候忽略，截取后的返回字符串不会被忽略大小写，将依然保持原始输入参数的大小写状态
	 * @author hcqt@qq.com*/
	public static final String subStringBefore(String str, String separator, Integer num, boolean ignoreCase) {
		if(null == str) {
			return null;
		}
		if(null == separator) {
			return null;
		}
		if(Integer.signum(num) == 0) {
			return null;
		}
		int i;
		if(ignoreCase) {
			i = indexOfIgnoreCase(str, separator, num);
		} else {
			i = indexOf(str, separator, num);
		}
		if(i == -1) {
			return null;
		} else {
			return str.substring(0, i);
		}
	}

	/**@see #subStringBefore(String, String, Integer, boolean)
	 * @author hcqt@qq.com*/
	public static final String subStringAfter(String str, String separator, Integer num, boolean ignoreCase) {
		if(null == str) {
			return null;
		}
		if(null == separator) {
			return null;
		}
		if(Integer.signum(num) == 0) {
			return null;
		}
		int i;
		if(ignoreCase) {
			i = indexOfIgnoreCase(str, separator, num);
		} else {
			i = indexOf(str, separator, num);
		}
		if(i == -1) {
			return null;
		} else {
			return str.substring(i + separator.length());
		}
	}

}
