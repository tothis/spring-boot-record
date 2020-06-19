<%@ page pageEncoding="utf8" trimDirectiveWhitespaces="true" %>
<%@ page import="com.example.util.SpringUtil" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!doctype html>
<html lang="en">
<head>
    <title>login</title>
    <meta charset="utf-8">
    <link href="<%=SpringUtil.getProperty("iconfont")%>" rel="stylesheet">
    <link href="<%=basePath%>static/css/login.css" rel="stylesheet">
    <title>登录</title>
</head>
<body>
<div class="title">李磊系统<br>
</div>
<div class="input account">
    <i class="to to-user icon"></i>
    <div class="account-text">账号</div>
    <input type="text" class="account-input" id="userName" placeholder="请输入账户">
</div>
<div class="input password">
    <i class="to to-password icon"></i>
    <div class="account-text">密码</div>
    <input type="password" class="account-input" id="password" placeholder="请输入密码">
</div>
<button class="login" onclick="login()">立即登录</button>

<input type="checkbox" id="remember-me">
<span class="remember-me-text">记住我</span>

<div class="input captcha">
    <img src="<%=basePath%>captcha/jpg" onclick="this.src='/captcha/jpg?'+Math.random()">
    <input id="captcha" class="account-input" style="left: 54%;" placeholder="请输入验证码">
</div>
<p class="footer">&copy;
    <script>document.write('&nbsp;' + new Date().getFullYear() + '\xa0' + location.hostname);</script>
</p>
<script src="<%=basePath%>webjars/layui/layui.js"></script>
<script src="<%=basePath%>static/js/login.js"></script>
</body>
</html>