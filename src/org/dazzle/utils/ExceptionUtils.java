package org.dazzle.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/** @author hcqt@qq.com */
public class ExceptionUtils {

	/** @author hcqt@qq.com */
	public static final String out(final Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(stringWriter);
			throwable.printStackTrace(printWriter);
			StringBuffer sb = stringWriter.getBuffer();
			if(null == sb) {
				return  null;
			}
			return sb.toString();
		} finally {
			try { stringWriter.close(); } catch (IOException e) { }
			try { printWriter.close(); } catch (Exception e) { }
		}
	}

}
