package org.dazzle.utils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DataTruncation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTransactionRollbackException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dazzle.common.exception.BaseException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**本软件为开源项目，最新项目发布于github，可提交您的代码到本开源软件，项目网址：<a href="https://github.com/hcqt/dazzle">https://github.com/hcqt/dazzle</a><br />
 * 本软件内的大多数方法禁止Override，原因是作者提倡组合，而非继承，如果您确实需要用到继承，而又希望用本软件提供的方法名称与参数列表，建议您自行采用适配器设计模式，逐个用同名方法包裹本软件所提供的方法，这样您依然可以使用继承
 * @author hcqt@qq.com*/
public class SpringDaoUtils {
	
	public enum Opt {
		/** 等于 = ,equal */
		EQ,
		/** 小于 < ,less than */
		LT,
		/** 大于 > ,greater than */
		GT,
		/** 小于等于 <= ,less than and equal */
		LE,
		/** 大于等于 >= ,greater than and equal */
		GE,
		/** 不等于 <> ,not equal */
		NEQ,
		/** 为空 IS NULL ,empty */
		EM,
		/** 不为空 IS NOT NULL ,not empty*/
		NEM,
		/** 全匹配模糊 LIKE '%abc%' */
		LK,
		/** 仅模糊左边 LIKE '%abc' */
		LKL,
		/** 仅模糊右边 LIKE 'abc%' */
		LKR,
		/** 模糊进行非运算 NOT LIKE '%abc%' */
		NLK,
		/** 仅模糊左边，进行非运算 NOT LIKE '%abc' */
		NLKL,
		/** 仅模糊右边，进行非运算 NOT LIKE 'abc%' */
		NLKR,
		/** IN */
		IN, 
		/** NOT IN */
		NIN,
		/** BETWEEN AND */
		BT,
		/** REGEXP */
		RE
		;
	}

//	/**@author hcqt@qq.com*/
//	public static final Long insert(
//			JdbcOperations jdbcOperations, 
//			Map<String, String> fieldsMapping, 
//			String tableName, 
//			Map<String, Object> data) {
//		String[] fields = DTU.cvt(String[].class, data.keySet().toArray());
//		StringBuilder sql = insertSql(fieldsMapping, tableName, fields);
//		return insert(jdbcOperations, sql.toString(), fields, data);
//	}
//
//	/**@author hcqt@qq.com*/
//	public static final Long insert(
//			JdbcOperations jdbcOperations, 
//			String sql,
//			Map<String, Object> data) {
//		String[] fields = DTU.cvt(String[].class, data.keySet().toArray());
//		return insert(jdbcOperations, sql, fields, data);
//	}

	/**@author hcqt@qq.com*/
	public static final Long insert(
			JdbcOperations jdbcOperations, 
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			String tableName, 
			Map<String, Object> data) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		insertSql(fieldsMapping, tablesMapping, tableName, data, sql, params);
		return insert(jdbcOperations, sql.toString(), params);
	}

	/**@author hcqt@qq.com*/
	public static final Long insert(
			JdbcOperations jdbcOperations, 
			final String sql,
			final List<Object> params) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			Logger.getLogger("org.dazzle.util").debug("sql ==>" + sql.toString());
			Logger.getLogger("org.dazzle.util").debug("args==>" + params);
			jdbcOperations.update(
					new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
							setParams(ps, params);
							return ps;
						}
					}, keyHolder);
			if(null != keyHolder.getKey()) {
				return keyHolder.getKey().longValue();
			}
		} catch (RuntimeException e) {
			catchException(e);
		}
		return null;
	}

	/**@author hcqt@qq.com*/
	public static final void update(
			JdbcOperations jdbcOperations, 
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			String tableName, 
			Object setData, 
			Object where) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		updateSql(fieldsMapping, tablesMapping, tableName, setData, where, sql, params);
		update(jdbcOperations, sql.toString(), params);
	}
