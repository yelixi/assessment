package com.sacc.assessment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 林夕
 * Date 2021/6/14 15:10
 */
@Controller
@Slf4j
public class TestController {

    @ResponseBody
    @GetMapping("/test")
    public String test(){
        log.error("程序出错，找曹梦浔");
        return "hello world";
    }
}
