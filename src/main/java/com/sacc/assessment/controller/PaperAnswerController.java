package com.sacc.assessment.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @Describe: 试卷答案描述
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/

@RequestMapping("/paper_answers")
@RestController
public class PaperAnswerController {


    /**
     * TODO:试卷管理
     * 1. 查阅用户试卷
     * 2. 修改用户试卷（得分）
     */

    @Secured({"ROLE_ISSUER"})
    @GetMapping("/")
    public void getPaperAnswers(){

    }

    @Secured({"ROLE_ISSUER"})
    @GetMapping("/{id}")
    public void getPaperAnswers(@PathVariable Long id){

    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ISSUER"})
    public void alterPaperAnswers(@PathVariable Long id){

    }
}
