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
        //String s = "Clannad0629+";
        //String s1 = passwordEncode.encode(s);
        //System.out.println(passwordEncode.matches(s,"$2a$10$p7aqwIMdo3aQ6ApqjORhjuqz3XZuQo3GEBwgFgzlXS0e58IA8Y5p6"));
        System.out.println(passwordEncode.encode("123456"));

    }
}
