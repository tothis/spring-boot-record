package com.example.mapper;

import com.example.model.Tree;
import com.example.model.User;
import com.example.type.State;
import org.apache.ibatis.annotations.*;

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

    // mysql if判断数字或字符串 除0外的数字或数字开头的值为true 其它值为false
    @Select("SELECT IF(#{value}, TRUE, FALSE)")
    boolean booleanString(String value);

    // java映射mysql数字 负数和0为false 正数为true
    @Select("SELECT #{value}")
    boolean booleanInt(int value);

    // 当foreach标签本次循环中无数据时 不会拼接separator
    @Select("<script>" +
            "SELECT CONCAT(" +
            "<foreach collection='values' item='value' index='index' separator=','>" +
            "<if test=\"value != null and value != ''\">" +
            "#{value}" +
            "</if>" +
            "</foreach>" +
            ")" +
            "</script>")
    // 数组需要使用@Param标识 一般单个参数不需要标识
    String arrayTest(@Param("values") String[] values);

    List<Tree> findAllTreeByParentId(Long parentId);

    @Select("SELECT id, parent_id AS parentId, name FROM tree")
    List<Tree> findAllTree();

    @Insert("INSERT INTO user (is_del) VALUES (#{state})")
    int insertEnum(State state);

    @Select("SELECT is_del FROM user LIMIT 1")
    State findEnum();
}