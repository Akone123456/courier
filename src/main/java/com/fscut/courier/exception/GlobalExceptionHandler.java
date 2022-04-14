package com.fscut.courier.exception;

import com.fscut.courier.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


import java.nio.file.AccessDeniedException;

import static com.fscut.courier.utils.ResultUtil.*;

/**
 * 统一异常处理
 *
 * @author ww
 * 2019-12-25 13:46
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 用户未登录异常处理
     */
    @ExceptionHandler(value = LoginException.class)
    public Result loginException(LoginException e) {
        return notLoginError(e.getMessage());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result authorityException(AccessDeniedException e) {
        return logicalError(e.getMessage());
    }

    /**
     * 校验错误处理
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class,
            ValidateUtil.ParamInvalidException.class})
    public Result paramExpTrue(Exception e) {
        return paramsError(e.getMessage());
    }

    /**
     * Validated参数校验抛出的异常
     *
     * @param e 异常
     * @return 异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleBindException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError == null) {
            return paramsError(e.getMessage());
        }
        return paramsError(fieldError.getDefaultMessage());
    }

    /**
     * 校验请求参数抛出的异常
     *
     * @param e 异常
     * @return 异常信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleRequestParamException(ConstraintViolationException e) {
        return paramsError(e.getConstraintViolations().iterator().next().getMessage());
    }

    /**
     * 业务逻辑异常
     */
    @ExceptionHandler(value = ValidateUtil.LogicalException.class)
    public Result logicalException(ValidateUtil.LogicalException e) {
        return logicalError(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public Result exception(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return internalError("系统异常");
    }
}
