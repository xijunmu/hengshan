package com.hengshan.handler;

import com.hengshan.common.utils.AESUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class EncryptTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        if (StringUtils.hasText(s)) {
            preparedStatement.setString(i, null);
            return;
        }
        preparedStatement.setString(i, AESUtil.encrypt(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return AESUtil.decrypt(resultSet.getString(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return AESUtil.decrypt(resultSet.getString(i));
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return AESUtil.decrypt(callableStatement.getString(i));
    }
}
