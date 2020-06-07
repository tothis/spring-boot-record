# thymeleaf

### 介绍
thymeleaf使用相关记录
***
```html
引用命名空间<html xmlns:th="http://www.thymeleaf.org">
```
thymeleaf无数据时input标签必须'/'结尾 不可以操作没有的数据 可通过修改配置文件解决
```json
#解析非严格的html5 标签不闭合也可使用 默认thymeleaf配置为HTML5
spring.thymeleaf.mode=LEGACYHTML5
```
逻辑运算符'>' '<' '<=' '>=' '==' '!='可正常使用 而使用'<' '>'时需要使用对应的html转义符 `&gt;` `&lt;`

使用对象名.属性名 或 对象名['属性名'] 的方式取值可直接获取值

判断实体是否为空 但最好还是使用如下方法
```html
<!-- 传统写法如果info属性不存在 会报parse错误 '?.'写法info值可为空但不能是不存在的属性 -->
<input type="text" th:value="${user?.info}">
<!-- 多级属性 -->
<input type="text" th:value="${one?.two?.three}">

<!-- 使用三元表达式进行取值 -->
<input type="text" th:value="${user!=null?user.info:''}">

<!-- 对象名?.属性名 如下等同于user==null?null:user.info -->
<input type="text" th:value="${user?.info}">

<!-- 如果user对象不存在则显示'默认值' -->
<span th:text="${user}?:'默认值'"></span>
```
后台springmvc 使用model传入数据
```java
@Controller
public class TestController {
	@ResquestMapping("test")
	public String testModel(Model model){
		List<User> userList = userService.findAll();
		User user = userService.findOne();
		model.addAttribute("user", userList);
		return "admin/userList";
	}
}
```
***
${}用法 变量表达式 访问页面上下文变量 配合th标签使用 功能同jstl中${}
```html
<input type="text" th:value="${user}">
有值时浏览器显示<input type="text" value="test">
无值时浏览器显示<input type="text" value>

<!-- 模拟数据 model.addAttribute("msg", "<h1>文本</h1>"); -->

<!-- th:text代表直接把值读取发在此标签内 -->
<input type="text" th:text="${msg}">
<!-- 结果会被转义 -->
有值时<input type="text" value="&gt;h1&lg;文本&gt;h1&lg;">
无值时<input type="text" th:text="${msg}">

<!-- th:text会对获取的值针对html转义 utext则直接将值替换到标签内部不做任何处理 -->
<span th:utext="${msg}"><span>
<!-- 结果不会被转义 -->
<span><h1>文本</h1><span>
```
另类取值方式
```html
<!-- 后台传递实体类到页面时通过实体方法取值 -->
<input type="text" th:text="${user.getName()}">
<!-- 后台实体类name变量为private但到页面可通过js取值方式获取值 -->
<input type="text" th:text="${user.name}">
<!-- 其它写法 原理同上 -->
<input type="text" th:text="${user['name']}">
```
字符串替换
```html
<!-- 原始写法 -->
<span th:text="'Welcome' + ${user.name} + '!'"></span>
<!-- 简便写法 '||'中只能包含变量表达式${} -->
<span th:text="|Welcome${user.name}!|"></span>
<!-- 结果如下 -->
<span>Welcome李磊</span>
```
获取当前url上的参数
```html
<!-- 与el表达式使用方式一致 -->
<span th:text="${param.id}"></span>
```
***
保存属性值中的引号
```html
<!-- 可以使用'|'保存引号 -->
<button th:onclick="|location.href='/user/form?id=${user.id}';|">修改</button>
<!-- 解析效果如下 -->
<button onclick="location.href='/user/form?id=1';">修改</button>

<!-- thymeleaf并不能使用'\'对字符进行转义 可增加两个引号解析成一个引号 -->
<input th:value="${'Welcome'''+user.name+''''}">
<a th:href="${'javascript:location.replace(''/word/form?id='+file.id+''');'}"></a>
<button th:onclick="${'location.href=''/video/form?id='+video.id+''''}">编辑</button>
<!-- 结果如下 -->
<input th:value="Welcome'李磊'">
<a th:href="javascript:location.replace('/word/form?id=1');"></a>
<button onclick="location.href='/video/form?id=1'">编辑</button>

注意 字符串必须包含再${}中 错误示范如下
<a th:href="'javascript:location.replace(''/word/form?id='+${file.id}+''');'"></a>
```
***
thymeleaf注释
```html
<!-- 与jsp类似thymeleaf也有可以编译时隐藏的注释 -->

thymeleaf解析时会先移除代码再解析页面
info为不存在的属性但并不会报错 staff为不存在的值解析正常
<!--/*-->
<p th:text="user.info">多行注释</p>
<p th:text="staff">多行注释</p>
<!--*/-->
info为不存在的属性但并不会报错 staff为不存在的值解析正常
<!--/*<p th:text="user.info">单行注释</p><p th:text="staff"></p>*/-->

----- ----- ----- ----- ----- ----- ----- ----- ----- -----

原型注释 thymeleaf解析时会先移除表达式保留结构再解析页面
info为不存在的属性 此时会抛出org.attoparser.ParseException
<!--/*/
	<div th:text="${user.info}">
	</div>
/*/-->
staff为不存在的值 解析正常
<!--/*/
	<div th:text="${staff}">
	</div>
/*/-->
如下为正常解析结果
<!--/*/
<div th:inner="text">
	[[${video.videoName}]]-lilei
    <p th:text="${video.videoName}"></p>
    <p>文本</p>
</div>
/*/-->
结果如下
<div inner="text">
	fileName-lilei
    <p>fileName</p>
    <p>文本</p>
</div>
----- ----- ----- ----- -----
<!--/*/
<div th:text="${video.videoName}">
	lilei
    <p th:text="${video.videoName}"></p>
    <p>文本</p>
</div>
/*/-->
结果如下
<div>fileName</div>
```
***
th:attr用法
```html
添加单个属性
th:attr="class=btn"
添加多个属性
th:attr="class=btn,title=普通按钮"
单个属性多个值
th:attr="class=|btn btn-dark|"
属性值动态赋值
th:attr="value=#{user.name},title=#{user.info}"
属性值动态拼接
th:attr="value=user_|#{user.id}|"
属性值中有引号
th:attr="value=|{test:'#user.info'}|"

src为''时 src属性会消失
th:attr="src=${user==null?'':'user.jpg')}"
```
***
在js中使用thymeleaf表达式获取数据
```html
<!--
	thymeleaf中的js使用& < >等符号会解析失败
	需把js代码包裹在<![CDATA[ XXX ]]>中转义
 -->
<script type="text/javascript" th:inline="JavaScript">
//<![CDATA[
	var user = [[${user}]]//json对象
//]]>
</script>
```
***
*{}用法 取值表达式 语法糖
```html
<!-- 相当于把userList[0]的返回值给th:object 子元素可直接通过*{}获取 -->
<div th:object="${userList[0]}" >
	<span th:text="*{name}"></span><br>
	<span th:text="*{info}"></span><br>
</div>
```
***
@{}用法 @{}相当于jsp如下用法
```java
<% String path = request.getContextPath();
	String basePath = request.getScheme()+"://"
	+request.getServerName()+":"request.getServerPort()+path+"/";
%>

${pageContext.request.getContextPath()}
```
```html
<img th:src="@{/resource/imgage/test.jpg}">
<a th:href="@{/user/findAll}">查询所有</a>
<a attr="href=${/user/findAll}">查询所有</a>
```
@{} 传参
```html
<!-- 使用逗号隔开多个参数 -->
<a th:href="@{/user/findAll(id=${user?.id}, userName='userName')}">button</a>
```
***
三元运算符用法 两种写法
```html
<td th:text="${user.sex==1?'男':'女'}"></td>
<td th:text="${user.sex}==1?'男':'女'"></td>
```
***
th:if用法 流程控制
```html
<span th:if="${userList!=null}">
	<span th:text="${user.name}"></span>
	<span th:if="${user.name} eq 'one'">相同</span>
	<span th:if="${user.name} ne 'two'">不相同</span>
	<span th:text="${user.name}?:'默认值'"></span>
</span>
```
th:unless th:if判断取反
```html
<span th:unless="${userList==null}">
	<span th:text="${user.name}"></span>
</span>
```
***
th:switch th:case 分支判断
```html
<!-- 某个case值为true 后续剩余case不会进行判断 -->
<div th:switch="${user.age}">
	<p th:case="18">18岁</p>
	<p th:case="16">16岁</p>
	<!-- *指明默认case 当整个分支无结果时的默认值 -->
	<p th:case="*">默认值</p>
</div>
```
***
foreach用法 遍历循环
```html
thymeleaf会自动生成一个变量名+'Stat'的状态变量
<tr th:each="user:${userList}">
	<td th:text="${userStat.index}"></td>
	<td th:text="${user.name}"></td>
</tr>
当然可以手动声明状态变量
<tr th:each="user,test:${userList}">
	<td th:text="${test.index}"></td>
	<td th:text="${user.name}"></td>
</tr>
从0开始 索引属性index
从1开始 统计属性count
大小属性 size
等同于当前元素 current
索引是否为奇数 odd
索引是否为偶数 even
当前元素是否为第一个元素 first
当前元素是否为最后一个元素 last
```
```java
/**
 * @Auther: 李磊
 * @Date: 2019/3/23 11:00
 */
@Controller
@RequestMapping("/Test")
public class TestController {
    @RequestMapping("/test")
    public String test(Model model){
        List list = new ArrayList();
        for (int i=0;i<10;i++) {
            list.add("第"+i+"元素");
        }
        model.addAttribute("list",list);
        return "index";
    }
}
```
```html
<table>
    <tr th:each="user:${list}">
        <td th:text="${user}"></td>
        <td th:text="${userStat.count}"/>
        <td th:text="${userStat.size}"/>
        <td th:text="${userStat.current}"/>
        <td th:text="${userStat.first}"/>
        <td th:text="${userStat.last}"/>
        <td th:text="${userStat.even}"/>
        <td th:text="${userStat.odd}"/>
        <td th:text="${userStat.index}"/>
        <td th:if="${userStat.current==user}">success</td>
    </tr>
</table>
```
浏览器显示

	第0元素	1	10	第0元素	true	false	false	true	0	success
	第1元素	2	10	第1元素	false	false	true	false	1	success
	第2元素	3	10	第2元素	false	false	false	true	2	success
	第3元素	4	10	第3元素	false	false	true	false	3	success
	第4元素	5	10	第4元素	false	false	false	true	4	success
	第5元素	6	10	第5元素	false	false	true	false	5	success
	第6元素	7	10	第6元素	false	false	false	true	6	success
	第7元素	8	10	第7元素	false	false	true	false	7	success
	第8元素	9	10	第8元素	false	false	false	true	8	success
	第9元素	10	10	第9元素	false	true	true	false	9	success
