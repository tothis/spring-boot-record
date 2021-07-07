package com.example.handler;

import com.example.entity.Result;
import com.example.exception.GlobalException;
import com.example.type.MessageType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常捕获
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

    /**
     * 校验 controller 方法实体参数
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result paramException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder message = new StringBuilder(MessageType.PARAMETER_ERROR.getMessage());
        if (result.hasErrors()) {
            // 获取类注解如：@ScriptAssert 异常信息
            final List<ObjectError> allErrors = result.getAllErrors();
            allErrors.forEach(error -> message.append(' ' + error.getDefaultMessage()));
            // 获取类属性如：@NotNull 异常信息
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> message.append(' ' + error.getField()
                    + "->" + error.getDefaultMessage()));
        }
        Result r = new Result();
        r.setCode(MessageType.PARAMETER_ERROR.getCode());
        r.setMessage(message.toString());
        return r;
    }

    /**
     * 校验 controller 方法非实体参数
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result paramException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder(MessageType.PARAMETER_ERROR.getMessage());
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        constraintViolations.forEach(item -> message.append(item.getMessage()));

        Result r = new Result();
        r.setCode(MessageType.PARAMETER_ERROR.getCode());
        r.setMessage(message.toString());
        return r;
    }

    @ExceptionHandler(RuntimeException.class)
    public Result catchRuntimeException(RuntimeException e) {
        e.printStackTrace();
        Result r = new Result();
        r.setCode(MessageType.SYSTEM_ERROR.getCode());
        r.setMessage(MessageType.SYSTEM_ERROR.getMessage());
        return r;
    }

    @ExceptionHandler(Exception.class)
    public Result catchException(Exception e) {
        e.printStackTrace();
        Result r = new Result();
        r.setCode(MessageType.SYSTEM_ERROR.getCode());
        r.setMessage(MessageType.SYSTEM_ERROR.getMessage());
        return r;
    }
}
