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
        String s = "2790673868@qq.comsacc";
        String s1 = passwordEncode.encode(s);
        System.out.println(passwordEncode.matches(s,s1));
        //System.out.println(passwordEncode.encode("B19031432sacc"));
    }
}
