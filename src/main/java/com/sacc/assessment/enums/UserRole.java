package com.sacc.assessment.enums;

/**
 * @Describe: 用户权限
 * @Author: tyf
 * @CreateTime: 2021/7/8
 **/
public enum UserRole {
    /**
     * 超级管理员___00
     */
    ROOT,

    /**
     * 试题发布人员__01
     */
    ISSUER,

    /**
     * 批改人员_____02
     */
    CORRECTOR,

    /**
     * 普通人员_____03
     */
    MEMBER
}