***
#{} 消息表达式或内置对象 读取properties文件 国际化

file_name.properties
```json
one = message1
two = message2
```
在application.properties中添加
```json
spring.messages.basename=file_name
```
html中使用
```html
<span th:value="#{one}"></span>
<span th:inline="text">[[#{two}]]</span>
```
内置对象 详细用法见[官网](http://www.thymeleaf.org)
```html
日期格式化
<span th:inner="text">
	[[${#dates.format(new java.util.Date().getTime(), 'yyyy-MM-dd hh:mm:ss')}]]
	[[${#dates.createNow()}]]
	[[${#dates.createToday()}]]
</span>
字符串处理
<span th:inner="text">
	[[${#strings.isEmpty(user.name)}]]
	[[${#strings.startsWith(name,'one')}]]
	[[${#strings.endsWith(name,endingFragment)}]]
	[[${#strings.length(str)}]]
	[[${#strings.equals(str)}]]
<span>

httpServletRequest
	${#httpServletRequest.getAttribute('user')}
	${#httpServletRequest.getParameter('info')}
	${#httpServletRequest.getContextPath()}
	${#httpServletRequest.getRequestName()}
httpSession
	${#httpSession.getAttribute('user')}
...
```
***
在js中使用thymeleaf表达式
```html
<script th:inline="javascript">
/*<![CDATA[*/

	var name = [[${user.name}]];
	//当无thymeleaf数据时默认值为'lilei' 有数据时会自动解析/*[[ ]]*/的表达式
	var name = /*[[${user.name}]]*/ 'lilei';

/*]]>*/
</script>
```
```html
<span th:text="${user.name}"></span>
<!-- 在文本中直接使用thymeleaf表达式 必须在其加入th:inline属性 -->
<span th:inline="text">
	Welcome : <span>[[${user.name}]]</span>
</span>
```
th:with 定义布局变量
```html
<!-- 相当于定义局部变量去接受表达式值 -->
<span th:with="name=${user.name}">
	username : <span th:text="${name}"></span>
</span>
<span th:with="name=${user.name},info=${user.info}">
	username : <span th:text="${name}"></span>
	userninfo : <span th:text="${info}"></span>
</span>
```
***
th:selected 下拉框默认选中
```html
<select>
	<!-- 表达式结果boolean值为true触发 -->
	<option th:selected="${sex=='0'}" value="0">男</option>
	<option th:selected="${sex=='1'}" value="1">女</option>
	<!-- 不管用时加上 ?'selected':'' 使用三元运算符强制赋值 -->
	<option th:selected="${sex=='x'}?'selected':''" value="x">x</option>
</select>
```
***
th:include 抽取通用代码
```html
<!-- 在template/page目录下创建test.html -->
<span th:fragment="testPage">
	<p>This is the test page</p>
</span>
```
```html
<!-- 在template目录下创建index.html -->
<!-- 被包含页的文件名文件后缀可不含 th:fragment的值 -->
<span th:replace="page/test::testPage"></span>
<span th:replace="page/test.html::testPage"></span>
```
```html
<!-- 只写第二个参数代表加载当前页面对应的fragment -->
<span th:replace="::testPage"></span>
<!-- 只写第一个参数代表加载整个页面 -->
<span th:replace="page/test::"></span>
<!-- 表达式支持 -->
<span th:include="page/test::(${user.power=10}?'admin':'staff')"></span>
```
th:include传参
```html
<!-- 声明参数 -->
<div th:fragment="test(one, two, three)">
	<p th:text="'>>> ' + ${one} + ' <<<'"></p>
	<p th:text="'>>> ' + ${two} + ' <<<'"></p>
	<p th:text="'>>> ' + ${three} + ' <<<'"></p>
</div>
```
```html
<!-- 传递参数 可使用表达式 多个参数用逗号分隔 -->
<div th:include="page/test :: test(
		one=${status} == true ? 'yes' : 'no'
		, two = 'two'
		, '测试'
	)">
</div>
```
***
th:insert和th:include与th:replace作用一致 细微区别如下
`th:insert		保留自己的主标签 保留th:fragment的主标签`
`th:replace	舍弃自己的主标签 保留th:fragment的主标签`
`th:include		保留自己的主标签 舍弃th:fragment的主标签`
如需两者标签都不保存可使用th:block配合

th:block标签
```html
<th:block th:each="video : ${videos}">
    <div th:text="${video.video_name} + '-lilei'"></div>
</th:block>
<!-- 结果如下 th:block不会产生冗余标签 -->
<div>fileName1-lilei</div>
<div>fileName2-lilei</div>
<div>fileName3-lilei</div>
----- ----- ----- ----- -----
<th:block th:each="video : ${videos}">
	<p th:text="${video}"></p>
</th:block>
<!-- 结果如下 -->
<p>fileName1</p>
<p>fileName2</p>
<p>fileName3</p>
```
## thymeleaf自定义标签
```java
/**
 * Thymeleaf 方言
 */
@Component // 被spring管理
public class NumberToLetterDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "dialectName";
    private static final String PREFIX = "prefix";

    public NumberToLetterDialect() {
        // 设置自定义方言与方言处理器优先级相同
        super(DIALECT_NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    /**
     * 元素处理器
     * @param dialectPrefix 方言前缀
     * @return
     */
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<>();
        // 添加自定义标签处理器
        processors.add(new NumberToLetterDialectProcessor(dialectPrefix));
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }
}
```
```java
/**
 * @author 李　磊
 * 标签处理器
 */
public class NumberToLetterDialectProcessor extends AbstractAttributeTagProcessor {
    // 标签名
    private static final String TAG_NAME = "tagName";
    // 属性名
    private static final String ATTRIBUTE_NAME = "attributeName";

    protected NumberToLetterDialectProcessor(String dialectPrefix) {
        super(
                // 此处理器将仅应用于HTML模式
                TemplateMode.HTML,
                // 标签前缀名 xxxx:text中的xxxx
                dialectPrefix,
                // 标签名称 匹配此名称的特定标签 null为不限制
                TAG_NAME,
                // 将标签前缀应用于标签名称
                false, // true
                // 标签前缀属性 如<xxxx:toLetter>
                ATTRIBUTE_NAME,
                // 开启属性名称前缀
                true,
                // 优先级和原生标签相同
                StandardDialect.PROCESSOR_PRECEDENCE,
                // 标签处理后是否移除自定义属性
                true);
    }

    @Override
    protected void doProcess(ITemplateContext context
            , IProcessableElementTag tag
            , AttributeName attributeName
            , String attributeValue
            , IElementTagStructureHandler structureHandler) {

        // 获取属性值
        final String rawValue = ThymeleafFacade.getRawValue(tag, attributeName);

        // 获取标签名
        // final String elementCompleteName = tag.getElementCompleteName();
        // 创建模型
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();
        // 添加模型 标签
        model.add(modelFactory.createOpenElementTag("h1"));
        model.add(modelFactory.createText(NumberToLetterConverter(rawValue)));
        model.add(modelFactory.createCloseElementTag("h1"));
        // 替换页面标签
        structureHandler.replaceWith(model, false);

        // 只改变标签内数据
        // structureHandler.setBody(NumberToLetterConverter(rawValue), false);
    }

    private String NumberToLetterConverter(String str) {
        int i = Integer.valueOf(str);
        char c;
        String s = "";
        if (i <= 26) {
            // 将ASCII码转换成字母 我这里都是小写
            c = (char) (i + 96);
            s = String.valueOf(c);
        } else if (i > 26) {
            List<Character> numlist = new ArrayList<>();
            // 单循环数大于26时 就在前加个啊 效果 27 : aa
            int num = i / 26;
            for (int a = 0; a < num; a++) {
                numlist.add('a');
            }
            numlist.add((char) (i % 26 + 97));
            for (Character character : numlist) {
                s = s + new StringBuilder().append(character).toString();
            }
        }
        return s;
    }
}
```
使用方式
`<tagName prefix:attributeName="1"></tagName>` `->` `<h1>a</h1>`