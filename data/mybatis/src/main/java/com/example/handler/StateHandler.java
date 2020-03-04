package com.example.handler;

import com.example.type.State;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义枚举处理器
 * <p>
 * EnumTypeHandler保存枚举name(默认使用)
 * EnumOrdinalTypeHandler保存枚举index
 */
@MappedTypes(value = {State.class})
public class StateHandler<T extends State> implements TypeHandler<T> {

    /**
     * 修改设置枚举参数 使用枚举自定义属性
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, State parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        return (T) State.type(rs.getInt(columnName));
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        return (T) State.type(rs.getInt(columnIndex));
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (T) State.type(cs.getInt(columnIndex));
    }
}