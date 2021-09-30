package com.sacc.assessment.vo;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/9/30 12:55
 */
@Data
public class ChangePasswordVo {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}
