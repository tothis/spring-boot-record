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

    @Delete("DELETE FROM user WHERE id = #{id}")
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

    /**
     * 使用别名时 ORDER BY要以别名排序 第一个表使用别名
     * 使用UNION时 注意字段数量一致
     *
     * @return
     */
    // @Select("SELECT 名称, 排序 FROM `test1` UNION ALL SELECT 名称, 排序 FROM `test2` ORDER BY 排序 DESC")
    @Select("SELECT 名称, 排序 AS sort FROM `test1` UNION ALL SELECT 名称, 排序 FROM `test2` ORDER BY sort DESC")
    List<Map> sort();

    /**
     * test表数据为 content = 'a,b,c'
     *
     * @return
     */
    @Select("SELECT substring_index( substring_index( a.content, ',', b.help_topic_id + 1 ), ',' ,-1 ) " +
            "FROM test a JOIN mysql.help_topic b ON b.help_topic_id < ( length( a.content ) " +
            "- length( REPLACE ( a.content, ',', '' ))+ 1 ) " +
            "WHERE substring_index(substring_index(a.content, ',', b.help_topic_id+1), ',', -1) <> ''")
    List<String> split();

    // 当字符串不为空串时 且判断具体值时需使用双引号包含
    @Select("<script>" +
            "<if test=\"e == ''\">" +
            "SELECT 0" +
            "</if>" +
            "<if test='e == \"1\"'>" +
            "SELECT 1" +
            "</if>" +
            "</script>")
    String $if(String content);
}