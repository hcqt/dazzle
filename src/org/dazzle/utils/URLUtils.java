package org.dazzle.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.dazzle.common.exception.BaseException;

/**本软件为开源项目，最新项目发布于github，可提交您的代码到本开源软件，项目网址：<a href="https://github.com/hcqt/dazzle">https://github.com/hcqt/dazzle</a><br />
 * 本软件内的大多数方法禁止Override，原因是作者提倡组合，而非继承，如果您确实需要用到继承，而又希望用本软件提供的方法名称与参数列表，建议您自行采用适配器设计模式，逐个用同名方法包裹本软件所提供的方法，这样您依然可以使用继承
 * @see #get(Class, Map, String)
 * @author hcqt@qq.com*/
public final class URLUtils {
	
	private static final String msg1Code = "SER_COMMON_CLASSPATH_km3Ns";
	private static final String msg1 = "无法获取程序的classpath路径";
	
	private static final String msg2Code = "SER_COMMON_IO_UTIL_WRITE_i92nU";
	private static final String msg2 = "把输入流写入指定URI:{0}的时候，发现未捕获异常，详情——{1}";

	public static final String SCHEME_CLASSPATH = "classpath";

	/**@see #resolve(URI)
	 * @author hcqt@qq.com */
	public static final URL resolve(final URL url) {
		try {
			return resolve(url.toURI()).toURL();
		} catch (MalformedURLException | URISyntaxException e) {
			throw new BaseException("SYS_COMMON_RESOLVE_REAL_PATH_9nm3g", "URL[{0}]语法错误，详情——{1}", e, url, EU.out(e));
		}
	}

	/**如果uri的协议头采用的是classpath，就把uri解析成绝对路径返回<br />
	 * 如果uri的协议头是其他则原样返回
	 * @author hcqt@qq.com */
	public static final URI resolve(final URI uri) {
		if(SCHEME_CLASSPATH.equalsIgnoreCase(uri.getScheme())) {
			URL baseUrl = NetUtils.class.getResource("/");
			if(null == baseUrl) {
				throw new BaseException(msg1Code, msg1);
			}
			try {
//				return baseUrl.toURI().resolve(uriString.substring(uriString.toLowerCase().indexOf(SCHEME_CLASSPATH) + SCHEME_CLASSPATH.length() + 1));
				return baseUrl.toURI().resolve(SU.deletePrefix(uri.getPath(), "/"));
			} catch (URISyntaxException e) {
				throw new BaseException(msg2Code, msg2, e, baseUrl, EU.out(e));
			}
		}
		return uri;
	}
	
	public static void main(String[] args) throws URISyntaxException {
//		System.out.println(resolve(URI.create("http://www.baidu.com")));
//		System.out.println(resolve(URI.create("classpath:/spring/x.xml")));
		System.out.println(resolve(URI.create("classpath:spring/x.xml")));
//		System.out.println(NetUtils.class.getResource("/").toURI().resolve("spring/x.xml"));
	}
}
