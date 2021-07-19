package com.sacc.assessment.controller;

import com.sacc.assessment.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @Describe: 题目管理
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /**
     * TODO:题目管理（增删改查）
     * @return
     */
    @Secured("ROLE_ISSUER")
    @GetMapping("/")
    public String getQuestions(){
        return "test";
    }

    @GetMapping("/{id}")
    public String getQuestions(@PathVariable Long id){
        return "test";
    }

    @PostMapping("/")
    @Secured("ROLE_ISSUER")
    public void addQuestions(){

    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ISSUER")
    public void deleteQuestions(@PathVariable Long id){

    }

    @PutMapping("/{id}")
    @Secured("ROLE_ISSUER")
    public void alterQuestions(@PathVariable Long id){

    }
}
