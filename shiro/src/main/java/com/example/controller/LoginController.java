package com.example.controller;

import com.example.entity.ResultEntity;
import com.example.model.User;
import com.example.type.MessageType;
import com.example.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class LoginController {

    /**
     * 登录
     */
    @ResponseBody
    @PostMapping("sign-in")
    public ResultEntity signIn(User user) {
        String userName = user.getUserName();
        String password = user.getPassword();
        if (StringUtil.isBlank(userName)) {
            return new ResultEntity() {{
                setCode(MessageType.SYSTEM_ERROR.getCode());
                setMessage("用户名不能为空");
            }};
        }
        if (StringUtil.isBlank(password)) {
            return new ResultEntity() {{
                setCode(MessageType.SYSTEM_ERROR.getCode());
                setMessage("密码不能为空");
            }};
        }
        Subject subject = SecurityUtils.getSubject();
        String result;
        try {
            // 登录
            subject.login(new UsernamePasswordToken(userName, password, user.getRememberMe()));
            // 从session取出用户信息
            User loginUser = (User) subject.getPrincipal();
            // 返回登录用户的信息给前台 含用户的所有角色和权限
            return ResultEntity.ok(loginUser);
        } catch (UnknownAccountException uae) {
            log.warn("用户帐号不存在");
            result = "用户帐号或密码不正确";
        } catch (IncorrectCredentialsException e) {
            log.warn("用户密码不正确");
            result = "用户帐号或密码不正确";
        } catch (LockedAccountException e) {
            log.warn("用户帐号被锁定");
            result = "用户帐号被锁定不可用";
        } catch (AuthenticationException e) {
            result = "登录出错";
        }
        return new ResultEntity() {{
            setCode(MessageType.SYSTEM_ERROR.getCode());
            setMessage(result);
        }};
    }

    /**
     * 退出登录
     */
    @GetMapping("sign-out")
    public String signOut() {
        SecurityUtils.getSubject().logout();
        return "/login.jsp";
    }
}