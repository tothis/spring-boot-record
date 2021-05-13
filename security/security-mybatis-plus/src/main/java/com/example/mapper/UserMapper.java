package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.LoginUser;
import com.example.model.User;
import org.apache.ibatis.annotations.Select;

/**
 * @author 李磊
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据登录名查询用户
     *
     * @param userName
     * @return
     */
    @Select("SELECT id, user_name userName, password FROM user WHERE user_name = #{userName}")
    LoginUser selectByUserName(String userName);
}