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