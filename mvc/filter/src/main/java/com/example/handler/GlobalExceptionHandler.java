package com.example.handler;

import com.example.entity.Result;
import com.example.exception.GlobalException;
import com.example.type.MessageType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常捕获
 *
 * @author 李磊
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public Result catchGlobalException(GlobalException e) {
        Result r = new Result();
        r.setCode(e.getCode());
        r.setMessage(e.getMessage());
        return r;
    }

    @ExceptionHandler(RuntimeException.class)
    public Result catchRuntimeException(RuntimeException e) {
        e.printStackTrace();
        Result r = new Result();
        r.setCode(MessageType.SYSTEM_ERROR.getCode());
        r.setMessage(e.getMessage());
        return r;
    }

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Result catchException(Exception e) {
        e.printStackTrace();
        Result r = new Result();
        r.setCode(MessageType.SYSTEM_ERROR.getCode());
        r.setMessage(MessageType.SYSTEM_ERROR.getMessage());
        return r;
    }
}
