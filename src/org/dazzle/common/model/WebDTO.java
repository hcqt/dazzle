package org.dazzle.common.model;

import java.util.List;
import java.util.Map;

/** @author hcqt@qq.com */
public interface WebDTO {

	/** @author hcqt@qq.com */
	public WebDTO set(String key, Object val);

	/** @author hcqt@qq.com */
	public WebDTO set(Map<String, Object> codes);

	/** @author hcqt@qq.com */
	public Map<String, Object> get();

	/** @author hcqt@qq.com */
	public String getJson();

	/** @author hcqt@qq.com */
	public List<Object> getList();

	/** @author hcqt@qq.com */
	public WebDTO setList(Object list);

	/** @author hcqt@qq.com */
	public String getMsg();

	/** @author hcqt@qq.com */
	public WebDTO setMsg(String msg);

	/** @author hcqt@qq.com */
	public String getSuccess();

	/** @author hcqt@qq.com */
	public WebDTO setSuccess(String success);

	/** @author hcqt@qq.com */
	public String getError();

	/** @author hcqt@qq.com */
	public WebDTO setError(String error);

	/** @author hcqt@qq.com */
	public String getCode();

	/** @author hcqt@qq.com */
	public WebDTO setCode(String code);

}
