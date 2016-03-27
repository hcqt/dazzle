package org.dazzle.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.dazzle.common.exception.BaseException;

/** @author hcqt@qq.com */
public final class URLUtils {
	
	private static final String msg1Code = "SER_COMMON_CLASSPATH_km3Ns";
	private static final String msg1 = "无法获取程序的classpath路径";
	
	private static final String msg2Code = "SER_COMMON_IO_UTIL_WRITE_i92nU";
	private static final String msg2 = "把输入流写入指定URI:{0}的时候，发现未捕获异常，详情——{1}";

	public static final String SCHEME_CLASSPATH = "classpath";

	/** @author hcqt@qq.com */
	public static final URL resolveRealPath(final URL url) {
		try {
			return resolveRealPath(url.toURI()).toURL();
		} catch (MalformedURLException | URISyntaxException e) {
			String errMsg = ResMsgUtils.resolve("URL[{0}]语法错误，详情——{1}", url, ExceptionUtils.out(e));
			Logger.getLogger("DEV_BACK_END_DATA").error(errMsg);
			throw new BaseException("SYS_COMMON_RESOLVE_REAL_PATH_9nm3g", errMsg, url, ExceptionUtils.out(e));
		}
	}

	/** @author hcqt@qq.com */
	public static final URI resolveRealPath(final URI uri) {
		if(SCHEME_CLASSPATH.equals(uri.getScheme())) {
			URL baseUrl = URLUtils.class.getResource("/");
			if(null == baseUrl) {
				throw new BaseException(msg1Code, msg1);
			}
			String uriString = uri.toString();
			try {
				return baseUrl.toURI().resolve(uriString.substring(uriString.indexOf(SCHEME_CLASSPATH) + SCHEME_CLASSPATH.length() + 1));
			} catch (URISyntaxException e) {
				throw new BaseException(msg2Code, ResMsgUtils.resolve(msg2, baseUrl, e), e);
			}
		}
		return uri;
	}
	
	public static final String err100Code = "SER_COMMON_EXPORT_DATA_58124";
	public static final String err100Msg = "无法正常连接URL——{0}，URL状态号——{1}";
	
	public static final String err98Code = "SER_COMMON_EXPORT_DATA_o2hj3";
	public static final String err98Msg = "uri书写不符合规范，您的书写——{0}";
	
	public static final String err97Code = "SER_COMMON_EXPORT_DATA_o2hj3";
	public static final String err97Msg = "url书写不符合规范，您的书写——{0}";

	/** @see #read(URL, String, String, Map) */
//	public static final String read(
//			final String url, 
//			final String urlMethod, 
//			final String contentType, 
//			final Map<String, Object> urlParam) throws BaseException {
//		try {
//			return read(new URL(url), urlMethod, contentType, urlParam);
//		} catch (MalformedURLException e) {
//			throw new BaseException(err97Code, ResMsgUtils.processMsg(err97Msg, e.getMessage()), e);
//		}
//	}

	/** @see #read(URL, String, String, Map)
	 * @author hcqt@qq.com */
	public static final String httpRead(
			final URI uri, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final Map<String, String[]> urlParam) throws BaseException {
		try {
			return httpRead(uri.toURL(), urlMethod, requestProperty, urlParam);
		} catch (MalformedURLException e) {
			throw new BaseException(err98Code, ResMsgUtils.resolve(err98Msg, e.getMessage()), e);
		}
	}

