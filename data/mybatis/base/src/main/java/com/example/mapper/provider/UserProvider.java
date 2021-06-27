package com.example.mapper.provider;

import com.example.model.User;
import com.example.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 李磊
 */
public class UserProvider {
    public String insert(User user) {
        /*return "insert into user (name, password, age, mail, birthday) " +
                "value (#{name}, #{password}, ${age}, #{mail}, #{birthday})";*/
        return new SQL() {{
            INSERT_INTO("user");
            if (StringUtil.isNotBlank(user.getName())) {
                VALUES("name", "#{name}");
            }
            if (StringUtil.isNotBlank(user.getPassword())) {
                VALUES("password", "#{password}");
            }
        }}.toString();
    }
}
