package com.example.config.security;

import com.example.model.User;
import com.example.service.UserService;
import com.example.type.HttpState;
import com.example.type.Result;
import com.example.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import java.io.PrintWriter;

@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String contentType = "application/json;charset=utf8";
    @Autowired
    private UserService userService;
    @Autowired
    private UserSecurityMetadataSource metadataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) {
        // 放行静态资源和免登录接口 必须以`/`开头 `**`代表多级匹配
        web.ignoring().antMatchers(
                "/static/**"
                , "/webjars/**"
                , "/captcha/*"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定义url权限
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(new UserDecisionManager());
                        o.setSecurityMetadataSource(metadataSource);
                        return o;
                    }
                })

                .and()
                .formLogin() // 登录配置
                .usernameParameter("userName")
                .passwordParameter("password")
                // 只配置loginPage而不配置loginProcessingUrl值时 loginProcessingUrl值为loginPage值
                .loginPage("/login.jsp")
                // 自定义登录接口url
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    response.setContentType(contentType);
                    PrintWriter out = response.getWriter();
                    User user = (User) authentication.getPrincipal();
                    user.setPassword(null);
                    out.write(JsonUtil.toJson(new Result(HttpState.OK.code(), "登录成功", user)));
                    out.flush();
                    out.close();
                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType(contentType);
                    PrintWriter out = response.getWriter();
                    String result = "登录失败";
                    if (exception instanceof LockedException) {
                        result = "账户被锁定 请联系管理员";
                    } else if (exception instanceof DisabledException) {
                        result = "账户被禁用 请联系管理员";
                    } else if (exception instanceof CredentialsExpiredException) {
                        result = "密码过期 请联系管理员";
                    } else if (exception instanceof AccountExpiredException) {
                        result = "账户过期 请联系管理员";
                    } else if (exception instanceof UsernameNotFoundException) {
                        result = "用户名输入错误 请重新输入";
                    } else if (exception instanceof BadCredentialsException) {
                        result = "密码输入错误 请重新输入";
                    } else if (exception instanceof RememberMeAuthenticationException) {
                        result = "记住我认证错误";
                    }
                    out.write(JsonUtil.toJson(new Result(HttpState.BAD_REQUEST.code(), result, null)));
                    out.flush();
                    out.close();
                })
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType(contentType);
                    PrintWriter out = response.getWriter();
                    out.write(JsonUtil.toJson(Result.success("注销成功")));
                    out.flush();
                    out.close();
                })
                .permitAll()

                .and()
                .csrf().disable()
                .exceptionHandling()
                // 无认证时执行方法
                .authenticationEntryPoint((request, response, e) -> {
                    if (e instanceof InsufficientAuthenticationException) {
                        log.info("身份验证级别不足 记住我或未登录");
                        response.sendRedirect("/login.jsp");
                    }
                });
    }
}