package com.sacc.assessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 林夕
 * Date 2021/6/14 15:10
 */
@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/test")
    public String test(){
        return "hello world";
    }
}
