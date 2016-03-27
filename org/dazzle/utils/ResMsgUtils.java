package org.dazzle.utils;

/** @author hcqt@qq.com */
public class ResMsgUtils {

	/**
	 * @param msg 资源消息，例如：“取消Ups运单，不存在shipmentNo为{0}运单”
	 * @param parameters 资源消息当中的参数，用来替换资源文件当中的占位符
	 * @return 按照资源消息，把资源消息当中的参数替换后返回
	 * @author hcqt@qq.com
	 */
	public static final String resolve(String msg, Object... parameters) {
		String ret = msg;
		if(null != parameters && 0 < parameters.length) {
			for (int i = 0; i < parameters.length; i++) {
				if(null != ret) {
					ret = ret.replace(new StringBuilder().append("{").append(i).append("}").toString(), null == parameters[i] ? "null" : parameters[i].toString());
				}
			}
		}
		return ret;
	}

}
