<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme()
            + "://" + request.getServerName()
            + ":" + request.getServerPort()
            + request.getContextPath() + "/";
%>
<!doctype html>
<html lang="en">
<head>
    <title>login</title>
    <meta charset="utf-8">
    <!-- fontawesome字体库 -->
    <link href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <title>登录</title>

    <style>

        * {
            margin: 0;
            padding: 0;
        }

        body {
            background-color: #214;
        }

        input, button {
            /* 取消点击补助框 */
            outline: none;
            /* ios点击后取消阴影
             * -webkit      ios浏览器
             * -tap         点击
             * -highlight   背景高亮
             * -color       颜色
             * rgba(0,0,0,0) = transparent 作用相同(纯黑透明)
             */
            /*-webkit-tap-highlight-color: transparent;*/
        }

        .title {
            position: absolute;
            margin-top: 3%;
            left: 20%;
            font-size: 30px;
            color: white;
            font-weight: bold;
        }

        .input {
            position: absolute;
            width: 60%;
            height: 50px;
            line-height: 50px;
            left: 20%;
            border: 2px solid pink;
            border-radius: 10px;
        }

        .account-input {
            color: white;
            width: 60%;
            height: 36px;
            font-size: 16px;
            position: absolute;
            left: 20%;
            border: 0px solid #ffffff;
            padding: 0;
            margin: 5px 0 0 0;
            background-color: rgba(0, 0, 0, 0);
        }

        .input.account {
            margin-top: 15%;
        }

        .input.password {
            margin-top: 21%;
        }

        .input.captcha {
            margin-top: 25.4%;
            width: 18%;
            left: 28%;
            border-color: #969696;
        }

        .captcha > img {
            margin-top: 12px;
            position: absolute;
            left: 4%;
            border-radius: 4px;
        }

        .icon {
            width: 20px;
            color: white;
            margin: 13px 0 0 3%;
        }

        .account-text {
            position: absolute;
            left: calc(3% + 32px);
            color: #fefefe;
            margin-top: -50px;
            font-size: 16px;
        }

        .login {
            border: none;
            position: absolute;
            left: 20%;
            margin-top: 30%;
            width: 60%;
            height: 50px;
            background-color: #f9f9f9;
            border-radius: 10px;
            color: #e4277d;
            font-size: 16px;
        }

        #remember-me {
            position: absolute;
            left: 20%;
            margin-top: 26.6%;
            width: 20px;
            height: 20px;
            border-radius: 50%;
        }

        .remember-me-text {
            position: absolute;
            margin-top: 26.48%;
            color: white;
            left: 22.5%;
        }

        .footer {
            position: absolute;
            color: white;
            margin-top: 35%;
            left: 20%;
        }

        /* placeholder开始 */
        input::-webkit-input-placeholder {
            color: #efe9e5;
        }

        input::-moz-placeholder { /* Mozilla Firefox 19+ */
            color: #efe9e5;
        }

        input:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
            color: #efe9e5;
        }

        input:-ms-input-placeholder { /* Internet Explorer 10-11 */
            color: #efe9e5;
        }

        /* placeholder结束 */

        /*check开始*/
        input[type=radio], input[type=checkbox] {
            /* 防止::after中的padding改变checkbox位置 */
            position: absolute;
            /* 去除浏览器默认样式 */
            -webkit-appearance: none;
            /* 手机端必须设置 */
            border: none; /* 边框 */
        }

        /* 选中前 */
        input[type=radio]::after, input[type=checkbox]::after {
            content: '';
            display: block;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            /* 此元素作用 宽高 = 内边距 + 边框 */
            box-sizing: border-box;
            background-color: #fff;
            text-align: center;
            font-size: 16px;
            /* 限制content的样式 */
            line-height: 14px;
        }

        /* radio选中后 */
        input[type=radio]:checked::after {
            content: '';
            background: #e61673;
        }

        /* checkbox选中后 */
        input[type=checkbox]:checked:after {
            content: 'L';
            color: #e61673;
            padding: 2px 0 0 2px;
            transform: matrix(-0.766044, -0.642788, -0.642788, 0.766044, 0, 0);
            -webkit-transform: matrix(-0.766044, -0.642788, -0.642788, 0.766044, 0, 0);
        }
    </style>
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
    <img src="s/captcha/jpg" onclick="this.src='/captcha/jpg?'+Math.random()">
    <input id="captcha" class="account-input" style="left: 54%;" placeholder="请输入验证码">
</div>

<p class="footer">&copy;
    <script>document.write('&nbsp;' + new Date().getFullYear() + '\xa0' + location.hostname);</script>
</p>

<script src="https://www.layuicdn.com/layui/layui.js"></script>
<script>
    layui.use(['layer'], function () {
        let layer = layui.layer,
            $ = layui.$;
        window.login = function () {

            let userName = $('#userName').val();
            let password = $('#password').val();
            let captcha = $('#captcha').val();

            if (userName.trim() === '') {
                layer.msg('用户名不能为空');
                return;
            }

            if (password.trim() === '') {
                layer.msg('密码不能为空', {
                    time: 5000 // 2秒关闭(默认3秒)
                }, function () {
                });
                return;
            }

            if (captcha.trim() === '') {
                layer.msg('验证码不能为空');
                return;
            }

            $.ajax({
                type: 'post',
                async: false,
                data: {
                    'userName': userName
                    , 'password': password
                    , 'rememberMe': $('#remember-me').checked
                    , 'captcha': captcha
                },
                url: '/login',
                success: function (data) {
                    if (data.code === 200) {
                        localStorage.clear();
                        sessionStorage.clear();
                        layer.msg('登录成功', {
                            time: 5000 // 5秒关闭(默认3秒)
                        }, function () {
                            location.href = '/';
                        });
                    } else if (data.code === 400) {
                        layer.msg(data.message, {
                            time: 5000 // 5秒关闭(默认3秒)
                        }, function () {
                        });
                    }
                }
            });
        }
    });

    /** 如果login页面在iframe中自动重定向 */
    if (window !== top) {
        top.location.href = location.href;
    }
</script>
</body>
</html>
