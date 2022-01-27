package com.pig4cloud.pig.common.mybatis.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CommObjectTypeHandler<E> extends BaseTypeHandler {

	private Class<E> type;

	public CommObjectTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null.");
		}
		this.type = type;
	}


	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
		preparedStatement.setString(i, JSON.toJSONString(o));
	}

	@Override
	public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
		String str = resultSet.getString(s);
		return resultSet.wasNull() ? null : JSONObject.parseObject(str, type);
	}

	@Override
	public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
		String str = resultSet.getString(i);
		return resultSet.wasNull() ? null : JSONObject.parseObject(str, type);
	}

	@Override
	public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		String str = callableStatement.getString(i);
		return callableStatement.wasNull() ? null : JSONObject.parseObject(str, type);
	}
}
