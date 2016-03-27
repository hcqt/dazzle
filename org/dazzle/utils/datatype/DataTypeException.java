package org.dazzle.utils.datatype;

import org.dazzle.common.exception.BaseException;

/** @author hcqt@qq.com */
public class DataTypeException extends BaseException {

	private static final long serialVersionUID = 4654330042965245163L;

	/** @author hcqt@qq.com */
	public DataTypeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** @author hcqt@qq.com */
	public DataTypeException(String code, String message, Object... msgArg) {
		super(code, message, msgArg);
		// TODO Auto-generated constructor stub
	}

	/** @author hcqt@qq.com */
	public DataTypeException(String code, String message, Throwable cause, Object... msgArg) {
		super(code, message, cause, msgArg);
		// TODO Auto-generated constructor stub
	}

}
