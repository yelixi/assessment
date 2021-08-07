package com.sacc.assessment.controller;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.AnswerService;
import com.sacc.assessment.service.ExamPaperAnswerService;
import com.sacc.assessment.service.ExamPaperService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 林夕
 * Date 2021/6/15 10:18
 */
@PreAuthorize("hasRole('MEMBER')")
@Controller
public class MemberController {

    @Resource
    private ExamPaperAnswerService answerService;

    @Resource
    private ExamPaperService examPaperService;
    @ResponseBody
    @PostMapping("/uploadAnswer")
    public RestResult<Boolean> uploadAnswer(@RequestBody ExamPaperAnswer examPaperAnswer, Authentication authentication){
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return RestResult.success(answerService.uploadAnswer(examPaperAnswer,userDetail));
    }

    @ResponseBody
    @GetMapping("/getExam")
    public RestResult<ExamPaper> getExam(@RequestParam Integer examId){
        return RestResult.success(examPaperService.getExam(examId));
    }

    @GetMapping("/Exam")
    public String exam(@RequestParam Integer examId, Model model){
        ExamPaper examPaper = examPaperService.getExam(examId);
        model.addAttribute("exam",examPaper);
        return "../static/html/member/exam.html";
    }
}
