# mybatis
***
**mybatis转义字符表**
转义字符|sql字符|描述
-|-|-
&lt;|<|小于
&gt;|>|大于
&amp;|&|与
&apos;|'|单引号
&quot;|"|双引号
或使用`<![CDATA[ ]]>` mybatis不会解析其中sql
如<![CDATA[ start_time <= TO_DAYS(NOW())]]>
***
JDBC Type|Java Type
-|-
CHAR|String
VARCHAR|String
LONGVARCHAR|String
NUMERIC|java.math.BigDecimal
DECIMAL|java.math.BigDecimal
BIT|boolean
BOOLEAN|boolean
TINYINT|byte
SMALLINT|short
INTEGER|INTEGER
BIGINT|long
REAL|float
FLOAT|double
DOUBLE|double
BINARY|byte[]
VARBINARY|byte[]
LONGVARBINARY|byte[]
DATE|java.sql.Date
TIME|java.sql.Time
TIMESTAMP|java.sql.Timestamp
CLOB|Clob
BLOB|Blob
ARRAY|Array
DISTINCT|mapping of underlying type
STRUCT|Struct
REF|Ref
DATALINK|java.net.URL
***
一对一关系
```xml
<resultMap id="one-user" type="user">
    <result column="detail" property="type.detail"/>
</resultMap>

<select id="user1" resultMap="one-user">
    SELECT a.user_name userName, b.detail FROM `user` a
    LEFT JOIN `type` b ON b.id = a.type_id
    WHERE a.id = 1
</select>
<!-- 一对一关系可以直接使用别名查询 -->
<select id="user2" resultType="user">
    SELECT a.user_name userName , b.detail `type.detail`
    FROM `user` a LEFT JOIN `type` b ON b.id = a.type_id
    WHERE a.id = 1
</select>
```
***
一对多关系
+ 内部类使用
```xml
<resultMap id="user" type="user">
    <id property="id" column="id"/>
    <result property="userName" column="user_name"/>
    <!-- 内部类不能使用实体别名 -->
    <collection property="addresses" ofType="com.example.model.User$Address">
        <result column="address_id" property="id"/>
        <result column="detail" property="detail"/>
    </collection>
</resultMap>
```