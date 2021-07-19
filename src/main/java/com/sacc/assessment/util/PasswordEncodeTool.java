package com.sacc.assessment.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Describe: 密码生成测试类
 * @Author: tyf
 * @CreateTime: 2021/7/9
 **/
public class PasswordEncodeTool {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();
        System.out.println(passwordEncode.encode("123456"));
    }
}
