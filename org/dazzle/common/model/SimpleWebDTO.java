package org.dazzle.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dazzle.common.exception.BaseException;
import org.dazzle.utils.DTU;
import org.dazzle.utils.EU;

import com.google.gson.GsonBuilder;

/** @author hcqt@qq.com */
public class SimpleWebDTO implements WebDTO {

	private final Map<String, Object> codes;

	/** @author hcqt@qq.com */
	public SimpleWebDTO() { 
		super(); 
		codes = new HashMap<>();
	}

	/** @author hcqt@qq.com */
	public SimpleWebDTO(boolean needSequence) { 
		super(); 
		if(needSequence) {
			codes = new LinkedHashMap<>();
		} else {
			codes = new HashMap<>();
		}
	}

	/** @author hcqt@qq.com */
	@SuppressWarnings("unchecked")
	public List<Object> getList() {
		return DTU.cvt(List.class, codes.get("list"));
	}

	/** @author hcqt@qq.com */
	public WebDTO setList(Object list) {
		codes.put("list", DTU.cvt(ArrayList.class, list));
		return this;
	}

	/** @author hcqt@qq.com */
	public String getSuccess() {
		return DTU.cvt(String.class, codes.get("success"));
	}

	/** @author hcqt@qq.com */
	public WebDTO setSuccess(String success) {
		codes.put("success", success);
		codes.put("msg", success);
		return this;
	}

	/** @author hcqt@qq.com */
	public String getError() {
		return DTU.cvt(String.class, codes.get("error"));
	}

	/** @author hcqt@qq.com */
	public WebDTO setError(String error) {
		codes.put("error", error);
		codes.put("msg", error);
		return this;
	}

	/** @author hcqt@qq.com */
	@Override
	public String getMsg() {
		return DTU.cvt(String.class, codes.get("msg"));
	}

	/** @author hcqt@qq.com */
	@Override
	public WebDTO setMsg(String msg) {
		codes.put("msg", msg);
		return this;
	}

	/** @author hcqt@qq.com */
	public String getCode() {
		return DTU.cvt(String.class, codes.get("code"));
	}

	/** @author hcqt@qq.com */
	public WebDTO setCode(String code) {
		codes.put("code", code);
		return this;
	}

	/** @author hcqt@qq.com */
	@Override
	public WebDTO set(String key, Object val) {
		this.codes.put(key, val);
		return this;
	}

	/** @author hcqt@qq.com */
	@Override
	public WebDTO set(Map<String, Object> codes) {
		this.codes.putAll(codes);
		return this;
	}

	/** @author hcqt@qq.com */
	@Override
	public Map<String, Object> get() {
		return codes;
	}

	/** @author hcqt@qq.com */
	@Override
	public String getJson() {
		try {
			return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create().toJson(get());
		} catch (Throwable e) {
			throw new BaseException("web_dto_k3h43", "数据无法转换为JSON格式，详情——{0}", e, EU.out(e));
		}
	}

}
