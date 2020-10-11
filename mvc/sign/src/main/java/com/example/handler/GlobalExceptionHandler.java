package com.example.handler;

import com.example.entity.ResultEntity;
import com.example.exception.GlobalException;
import com.example.exception.MessageType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常捕获
 *
 * @author 李磊
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResultEntity catchGlobalException(GlobalException e) {
        ResultEntity r = new ResultEntity();
        r.setCode(e.getCode());
        r.setMessage(e.getMessage());
        return r;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultEntity catchRuntimeException(RuntimeException e) {
        e.printStackTrace();
        ResultEntity r = new ResultEntity();
        r.setCode(MessageType.SYSTEM_ERROR.getCode());
        r.setMessage(e.getMessage());
        return r;
    }

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResultEntity catchException(Exception e) {
        e.printStackTrace();
        ResultEntity r = new ResultEntity();
        r.setCode(MessageType.SYSTEM_ERROR.getCode());
        r.setMessage(MessageType.SYSTEM_ERROR.getMessage());
        return r;
    }
}