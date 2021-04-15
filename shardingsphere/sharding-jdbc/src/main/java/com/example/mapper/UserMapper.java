package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.User;
import org.apache.ibatis.annotations.Insert;

/**
 * @author 李磊
 * @since 1.0
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 执行SQL
     *
     * @param sql
     * @return
     */
    @Insert("${sql}")
    void exec(String sql);
}