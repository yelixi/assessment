package com.sacc.assessment.enums;

import lombok.Getter;

/**
 * Created by 林夕
 * Date 2021/6/14 16:50
 */
@Getter
public enum  ResultEnum {
    SUCCESS(200, "成功!")
    ,
    UNSUPPORTED_MEDIA_TYPE(415, "请求的数据格式不符!")
    ,
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!")
    ,
    NULL_POINT(500, "空指针异常")
    ,
    BAD_REQUEST(400, "错误请求")
    ,
    NO_PERMISSION_ACCESS(403,"没有权限访问")
    ,
    NO_SUCH_ELEMENT(600,"数据库查询异常")
    ,
    TIME_HAS_EXPIRED(601,"时间已截止")
    ,
    EXAM_IS_NOT_START(602,"考试尚未开始")
    ,
    EXAM_IS_END(603,"考试已结束,可继续参与考试但无法提交")
    ,
    USER_IS_NOT_EXIT(604,"用户不存在")
    ,
    NEW_PASSWORD_ID_NOT_EQUAL_CONFIRM_PASSWORD(605,"新密码与确认密码不相同")
    ,
    OLD_PASSWORD_ERROR(606,"旧密码错误")
    ,
    STUDENT_ID_IS_EXIT(607,"学号已存在")
    ,
    EMAIL_IS_EXIT(608,"邮箱已存在")
    ;


    private final Integer resultCode;
    private final String resultMsg;

    ResultEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
