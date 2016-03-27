package org.dazzle.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.dazzle.common.exception.BaseException;

/** @author hcqt@qq.com */
public class DateUtils {

	public static final String DEF_FMT = "yyyy-MM-dd HH:mm:ss";
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DEF_FMT);

	/** 得到几天前的时间
	 * @author hcqt@qq.com */
	public static Date getBeforeDay(Date date, Integer day) {
		if(null == date) {
			return null;
		}
		if(null == day){
			return date;
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
			return now.getTime();
		}
	}

	/** 得到几天后的时间
	 * @author hcqt@qq.com */
	public static Date getAfterDay(Date date, Integer day) {
		if(null == date) {
			return null;
		}
		if(null == day){
			return date;
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
			return now.getTime();
		}
	}
	
	/** @author hcqt@qq.com */
	public static final Date parse(String date, String format) {
		if(date == null || date.trim().isEmpty()) {
			return null;
		}
		DateFormat df = null;
		if(format == null || format.trim().isEmpty()) {
			df = DATE_FORMAT;
		}
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new BaseException("ser_date_39H8n", "传入的字符串\"{0}\"无法转换为时间格式\"{1}\"", e, date, ((format == null || format.trim().isEmpty() ? DEF_FMT : format)));
		}
	}

	/** @author hcqt@qq.com */
	public static final String format(Date date,String format) {
		if(date == null) {
			return null;
		}
		DateFormat df = null;
		if(format == null || format.trim().isEmpty()) {
			df = DATE_FORMAT;
		}
		return df.format(date);
	}

}
