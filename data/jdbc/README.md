# jdbc
***
**官网文档 https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html**

参数名称|参数说明|缺省值
-|-|-
user|用户名|无
passWord|用户密码|无
useUnicode|是否使用Unicode字符集|false
characterEncoding|useUnicode为true时可指定字符编码 如gbk utf8|无
serverTimezone|时区 默认东八区 中国时间会相差8个小时|utc
useSSL|是否开启ssl连接|true
useAffectedRows|是否用受影响行数替代匹配行数返回|false
autoReconnect|数据库连接异常中断时是否自动重新连接|false
autoReconnectForPools|是否使用针对数据库连接池的重连策略|false
initialTimeout|autoReconnect设置为true时两次重连之间的时间间隔(单位秒)|2
failOverReadOnly|自动重连成功后连接是否设置为只读|true
maxReconnects|autoReconnect设置为true时重试连接的次数|3
connectTimeout|和数据库服务器建立socket连接时的超时(单位毫秒)|0(永不超时)
socketTimeout|socket操作读写超时(单位毫秒) 0表示永不超时|0
zeroDateTimeBehavior|java连接mysql操作值为0的DATETIME值 可选值 默认EXCEPTION抛出异常 ROUND替换为最近日期 CONVERT_TO_NULL将日期转换成NULL值
allowMultiQueries|sql语句可携带分号 多语句执行|false
treatTinyAsBoolean|是否将TINYINT(1)视为布尔型|true
allowPublicKeyRetrieval|是否允许客户端从服务器获取公钥|false