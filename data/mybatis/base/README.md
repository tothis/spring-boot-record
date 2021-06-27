# MyBatis

***

转义字符表

转义字符|SQL 字符|描述
-|-|-
&lt;|<|小于
&gt;|>|大于
或使用`<![CDATA[ ]]>` mybatis不会解析其中sql
如<![CDATA[ start_time <= TO_DAYS(NOW())]]>
***
JDBC Type|Java Type
-|-
char|String
varchar|String
decimal|BigDecimal
bit(1)|boolean
tinyint|byte
smallint|short
int|Integer
bigint|long
float|float
double|double
date|LocalDate
time|LocalTime
datetime|LocalDateTime
timestamp|long
