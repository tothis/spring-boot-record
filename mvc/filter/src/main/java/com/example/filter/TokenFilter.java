package com.example.filter;

import cn.hutool.core.util.StrUtil;
import com.example.constant.CommonConstant;
import com.example.entity.ResultEntity;
import com.example.exception.MessageType;
import com.example.util.ServletUtil;
import com.example.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
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
 * token过滤器
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "lilei.token.enabled", havingValue = "true")
public class TokenFilter extends OncePerRequestFilter implements Ordered {

    /**
     * 接口白名单
     */
    private static final List<String> WHITELIST = Collections.unmodifiableList(Arrays.asList(
            "/user/login"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        if (WHITELIST.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(CommonConstant.TOKEN_HEADER);
        // @ControllerAdvice无法捕获Filter中的异常 此处手动返回前端异常数据
        if (StrUtil.isBlank(token)) {
            ResultEntity entity = new ResultEntity();
            entity.setCode(MessageType.USER_TOKEN_BLANK.getCode());
            entity.setMessage(MessageType.USER_TOKEN_BLANK.getMessage());
            ServletUtil.write(entity);
            return;
        }
        Long userId = UserUtil.getUserId();
        if (userId == null) {
            ResultEntity entity = new ResultEntity();
            entity.setCode(MessageType.USER_TOKEN_INVALID.getCode());
            entity.setMessage(MessageType.USER_TOKEN_INVALID.getMessage());
            ServletUtil.write(entity);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        log.info("token启动");
        return 0;
    }
}