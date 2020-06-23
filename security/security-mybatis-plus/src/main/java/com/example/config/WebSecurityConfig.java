package com.example.config;

import com.example.filter.JwtAuthenticationFilter;
import com.example.filter.JwtLoginFilter;
import com.example.handler.*;
import com.example.service.UserService;
import com.example.service.impl.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description
 */

/*
 * @EnableGlobalMethodSecurity
 *   securedEnabled=true 开启@Secured注解
 *   jsr250Enabled=true 开启@RolesAllowed注解
 *   prePostEnabled=true
 *     @PreAuthorize 方法调用前 通过表达式计算限制访问
 *     @PostAuthorize 允许方法调用 表达式计算不通过 则抛出异常
 *     @PostFilter 允许方法调用 但按照表达式来过滤返回值
 *     @PreFilter 允许方法调用 但按照表达式过滤实参
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * url放行
     */
    private static final String[] AUTH_WHITELIST = {
            // 用户注册
            "/user/save"
    };

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 设置http验证规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()

                .and().csrf().disable()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated() // 所有请求需要身份认证

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint())
                .and()

                // 自定义访问失败处理器
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())

                .and()
                .formLogin() // 登录配置
                .usernameParameter("userName")
                .passwordParameter("password")
                // 只配置loginPage而不配置loginProcessingUrl值时 loginProcessingUrl值为loginPage值
                .loginPage("/login.jsp")
                .loginProcessingUrl("/login") // 自定义登录接口
                .successHandler(new CustomAuthenticationSuccessHandler())
                .failureHandler(new CustomAuthenticationFailureHandler())
                .permitAll()

                .and()
                .addFilter(new JwtLoginFilter(super.authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(super.authenticationManager(), userService))
                .logout() // 默认注销行为logout 可通过如下方式修改
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login") // 设置注销成功后跳转url 默认跳转至登录页面
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .permitAll();
    }

    // 登录时会进入
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        // 使用身份验证组件
        // auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        // 使用自定义身份验证组件
        auth.authenticationProvider(new CustomAuthenticationProvider(userService, bCryptPasswordEncoder));
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}