//	public static final void update(
//			JdbcOperations jdbcOperations, 
//			Map<String, String> fieldsMapping, 
//			String tableName, 
//			Object setData, 
//			Object where) {
////		List<Map<String, Object>> _where = whereMapFormat(where);
//		String[] fields = DTU.cvt(String[].class, setData.keySet().toArray());
//		StringBuilder sql = new StringBuilder();
//		List<Object> params = new ArrayList<>();
//		updateSql(fieldsMapping, tableName, fields, where, sql, params);
////		updateSqlAppendWhere(sql, fieldsMapping, _where);
////		update(jdbcOperations, sql.toString(), fields, setData, _where);
//		update(jdbcOperations, sql.toString(), params);
//	}
//	
//	public static final void update(
//			JdbcOperations jdbcOperations, 
//			final String sql, 
//			final String[] fields, 
//			final Map<String, Object> setData, 
//			final List<Map<String, Object>> where) {
//		try {
//			jdbcOperations.update(
//					new PreparedStatementCreator() {
//						@Override
//						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//							PreparedStatement ps = conn.prepareStatement(sql.toString());
//							List<String> fieldsList = Arrays.asList(fields);
//							Map<String, Object> _data = new HashMap<>(setData);
//							renameDuplicateField(fieldsList, _data, where);
//							setData(ps, DTU.cvt(String[].class, fieldsList.toArray()), _data);
//							return ps;
//						}
//					});
//		} catch (RuntimeException e) {
//			catchException(e);
//		}
//	}

	/**@author hcqt@qq.com*/
	public static final void update(
			JdbcOperations jdbcOperations, 
			final String sql, 
			final List<Object> params) {
		try {
			Logger.getLogger("org.dazzle.util").debug("sql ==>" + sql.toString());
			Logger.getLogger("org.dazzle.util").debug("args==>" + params);
			jdbcOperations.update(
					new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(sql.toString());
							setParams(ps, params);
							return ps;
						}
					});
		} catch (RuntimeException e) {
			catchException(e);
		}
	}

	/**@author hcqt@qq.com*/
	private static final void setParams(
			PreparedStatement ps, 
			List<Object> params) throws SQLException {
		if(params != null) {
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
		}
	}

	/**@author hcqt@qq.com*/
	public static final void delete(
			JdbcOperations jdbcOperations, 
			Map<String, String> fieldsMapping, 
			String tableName, 
			List<Map<String, Object>> where) {
		StringBuilder sql = deleteSql(fieldsMapping, tableName, where);
		delete(jdbcOperations, sql.toString(), where);
	}

	/**@author hcqt@qq.com*/
	public static final void delete(
			JdbcOperations jdbcOperations, 
			final String sql, 
			final List<Map<String, Object>> where) {
		try {
			jdbcOperations.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(sql);
					List<String> fields = new ArrayList<>();
					Map<String, Object> data = new HashMap<>();
					renameDuplicateField(fields, data, where);
					setData(ps, DTU.cvt(String[].class, fields.toArray()), data);
					return ps;
				}
			});
		} catch (RuntimeException e) {
			catchException(e);
		}
	}

	/**@author hcqt@qq.com*/
	public static final Map<String, Object> find(
			JdbcOperations jdbcOperations, 
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			Map<String, String> resultMappping, 
			Object selectFields, 
			String fromTable, 
			Object where, 
			String groupByHaving, 
			String orderBy,
			Integer startNum, 
			Integer rowNum, 
			Boolean autoCount, 
			Boolean needResultSequence) {
		return find(jdbcOperations, fieldsMapping, tablesMapping, resultMapppingTranslate(resultMappping), selectFields, fromTable, where, groupByHaving, orderBy, startNum, rowNum, autoCount, needResultSequence);
	}

	/**@author hcqt@qq.com*/
	public static final Map<String, Object> find(
			JdbcOperations jdbcOperations, 
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			Map<String, String> resultMappping, 
			Map<String, Object> sqlArgs) {
		return find(jdbcOperations, fieldsMapping, tablesMapping, resultMapppingTranslate(resultMappping), sqlArgs);
	}
	
	/**@author hcqt@qq.com*/
	public static final Map<String, Object> find(
			JdbcOperations jdbcOperations, 
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			List<Map<String, Object>> resultMappping, 
			Map<String, Object> sqlArgs) {
		Object selectFields = DTU.cvt(Object.class, sqlArgs.get("selectFields"));
		Boolean needResultSequence = DTU.cvt(Boolean.class, sqlArgs.get("needResultSequence"));
		Boolean autoCount = DTU.cvt(Boolean.class, sqlArgs.get("autoCount"));
		Integer rowNum = DTU.cvt(Integer.class, sqlArgs.get("rowNum"));
		Integer startNum = DTU.cvt(Integer.class, sqlArgs.get("startNum"));
		String orderBy = DTU.cvt(String.class, sqlArgs.get("orderBy"));
		String groupByHaving = DTU.cvt(String.class, sqlArgs.get("groupByHaving"));
		Object where = DTU.cvt(Object.class, sqlArgs.get("where"));
		String fromTable = DTU.cvt(String.class, sqlArgs.get("fromTable"));
		return find(jdbcOperations, fieldsMapping, tablesMapping, resultMappping, selectFields, fromTable, where, groupByHaving, orderBy, startNum, rowNum, autoCount, needResultSequence);
	}

	/**@author hcqt@qq.com*/
	public static final Map<String, Object> find(
			JdbcOperations jdbcOperations, 
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			List<Map<String, Object>> resultMappping, 
			Object selectFields, 
			String fromTable, 
			Object where, 
			String groupByHaving, 
			String orderBy,
			Integer startNum, 
			Integer rowNum, 
			Boolean autoCount, 
			Boolean needResultSequence) {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		findSql(fieldsMapping, tablesMapping, selectFields, fromTable, where, groupByHaving, orderBy, startNum, rowNum, sql, params);
		List<Map<String, Object>> result = find(jdbcOperations, resultMappping, needResultSequence, sql.toString(), params);
		Map<String, Object> ret = new LinkedHashMap<>();
		if(null != autoCount && autoCount) {
			ret.put("countNum", findAutoCount(jdbcOperations, needResultSequence, sql.toString(), params));
		}
		ret.put("fieldsMapping", fieldsMapping);
		ret.put("tablesMapping", tablesMapping);
		ret.put("resultMappping", resultMappping);
		ret.put("selectFields", selectFields);
		ret.put("fromTable", fromTable);
		ret.put("where", where);
		ret.put("groupByHaving", groupByHaving);
		ret.put("orderBy", orderBy);
		ret.put("startNum", startNum);
		ret.put("rowNum", rowNum);
		ret.put("autoCount", autoCount);
		ret.put("needResultSequence", needResultSequence);
		ret.put("sql", sql.toString());
		ret.put("sqlParams", params);
		ret.put("result", result);
		pageCalc(ret);
		return ret;
	}

	/**@author hcqt@qq.com*/
	public static final List<Map<String, Object>> find(
			JdbcOperations jdbcOperations, 
			Map<String, String> resultMappping, 
			Boolean needResultSequence, 
			String sql, 
			List<Object> params) {
		return find(jdbcOperations, resultMapppingTranslate(resultMappping), needResultSequence, sql, params);
	}

	/**@author hcqt@qq.com*/
	public static final void findSql(
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			Object selectFields, 
			String fromTable, 
			Object where, 
			String groupByHaving, 
			String orderBy,
			Integer startNum, 
			Integer rowNum, 
			StringBuilder sql,
			List<Object> params
			) {
		if(sql == null) {
			// TODO 
		}
		if(params == null) {
			// TODO 
		}
		sql.append("SELECT");
		fieldsSql(sql, fieldsMapping, selectFields);
		sql.append(" FROM");
		tableSql(sql, tablesMapping, fieldsMapping, fromTable);
		whereSql(sql, where, fieldsMapping, params);
		groupBySql(sql, groupByHaving, fieldsMapping);
		orderBySql(sql, orderBy, fieldsMapping);
		limitSql(sql, startNum, rowNum, params);
	}

	/**@author hcqt@qq.com*/
	public static final String findSql(
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			Object selectFields, 
			String fromTable, 
			Object where, 
			String groupByHaving, 
			String orderBy,
			Integer startNum, 
			Integer rowNum
			) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		findSql(fieldsMapping, tablesMapping, selectFields, fromTable, where, groupByHaving, orderBy, startNum, rowNum, sql, params);
		return sql.toString();
	}

	/**@author hcqt@qq.com*/
	public static final List<Object> findSqlParams(
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			Object selectFields, 
			String fromTable, 
			Object where, 
			String groupByHaving, 
			String orderBy,
			Integer startNum, 
			Integer rowNum) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append("SELECT");
		fieldsSql(sql, fieldsMapping, selectFields);
		sql.append(" FROM");
		tableSql(sql, tablesMapping, fieldsMapping, fromTable);
		whereSql(sql, where, fieldsMapping, params);
		groupBySql(sql, groupByHaving, fieldsMapping);
		orderBySql(sql, orderBy, fieldsMapping);
		limitSql(sql, startNum, rowNum, params);
		return params;
	}

	/**@author hcqt@qq.com*/
	public static final List<Map<String, Object>> find(
			JdbcOperations jdbcOperations, 
			final List<Map<String, Object>> resultMappping, 
			final Boolean needResultSequence, 
			final String sql, 
			final List<Object> params) {
		try {
			Logger.getLogger("org.dazzle.util").debug("sql ==>" + sql.toString());
			Logger.getLogger("org.dazzle.util").debug("args==>" + params);
			List<Map<String, Object>> result = jdbcOperations.query(
					new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(sql);
							setParams(ps, params);
							return ps;
						}
					}, new RowMapper<Map<String, Object>>() {
						@Override
						public Map<String, Object> mapRow(ResultSet rs, int rn) throws SQLException {
							Map<String, Object> ret;
							if(null != needResultSequence && needResultSequence) {
								ret = new LinkedHashMap<>();
							} else {
								ret = new HashMap<>();
							}
							Class<?> type = null;
							String field = null;
							String tbField = null;
							for (Map<String, Object> item : resultMappping) {
								if(null == item) {
									continue;
								}
								field = DTU.cvt(String.class, item.get("field"));
								if(null == field) {
									continue;
								}
								tbField = DTU.cvt(String.class, item.get("tbField"));
								if(null == tbField) {
									continue;
								}
								type = DTU.cvt(Class.class, item.get("type"));
								if(null == type) {
									type = Object.class;
								}
								try {
									ret.put(field, DTU.cvt(type, rs.getObject(tbField)));
								} catch (SQLException e) {
									ret.put(field, null);
								}
							}
							return ret;
						}
					});
			return result;
		} catch (RuntimeException e) {
			catchException(e);
			return null;
		}
	}

	/**@author hcqt@qq.com*/
	public static final void execute(
			JdbcOperations jdbcOperations, 
			final String sql, 
			final List<Object> params) {
		try {
			Logger.getLogger("org.dazzle.util").debug("sql ==>" + sql.toString());
			Logger.getLogger("org.dazzle.util").debug("args==>" + params);
			jdbcOperations.execute(
					new PreparedStatementCreator() {
						
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(sql);
							if(params != null) {
								for (int i = 0; i < params.size(); i++) {
									ps.setObject(i + 1, params.get(i));
								}
							}
							return ps;
						}
					},
					new PreparedStatementCallback<Object>() {
						@Override
						public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
							ps.execute();
							return null;
						}
					});
		} catch (RuntimeException e) {
			catchException(e);
		}
	}

	/**@author hcqt@qq.com*/
	public static final List<Map<String, Object>> whereMapFormat(Map<String, Object> where) {
		List<Map<String, Object>> _where = new ArrayList<>();
		Map<String, Object> tmp = null;
		for (Entry<String, Object> item : where.entrySet()) {
			tmp = new HashMap<>();
			tmp.put("field", item.getKey());
			tmp.put("opt", Opt.EQ);
			tmp.put("val", item.getValue());
			tmp.put("conn", "AND");
			_where.add(tmp);
		}
		return _where;
	}

	/**@author hcqt@qq.com*/
	private static final void pageCalc(Map<String, Object> findRetData) {
		/*
		 * 依据startNum和rowNum以及countNum计算出
		 * 当前页数/上一页数/下一页数/是否有上一页/是否有下一页
		 * 总页数/第一页数/最后一页数
		 */
		Integer countNum = DTU.cvt(Integer.class, findRetData.get("countNum"));
		if(null == countNum || countNum.compareTo(1) <= 0) {
			return;
		}
		Integer rowNum = DTU.cvt(Integer.class, findRetData.get("rowNum"));
		rowNum = ((rowNum == null || rowNum <= 0) ? countNum : rowNum);
		// 总条数除以每页的数量，然后向上取整就是总页数
		Integer totalPageNum = (int) Math.ceil((double) countNum/ (double) rowNum);
		
		findRetData.put("tpn", totalPageNum);
		findRetData.put("totalPageNum", totalPageNum);
		
		findRetData.put("fpn", 1);
		findRetData.put("firstPageNum", 1);
		
		findRetData.put("lpn", totalPageNum);
		findRetData.put("lastPageNum", totalPageNum);
		
		Integer currentPageNum;
		if(rowNum <= 1) {// 这里不用三目运算符是由于避免发生除零异常
			currentPageNum = 1;
		} else {
			Integer startNum = DTU.cvt(Integer.class, findRetData.get("startNum"));
			if(startNum == null || startNum < 0) {
				startNum = 0;
			}
			currentPageNum = (startNum / rowNum) + 1;
		}
		
		findRetData.put("cpn", currentPageNum);
		findRetData.put("currentPageNum", currentPageNum);
		
		Integer previousPageNum = ((currentPageNum - 1) <= 0) ? 1 : currentPageNum - 1;
		previousPageNum = (previousPageNum > totalPageNum) ? totalPageNum : previousPageNum;
		findRetData.put("ppn", previousPageNum);
		findRetData.put("previousPageNum", previousPageNum);
		
		
		Integer nextPageNum = ((currentPageNum + 1) <= 0) ? 1 : currentPageNum + 1;
		nextPageNum = (nextPageNum > totalPageNum) ? totalPageNum : nextPageNum;
		findRetData.put("npn", nextPageNum);
		findRetData.put("nextPageNum", nextPageNum);
		
		boolean hasPreviousPage = previousPageNum < currentPageNum;
		findRetData.put("hpp", hasPreviousPage);
		findRetData.put("hasPreviousPage", hasPreviousPage);
		
		boolean hasNextPage = nextPageNum > currentPageNum;
		findRetData.put("hnp", hasNextPage);
		findRetData.put("hasNextPage", hasNextPage);
	}

	public static final Integer calcStartNumByPageNum(Integer pageNum, Integer pageSize) {
		if(pageNum == null) {
			return null;
		}
		if(pageNum <= 0) {
			return null;
		}
		if(pageSize == null) {
			return null;
		}
		if(pageSize <= 0) {
			return null;
		}
		return (pageNum - 1) * pageSize;
	}

	/**@author hcqt@qq.com*/
	private static final int findAutoCount(
			JdbcOperations jdbcOperations, 
			Boolean needResultSequence, 
			String sql, 
			List<Object> params) {
		sql = sql.replaceAll("\\s+", " ");
		// 把原始sql当中where之后的其他子语句全部截掉
		String[] keywords = {"GROUP BY","HAVING","ORDER BY","LIMIT"};
		String tmp = null;
		for (String keyword : keywords) {
			tmp = SU.subStringBeforeIgnoreCase(sql, keyword, 1);
			if(tmp != null) {
				int countNum = SU.subStringCountNum(SU.subStringAfterIgnoreCase(sql, keyword, 1), "?");
				for (int i = params.size(), j = i - countNum; i > j; i--) {
					params.remove(i - 1);
				}
				sql = tmp;
			}
		}
		// 把原始sql当中的字段替换成COUNT聚合
		sql = sql.replaceAll("SELECT.*FROM", "SELECT COUNT(1) AS countNum FROM");
		Map<String, String> resultMappping = new HashMap<>();
		resultMappping.put("countNum", "countNum");
		List<Map<String, Object>> result = find(jdbcOperations, resultMappping, needResultSequence, sql, params);
		if(null == result) {
			return 0;
		}
		for (Map<String, Object> r : result) {
			if(null == r) {
				continue;
			}
			Object countNumObj = r.get("countNum");
			if(null == countNumObj) {
				continue;
			}
			return DTU.cvt(int.class, countNumObj);
		}
		return 0;
	}

	/**@author hcqt@qq.com*/
	private static final List<Map<String, Object>> resultMapppingTranslate(Map<String, String> resultMappping) {
		if(null == resultMappping) {
			return null;
		}
		List<Map<String, Object>> ret = new ArrayList<>();
		for (Entry<String, String> item : resultMappping.entrySet()) {
			Map<String, Object> map = new HashMap<>();
			map.put("field", item.getKey());
			map.put("tbField", item.getValue());
			map.put("type", Object.class);
			ret.add(map);
		}
		return ret;
	}

	/**@author hcqt@qq.com*/
	private static final void orderBySql(StringBuilder sql, String orderBy, Map<String, String> fieldsMapping) {
		/*
		 * 按照逗号分隔，每一段逐个解析
		 */
		if(null == orderBy || orderBy.trim().isEmpty()) {
			return;
		}
		orderBy = orderBy.replaceAll("\\s+", " ");
		String[] orderBySplit = orderBy.split(",");
		if(DTU.deepIsNull(orderBySplit)) {
			return;
		}
		sql.append(" ORDER BY");
		int i;
		String tmp0,tmp1;
		for (String item : orderBySplit) {
			i = SU.indexOfIgnoreCase(item.trim(), "DESC", -1);
			if(i != -1) {
				tmp0 = SU.subStringBeforeIgnoreCase(item.trim(), "DESC", -1);
				tmp1 = fieldTranslate(tmp0, fieldsMapping);
				if(null == tmp1) {
					continue;
				}
				sql.append(tmp1).append(" DESC,");
			}
			i = SU.indexOfIgnoreCase(item.trim(), "ASC", -1);
			if(i != -1) {
				tmp0 = SU.subStringBeforeIgnoreCase(item.trim(), "ASC", -1);
				tmp1 = fieldTranslate(tmp0, fieldsMapping);
				if(null == tmp1) {
					continue;
				}
				sql.append(tmp1).append(" ASC,");
			}
		}
		SU.deleteSuffix(sql, ",");
	}

	/**@author hcqt@qq.com*/
	private static final void limitSql(StringBuilder sql, Integer startNum, Integer rowNum, List<Object> params) {
		if (null != startNum && null != rowNum) {
			sql.append(" LIMIT ?,?");
			params.add(startNum);
			params.add(rowNum);
		} else if(null != rowNum) {
			sql.append(" LIMIT ?");
			params.add(rowNum);
		}
	}

	/**@author hcqt@qq.com*/
	private static final void groupBySql(StringBuilder sql, String groupByHaving, Map<String, String> fieldsMapping) {
		if(null == groupByHaving) {
			return;
		}
		groupByHaving = groupByHaving.replaceAll("\\s+", " ");
		/*
		 * having关键字前后截断，前边的是groupby的语句，按照逗号再截取，然后把字段进行翻译
		 * 后半部分可能包含聚合函数，要把聚合函数中的完全限定名、字段名进行解析
		 */
		String beforHaving = SU.subStringBeforeIgnoreCase(groupByHaving, "HAVING", 1);
		String afterHaving = SU.subStringAfterIgnoreCase(groupByHaving, "HAVING", 1);
		if(null == beforHaving && null == afterHaving) {
			beforHaving = groupByHaving;
		}
		groupBySqlBefor(sql, beforHaving, fieldsMapping);
		if(null != afterHaving) {
			groupBySqlAfter(sql, afterHaving, fieldsMapping);
		}
	}

	/**@author hcqt@qq.com*/
	private static final void groupBySqlAfter(StringBuilder sql, String afterHaving, Map<String, String> fieldsMapping) {
		// TODO
	}

	/**@author hcqt@qq.com*/
	private static final void groupBySqlBefor(StringBuilder sql, String beforHaving, Map<String, String> fieldsMapping) {
		if(null == beforHaving) {
			return;
		}
		String[] beforSplit = beforHaving.split(",");
		if(DTU.deepIsNull(beforSplit)) {
			return;
		}
		sql.append(" GROUP BY ");
		String tmp = null;
		for (String item : beforSplit) {
			tmp = fieldTranslate(item, fieldsMapping);
			// 这里的字段翻译不准出现AS
			if(-1 != SU.indexOfIgnoreCase(tmp, " AS ", 1)) {
				tmp  = SU.subStringBeforeIgnoreCase(tmp, " AS ", 1);
			}
			if(null == tmp || tmp.trim().isEmpty()) {
				continue;
			}
			sql.append(tmp).append(",");
		}
		SU.deleteSuffix(sql, ",");
	}

	/**@author hcqt@qq.com*/
	private static final void fieldsSql(StringBuilder sql, Map<String, String> fieldsMapping, Object fields) {
		if(null == fields) {
			return;
		}
		String[] _fields = null;
		if(String.class.isAssignableFrom(fields.getClass())) {
			String tmp = DTU.cvt(String.class, fields);
			if(null != tmp) {
				_fields = tmp.split(",");
			}
		}
		else {
			_fields = DTU.cvt(String[].class, fields);
		}
		if(null != _fields && _fields.length >= 1) {
			for (String field : _fields) {
				sql.append(fieldTranslate(field, fieldsMapping)).append(",");
			}
			sql.delete(sql.length()-1, sql.length());
		}
	}

	/**@author hcqt@qq.com*/
	private static final String tableTranslate(String ele, Map<String, String> eleMapping) {
		ele = ele.trim();
		String currentNameSpace = SU.subStringBefore(ele, ".", -1);
		String currentTableAndAlias = SU.subStringAfter(ele, ".", -1);
		if(null == currentTableAndAlias) {
			currentTableAndAlias = ele;
		}
		String currentTableName = SU.subStringBeforeIgnoreCase(currentTableAndAlias, " AS ", -1);
		String currentAlias = SU.subStringAfterIgnoreCase(currentTableAndAlias, " AS ", -1);
		if(null == currentTableName) {
			currentTableName = SU.subStringBefore(currentTableAndAlias, " ", -1);
		}
		if(null == currentTableName) {
			currentTableName = currentTableAndAlias;
		}
		if(null != eleMapping && null != eleMapping.get(currentTableName)) {
			currentTableName = eleMapping.get(currentTableName);
		}
		if(null == currentAlias) {
			currentAlias = SU.subStringAfter(currentTableAndAlias, " ", -1);
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" ");
		if(null != currentNameSpace) {
			sql.append(currentNameSpace).append(".");
		}
		if(null != currentTableName) {
			sql.append(currentTableName);
		}
		if(null != currentAlias) {
			sql.append(" AS ").append(currentAlias);
		}
		return sql.toString();
	}

	/**@author hcqt@qq.com*/
	private static final String fieldTranslate(String ele, Map<String, String> eleMapping) {
		ele = ele.trim();
		String currentNameSpace = SU.subStringBefore(ele, ".", -1);
		String currentTableAndAlias = SU.subStringAfter(ele, ".", -1);
		if(null == currentTableAndAlias) {
			currentTableAndAlias = ele;
		}
		String currentTableName = SU.subStringBeforeIgnoreCase(currentTableAndAlias, " AS ", -1);
		String currentAlias = SU.subStringAfterIgnoreCase(currentTableAndAlias, " AS ", -1);
		if(null == currentTableName) {
			currentTableName = SU.subStringBefore(currentTableAndAlias, " ", -1);
		}
		if(null == currentTableName) {
			currentTableName = currentTableAndAlias;
		}
		if(null == currentNameSpace && null != eleMapping && null != eleMapping.get(currentTableName)) {
			currentTableName = eleMapping.get(currentTableName);
		} 
		else if(null != currentNameSpace && null != eleMapping && null != eleMapping.get(currentNameSpace + "." + currentTableName)) {
			currentTableName = eleMapping.get(currentNameSpace + "." + currentTableName);
			// get出来的实际字段也有可能是一个完全限定名，所以按照有别名的考虑截取，保证最大兼容
			currentTableName = SU.subStringAfter(currentTableName, ".", -1);
		}
		if(null == currentAlias) {
			currentAlias = SU.subStringAfter(currentTableAndAlias, " ", -1);
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" ");
		if(null != currentNameSpace) {
			sql.append(currentNameSpace).append(".");
		}
		if(null != currentTableName) {
			sql.append(currentTableName);
		}
		if(null != currentAlias) {
			sql.append(" AS ").append(currentAlias);
		}
		return sql.toString();
	}
	
