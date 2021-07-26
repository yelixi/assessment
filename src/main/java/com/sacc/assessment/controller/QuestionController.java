package com.sacc.assessment.controller;

import com.sacc.assessment.entity.Question;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * @Describe: 题目管理
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Resource
    QuestionService questionService;

    /**
     * TODO:题目管理（增删改查）
     * @return
     */
    @Secured({"ROLE_ISSUER","ROLE_ROOT"})
    @GetMapping("/")
    public RestResult<List<Question>> getQuestions(){
        return RestResult.success(questionService.findAll());
    }

    @GetMapping("/{id}")
    public RestResult<Question> getQuestions(@PathVariable Integer id){
        Question question = questionService.selectQuestion(id);
        if(question == null){
            return RestResult.error("未找到！");
        }
        return RestResult.success(200, question);
    }

    @PostMapping("/")
    @Secured({"ROLE_ISSUER","ROLE_ROOT"})
    public RestResult<Question> addQuestions(@RequestBody Question question, Principal principal){
        Question insertedQuestion = questionService.insertQuestion(question, principal.getName());
        if(insertedQuestion == null) return RestResult.error("添加问题失败！");
        return RestResult.success(insertedQuestion);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ISSUER","ROLE_ROOT"})
    public RestResult<Boolean> deleteQuestions(@PathVariable Integer id){
        Question question = questionService.selectQuestion(id);
        question.setDeleted(true);
        return RestResult.success(questionService.updateQuestion(question, null));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ISSUER","ROLE_ROOT"})
    public void alterQuestions(@PathVariable Long id){

    }
}
