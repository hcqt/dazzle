package org.dazzle.utils;

/**@author hcqt@qq.com*/
public class StringUtils {

	/**@author hcqt@qq.com*/
	StringUtils(){ super(); };

	/**@author hcqt@qq.com*/
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

	/**@author hcqt@qq.com*/
	public static final void deletePrefix(StringBuilder str, String prefix) {
		String after = subStringAfter(str.toString(), prefix, -1);
		if(null == after) {// 如果原始文本中不存在指定的字符串
			return;
		}
		if(after.matches("\\w+")) {// 如果原始文本的指定字符串后还有字母数字下划线
			return;
		}
		int start = indexOf(str.toString(), prefix, -1);
		int end = str.length();
		if(start <= -1) {
			start = 0;
		}
		if(end <= -1) {
			end = 0;
		}
		str.delete(start, end);
	}

	/**@author hcqt@qq.com*/
	public static final void deletePrefixIgnoreCase(StringBuilder str, String prefix) {
		String after = subStringAfterIgnoreCase(str.toString(), prefix, -1);
		if(null == after) {// 如果原始文本中不存在指定的字符串
			return;
		}
		if(after.matches("\\w+")) {// 如果原始文本的指定字符串后还有字母数字下划线
			return;
		}
		int start = indexOfIgnoreCase(str.toString(), prefix, -1);
		int end = str.length();
		if(start <= -1) {
			start = 0;
		}
		if(end <= -1) {
			end = 0;
		}
		str.delete(start, end);
	}

	/**@author hcqt@qq.com*/
	public static final String deletePrefix(String str, String prefix) {
		StringBuilder sb = new StringBuilder(str);
		deletePrefix(sb, prefix);
		return sb.toString();
	}

	/**@author hcqt@qq.com*/
	public static final String deletePrefixIgnoreCase(String str, String prefix) {
		StringBuilder sb = new StringBuilder(str);
		deletePrefixIgnoreCase(sb, prefix);
		return sb.toString();
	}

	/**@author hcqt@qq.com*/
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

	/**@author hcqt@qq.com*/
	public static final int indexOfIgnoreCase(String str, String separator, Integer num) {
		if(null == str) {
			return -1;
		}
		if(null == separator) {
			return -1;
		}
		return indexOf(str.toUpperCase(), separator.toUpperCase(), num);
	}

	/**@author hcqt@qq.com*/
	public static final String subStringBefor(String str, String separator, Integer num) {
		return subStringBefor(str, separator, num, false);
	}

	/**@author hcqt@qq.com*/
	public static final String subStringAfter(String str, String separator, Integer num) {
		return subStringAfter(str, separator, num, false);
	}

	/**@author hcqt@qq.com*/
	public static final String subStringBeforIgnoreCase(String str, String separator, Integer num) {
		return subStringBefor(str, separator, num, true);
	}

	/**@author hcqt@qq.com*/
	public static final String subStringAfterIgnoreCase(String str, String separator, Integer num) {
		return subStringAfter(str, separator, num, true);
	}

	/**@author hcqt@qq.com*/
	private static final String subStringBefor(String str, String separator, Integer num, boolean ignoreCase) {
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

	/**@author hcqt@qq.com*/
	private static final String subStringAfter(String str, String separator, Integer num, boolean ignoreCase) {
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
