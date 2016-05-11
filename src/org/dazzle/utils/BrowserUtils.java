package org.dazzle.utils;

import javax.servlet.http.HttpServletRequest;


/** @author hcqt@qq.com */
public class BrowserUtils {

	/** @author hcqt@qq.com */
	public enum Browser {
		IE, Chrome, Firefox, Safari, UnKnow;

		public String toString() {
			return this.name();
		}
	}

	/** @author hcqt@qq.com */
	public static final Browser getBrowserType(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		if(null == agent){
			return Browser.UnKnow;
		}
		else if(0 <= agent.indexOf("MSIE")){
			return Browser.IE;
		}
		else if(0 <= agent.indexOf("Chrome")){
			return Browser.Chrome;
		}
		else if(0 <= agent.indexOf("Firefox")){
			return Browser.Firefox;
		}
		else if(0 <= agent.indexOf("Safari")){
			return Browser.Safari;
		}
		return Browser.UnKnow;
	}
	
}
