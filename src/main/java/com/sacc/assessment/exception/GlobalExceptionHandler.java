package com.sacc.assessment.exception;

import com.sacc.assessment.enums.ResultEnum;
import com.sacc.assessment.model.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @desc 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局处理Exception类型的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestResult<ResultEnum> Exception(Exception e){
        logger.error("未知错误",e);
        return RestResult.error(ResultEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 全局处理NullPointerException类型的异常
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public RestResult<ResultEnum> NullPointerException(NullPointerException e){
        logger.error("空指针异常",e);
        return RestResult.error(ResultEnum.NULL_POINT);

    }
    /**
     * 捕获请求方式异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public RestResult<ResultEnum> HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        logger.error("错误请求",e);
        return RestResult.error(ResultEnum.BAD_REQUEST);
    }

    /**
     * 捕获400异常
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public RestResult<ResultEnum> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        logger.error("错误请求",e);
        return RestResult.error(ResultEnum.BAD_REQUEST);
    }

    /**
     *捕获数据库操作异常
     */
    @ExceptionHandler(value = SQLException.class)
    public RestResult<ResultEnum> SQLExceptionHandler(SQLException e){
        logger.error("数据库操作异常:",e);
        return RestResult.error(e.getErrorCode(),e.getMessage());
    }

    /**
     *捕获数据校验异常
     */
    @ExceptionHandler(value = BindException.class)
    public RestResult<ResultEnum> bindExceptionHandler(BindException e){
        logger.error("数据校验异常",e);
        return RestResult.error(ResultEnum.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 捕获自定义异常
     * */
    @ExceptionHandler(value = BusinessException.class)
    public RestResult<ResultEnum> businessException(BusinessException e){
        return RestResult.error(e.getResultEnum());
    }


}
