package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 李磊
 * @datatime 2020-01-16
 * @description mapper
 */
public interface UserMapper {

    @Insert("INSERT INTO user (user_name, password, age, address) VALUES (#{userName}, #{password}, #{age}, #{address})")
    int insert(User user);

    @Delete("DEELTE FROM user WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT user_name, password, age, address FROM user WHERE id = #{id}")
    User selectById(Long id);

    List<User> findAll(User user);
}