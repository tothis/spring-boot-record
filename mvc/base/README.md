# base

### 介绍
mvc使用相关记录
***
HTTP协议中Accept与Content-Type区别

Accept属于请求头 Content-Type属于请求体|响应体

请求方报头结构 请求行|请求头|请求体
响应方报头结构 响应行|响应头|响应体

请求行由 请求方法 URL 协议版本三部分组成
响应行由 状态码 状态码描述 协议版本三部分组成

Accept代表发送端 客户端 希望接受的数据类型 如Accept:text/xml 代表客户端希望接受的数据类型是xml类型

Content-Type代表发送端(客户端|服务器)发送的实体数据的数据类型 如Content-Type:text/html代表发送端发送的数据格式是html

`Accept:text/xml;Content-Type:text/html`代表希望接受数据类型为xml格式 本次请求|发送数据格式是html

@RequestMapping中属性|描述
-|-
value|请求地址
params|请求参数
method|请求method类型
consumes|限定请求内容类型 如text/html只对请求为Content-Type为text/html的请求作处理
produces|设置返回内容类型 仅当request请求头中的(Accept)类型中包含该类型才返回
headers|指定request中必须包含某些指定的header值，才能让该方法处理请求
+ 返回值为void produces属性不生效 response.setContentType生效
+ 返回值不为void response.setContentType不生效 produces属性生效
+ headers="key=lilei" 访问此接口需在请求中携带'key=lilei'请求头
```java
@GetMapping(value = "captcha.jpg"//, produces = {MediaType.IMAGE_JPEG_VALUE}
        , headers = {"key=lilei"}
)
public void captcha(HttpSession session, HttpServletResponse response) {
    // 设置相应类型 告诉浏览器输出的内容为图片
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    // 不缓存此内容
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("expires", -1);
    try {
        StringBuffer code = new StringBuffer();
        BufferedImage image = CaptchaUtil.genRandomCodeImage(code);
        session.setAttribute(KEY_CAPTCHA, code.toString());
        // 将内存中的图片通过流动形式输出到客户端
        ImageIO.write(image, "JPG", response.getOutputStream());
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```