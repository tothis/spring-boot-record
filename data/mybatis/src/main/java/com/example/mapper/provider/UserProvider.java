package com.example.mapper.provider;

import com.example.model.User;
import com.example.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 李磊
 * @datetime 2020/4/29 23:29
 * @description
 */
public class UserProvider {
    public String insert(User user) {
        // return "INSERT INTO user (user_name, password, age, mail, birthday) " +
        //         "VALUES (#{userName}, #{password}, #{age}, #{mail}, #{birthday})";
        return new SQL() {{
            INSERT_INTO("user");
            if (StringUtil.isNotBlank(user.getUserName())) {
                VALUES("user_name", "#{userName}");
            }
            if (StringUtil.isNotBlank(user.getPassword())) {
                VALUES("password", "#{password}");
            }
        }}.toString();
    }
}