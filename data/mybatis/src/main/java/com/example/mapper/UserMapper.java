package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author 李磊
 * @datatime 2020-01-16
 * @description mapper
 */
public interface UserMapper {

    @Insert("INSERT INTO user (user_name, password, age, mail, birthday, address) VALUES (#{userName}, #{password}, #{age}, #{mail}, #{birthday}, #{address})")
    int insert(User user);

    @Delete("DEELTE FROM user WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT user_name, password, age, mail, birthday, address FROM user WHERE id = #{id}")
    User selectById(Long id);

    List<User> findAll(User user);

    @MapKey("id")
    Map<Long, User> findMap(Map params);

    @Select("SELECT #{value}")
    boolean booleanTest(Object value); // 除0外的数字为true 0 日期 字符串等为false
}