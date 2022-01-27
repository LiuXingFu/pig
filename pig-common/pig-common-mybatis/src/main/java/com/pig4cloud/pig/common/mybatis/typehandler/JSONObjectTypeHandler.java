package com.pig4cloud.pig.common.mybatis.typehandler;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({JSONObject.class})
public class JSONObjectTypeHandler extends BaseTypeHandler<JSONObject> {


	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONObject o, JdbcType jdbcType) throws SQLException {

		preparedStatement.setString(i,  o.toJSONString());
	}

	@Override
	public JSONObject getNullableResult(ResultSet resultSet, String s) throws SQLException {
		String str = resultSet.getString(s);
		return resultSet.wasNull() ? null : JSONObject.parseObject(str);
	}

	@Override
	public JSONObject getNullableResult(ResultSet resultSet, int i) throws SQLException {
		String str = resultSet.getString(i);
		return resultSet.wasNull() ? null : JSONObject.parseObject(str);
	}

	@Override
	public JSONObject getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		String str = callableStatement.getString(i);
		return callableStatement.wasNull() ? null : JSONObject.parseObject(str);
	}
}