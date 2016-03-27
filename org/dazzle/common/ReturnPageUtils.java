package org.dazzle.common;

import org.dazzle.common.exception.BaseException;

/** @author hcqt@qq.com */
public class ReturnPageUtils {

	/** @author hcqt@qq.com */
	ReturnPageUtils() { super(); };

	/** @author hcqt@qq.com */
	public static final String get(String[] returnPages) {
		return get(returnPages, 0);
	}

	/** @author hcqt@qq.com */
	public static final String get(String[] returnPages, Integer index) {
		if(null == returnPages || returnPages.length < 1) {
			throw new BaseException("web_return_page_k2snl", "代码编写不符合规范，缺少returnPage，请检查代码");
		}
		if(null == index) {
			return returnPages[0];
		}
		try {
			return returnPages[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BaseException("web_return_page_n3k3l", "returnPage数组长度为{0}，下标设置为{1}，发生数组下标越界", e, returnPages.length, index);
		}
	}

}
