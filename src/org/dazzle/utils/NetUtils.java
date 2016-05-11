package org.dazzle.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dazzle.common.exception.BaseException;

/**本软件为开源项目，最新项目发布于github，可提交您的代码到本开源软件，项目网址：<a href="https://github.com/hcqt/dazzle">https://github.com/hcqt/dazzle</a><br />
 * 本软件内的大多数方法禁止Override，原因是作者提倡组合，而非继承，如果您确实需要用到继承，而又希望用本软件提供的方法名称与参数列表，建议您自行采用适配器设计模式，逐个用同名方法包裹本软件所提供的方法，这样您依然可以使用继承
 * @author hcqt@qq.com*/
public class NetUtils {

	/** @author hcqt@qq.com */
	NetUtils() { super(); }

	/** @author hcqt@qq.com */
	public static final void printPlainText(
			HttpServletResponse response, 
			String content) {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "text/plain; charset=utf-8");
		print(response, header, content);
	}

	/** @author hcqt@qq.com */
	public static final void printJsonText(
			HttpServletResponse response, 
			String content) {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json; charset=utf-8");
		print(response, header, content);
	}

	/** @author hcqt@qq.com */
	public static final void print(
			HttpServletResponse response, 
			Map<String, String> header, 
			String content) {
		if(null == header) {
			header = new HashMap<>();
		}
		if(null == MU.getIgnoreCaseTrim(header, "Content-Type")) {
			header.put("Content-Type", "text/html; charset=utf-8");
		}
		for (Entry<String, String> item : header.entrySet()) {
			if(null == item.getKey()) {
				continue;
			}
			response.setHeader(item.getKey(), item.getValue());
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(content);
		} catch (IOException e) {
			throw new BaseException("net_utils_UWnk3", "从response获取out失败，详情——{0}", e, EU.out(e));
		} finally {
			out.flush();
		}
	}

	/** @see #httpRead(URL, String, Map, String)
	 * @author hcqt@qq.com */
	public static final String httpRead(
			final URI uri, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final Map<String, Object> urlParam) throws BaseException {
		try {
			return httpRead(uri.toURL(), urlMethod, requestProperty, urlParam);
		} catch (MalformedURLException e) {
			throw new BaseException(err98Code, err98Msg, e, e.getMessage());
		}
	}

	/** @see #httpRead(URL, String, Map, String)
	 * @author hcqt@qq.com */
	public static final String httpRead(
			final URL url, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final Map<String, Object> urlParam) throws BaseException {
		return httpRead(url, urlMethod, requestProperty, urlParamToString(urlParam));
	}

	/** @see #httpRead(URL, String, Map, String)
	 * @author hcqt@qq.com */
	public static final String httpRead(
			final URI uri, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final String urlQuery) throws BaseException {
		return httpRead(uriToUrl(uri), urlMethod, requestProperty, urlQuery);
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
			if(null != urlQuery) {
				out = httpConn.getOutputStream();
				out.write(urlQuery.getBytes());
			}
			httpConn.connect();
			if(200 != httpConn.getResponseCode()) {
				throw new BaseException(err100Code, err100Msg, url, httpConn.getResponseCode());
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
			final Map<String, Object> urlParam) {
		return httpsRead(uriToUrl(uri), urlMethod, requestProperty, urlParamToString(urlParam));
	}

	/** @author hcqt@qq.com */
	public static final String httpsRead(
			final URL url, 
			final String urlMethod, 
			final Map<String, String> requestProperty, 
			final Map<String, Object> urlParam) {
		return httpsRead(url, urlMethod, requestProperty, urlParamToString(urlParam));
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
			throw new BaseException(err98Code, err98Msg, e, e.getMessage());
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
			try {
				httpConn = (HttpsURLConnection) url.openConnection();
			} catch (ClassCastException e) {
				throw new BaseException("net_utils_4jjhs", "您传入的url的协议头不是https", e);
			}
			httpConn.setRequestMethod(urlMethod);
			httpConn.setDoOutput(true);
			if(null != requestProperty) {
				for (Entry<String, String> entry : requestProperty.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			if(null != urlQuery) {
				out = httpConn.getOutputStream();
				out.write(urlQuery.getBytes());
			}
			httpConn.connect();
			if(200 != httpConn.getResponseCode()) {
				throw new BaseException(err100Code, err100Msg, url, httpConn.getResponseCode());
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
			if(in != null){
				try { in.close(); } catch (IOException e) { }
			}
			if(null != httpConn) {
				httpConn.disconnect();
			}
		}
	}

	/** @author hcqt@qq.com */
	public static final URI appendParameter(URL url, Map<String, String[]> parameters, String enc) {
		return appendParameter(url.toString(), parameters, enc);
	}

	/** @author hcqt@qq.com */
	public static final URI appendParameter(String url, Map<String, String[]> parameters, String enc) {
		if(null == url) {
			return null;
		}
		Map<String, String[]> param = new LinkedHashMap<>();
		Map<String, String[]> oldParam = getParameterMap(url, enc, true);
		if(null != oldParam) {
			parameters.putAll(oldParam);
		}
		if(null != parameters) {
			param.putAll(parameters);
		}
		StringBuilder newUrl = new StringBuilder();
		newUrl.append(getUriQueryOther(url));
		if(1 <= param.size()) {
			newUrl.append("?");
		}
		for (Entry<String, String[]> item : param.entrySet()) {
			newUrl.append(item.getKey());
			newUrl.append("=");
			if(null != item.getValue()) {
				String[] values = item.getValue();
				if(1 == values.length) {
					newUrl.append(values[0]);
				}
				else if(1 < values.length) {
					for (String value : values) {
						newUrl.append(value);
						newUrl.append(",");
					}
					newUrl.delete(newUrl.length() - 1, newUrl.length());
				}
			}
			newUrl.append("&");
		}
		newUrl.delete(newUrl.length() - 1, newUrl.length());
		return strToUri(newUrl.toString());
	}

	/** @author hcqt@qq.com */
	public static final Map<String, String[]> getParameterMap(String url, String enc, boolean needSequence) {
		Map<String, String[]> ret = null;
		if(needSequence) {
			ret = new LinkedHashMap<>();
		} else {
			ret = new HashMap<>();
		}
		RequestUtil.parseParameters(ret, getUriQuery(url), enc);
		return ret;
	}
	
	/** 对请求参数进行排序，参数都已URL编码
	 * @author hcqt@qq.com */
	public static final Map<String, String[]> getParameterMap(HttpServletRequest request, String enc, boolean needSequence) {
		Enumeration<?> enu = request.getParameterNames();
		Map<String, String[]> oldRequestParam = request.getParameterMap();
		Map<String, String[]> newRequestParam = null;
		if(needSequence) {
			newRequestParam = new LinkedHashMap<String, String[]>();
		} else {
			newRequestParam = new HashMap<String, String[]>();
		}
		while(enu.hasMoreElements()) {
			String paramKey = DataTypeUtils.convert(String.class, enu.nextElement());
			if(oldRequestParam.containsKey(paramKey)) {
				String[] valueStringArray = null;
				if(null == oldRequestParam.get(paramKey)) {
					valueStringArray = null;
				} 
				else if(oldRequestParam.get(paramKey) instanceof String[]) {
					List<String> tmp = new ArrayList<String>();
					for (String string : oldRequestParam.get(paramKey)) {
						try {
							tmp.add(URLEncoder.encode(string, enc));
						} catch (UnsupportedEncodingException e) {
							tmp.add(string);
						}
					}
					valueStringArray = tmp.toArray(new String[0]);
				} 
				if(null == valueStringArray) {
					newRequestParam.put(paramKey, null);
				} 
				else {
					newRequestParam.put(paramKey, valueStringArray);
				}
			}
		}
		return newRequestParam;
	}

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
			throw new BaseException(err99Code, err99Msg, e, e.getMessage());
		} finally {
			if(null != outStream) {
				try { outStream.close(); } catch (IOException e) { }
			}
		}
	}

	private static final String urlParamToString(Map<String, Object> urlParam) {
		StringBuilder tmp = new StringBuilder();
		for (Iterator<Entry<String, Object>> it = urlParam.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> entry = it.next();
			String key = (String) entry.getKey();
			String value = "";
			if (null == entry.getValue()) {
				value = "";
			} else if(entry.getValue().getClass().isArray()) {
				for (Object _val : (Object[]) entry.getValue()) {
					value += _val + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = DTU.convert(String.class, entry.getValue());
			}
			tmp.append(key).append("=").append(value).append("&");
		}
		if (tmp.length() > 0) {
			tmp.delete(tmp.length() - 1, tmp.length());
		}
		return tmp.toString();
	}

	/** @author hcqt@qq.com */
	public static final URL uriToUrl(URI uri) {
		if(uri == null) {
			return null;
		}
		try {
			return uri.toURL();
		} catch (MalformedURLException e) {
			throw new BaseException(err98Code, err98Msg, e, e.getMessage());
		}
	}

	/** @author hcqt@qq.com */
	public static final URL strToUrl(String uriOrUrl) {
		try {
			return new URL(uriOrUrl);
		} catch (MalformedURLException e) {
			throw new BaseException(err97Code, err97Msg, e, uriOrUrl);
		}
	}

	/** @author hcqt@qq.com */
	public static final URI strToUri(String uriOrUrl) {
		try {
			return new URI(uriOrUrl);
		} catch (URISyntaxException e) {
			throw new BaseException(err87Code, err87Msg, e, uriOrUrl);
		}
	}

	/** @author hcqt@qq.com */
	public static final String getUriQuery(String uri) {
		if(uri == null) {
			return null;
		}
		if(uri.matches(".*\\?.*")) {
			return uri.substring(uri.indexOf("?") + 1, uri.length());
		} else {
			return uri;
		}
	}

	/** @author hcqt@qq.com */
	public static final String getUriQueryOther(String uri) {
		if(uri == null) {
			return null;
		}
		if(uri.matches(".*\\?.*")) {
			return uri.substring(uri.indexOf("?"));
		} else {
			return uri;
		}
	}
	
	private static final void catchException(Throwable e) {
		if(e instanceof BaseException) {
			throw (BaseException) e;
		}
		if(e instanceof java.net.ConnectException) {
			throw new BaseException(err96Code, err96Msg, e);
		} 
		else if(e instanceof java.net.NoRouteToHostException) {
			throw new BaseException(err95Code, err95Msg, e, e.getMessage());
		} 
		else {
			throw new BaseException(err94Code, err94Msg, e, EU.out(e));
		}
	}


	public static final String err100Code = "net_utils_58124";
	public static final String err100Msg = "无法正常连接URL——{0}，URL状态号——{1}";
	
	public static final String err98Code = "net_utils_o2hj3";
	public static final String err98Msg = "uri书写不符合规范，您的书写——{0}";
	
	public static final String err97Code = "net_utils_o2hj3";
	public static final String err97Msg = "url书写不符合规范，您的书写——{0}";
	
	public static final String err87Code = "net_utils_Ek3p7";
	public static final String err87Msg = "uri书写不符合规范，您的书写——{0}";
	
	public static final String err96Code = "net_utils_ugws1";
	public static final String err96Msg = "网络连接超时";

	public static final String err95Code = "net_utils_k23n5";
	public static final String err95Msg = "连接不到主机，详情——{0}";

	public static final String err94Code = "net_utils_y2ksc";
	public static final String err94Msg = "未知异常，详情——{0}";
	
	public static final String err451Code = "net_utils_y7h3m";
	public static final String err451Msg = "URL-->[{0}]语法错误，这不是一个URL";

	public static final String err99Code = "net_utils_ngs5t";
	public static final String err99Msg = "未知IO异常，详情——{0}";

	/**@author hcqt@qq.com*/
	private static final class RequestUtil {

		private static final void parseParameters(Map<String, String[]> map, String data, String encoding)
		{
			if ((data != null) && (data.length() > 0))
			{
				byte[] bytes = null;
				try {
					bytes = data.getBytes(encoding);
				} catch (UnsupportedEncodingException e) {
					throw new BaseException("net_utils_kj3hO", "字符串{0}无法转换为字符集{1}", e, data, encoding);
				}
				parseParameters(map, bytes, encoding);
			}
		}

		private static final void parseParameters(Map<String, String[]> map, byte[] data, String encoding) {
			Charset charset = Charset.forName(encoding);
			if ((data != null) && (data.length > 0)) {
				int ix = 0;
				int ox = 0;
				String key = null;
				String value = null;
				while (ix < data.length) {
					byte c = data[(ix++)];
					switch ((char)c) {
					case '&': 
						value = new String(data, 0, ox, charset);
						if (key != null) {
							putMapEntry(map, key, value);
							key = null;
						}
						ox = 0;
						break;
					case '=': 
						if (key == null) {
							key = new String(data, 0, ox, charset);
							ox = 0;
						} else {
							data[(ox++)] = c;
						}
						break;
					case '+': 
						data[(ox++)] = 32;
						break;
					case '%': 
						data[(ox++)] = ((byte)((convertHexDigit(data[(ix++)]) << 4) + convertHexDigit(data[(ix++)])));
						
						break;
					default: 
						data[(ox++)] = c;
					}
				}
				if (key != null) {
					value = new String(data, 0, ox, charset);
					putMapEntry(map, key, value);
				}
			}
		}

		private static final void putMapEntry(Map<String, String[]> map, String name, String value)
		{
			String[] newValues = null;
			String[] oldValues = (String[])map.get(name);
			if (oldValues == null) {
				newValues = new String[1];
				newValues[0] = value;
			} else {
				newValues = new String[oldValues.length + 1];
				System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
				newValues[oldValues.length] = value;
			}
			map.put(name, newValues);
		}
		private static final byte convertHexDigit(byte b)
		{
			if ((b >= 48) && (b <= 57)) return (byte)(b - 48);
			if ((b >= 97) && (b <= 102)) return (byte)(b - 97 + 10);
			if ((b >= 65) && (b <= 70)) return (byte)(b - 65 + 10);
			throw new BaseException("net_utils_Ke38E", "byte数据{0}无法转换为十六进制数字", b);
		}
	}
	
}
