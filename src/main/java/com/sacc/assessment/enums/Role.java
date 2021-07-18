package com.sacc.assessment.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 林夕
 * Date 2021/6/15 10:07
 */
@Getter
public enum Role {

    //管理员
    ROOT,

    //布置题目
    ISSUER,

    //批改人员
    CORRECTOR,

    //普通成员
    MEMBER,

    ;
}