//	public static void main(String[] args) {
////		String table = "table1 as a left join table2 as b join table3 c right join test.table1 d inner Join  test.table1  e join test.table2 ";
////		String table = "table3 c right join test.table1 d inner Join  test.table1  e";
//		String table = "test.table3 c right join test.table1 d inner Join  test.table1  e";
//		Map<String, String> tablesMapping = new HashMap<>();
//		tablesMapping.put("table1", "user");
//		tablesMapping.put("table2", "userInfo");
//		tablesMapping.put("table3", "userLog");
//		StringBuilder sql = new StringBuilder();
//		tableSql(sql, tablesMapping, table);
//		System.out.println(sql.toString());
//	}
	private static final String[] separators = {"JOIN","INNER JOIN","LEFT JOIN","LEFT OUTER JOIN","RIGHT JOIN","RIGHT OUTER JOIN","FULL JOIN","FULL OUTER JOIN","CROSS JOIN"," ON "};

//	private static final void tableSql(StringBuilder sql, Map<String, String> tablesMapping, String table) {
//		table = table.replaceAll("\\s+", " ").trim();
//		String firstSeparator = getFirstSeparator(table, separators);
//		String current;
//		if(null == firstSeparator) {
//			current = table;
//		} else {
//			current = SU.subStringBeforIgnoreCase(table, firstSeparator, 1);
//		}
//		current = current.trim();
//		String currentNameSpace = SU.subStringBefor(current, ".", -1);
//		String currentTableAndAlias = SU.subStringAfter(current, ".", -1);
//		if(null == currentTableAndAlias) {
//			currentTableAndAlias = current;
//		}
//		String currentTableName = SU.subStringBeforIgnoreCase(currentTableAndAlias, " AS ", -1);
//		String currentAlias = SU.subStringAfterIgnoreCase(currentTableAndAlias, " AS ", -1);
//		if(null == currentTableName) {
//			currentTableName = SU.subStringBefor(currentTableAndAlias, " ", -1);
//		}
//		if(null != tablesMapping && null != tablesMapping.get(currentTableName)) {
//			currentTableName = tablesMapping.get(currentTableName);
//		}
//		if(null == currentAlias) {
//			currentAlias = SU.subStringAfter(currentTableAndAlias, " ", -1);
//		}
//		sql.append(" ");
//		if(null != currentNameSpace) {
//			sql.append(currentNameSpace).append(".");
//		}
//		if(null != currentTableName) {
//			sql.append(currentTableName);
//		}
//		if(null != currentAlias) {
//			sql.append(" AS ").append(currentAlias);
//		}
//		if(null != firstSeparator) {
//			sql.append(" ").append(firstSeparator);
//			tableSql(sql, tablesMapping, SU.subStringAfterIgnoreCase(table, firstSeparator, 1));
//		}
//	}
	/**@author hcqt@qq.com*/
	private static final void tableSql(StringBuilder sql, Map<String, String> tablesMapping, Map<String, String> fieldsMapping, String table) {
		table = table.replaceAll("\\s+", " ").trim();
		String firstSeparator = getFirstSeparator(table, separators);
		String current;
		if(null == firstSeparator) {
			current = table;
		} else {
			current = SU.subStringBeforeIgnoreCase(table, firstSeparator, 1);
		}
		if(tableNameActuallyIsField(current) == null) {
			sql.append(tableTranslate(current, tablesMapping));
		} else {
			String[] currentFields = current.split("\\s+");
			for (String currentField : currentFields) {
				sql.append(" ").append(fieldTranslate(currentField, fieldsMapping));
			}
		}
		if(null != firstSeparator) {
			sql.append(" ").append(firstSeparator);
			tableSql(sql, tablesMapping, fieldsMapping, SU.subStringAfterIgnoreCase(table, firstSeparator, 1));
		}
	}

	/**@author hcqt@qq.com*/
	private static final Opt tableNameActuallyIsField(String tableName) {
		// TODO 正则表达式扩充，最大兼容判断所有情况
		if(tableName.contains("<=")) {
			return Opt.LE;
		}
		else if(tableName.contains(">=")) {
			return Opt.GE;
		}
		else if(tableName.contains("<>")) {
			return Opt.NEQ;
		}
		else if(tableName.contains("!=")) {
			return Opt.NEQ;
		}
		else if(tableName.contains("=")) {
			return Opt.EQ;
		}
		else if(tableName.contains("<")) {
			return Opt.LT;
		}
		else if(tableName.contains(">")) {
			return Opt.GT;
		}
		else if(tableName.contains("IS NULL")) {
			return Opt.EM;
		}
		else if(tableName.contains("IS NOT NULL")) {
			return Opt.NEM;
		}
		else if(tableName.contains("LIKE")) {
			return Opt.LK;// TODO 其他几种LIKE将来用正则扩充
		}
		else if(tableName.contains("NOT IN")) {
			return Opt.NIN;
		}
		else if(tableName.contains("IN")) {
			return Opt.IN;
		}
		else if(tableName.contains("BETWEEN")) {
			return Opt.BT;
		}
		else if(tableName.contains("REGEXP")) {
			return Opt.RE;
		}
		return null;
	}

	/**@author hcqt@qq.com*/
	private static final String getFirstSeparator(String table, String... separators) {
		Integer a = getFirstSeparatorIndex(table, separators);
		if(null == a || a == -1) {
			return null;
		}
		for (String separator : separators) {
			if(a.intValue() == SU.indexOfIgnoreCase(table, separator, 1)) {
				return separator;
			}
		}
		return null;
	}

	/**@author hcqt@qq.com*/
	private static final Integer getFirstSeparatorIndex(String table, String... separators) {
		Set<Integer> a = new LinkedHashSet<>();
		for (String separator : separators) {
			a.add(SU.indexOfIgnoreCase(table, separator, 1));
		}
		a.remove(-1);
		Integer[] b = DTU.cvt(Integer[].class, a);
		if(DTU.deepIsNull(b)) {
			
		}
		Arrays.sort(b);
		if(b.length <= 0) {
			return -1;
		}
		return b[0];
	}

