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
                , 'rememberMe': $('#remember-me').get(0).checked
                , 'captcha': captcha
            },
            url: '/sign-in',
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
                        time: 5000
                    });
                }
            }
        });
    }
});

/** 如果login页面在iframe中自动重定向到上一级 */
if (window !== top) {
    top.location.href = location.href;
}