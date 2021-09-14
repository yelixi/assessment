package com.sacc.assessment.controller;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.Question;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Describe: 题目管理
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Resource
    QuestionService questionService;

    /**
     * @return
     */
    @Secured({"ROLE_ISSUER","ROLE_ROOT"})
    @GetMapping("/")
    public String getQuestions(Model model,
                               @RequestParam(required = false,defaultValue = "0",name = "page") Integer page ,
                               @RequestParam(required = false,defaultValue = "10",name = "pagesize") Integer pageSize){
        if (page == null) page = 1;
        if(page <= -1) page++;
        if(pageSize == null) pageSize = 10;
        //默认一页10，按照题目序号升序。
        PageRequest pageRequest = PageRequest.of(page,pageSize,Sort.by(Sort.Direction.ASC,"id"));
        Page<Question> questionPage = questionService.findAll(pageRequest);
        int totalPages = questionPage.getTotalPages();
        //拿出集合
        List<Question> questions = questionPage.getContent();
        List<Integer> exams     = new ArrayList<>();
        for(Question question : questions) exams.add(questionService.findExamPaperByQuestionId(question.getId()));
        model.addAttribute("questionLists",questions);
        model.addAttribute("examList",exams);
        model.addAttribute("TotalPages",totalPages);
        model.addAttribute("TotalElements", questionPage.getTotalElements());
        model.addAttribute("Number", questionPage.getNumber()-1);
        model.addAttribute("NumberOfElements",questionPage.getNumberOfElements());
        model.addAttribute("page", page);
        return "../static/html/issuer/questions.html";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public RestResult<Question> getQuestions(@PathVariable Integer id){
        Question question = questionService.selectQuestion(id);
        if(question == null){
            return RestResult.error("未找到！");
        }
        return RestResult.success(200, question);
    }

    @PostMapping("/")
    @ResponseBody
    @Secured({"ROLE_ISSUER","ROLE_ROOT"})
    public RestResult<Question> addQuestions(@RequestBody Question question, Principal principal){
        Question insertedQuestion = questionService.insertQuestion(question, principal.getName());
        if(insertedQuestion == null) return RestResult.error("添加问题失败！");
        return RestResult.success(insertedQuestion);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Secured({"ROLE_ISSUER","ROLE_ROOT"})
    public RestResult<Boolean> deleteQuestions(@PathVariable Integer id){
        Question question = questionService.selectQuestion(id);
        question.setDeleted(true);
        questionService.deleteQuesionById(id);
        return RestResult.success(200 ,Boolean.TRUE);
//        return RestResult.success(questionService.updateQuestion(question));
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Secured({"ROLE_ISSUER","ROLE_ROOT"})
    public RestResult<Boolean> alterQuestions(@PathVariable Integer id, @RequestBody Question questions,
                                              Principal principal){
        return RestResult.success(questionService.updateQuestion(questions));
    }
}
