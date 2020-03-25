package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author 李磊
 * @datetime 2020/3/17 13:39
 * @description
 */
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id")
            , @Result(column = "user_name", property = "userName")
    })
    User findById(Long id);

    @Update("UPDATE user SET user_name = 'xxxx'")
    long update(Long id);
}