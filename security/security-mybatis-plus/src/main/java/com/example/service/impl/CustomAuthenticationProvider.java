package com.example.service.impl;

import com.example.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:55
 * @description 自定义身份认证验证组件
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(UserService userService
            , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名和密码
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        // 认证逻辑
        UserDetails userDetails = userService.loadUserByUsername(userName);
        if (userDetails != null) {
            if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                // 设置权限和角色
                /*
                 * 角色授权 授权码前缀必须为大写的'ROLE_' 使用时不需加前缀
                 * 权限授权 授权码前缀不能为大写的'ROLE_' 使用时与设置时保存一致
                 */
                List<GrantedAuthority> authorities = userService.selectAuthorities(userName);
                // 生成令牌 令牌中保存 userName password authorities
                return new UsernamePasswordAuthenticationToken(userName, password, authorities);
            } else {
                throw new BadCredentialsException("密码错误");
            }
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    /**
     * 是否可提供输入类型的认证服务
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}