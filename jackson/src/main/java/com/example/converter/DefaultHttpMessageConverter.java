package com.example.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 自定义序列化消息转换器 自定义返回响应数据结构
 * <p>
 * read和writeInternal对应请求和响应
 * read只对'application/json'请求生效
 * writeInternal对基础类型 基础类型包装类和String不生效
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
public class DefaultHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        Object result = super.read(type, contextClass, inputMessage);
        log.info("read {}", result);
        return result;
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        log.info("writeInternal {}", o);
        super.writeInternal(o, type, outputMessage);
    }
}