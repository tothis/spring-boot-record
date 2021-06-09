package com.example.filter;

import com.example.entity.ResultEntity;
import com.example.exception.GlobalException;
import com.example.util.ServletUtil;
import com.example.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * token 过滤器
 * <p>
 * 使用 FilterRegistrationBean 注册过滤器时，实现 Ordered 中的 getOrder 会失效。
 *
 * @author 李磊
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "lilei.token.enabled", havingValue = "true")
public class TokenFilter extends OncePerRequestFilter {

    /**
     * 接口白名单
     */
    private static final List<String> WHITELIST = Collections.unmodifiableList(Arrays.asList(
            "/user/login"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        if (WHITELIST.contains(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
        try {
            /*String token = request.getHeader(CommonConstant.TOKEN_HEADER);
            Long userId = */
            UserUtil.getUserId();
            // 刷新 token 过期时间，30 分钟后失效。
            // RedisUtil.set(RedisKeyConstant.TOKEN + token, userId, 30, TimeUnit.MINUTES);
        } catch (GlobalException e) {
            // @ControllerAdvice 无法捕获 Filter 中的异常 此处手动返回前端异常数据
            ResultEntity entity = new ResultEntity();
            entity.setCode(e.getCode());
            entity.setMessage(e.getMessage());
            ServletUtil.write(entity);
            return;
        }
        chain.doFilter(request, response);
    }
}