//	private static final Map<String, Integer> separatorFirstIndex(String str, String[] separators) {
//		if(null == str) {
//			return null;
//		}
//		Map<String, Integer> ret = new HashMap<>();
//		for (String separator : separators) {
//			if(null == separator) {
//				ret.put(separator, -1);
//			}
//			ret.put(separator, str.indexOf(separator));
//		}
//		return ret;
//	}
	
//	private static final Map<String, Integer> separatorFirstIndexIgnoreCase(String str, String[] separators) {
//		if(null == str) {
//			return null;
//		}
//		Map<String, Integer> ret = new HashMap<>();
//		for (String separator : separators) {
//			if(null == separator) {
//				ret.put(separator, -1);
//			}
//			ret.put(separator, str.toUpperCase().indexOf(separator.toUpperCase()));
//		}
//		return ret;
//	}

	/**@author hcqt@qq.com*/
	private static final void whereSql(StringBuilder sql, Object where, Map<String, String> fieldsMapping, List<Object> params) {
		if(null == where) {
			return;
		}
		if(-1 == SU.indexOfIgnoreCase(sql.toString(), "WHERE", 1)) {
			sql.append(" WHERE");
		}
		if(List.class.isAssignableFrom(where.getClass())) {
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) where;
			sql.append(" (");
			for (Object item : list) {
				whereSql(sql, item, fieldsMapping, params);
			}
			SU.deleteSuffix(sql, "AND");
			sql.append(")");
		}
		else if(Map.class.isAssignableFrom(where.getClass())) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) where;
			Opt opt = DTU.cvt(Opt.class, MU.getIgnoreCase(map, "opt"));
			String conn = DTU.cvt(String.class, MU.getIgnoreCase(map, "conn"));
			if(opt == null && conn == null) {// 如果map没有遵循规范，自动规范，然后递归此方法
				whereSql(sql, whereMapFormat(map), fieldsMapping, params);
			} else {
				if(null != opt && null != MU.getIgnoreCase(map, "field")) {
					whereSqlMap0(sql, opt, map, fieldsMapping, params);
				}
				if(null != conn) {
					sql.append(" ").append(conn.trim().toUpperCase());
				}
			}
		}
		else if(String.class.isAssignableFrom(where.getClass())) {
			String conn = DTU.cvt(String.class, where);
			if(conn.trim().equalsIgnoreCase("AND")) {
				sql.append(" AND");
			} 
			else if(conn.trim().equalsIgnoreCase("OR")) {
				sql.append(" OR");
			} 
			else {// 如果是输入了字符串写就的where条件，这里自动格式化为规范数据，然后递归此方法
				// 判断是否为json字符串，如果是json字符串，就格式化为list/map嵌套格式，然后递归此方法
				// 如果不是json字符串，先抛出异常，将来兼容
				try {
					whereSql(sql, JU.toObj(conn), fieldsMapping, params);
				} catch (Exception e) {
					throw new BaseException("springDaoUtils_83n0J", "传入的where条件不符合规范");
				}
			}
		}
	}

