package com.example.mapper;

import com.example.mapper.provider.UserProvider;
import com.example.model.Inner;
import com.example.model.Tree;
import com.example.model.User;
import com.example.type.State;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author 李磊
 */
// 继承 CoreMapper 同时也继承了 CoreMapper 对应 XML 的配置
public interface UserMapper/* extends CoreMapper*/ {

    // 相当于 mapper.xml 中的useGeneratedKeys="true" keyColumn="id" keyProperty="id"
    // @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    // 可以获取所有数据类型的id
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    /*
     * @SelectProvide
     * 指定的 Class 必须能通过无参构造初始化
     * 指定方法必须为 public 返回值必须为 String
     */
    @InsertProvider(type = UserProvider.class, method = "insert")
    /*@Insert("insert into user (name, password, age, mail, birthday) " +
            "value (#{name}, #{password}, #{age}, #{mail}, #{birthday})")*/
    int insert(User user);

    @Insert("<script>" +
            "insert into user (name, password, age, mail, birthday) " +
            "value" +
            "<foreach collection='array' item='item' separator=','>" +
            "(#{item.name}, #{item.password}, #{item.age}, #{item.mail}, #{item.birthday})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int inserts(User[] users);

    @Delete("delete from user where id = #{id}")
    int deleteById(Long id);

    /**
     * 可使用 Optional 接收
     *
     * @param id -
     * @return -
     */
    @ResultMap("user")
    @Select("select name, password, age, mail, birthday from user WHERE id = #{id}")
    Optional<User> selectById(Long id);

    // @ResultMap("com.example.mapper.CoreMapper.user")

    /*
     * @Results
     *   ID 当前类中或当前对应 XML，其它方法可使用 @ResultMap("id") 使用此映射
     *   但其它类无法通过 namespace 引用，XML 方式还可通过 namespace 引用
     * @Result
     *   映射字段名称和类型
     * 无法定义一对多 一对一字段对应 只能通过指定其它方法实现
     */
    @Results(id = "user", value = {
            // 相同名称 可省略书写映射
            @Result(column = "name", property = "name")
            // , @Result(id = true, column = "id", property = "id")
            // , @Result(column = "age", property = "age")
    })
    @Select("select id, name, password, age from user")
    List<User> findAll(User user);

    @MapKey("id")
    Map<Long, User> findMap(Map params);

    /**
     * Java 映射 MySQL 数字，负数和 0 为 false，正数为 true
     * 非数字类型字符串直接报错
     */
    @Select("select #{value}")
    boolean booleanTest(String value);

    /**
     * 当字符串不为空串时 且判断具体值时需使用双引号包含
     */
    @Select("<script>" +
            "<if test=\"e == ''\">" +
            "select 0" +
            "</if>" +
            "<if test='e == \"1\"'>" +
            "select 1" +
            "</if>" +
            "</script>")
    String ifTest(String content);

    /**
     * 当 foreach 标签本次循环中无数据时 不会拼接 separator
     * separator 默认值为空字符串
     * 数组或集合需要使用 @Param 标识 单个实体类参数不需要标识
     * 数组可使用 `array` 作为变量名称
     * list可使用 `list` 作为变量名称
     */
    @Select("<script>" +
            "select concat(" +
            "<foreach collection='array' item='value' index='index' separator=','>" +
            "<if test=\"value != null and value != ''\">" +
            "#{value}" +
            "</if>" +
            "</foreach>" +
            ")" +
            "</script>")
    String arrayTest(/*@Param("values") */String[] values);

    @Select("select id, parent_id AS parentId, name from tree")
    List<Tree> tree();

    List<Tree> dbTree(Long parentId);

    @Insert("insert into user (is_del) value (#{state})")
    int insertEnum(State state);

    @Select("select is_del from user limit 1")
    State findEnum();

    List<Map> findPage();

    // https://commons.apache.org/proper/commons-ognl/language-guide.html
    // 调用 Java 类方法需使用`$`包含
    // @Select("select '${@com.example.util.StringUtil@uuid()}'")
    // @Select("select ${@com.example.util.StringUtil@uuid().hashCode}")
    // @Select("select '${@com.example.util.DateUtil@FORMAT}'")
    @Select("select '${'李磊'.hashCode}'")
    String run();

    @Select("<script>" +
            "select concat(#{content} ," +
            "<foreach collection='inner1s' item='item1' separator=','>" +
            "   #{item1.content} ," +
            "   <foreach collection='item1.inner2s' item='item2' separator=','>" +
            "       #{item2.content}" +
            "   </foreach>" +
            "</foreach> ," +
            "<foreach collection='inner2s' item='item2' separator=','>" +
            "   #{item2.content}" +
            "</foreach>" +
            ")" +
            "</script>")
    String inner(Inner inner);

    User.Address selectInner();
}
