package org.dazzle.utils;

/**本软件为开源项目，最新项目发布于github，可提交您的代码到本开源软件，项目网址：<a href="https://github.com/hcqt/dazzle">https://github.com/hcqt/dazzle</a><br />
 * 本软件内的大多数方法禁止Override，原因是作者提倡组合，而非继承，如果您确实需要用到继承，而又希望用本软件提供的方法名称与参数列表，建议您自行采用适配器设计模式，逐个用同名方法包裹本软件所提供的方法，这样您依然可以使用继承
 * @author hcqt@qq.com*/
public class StringUtils {

	/**@author hcqt@qq.com*/
	StringUtils(){ super(); };

	/**@see #subStringCountNum(String, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final int subStringCountNum(String src, String dest) {
		return subStringCountNum(src, dest, false);
	}

	/**@see #subStringCountNum(String, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final int subStringCountNumIgnoreCase(String src, String dest) {
		return subStringCountNum(src, dest, true);
	}

	/**@see #deleteSuffix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final void deleteSuffix(StringBuilder str, String suffix) {
		deleteSuffix(str, suffix, false);
	}

	/**@see #deleteSuffix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final void deleteSuffixIgnoreCase(StringBuilder str, String suffix) {
		deleteSuffix(str, suffix, true);
	}

	/**@see #deleteSuffix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final String deleteSuffix(String str, String suffix) {
		if(str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(str);
		deleteSuffix(sb, suffix, false);
		return sb.toString();
	}

	/**@see #deleteSuffix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final String deleteSuffixIgnoreCase(String str, String suffix) {
		if(str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(str);
		deleteSuffix(sb, suffix, true);
		return sb.toString();
	}

	/**@see #deleteSuffix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final void deletePrefix(StringBuilder str, String prefix) {
		deletePrefix(str, prefix, false);
	}

	/**@see #deletePrefix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final void deletePrefixIgnoreCase(StringBuilder str, String prefix) {
		deletePrefix(str, prefix, true);
	}
	
	/**@see #deletePrefix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final String deletePrefix(String str, String prefix) {
		if(str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(str);
		deletePrefix(sb, prefix, false);
		return sb.toString();
	}
	
	/**@see #deletePrefix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final String deletePrefixIgnoreCase(String str, String prefix) {
		if(str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(str);
		deletePrefix(sb, prefix, true);
		return sb.toString();
	}

	/**@see #indexOf(String, String, Integer, Boolean)
	 * @author hcqt@qq.com*/
	public static final int indexOf(String str, String separator, Integer num) {
		return indexOf(str, separator, num, false);
	}

	/**@see #indexOf(String, String, Integer, Boolean)
	 * @author hcqt@qq.com*/
	public static final int indexOfIgnoreCase(String str, String separator, Integer num) {
		return indexOf(str, separator, num, true);
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

	/**统计目标字符串在源字符串中共出现多少次<br>
	 * @param src 源字符串
	 * @param dest 要统计的目标字符串
	 * @author hcqt@qq.com*/
	public static final int subStringCountNum(String src, String dest, Boolean isIgnoreCase) {
		if(src == null || dest == null) {
			return 0;
		}
		if(isIgnoreCase == null) {
			isIgnoreCase = Boolean.FALSE;
		}
		String tmp = null;
		int ret = 0;
		for (boolean flag = true; flag; ) {
			tmp = SU.subStringAfter(src, dest, 1, isIgnoreCase);
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
	public static final void deleteSuffix(StringBuilder str, String suffix, Boolean isIgnoreCase) {
		if(str == null || suffix == null) {
			return;
		}
		if(isIgnoreCase == null) {
			isIgnoreCase = Boolean.FALSE;
		}
		String after = subStringAfter(str.toString(), suffix, -1, isIgnoreCase);
		if(null == after) {// 如果原始文本中不存在指定的字符串
			return;
		}
		if(after.matches("\\w+")) {// 如果原始文本的指定字符串后还有字母数字下划线
			return;
		}
		int start = indexOf(str.toString(), suffix, -1, isIgnoreCase);
		int end = str.length();
		if(start <= -1) {
			start = 0;
		}
		if(end <= -1) {
			end = 0;
		}
		str.delete(start, end);
	}

	/**删除前缀，从原始字符串中删除指定的前缀，如果指定的前缀前边依然存在字母数字下划线，则前缀条件不成立，不会发生删除操作
	 * @see #deleteSuffix(StringBuilder, String, Boolean)
	 * @author hcqt@qq.com*/
	public static final void deletePrefix(StringBuilder str, String prefix, Boolean isIgnoreCase) {
		if(str == null || prefix == null) {
			return;
		}
		if(isIgnoreCase == null) {
			isIgnoreCase = Boolean.FALSE;
		}
		String before = subStringBefore(str.toString(), prefix, 1, isIgnoreCase);
		if(null == before) {// 如果原始文本中不存在指定的字符串
			return;
		}
		if(before.matches("\\w+")) {// 如果原始文本的指定字符串后还有字母数字下划线
			return;
		}
		int start = 0;
		int end = indexOf(str.toString(), prefix, 1, isIgnoreCase) + prefix.length();
		if(start <= -1) {
			start = 0;
		}
		if(end <= -1) {
			end = 0;
		}
		str.delete(start, end);
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
	public static final int indexOf(String str, String separator, Integer num, Boolean isIgnoreCase) {
		if(isIgnoreCase == null) {
			isIgnoreCase = Boolean.FALSE;
		}
		if(null == str) {
			return -1;
		}
		if(null == separator) {
			return -1;
		}
		if(isIgnoreCase) {
			str = str.toUpperCase();
			separator = separator.toUpperCase();
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
	public static final String subStringBefore(String str, String separator, Integer num, Boolean ignoreCase) {
		if(null == str) {
			return null;
		}
		if(null == separator) {
			return null;
		}
		if(Integer.signum(num) == 0) {
			return null;
		}
		if(ignoreCase == null) {
			ignoreCase = Boolean.FALSE;
		}
		int i = indexOf(str, separator, num, ignoreCase);
		if(i == -1) {
			return null;
		} else {
			return str.substring(0, i);
		}
	}

	/**@see #subStringBefore(String, String, Integer, boolean)
	 * @author hcqt@qq.com*/
	public static final String subStringAfter(String str, String separator, Integer num, Boolean ignoreCase) {
		if(null == str) {
			return null;
		}
		if(null == separator) {
			return null;
		}
		if(Integer.signum(num) == 0) {
			return null;
		}
		if(ignoreCase == null) {
			ignoreCase = Boolean.FALSE;
		}
		int i = indexOf(str, separator, num, ignoreCase);
		if(i == -1) {
			return null;
		} else {
			return str.substring(i + separator.length());
		}
	}

}
