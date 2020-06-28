package com.example.filter;

import com.example.model.LoginUser;
import com.example.type.Result;
import com.example.util.JsonUtil;
import com.example.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description 自定义登录过滤器
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 接收并解析用户凭证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoginUser user = JsonUtil.toObject(request.getInputStream(), LoginUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户成功登录后 会调用此方法 在此方法中生成token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain, Authentication authResult) {
        try {
            // 存放角色集合
            // List roleList = authResult.getAuthorities().stream()
            //         .map(GrantedAuthority::getAuthority)
            //         .collect(Collectors.toList());
            // 登录成功后 把token设置到header
            response.addHeader(JwtUtil.TOKEN_HEADER, JwtUtil.createToken(authResult, false));
            response.setContentType("application/json;charset=utf8");
            // 登录成功
            response.getWriter().write(JsonUtil.toJson(Result.success()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证失败候调用
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException failed) throws IOException {
        response.setContentType("application/json;charset=utf8");
        response.getWriter().write("验证失败 " + failed.getMessage());
    }
}