	/**
	 * 打开URL连接，一次性读出连接的所有内容
	 * @param url
	 * @param urlMethod
	 * @param contentType 如果为空，则连接URL的时候不会指定Content-Type
	 * @param urlParam 调用URL时候的参数，此方法不会自动进行URL编解码，如需编码，请自己编码
	 * @return
	 * @throws BaseException 
	 * {
	 *     {@value #err100Code}:{@value #err100Msg},
	 *     {@value #err99Code}:{@value #err99Msg},
	 *     {@value #err96Code}:{@value #err96Msg},
	 *     {@value #err95Code}:{@value #err95Msg},
	 *     {@value #err94Code}:{@value #err94Msg}
	 * }
	 * @author hcqt@qq.com
	 */
	public static final String httpRead(
			final URL url, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final String urlQuery) throws BaseException {
		OutputStream out = null;
		InputStream in = null;
		HttpURLConnection httpConn = null;
		try {
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod(urlMethod);
			httpConn.setDoOutput(true);
			if(null != requestProperty) {
				for (Entry<String, String> entry : requestProperty.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
//			if(null != contentType) {
//				httpConn.setRequestProperty("Content-Type", contentType);
//			}
			out = httpConn.getOutputStream();
			out.write(urlQuery.getBytes());
			httpConn.connect();
			if(200 != httpConn.getResponseCode()) {
				throw new BaseException(err100Code, ResMsgUtils.resolve(err100Msg, url, httpConn.getResponseCode()));
			}
			in = httpConn.getInputStream();
			return readInputStreamToString(in);
		} catch (Exception e) {			
			if(e instanceof java.net.ConnectException) {
				throw new BaseException(err96Code, ResMsgUtils.resolve(err96Msg), e);
			} 
			else if(e instanceof java.net.NoRouteToHostException) {
				throw new BaseException(err95Code, ResMsgUtils.resolve(err95Msg, e.getMessage()), e);
			} 
			else {
				throw new BaseException(err94Code, ResMsgUtils.resolve(err94Msg, e.getMessage()), e);
			}
		} finally {
			if(out != null) {
				try { out.close(); } catch (IOException e) { }
			}
			if(in != null){
				try { in.close(); } catch (IOException e) { }
			}
			if(null != httpConn) {
				httpConn.disconnect();
			}
		}
	}

	/** @see #read(URL, String, String, Map)
	 * @author hcqt@qq.com */
	public static final String httpRead(
			final URI uri, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final String urlQuery) throws BaseException {
		try {
			return httpRead(uri.toURL(), urlMethod, requestProperty, urlQuery);
		} catch (MalformedURLException e) {
			throw new BaseException(err98Code, ResMsgUtils.resolve(err98Msg, e.getMessage()), e);
		}
	}
	
	/**
	 * 打开URL连接，一次性读出连接的所有内容
	 * @param url
	 * @param urlMethod
	 * @param contentType 如果为空，则连接URL的时候不会指定Content-Type
	 * @param urlParam 调用URL时候的参数，此方法不会自动进行URL编解码，如需编码，请自己编码
	 * @return
	 * @throws BaseException 
	 * {
	 *     {@value #err100Code}:{@value #err100Msg},
	 *     {@value #err99Code}:{@value #err99Msg},
	 *     {@value #err96Code}:{@value #err96Msg},
	 *     {@value #err95Code}:{@value #err95Msg},
	 *     {@value #err94Code}:{@value #err94Msg}
	 * }
	 * @author hcqt@qq.com
	 */
	public static final String httpRead(
			final URL url, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final Map<String, String[]> urlParam) throws BaseException {
		OutputStream out = null;
		InputStream in = null;
		HttpURLConnection httpConn = null;
		try {
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod(urlMethod);
			httpConn.setDoOutput(true);
			if(null != requestProperty) {
				for (Entry<String, String> entry : requestProperty.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
//			if(null != contentType) {
//				httpConn.setRequestProperty("Content-Type", contentType);
//			}
			out = httpConn.getOutputStream();
			String params = urlParamToString(urlParam);
			out.write(params.getBytes());
			httpConn.connect();
			if(200 != httpConn.getResponseCode()) {
				throw new BaseException(err100Code, ResMsgUtils.resolve(err100Msg, url, httpConn.getResponseCode()), url, httpConn.getResponseCode());
			}
			in = httpConn.getInputStream();
			return readInputStreamToString(in);
		} catch (Exception e) {
			catchException(e);
			return null;
		} finally {
			if(out != null) {
				try { out.close(); } catch (IOException e) { }
			}
			if(in != null) {
				try { in.close(); } catch (IOException e) { }
			}
			if(null != httpConn) {
				httpConn.disconnect();
			}
		}
	}

	/** @author hcqt@qq.com */
	public static final String httpsRead(
			final URL url, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final Map<String, String[]> urlParam) {
		OutputStream out = null;
		InputStream in = null;
		HttpsURLConnection httpConn = null;
		try {
			httpConn = (HttpsURLConnection) url.openConnection();
			httpConn.setRequestMethod(urlMethod);
			httpConn.setDoOutput(true);
			if(null != requestProperty) {
				for (Entry<String, String> entry : requestProperty.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			out = httpConn.getOutputStream();
			String params = urlParamToString(urlParam);
			out.write(params.getBytes());
			httpConn.connect();
			if(200 != httpConn.getResponseCode()) {
				throw new BaseException(err100Code, ResMsgUtils.resolve(err100Msg, url, httpConn.getResponseCode()), url, httpConn.getResponseCode());
			}
			in = httpConn.getInputStream();
			return readInputStreamToString(in);
		} catch (Exception e) {		
//			if(e instanceof java.net.ConnectException) {
//				throw new BaseException(err96Code, ResMsgUtils.processMsg(err96Msg), e);
//			} 
//			else if(e instanceof java.net.NoRouteToHostException) {
//				throw new BaseException(err95Code, ResMsgUtils.processMsg(err95Msg, e.getMessage()), e);
//			} 
//			else {
//				throw new BaseException(err94Code, ResMsgUtils.processMsg(err94Msg, e.getMessage()), e);
//			}
			catchException(e);
			return null;
		} finally {
			if(out != null) {
				try { out.close(); } catch (IOException e) { }
			}
			if(in != null){
				try { in.close(); } catch (IOException e) { }
			}
			if(null != httpConn) {
				httpConn.disconnect();
			}
		}
	}

	/** @author hcqt@qq.com */
	public static final URL setParameter(URL url, Map<String, String[]> parameters, String enc) {
		if(null == url) {
			return null;
		}
		return setParameter(url.toString(), parameters, enc);
	}

	/** @author hcqt@qq.com */
	public static final URL setParameter(String url, Map<String, String[]> parameters, String enc) {
		if(null == url) {
			return null;
		}
		String[] urlArray = url.split("\\?");
		Map<String, String[]> query = new LinkedHashMap<>();
		if(1 < urlArray.length) {
			Map<String, String[]> metaQuery = getParameterMap(urlArray[1], enc);
			query.putAll(metaQuery);
		}
		if(null != parameters) {
			query.putAll(parameters);
		}
		StringBuilder urlQuery = new StringBuilder();
		for (Entry<String, String[]> entry : query.entrySet()) {
			if(null == entry.getKey()) {
				continue;
			}
			urlQuery.append(entry.getKey()).append("=");
			if(null != entry.getValue()) {
				if(1 == entry.getValue().length) {
					urlQuery.append(entry.getValue()[0]);
				} else {
					for (String string : entry.getValue()) {
						urlQuery.append(string).append(",");
					}
					urlQuery.delete(urlQuery.length() - 1, urlQuery.length());
				}
			}
			urlQuery.append("&");
		}
		try {
			if(0 < urlQuery.length()) {
				urlQuery.delete(urlQuery.length() - 1, urlQuery.length());
				return new URL(urlArray[0] + "?" + urlQuery.toString());
			} else {
				return new URL(urlArray[0]);
			}
		} catch (MalformedURLException e) {
			throw new BaseException("SYS_COMMON_URL_SET_PARAMETER_m9kn2", ResMsgUtils.resolve("URL：[{0}]存在语法错误，详情——{1}", ExceptionUtils.out(e)), e, ExceptionUtils.out(e));
		}
	}
//public static void main(String[] args) {
////	String url = "http://www.baidu.com?a=b&c=3&d=";
//	String url = "http://www.baidu.com?a=b&c=3&d=";
//	Map<String, String[]> parameters = new LinkedHashMap<>();
//	parameters.put("xxxxx", new String[] { "yyyyyyyyyyyy" });
//	System.out.println(setParameter(url, parameters, "UTF-8"));
//}
	/** @author hcqt@qq.com */
	public static final Map<String, String[]> getParameterMap(final HttpServletRequest request, final String enc) {
		return orderRequestParam(request, enc);
	}

	/** @author hcqt@qq.com */
	public static final Map<String, String[]> getParameterMap(String urlQuery, String enc) {
		if(null == enc) {
			enc = "UTF-8";
		}
		try {
			Map<String, String[]> ret = new LinkedHashMap<>();
			String[] params = urlQuery.split("&");
			for (int i = 0; i < params.length; i++) {
				String[] paramArray = params[i].split("=");
				if (paramArray.length < 1) {
					continue;
				}
				String key = URLDecoder.decode(paramArray[0], enc);
				String[] values = null;
				if(1 < paramArray.length && null != paramArray[1]) {
					String[] paramValues = paramArray[1].split(",");
					values = new String[paramValues.length];
					for (int j = 0; j < paramValues.length; j++) {
						values[j] = URLDecoder.decode(paramValues[j], enc);
					}
				}
				ret.put(key, values);
			}
			return ret;
		} catch (UnsupportedEncodingException e) {
			throw new BaseException(
					"SYS_COMMON_URL_GET_PARAMETER_MAP_8n3gi", 
					ResMsgUtils.resolve("不支持的字符集编码[{0}]，进行URL解码", enc), 
					e, 
					enc);
		}
	}

	private static final void catchException(Throwable e) {
		if(e instanceof BaseException) {
			throw (BaseException) e;
		}
		if(e instanceof java.net.ConnectException) {
			throw new BaseException(err96Code, ResMsgUtils.resolve(err96Msg), e);
		} 
		else if(e instanceof java.net.NoRouteToHostException) {
			throw new BaseException(err95Code, ResMsgUtils.resolve(err95Msg, e.getMessage()), e, e.getMessage());
		} 
		else {
			throw new BaseException(err94Code, ResMsgUtils.resolve(err94Msg, ExceptionUtils.out(e)), e, ExceptionUtils.out(e));
		}
	}

	/** @author hcqt@qq.com */
	public static final String httpsRead(
			final URL url, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final String urlQuery) throws BaseException {
		OutputStream out = null;
		InputStream in = null;
		HttpsURLConnection httpConn = null;
		try {
			httpConn = (HttpsURLConnection) url.openConnection();
			httpConn.setRequestMethod(urlMethod);
			httpConn.setDoOutput(true);
			if(null != requestProperty) {
				for (Entry<String, String> entry : requestProperty.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			out = httpConn.getOutputStream();
			out.write(urlQuery.getBytes());
			httpConn.connect();
			if(200 != httpConn.getResponseCode()) {
				throw new BaseException(err100Code, ResMsgUtils.resolve(err100Msg, url, httpConn.getResponseCode()));
			}
			in = httpConn.getInputStream();
			return readInputStreamToString(in);
		} catch (Exception e) {			
			if(e instanceof java.net.ConnectException) {
				throw new BaseException(err96Code, ResMsgUtils.resolve(err96Msg), e);
			} 
			else if(e instanceof java.net.NoRouteToHostException) {
				throw new BaseException(err95Code, ResMsgUtils.resolve(err95Msg, e.getMessage()), e);
			} 
			else {
				throw new BaseException(err94Code, ResMsgUtils.resolve(err94Msg, e.getMessage()), e);
			}
		} finally {
			if(out != null) {
				try { out.close(); } catch (IOException e) { }
			}
			if(in != null){
				try { in.close(); } catch (IOException e) { }
			}
			if(null != httpConn) {
				httpConn.disconnect();
			}
		}
	}

	/** @author hcqt@qq.com */
	public static final String httpsRead(
			final URI uri, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final String urlQuery) throws BaseException {
		try {
			return httpsRead(uri.toURL(), urlMethod, requestProperty, urlQuery);
		} catch (MalformedURLException e) {
			throw new BaseException(err98Code, ResMsgUtils.resolve(err98Msg, e.getMessage()), e);
		}
	}

	public static final String err96Code = "SER_COMMON_READ_NET_DATA_ugws1";
	public static final String err96Msg = "网络连接超时";

	public static final String err95Code = "SER_COMMON_READ_NET_DATA_k23n5";
	public static final String err95Msg = "连接不到主机，详情——{0}";

	public static final String err94Code = "SER_COMMON_READ_NET_DATA_y2ksc";
	public static final String err94Msg = "未知异常，详情——{0}";
	
	public static final String err451Code = "SER_COMMON_READ_NET_DATA_y7h3m";
	public static final String err451Msg = "URL-->[{0}]语法错误，这不是一个URL";

	public static final String err99Code = "SER_COMMON_READ_NET_DATA_ngs5t";
	public static final String err99Msg = "未知IO异常，详情——{0}";

	private static final String readInputStreamToString(final InputStream in) {
		int BUFFER_SIZE = 1024;
		ByteArrayOutputStream outStream = null;
		try {
			outStream = new ByteArrayOutputStream();
			int count = -1;
			byte[] data = new byte[BUFFER_SIZE];
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
				outStream.write(data, 0, count);
			}
			data = null;
			return new String(outStream.toByteArray(), "UTF-8");
		} catch (IOException e) {
			throw new BaseException(err99Code, ResMsgUtils.resolve(err99Msg, e.getMessage()), e);
		} finally {
			if(null != outStream) {
				try { outStream.close(); } catch (IOException e) { }
			}
		}
	}

	/** 对请求参数进行排序，参数都已URL编码 */
	public static final Map<String, String[]> orderRequestParam(final HttpServletRequest request, final String enc) {
		Enumeration<?> enu = request.getParameterNames();
//		@SuppressWarnings("unchecked")
		Map<String, String[]> oldRequestParam = request.getParameterMap();
		Map<String, String[]> newRequestParam = new LinkedHashMap<String, String[]>();
		while(enu.hasMoreElements()) {
			String paramKey = DataTypeUtils.convert(String.class, enu.nextElement());
			if(oldRequestParam.containsKey(paramKey)) {
				String[] valueStringArray = null;
				if(null == oldRequestParam.get(paramKey)) {
					valueStringArray = null;
				} 
//				else if(oldRequestParam.get(paramKey) instanceof String) {
//					try {
//						valueStringArray = new String[] { URLEncoder.encode((String) oldRequestParam.get(paramKey), enc) };
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//						valueStringArray = new String[] { (String) oldRequestParam.get(paramKey) };
//					}
//				}
				else if(oldRequestParam.get(paramKey) instanceof String[]) {
					List<String> tmp = new ArrayList<String>();
					for (String string : oldRequestParam.get(paramKey)) {
						try {
							tmp.add(URLEncoder.encode(string, enc));
						} catch (UnsupportedEncodingException e) {
//							e.printStackTrace();
							tmp.add(string);
						}
					}
					valueStringArray = tmp.toArray(new String[0]);
				} 
				
				
//				newRequestParam.put(paramKey, (((valueStringArray != null) && (valueStringArray.length > 0)) ? valueStringArray[0] : null));
				if(null == valueStringArray) {
					newRequestParam.put(paramKey, null);
				} 
//				else if(1 == valueStringArray.length) {
//					newRequestParam.put(paramKey, valueStringArray[0]);
//				}
				else {
					newRequestParam.put(paramKey, valueStringArray);
				}
			}
		}
		return newRequestParam;
	}

	/** @see #orderRequestParam(HttpServletRequest, String) */
	public static final Map<String, String[]> orderRequestParam(final HttpServletRequest request) {
		return orderRequestParam(request, "UTF-8");
	}

//	/* 将map参数转换为String类型写入request请求体中 */
//	private static final String urlParamToString(final Map<String, Object> urlParam) {
//		StringBuilder tmp = new StringBuilder();
//		for (Iterator<Entry<String, Object>> it = urlParam.entrySet().iterator(); it.hasNext();) {
//			Entry<String, Object> entry = it.next();
//			String key = entry.getKey();
//			String value = entry.getValue().toString();
//			tmp.append(key).append("=").append(value).append("&");
//		}
//		if(0 < tmp.length()) {// 截掉最后一个&号
//			tmp.delete(tmp.length()-1, tmp.length());
//		}
//		return tmp.toString();
//	}
//
//	/* 将map参数转换为String类型写入request请求体中 */
//	private static final String urlParamToString(Map<String, String[]> urlParam) {
//		StringBuilder tmp = new StringBuilder();
//		for (Iterator<Entry<String, String[]>> it = urlParam.entrySet().iterator(); it.hasNext();) {
//			Entry<String, String[]> entry = it.next();
//			String key = (String) entry.getKey();
//			String value = "";
//			if (null == entry.getValue()) {
//				value = "";
//			} else if (entry.getValue() instanceof Object[]) {
//				value += "[";
//				for (Object _val : (Object[]) entry.getValue()) {
//					value += "\"" + _val + "\",";
//				}
//				value = value.substring(0, value.length() - 1);
//				value += "]";
//			}
//			tmp.append(key).append("=").append(value).append("&");
//		}
//		if (tmp.length() > 0) {
//			tmp.delete(tmp.length() - 1, tmp.length());
//		}
//		return tmp.toString();
//	}

	/* 将map参数转换为String类型写入request请求体中 */
	private static final String urlParamToString(Map<String, String[]> urlParam) {
		StringBuilder tmp = new StringBuilder();
		for (Iterator<Entry<String, String[]>> it = urlParam.entrySet().iterator(); it.hasNext();) {
			Entry<String, String[]> entry = it.next();
			String key = (String) entry.getKey();
			String value = "";
			if (null == entry.getValue()) {
				value = "";
			} else {
				for (String _val : entry.getValue()) {
					value += _val + ",";
				}
				value = value.substring(0, value.length() - 1);
			}
			tmp.append(key).append("=").append(value).append("&");
		}
		if (tmp.length() > 0) {
			tmp.delete(tmp.length() - 1, tmp.length());
		}
		return tmp.toString();
	}

	/** @author hcqt@qq.com */
	public static final boolean urlExists(String url) {
		try {
			URL _url = new URL(url);
			return urlExists(_url);
		} catch (MalformedURLException e) {
			throw new BaseException(
					err451Code, 
					ResMsgUtils.resolve(err451Msg, url), 
					e, 
					url);
		}
	}

	/** @author hcqt@qq.com */
	public static final boolean urlExists(URL url) {
		InputStream netFileInputStream = null;
		try {
			URLConnection urlConn = url.openConnection();
			netFileInputStream = urlConn.getInputStream();
			if (null != netFileInputStream) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		} finally {
			if (netFileInputStream != null) {
				try { netFileInputStream.close(); } catch (IOException e) { }
			}
		}
	}
}
