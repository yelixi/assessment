package com.sacc.assessment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sacc.assessment.enums.ResultEnum;
import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/6/14 16:48
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult<T> {
    private Integer code;
    private String message;
    private T data;

    public RestResult(Integer code, String message){
        this.code=code;
        this.message=message;
    }

    public RestResult(Integer code, T data){
        this.code=code;
        this.data=data;
    }

    public static <T> RestResult<T> success(Integer code, T data){
        return new RestResult<>(code,data);
    }
    public static <T> RestResult<T> success(T data){
        return new RestResult<>(200,data);
    }
    public static <T> RestResult<T> success(Integer code, String message){
        return new RestResult<>(code,message);
    }
    public static <T> RestResult<T> success(ResultEnum resultEnum){
        return new RestResult<>(resultEnum.getResultCode(),resultEnum.getResultMsg());
    }

    public static <T> RestResult<T> error(Integer code, String message){
        return new RestResult<>(code,message);
    }

    public static <T> RestResult<T> error(String message){
        return new RestResult<>(500,message);
    }
    public static <T> RestResult<T> error(ResultEnum resultEnum){
        return new RestResult<>(resultEnum.getResultCode(),resultEnum.getResultMsg());
    }
}