//	@SuppressWarnings("unchecked")
//	private static final void whereSqlEQ(
//			StringBuilder sql, 
//			Map<String, Object> where, 
//			Map<String, String> fieldsMapping) {
//		Object val = MU.getIgnoreCase(where, "val");
//		sql.append(" ").append(aliasField(where, fieldsMapping));
//		if(val instanceof Map) {
//			sql.append("=").append(aliasField(DTU.cvt(Map.class, val), fieldsMapping));
//		} else {
//			sql.append("=?");
//		}
//	}

	/**@author hcqt@qq.com*/
	private static final void whereSqlMap0(
			StringBuilder sql, 
			Opt opt, 
			Map<String, Object> where, 
			Map<String, String> fieldsMapping, 
			List<Object> params) {
		if(null == opt) {
			opt = Opt.EQ;
		}
		Object val = MU.getIgnoreCase(where, "val");
		if(null != val && val.getClass().isArray()) {
			if(opt.equals(Opt.EQ)) {
				opt = Opt.IN;
			}
		} 
		switch (opt) {
			case EQ: {
				whereSqlSwitch0(sql, where, fieldsMapping, opt, params);
				break;
			}
			case LT: {
				whereSqlSwitch0(sql, where, fieldsMapping, opt, params);
				break;
			}
			case GT: {
				whereSqlSwitch0(sql, where, fieldsMapping, opt, params);
				break;
			}
			case LE: {
				whereSqlSwitch0(sql, where, fieldsMapping, opt, params);
				break;
			}
			case GE: {
				whereSqlSwitch0(sql, where, fieldsMapping, opt, params);
				break;
			}
			case NEQ: {
				whereSqlSwitch0(sql, where, fieldsMapping, opt, params);
				break;
			}
			case EM: {
				whereSqlSwitch1(sql, where, fieldsMapping, opt);
				break;
			}
			case NEM: {
				whereSqlSwitch1(sql, where, fieldsMapping, opt);
				break;
			}
			case LK: {
				whereSqlSwitch3(sql, where, fieldsMapping, opt, params);
				break;
			}
			case LKL: {
				whereSqlSwitch3(sql, where, fieldsMapping, opt, params);
				break;
			}
			case LKR: {
				whereSqlSwitch3(sql, where, fieldsMapping, opt, params);
				break;
			}
			case NLK: {
				whereSqlSwitch3(sql, where, fieldsMapping, opt, params);
				break;
			}
			case NLKL: {
				whereSqlSwitch3(sql, where, fieldsMapping, opt, params);
				break;
			}
			case NLKR: {
				whereSqlSwitch3(sql, where, fieldsMapping, opt, params);
				break;
			}
			case IN : {
				whereSqlSwitch2(sql, where, fieldsMapping, opt, params);
				break;
			}
			case NIN : {
				whereSqlSwitch2(sql, where, fieldsMapping, opt, params);
				break;
			}
			case BT : {
				whereSqlSwitchBT(sql, where, fieldsMapping, params);
				break;
			}
			case RE : {
				whereSqlSwitchRE(sql, where, fieldsMapping, params);
				break;
			}
			default:
				// TODO
				break;
		}
	}

	/**@author hcqt@qq.com*/
	@SuppressWarnings("unchecked")
	private static final void whereSqlSwitch0(
			StringBuilder sql, 
			Map<String, Object> where, 
			Map<String, String> fieldsMapping, 
			Opt opt, 
			List<Object> params) {
		Object val = MU.getIgnoreCase(where, "val");
		sql.append(" ").append(aliasField(where, fieldsMapping));
		switch (opt) {
			case EQ:
				sql.append("=");
				break;
			case LT:
				sql.append("<");
				break;
			case GT: 
				sql.append(">");
				break;
			case LE: 
				sql.append("<=");
				break;
			case GE: 
				sql.append(">=");
				break;
			case NEQ: 
				sql.append("<>");
				break;
			default:
//				throw new BaseException(code, message);
				break;
		}
		if(val instanceof Map) {
			sql.append(aliasField(DTU.cvt(Map.class, val), fieldsMapping));
		} else {
			sql.append("?");
			params.add(val);
		}
	}

	/**@author hcqt@qq.com*/
	private static final void whereSqlSwitch1(
			StringBuilder sql, 
			Map<String, Object> where, 
			Map<String, String> fieldsMapping, 
			Opt opt) {
		sql.append(" ").append(aliasField(where, fieldsMapping));
		switch (opt) {
			case EM:
				sql.append(" IS NULL ");
				break;
			case NEM: 
				sql.append(" IS NOT NULL ");
				break;
			default:
//				throw new BaseException(code, message);
				break;
		}
	}

	/**@author hcqt@qq.com*/
	private static final void whereSqlSwitch2(
			StringBuilder sql, 
			Map<String, Object> where, 
			Map<String, String> fieldsMapping, 
			Opt opt, 
			List<Object> params) {
		sql.append(" ").append(aliasField(where, fieldsMapping));
		switch (opt) {
			case IN:
				sql.append(" IN(");
				break;
			case NIN:
				sql.append(" NOT IN(");
				break;
			default:
//				throw new BaseException(code, message);
				break;
		}
		Object[] arr = DTU.cvt(Object[].class, MU.getIgnoreCase(where, "val"));
		if(DTU.deepIsNull(arr)) {
			throw new BaseException("springDaoUtils_mj3kP", "使用in运算符时，传入值必须是“数组”/“Collection”，且不能为空");
		}
//		for (Object it : arr) {
//			if(DTU.isNum(it)) {
//				sql.append(it).append(",");
//			} else {
//				sql.append("'").append(escapeSpecialChar(DTU.cvt(String.class, it))).append("'").append(",");
//			}
//		}
		for (Object it : arr) {
			if(DTU.isNum(it)) {
				sql.append("?,");
			} else {
				sql.append("'?',");
			}
			params.add(it);
		}
		sql.delete(sql.length() - 1, sql.length());
		sql.append(")");
	}

	/**@author hcqt@qq.com*/
	private static final void whereSqlSwitchBT(
			StringBuilder sql, 
			Map<String, Object> where, 
			Map<String, String> fieldsMapping,
			List<Object> params) {
		Object[] arr = DTU.cvt(Object[].class, MU.getIgnoreCase(where, "val"));
		if(null == arr || arr.length != 2) {
			throw new BaseException("springDaoUtils_k3hmj","使用between运算符时,传入的值必须是有且只有两个元素的\"数组\"/\"Collection\"");
		}
		sql.append(" ").append(aliasField(where, fieldsMapping));
		if(DTU.isNum(arr)) {
			sql.append(" BETWEEN ? AND ?");
			params.add(arr[0]);
			params.add(arr[1]);
		} else {
//			sql.append(" BETWEEN '");
//			sql.append(DTU.cvt(String.class, arr[0]));
//			sql.append("' AND '");
//			sql.append(DTU.cvt(String.class, arr[1]));
//			sql.append("'");
			
			sql.append(" BETWEEN '?' AND '?'");
			params.add(DTU.cvt(String.class, arr[0]));
			params.add(DTU.cvt(String.class, arr[1]));
		}
	}

	/**@author hcqt@qq.com*/
	private static final void whereSqlSwitchRE(
			StringBuilder sql, 
			Map<String, Object> where, 
			Map<String, String> fieldsMapping, 
			List<Object> params) {
//		sql.append(" ").append(aliasField(where, fieldsMapping));
//		sql.append(" REGEXP '");
//		sql.append(DTU.cvt(String.class, MU.getIgnoreCase(where, "val")));
//		sql.append("'");
		sql.append(" ").append(aliasField(where, fieldsMapping));
		sql.append(" REGEXP '?'");
		params.add(DTU.cvt(String.class, MU.getIgnoreCase(where, "val")));
	}

	/**@author hcqt@qq.com*/
	private static final void whereSqlSwitch3(
			StringBuilder sql, 
			Map<String, Object> where, 
			Map<String, String> fieldsMapping, 
			Opt opt,
			List<Object> params) {
		sql.append(" ").append(aliasField(where, fieldsMapping));
		switch (opt) {
			case LK:
				sql.append(" LIKE '%?%'");
				break;
			case LKL:
				sql.append(" LIKE '%?'");
				break;
			case LKR:
				sql.append(" LIKE '?%'");
				break;
			case NLK:
				sql.append(" NOT LIKE '%?%'");
				break;
			case NLKL:
				sql.append(" NOT LIKE '%?'");
				break;
			case NLKR:
				sql.append(" NOT LIKE '?%'");
				break;
			default:
				// TODO
				break;
		}
		params.add(escapeSpecialChar(DTU.cvt(String.class, MU.getIgnoreCase(where, "val"))));
		
//		sql.append(" ").append(aliasField(where, fieldsMapping));
//		switch (opt) {
//			case LK:
//				sql.append(" LIKE '%");
//				break;
//			case LKL:
//				sql.append(" LIKE '%");
//				break;
//			case LKR:
//				sql.append(" LIKE '");
//				break;
//			case NLK:
//				sql.append(" NOT LIKE '%");
//				break;
//			case NLKL:
//				sql.append(" NOT LIKE '%");
//				break;
//			case NLKR:
//				sql.append(" NOT LIKE '");
//				break;
//			default:
//				// 
//				break;
//		}
//		sql.append(escapeSpecialChar(DTU.cvt(String.class, MU.getIgnoreCase(where, "val"))));
//		switch (opt) {
//			case LK:
//				sql.append("%'");
//				break;
//			case LKL:
//				sql.append("'");
//				break;
//			case LKR:
//				sql.append("%'");
//				break;
//			case NLK:
//				sql.append("%'");
//				break;
//			case NLKL:
//				sql.append("'");
//				break;
//			case NLKR:
//				sql.append("%'");
//				break;
//			default:
////				throw new 
//				break;
//		}
	}

	/**@author hcqt@qq.com*/
	private static final String aliasField(
			Map<String, Object> where, 
			Map<String, String> fieldsMapping) {
		String field = DTU.cvt(String.class, MU.getIgnoreCase(where, "field"));
		String[] fields = field.split("\\.");
		String tbAlias = null;
		String tbField = null;
		if(fields.length == 1) {
			if(null == fieldsMapping) {
				tbField = fields[0];
			} else {
				tbField = fieldsMapping.get(fields[0]);
			}
		}
		else if(fields.length == 2) {
			if(null == fieldsMapping) {
				tbAlias = fields[0];
				tbField = fields[1];
			} else {
				tbAlias = fields[0];
				// 先用完全限定名的方式尝试获取
				tbField = fieldsMapping.get(field);
				if(null == tbField) {
					tbField = fieldsMapping.get(fields[1]);
				}
				tbField = SU.subStringAfter(tbField, ".", -1);
			}
		}
		if(tbAlias == null) {
			return tbField;
		} else {
			return tbAlias + "." + tbField;
		}
	}

	/**@author hcqt@qq.com*/
	private static final String escapeSpecialChar(String str) {
		return str.replace("'", "\\'").replace("%", "\\%").replace("_", "\\_").replace("\\", "\\\\\\");
	}

	private static final StringBuilder deleteSql(
			Map<String, String> fieldsMapping, 
			String tableName, 
			List<Map<String, Object>> where) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append(tableName);
		sql.append("WHERE ");
		String tbField = null;
		String field = null;
		String opt = null;
		for (Map<String, Object> item : where) {
			field = DTU.cvt(String.class, item.get("field"));
			if(null == field) {
				continue;
			}
			if(fieldsMapping != null) {
				tbField = fieldsMapping.get(field);
			} else {
				tbField = field;
			}
			if(null == tbField) {
				continue;
			}
			sql.append(tbField);
			sql.append(" ");
			opt = DTU.cvt(String.class, item.get("opt"));
			if(null == opt) {
				opt = "=";
			}
			sql.append(opt);
			sql.append(" ? AND");
		}
		sql.delete(sql.length() - 3, sql.length());
		return sql;
	}

