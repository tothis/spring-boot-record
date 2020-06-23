package com.example.filter;

import com.example.exception.TokenException;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import com.example.util.StringUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description 自定义jwt认证过滤器
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtUtil.TOKEN_HEADER);
        if (StringUtil.isBlank(header)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String userToken = request.getHeader(JwtUtil.TOKEN_HEADER);
        if (StringUtil.isBlank(userToken)) {
            throw new TokenException("token为空");
        }
        try {
            String token = JwtUtil.parserToken(userToken);
            if (token != null) {
                List<GrantedAuthority> authorities = userService.selectAuthorities(userToken);
                return new UsernamePasswordAuthenticationToken(token, null, authorities);
            }
        } catch (ExpiredJwtException e) {
            log.error("token已过期 {} " + e);
            throw new TokenException("token已过期");
        } catch (UnsupportedJwtException e) {
            log.error("token格式错误 {} " + e);
            throw new TokenException("token格式错误");
        } catch (MalformedJwtException e) {
            log.error("token没有被正确构造 {} " + e);
            throw new TokenException("token没有被正确构造");
        } catch (SignatureException e) {
            log.error("签名失败 {} " + e);
            throw new TokenException("签名失败");
        } catch (IllegalArgumentException e) {
            log.error("非法参数异常 {} " + e);
            throw new TokenException("非法参数异常");
        }
        return null;
    }
}