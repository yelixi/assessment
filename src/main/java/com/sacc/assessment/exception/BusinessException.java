package com.sacc.assessment.exception;

import com.sacc.assessment.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 林夕
 * Date 2021/7/14 14:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private ResultEnum resultEnum;

    public BusinessException(ResultEnum resultEnum){
        super(resultEnum.getResultMsg());
        this.resultEnum = resultEnum;
    }
}