//	/**@author hcqt@qq.com*/
//	private static final StringBuilder insertSql(
//			Map<String, String> fieldsMapping, 
//			String tableName, 
//			String[] fields) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("INSERT INTO");
//		sql.append(" ").append(tableName);
//		insertSqlAppendFields0(fieldsMapping, sql, fields);
//		sql.append(" ").append("VALUES");
//		insertSqlAppendFields1(fieldsMapping, sql, fields);
//		return sql;
//	}
//	
	/**@author hcqt@qq.com*/
	private static final StringBuilder insertSql(
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			String tableName, 
			Map<String, Object> data, 
			StringBuilder sql, 
			List<Object> params) {
		sql.append("INSERT INTO");
		tableSql(sql, tablesMapping, fieldsMapping, tableName);
		sql.append(" (");
		for (Entry<String, Object> param : data.entrySet()) {
			if(param == null) {
				continue;
			}
			sql.append(fieldTranslate(param.getKey(), fieldsMapping));
			sql.append(",");
		}
		SU.deleteSuffix(sql, ",");
		sql.append(")");
		sql.append(" ").append("VALUES");
		sql.append(" (");
		for (Entry<String, Object> it : data.entrySet()) {
			if(it == null) {
				continue;
			}
			sql.append("?,");
			params.add(it.getValue());
		}
		SU.deleteSuffix(sql, ",");
		sql.append(")");
		return sql;
	}

	/**@author hcqt@qq.com*/
	private static final void updateSql(
			Map<String, String> fieldsMapping, 
			Map<String, String> tablesMapping, 
			String tableName, 
			Object setData, 
			Object where, 
			StringBuilder sql,
			List<Object> params) {
		sql.append("UPDATE ");
		tableSql(sql, tablesMapping, fieldsMapping, tableName);
		sql.append(" SET ");
		// 检查是否有set数据
		if(Map.class.isAssignableFrom(setData.getClass())) {
			@SuppressWarnings("unchecked")
			Map<String, Object> setDataMap = DTU.cvt(Map.class, setData);
			for (Entry<String, Object> it : setDataMap.entrySet()) {
				sql.append(it.getKey());
				params.add(it.getValue());
				sql.append("=?,");
			}
		} else {
			// TODO 
		}
		SU.deleteSuffix(sql, ",");
//		sql.delete(sql.length() - 1, sql.length());
//		updateSqlAppendWhere(sql, fieldsMapping, where);
		whereSql(sql, where, fieldsMapping, params);
	}

	/**@author hcqt@qq.com*/
	private static final void renameDuplicateField(
			List<String> fields, 
			Map<String, Object> data, 
			List<Map<String, Object>> where) {
		String field = null;
		Map<String, Object> item = null;
		String renameField = null;
		for (int i = 0; i < where.size(); i++) {
			item = where.get(i);
			if(null == item) {
				continue;
			}
			field = DTU.cvt(String.class, item.get("field"));
			if(null == field) {
				continue;
			}
			renameField = "w" + i + "_" + field;
			fields.add(renameField);
			data.put(renameField, item.get("val"));
		}
	}

