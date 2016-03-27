package org.dazzle.common.exception;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.dazzle.utils.RMU;

/** @author hcqt@qq.com */
public class MsgI18n {

	private static ThreadLocal<Locale> currentLocale = new ThreadLocal<Locale>() {
		@Override protected Locale initialValue() {
			return Locale.getDefault();// 初始化采用虚拟机的默认地区
		}
	};

	/** @author hcqt@qq.com */
	public static void setLocale(Locale locale) {
		currentLocale.set(locale);
	}

	/** @author hcqt@qq.com */
	public static Locale getLocale() {
		return currentLocale.get();
	}

	/** @author hcqt@qq.com */
	public static void clearLocale() {
		currentLocale.remove();
	}

	/**
	 * 到国际化资源文件当中找到code键相应的值作为msg，如果找不到，那么使用传入的defaultMsg<br />
	 * msg中的占位符会以msgArg逐个按顺序替换<br />
	 * 占位符例子：<br />
	 * 你好{0}先生；你好{1}女士，好久不见；<br />
	 * @author hcqt@qq.com
	 */
	public static String getMsg(String code, String defaultMsg, Object... msgArg) {
		String ret = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle("message", MsgI18n.getLocale());
			if(null != rb) {
				String cfgMsg = rb.getString(code);
				if(null != cfgMsg) {
					ret = RMU.resolve(cfgMsg, msgArg);
				}
			}
		} catch (MissingResourceException e) {
			ret = RMU.resolve(defaultMsg, msgArg);
		}
		if(null == ret) {
			ret = RMU.resolve(defaultMsg, msgArg);
		}
		return ret;
	}
	
}
