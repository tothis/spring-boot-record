package com.example.handler;

import com.example.entity.ResultEntity;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * 统一返回数据
 *
 * @author 李磊
 */
// 配置 basePackages 防止修改第三方 JAR 返回值。如：Swagger
@RestControllerAdvice(basePackages = "com.example")
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    private static final Class<? extends Annotation> ANNOTATION_TYPE
            = ResponseBody.class;

    /**
     * 判断类或者方法是否使用 @ResponseBody
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return AnnotatedElementUtils.hasAnnotation(methodParameter
                .getContainingClass(), ANNOTATION_TYPE)
                || methodParameter.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    /**
     * 当类或者方法已使用 @ResponseBody 时会调用此方法
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 防止嵌套 GlobalExceptionHandler 处理后的数据
        return o instanceof ResultEntity ? o : ResultEntity.ok(o);
    }
}