//	private static final void updateSqlAppendWhere(
//			StringBuilder sql, 
//			Map<String, String> fieldsMapping, 
//			List<Map<String, Object>> where) {
//		sql.append("WHERE");
//		String field = null;
//		String tbField = null;
//		String opt = null;
//		for (Map<String, Object> item : where) {
//			field = DTU.cvt(String.class, item.get("field"));
//			if(null == field) {
//				continue;
//			}
//			if(null == fieldsMapping) {
//				tbField = field;
//			} else {
//				tbField = fieldsMapping.get(field);
//			}
//			sql.append(" ").append(tbField);
//			opt = DTU.cvt(String.class, item.get("opt"));
//			if(null == opt) {
//				sql.append("=");
//			} else {
//				sql.append(opt);
//			}
//			sql.append(" ? AND");
//		}
//		sql.delete(sql.length() - 3, sql.length());
//	}

	/**@author hcqt@qq.com*/
	private static final void catchException(RuntimeException runtimeException) {
		if(!(runtimeException instanceof DataAccessException)) {
			throw runtimeException;
		}
		if(!(runtimeException.getCause() instanceof SQLException)) {
			throw runtimeException;
		}
		
		SQLException sqlException = (SQLException) runtimeException.getCause();
		if(sqlException instanceof DataTruncation) {
			DataTruncation dataTruncation = (DataTruncation) sqlException;
			if("22001".equals(dataTruncation.getSQLState())) {
				throw new BaseException(
						msg2Code, 
						msg2, 
						runtimeException,
						dataTruncation.getMessage());
			}
		} 
		else if(sqlException instanceof SQLSyntaxErrorException) {
			SQLSyntaxErrorException sqlSyntaxErrorException = (SQLSyntaxErrorException) sqlException;
			if("42000".equals(sqlSyntaxErrorException.getSQLState())) {
				if(1110 == sqlSyntaxErrorException.getErrorCode()) {
					throw new BaseException(
							msg3Code, 
							msg3, 
							runtimeException,
							sqlSyntaxErrorException.getMessage());
				} 
				else if(1142 == sqlSyntaxErrorException.getErrorCode()) {
					throw new BaseException(
							msg4Code, 
							msg4, 
							runtimeException,
							sqlSyntaxErrorException.getMessage());
				}
			}
			else if("42S02".equals(sqlSyntaxErrorException.getSQLState())) {
				if(1146 == sqlSyntaxErrorException.getErrorCode()) {
					throw new BaseException(
							msg5Code, 
							msg5,
							runtimeException,
							sqlSyntaxErrorException.getMessage());
				}
			}
			else if("42S22".equals(sqlSyntaxErrorException.getSQLState())) {
				if(1054 == sqlSyntaxErrorException.getErrorCode()) {
					throw new BaseException(
							msg6Code, 
							msg6,
							runtimeException,
							sqlSyntaxErrorException.getMessage());
				}
			}
			throw new BaseException(
					msg7Code, 
					msg7,
					runtimeException,
					sqlSyntaxErrorException.getMessage());
		} 
		else if(sqlException instanceof SQLTransactionRollbackException) {
			SQLTransactionRollbackException sqlTransactionRollbackException = (SQLTransactionRollbackException) sqlException;
			throw new BaseException(
					msg8Code, 
					msg8,
					runtimeException,
					sqlTransactionRollbackException.getMessage());
		}
		else if(sqlException instanceof SQLIntegrityConstraintViolationException) {
			if("23000".equals(sqlException.getSQLState())) {
				if(1048 == sqlException.getErrorCode()) {
					throw new BaseException(
							msg9Code, 
							msg9,
							runtimeException,
							sqlException.getMessage());
				}
				if(1062 == sqlException.getErrorCode()) {
					throw new BaseException(
							msg10Code, 
							msg10,
							runtimeException,
							sqlException.getMessage());
				}
			}
		}
		else if(sqlException instanceof SQLNonTransientConnectionException) {
			if("08003".equals(sqlException.getSQLState())) {
				if(0 == sqlException.getErrorCode()) {
					throw new BaseException(
							msg11Code, 
							msg11,
							runtimeException,
							sqlException.getMessage());
				}
			}
		}
		/////////other////////
		if("41000".equals(sqlException.getSQLState())) {
			if(1205 == sqlException.getErrorCode()) {
				throw new BaseException(
						msg12Code, 
						msg12, 
						runtimeException,
						sqlException.getMessage());
			}
		} 
		else if("HY000".equals(sqlException.getSQLState())) {
			if(1364 == sqlException.getErrorCode()) {
				throw new BaseException(
						msg13Code, 
						msg13, 
						runtimeException,
						sqlException.getMessage());
			}
		}
//			else if("S1009".equals(sqlException.getSQLState())) {
//				if(sqlException.getCause() instanceof NotSerializableException) {
//					if(0 == sqlException.getErrorCode()) {
//						throw new BaseException(
//								msg14Code, 
//								msg14, 
//								runtimeException,
//								sqlException.getMessage());
//					}
//				}
//			}
		else if("S0022".equals(sqlException.getSQLState())) {
			if(0 == sqlException.getErrorCode()) {
				throw new BaseException(
						"spring_dao_83k4S", 
						"指定了在表中不存在列，详情——{0}", 
						runtimeException,
						sqlException.getMessage());
			}
		}
		else if("S1009".equals(sqlException.getSQLState())) {
			if(0 == sqlException.getErrorCode()) {
				throw new BaseException(
						"spring_dao_9j3kQ", 
						"SQL设置的参数超出SQL需要的个数，详情——{0}", 
						runtimeException,
						sqlException.getMessage());
			}
		}
		System.out.println("\n\n\n未捕获的SQLState\t" + sqlException.getSQLState() + "未捕获的SQL错误号\t"+sqlException.getErrorCode()+"\n\n\n");
		throw runtimeException;
	}

	/**@author hcqt@qq.com*/
	private static final void setData(
			PreparedStatement ps, 
			String[] fields, 
			Map<String, Object> data
			) throws SQLException {
		Object val = null;
		for (int i = 1; i <= fields.length; i++) {
			val = data.get(fields[i - 1]);
			if(val instanceof String) {
				ps.setString(i, DTU.cvt(String.class, val));
			}
			else if(val instanceof Number) {
				if(val instanceof Integer) {
					ps.setInt(i, DTU.cvt(Integer.class, val));
				}
				else if(val instanceof Long) {
					ps.setLong(i, DTU.cvt(Long.class, val));
				}
				else if(val instanceof Double) {
					ps.setDouble(i, DTU.cvt(Double.class, val));
				}
				else if(val instanceof BigDecimal) {
					ps.setBigDecimal(i, DTU.cvt(BigDecimal.class, val));
				}
				else if(val instanceof Float) {
					ps.setFloat(i, DTU.cvt(Float.class, val));
				}
				else if(val instanceof Short) {
					ps.setShort(i, DTU.cvt(Short.class, val));
				} 
				else {
					ps.setObject(i, val);
				}
			}
			else if(val instanceof Date) {
				if(val instanceof Time) {
					ps.setTime(i, DTU.cvt(java.sql.Time.class, val));
				}
				else if(val instanceof Timestamp) {
					ps.setTimestamp(i, DTU.cvt(java.sql.Timestamp.class, val));
				} 
				else {
					ps.setDate(i, DTU.cvt(java.sql.Date.class, val));
				}
			}
			else if(val instanceof Blob) {
				ps.setBlob(i, DTU.cvt(Blob.class, val));
			}
			else if(val instanceof Boolean) {
				ps.setBoolean(i, DTU.cvt(Boolean.class, val));
			}
			else if(val instanceof Clob) {
				ps.setClob(i, DTU.cvt(Clob.class, val));
			}
			else if(val instanceof byte[]) {
				ps.setBytes(i, DTU.cvt(byte[].class, val));
			}
			else {
				ps.setObject(i, val);
			}
//			ps.setCharacterStream(parameterIndex, reader, length);
//			ps.setArray(parameterIndex, x);
//			ps.setAsciiStream(parameterIndex, x);
//			ps.setTime(parameterIndex, x, cal);
//			ps.setTimestamp(parameterIndex, x, cal);
//			ps.setNull(parameterIndex, sqlType);
//			ps.setURL(parameterIndex, x);
		}
	}
