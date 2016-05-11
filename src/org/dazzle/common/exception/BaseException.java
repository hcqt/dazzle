package org.dazzle.common.exception;

/** @author hcqt@qq.com */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 7369919371075409501L;
	
	private String code = null;

	/** 获取异常编码 
	 * @author hcqt@qq.com*/
	public String getCode() {
		if(null != code) {
			return code;
		}
		return code;
	}

	/** @author hcqt@qq.com */
	public BaseException() {
		super();
	}

	/** @author hcqt@qq.com */
	public BaseException(
			final String code, 
			final String message, 
			final Object... msgArg) {
		super(MsgI18n.getMsg(code, message, msgArg));
		this.code = code;
	}

	/** @author hcqt@qq.com */
	public BaseException(
			final String code, 
			final String message, 
			final Throwable cause, 
			final Object... msgArg) {
		super(MsgI18n.getMsg(code, message, msgArg), cause);
		this.code = code;
	}

}