//
//	/**@author hcqt@qq.com*/
//	private static final void insertSqlAppendFields0(
//			Map<String, String> fieldsMapping, 
//			StringBuilder sql, 
//			String[] fields) {
//		sql.append("(");
//		String tbField = null;
//		for (String field : fields) {
//			if(null == fieldsMapping) {
//				tbField = field;
//			} else {
//				tbField = fieldsMapping.get(field);
//			}
//			if(null == tbField) {
//				continue;
//			}
//			sql.append(tbField);
//			sql.append(",");
//		}
//		sql.delete(sql.length() - 1, sql.length());
//		sql.append(")");
//	}
//
//	/**@author hcqt@qq.com*/
//	private static final void insertSqlAppendFields1(
//			Map<String, String> fieldsMapping, 
//			StringBuilder sql, 
//			String[] fields) {
//		sql.append("(");
//		String tbField = null;
//		for (String field : fields) {
//			if(null == fieldsMapping) {
//				tbField = field;
//			} else {
//				tbField = fieldsMapping.get(field);
//			}
//			if(null == tbField) {
//				continue;
//			}
//			sql.append("?");
//			sql.append(",");
//		}
//		sql.delete(sql.length() - 1, sql.length());
//		sql.append(")");
//	}

	private static final String msg2Code = "spring_dao_28219";
	private static final String msg2 = "输入值数据长度超出数据库限制，详情——{0}";

	private static final String msg3Code = "spring_dao_52141";
	private static final String msg3 = "SQL语法错误，相同列名指定了多次，详情——{0}";

	private static final String msg4Code = "spring_dao_71256";
	private static final String msg4 = "您的数据库账号没有权限执行这个SQL命令，详情——{0}";

	private static final String msg5Code = "spring_dao_87272";
	private static final String msg5 = "SQL命令中使用了不存在的表，详情——{0}";

	private static final String msg6Code = "spring_dao_15125";
	private static final String msg6 = "SQL命令中发现了不存在的列名，详情——{0}";

	private static final String msg7Code = "spring_dao_54124";
	private static final String msg7 = "SQL语法错误，详情——{0}";

	private static final String msg8Code = "spring_dao_51247";
	private static final String msg8 = "无法获取锁，请重试，详情——{0}";

	private static final String msg9Code = "spring_dao_55327";
	private static final String msg9 = "SQL命令违反数据库完整性约束，详情——{0}";

	private static final String msg10Code = "spring_dao_23uhD";
	private static final String msg10 = "SQL命令违反主键约束，主键要求不能重复，详情——{0}";

	private static final String msg11Code = "spring_dao_23JHS";
	private static final String msg11 = "无法连接到数据库，详情——{0}";

	private static final String msg12Code = "spring_dao_j32bh";
	private static final String msg12 = "事务等待超时，请重试，详情——{0}";

	private static final String msg13Code = "spring_dao_J2iH5";
	private static final String msg13 = "表字段必须有值，详情——{0}";

//	private static final String msg14Code = "spring_dao_84hM3";
//	private static final String msg14 = "要操作的数据库字段无法序列化，详情——{0}";

}
