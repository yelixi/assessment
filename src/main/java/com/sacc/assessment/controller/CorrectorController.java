package com.sacc.assessment.controller;

import com.sacc.assessment.entity.*;
import com.sacc.assessment.form.ScoreForm;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.*;
import org.apache.xpath.operations.Bool;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:18
 */
@PreAuthorize("hasRole('CORRECTOR')")
@Controller
public class CorrectorController {

    @Resource
    private ScoreService scoreService;

    @Resource
    private ExamPaperService examPaperService;

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    private ExamPaperAnswerService examPaperAnswerService;

    @ResponseBody
    @PostMapping("/correction")
    public RestResult<Boolean> correction(@RequestBody ScoreForm scoreForm,Authentication authentication){
        UserDetail userDetail = (UserDetail)authentication.getPrincipal();
        return RestResult.success(scoreService.correction(scoreForm,userDetail));
    }

    @ResponseBody
    @PostMapping("/updateCorrection")
    public RestResult<Boolean> updateCorrection(@RequestBody ScoreForm scoreForm,Authentication authentication){
        UserDetail userDetail = (UserDetail)authentication.getPrincipal();
        return RestResult.success(scoreService.updateCorrection(scoreForm,userDetail));
    }

    @ResponseBody
    @PostMapping("/getAllAnswer")
    public RestResult<List<Answer>> getAllAnswer(@RequestParam Integer examPaperAnswerId){
        return RestResult.success(scoreService.getAllAnswer(examPaperAnswerId));
    }

    @GetMapping("/getAnswer")
    public String getAnswer(@RequestParam Integer answerId,Integer examPageId,Model model){
        Answer answer = scoreService.getAnswer(answerId);
        Question question = questionService.selectQuestion(answer.getQuestionId());
        User user = userService.getUser(answer.getUserId());
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.findOne(examPageId);
        List<Answer> answerList = examPaperService.answerList(answer.getQuestionId(),answer.getExamPageId());
        for (Answer a : answerList) {
            if (a.getId().equals(answer.getId())){
                int th = answerList.indexOf(a);
                if(th==0&&th==answerList.size()-1) {
                    model.addAttribute("pre", -1);
                    model.addAttribute("next", -1);
                }
                else if(th==0){
                    model.addAttribute("pre", -1);
                    model.addAttribute("next", answerList.get(th + 1).getId());
                }
                else if(th==answerList.size()-1) {
                    model.addAttribute("pre", answerList.get(th - 1).getId());
                    model.addAttribute("next", -1);
                }
                else {
                    model.addAttribute("pre", answerList.get(th - 1).getId());
                    model.addAttribute("next", answerList.get(th + 1).getId());
                }
                break;
            }
        }
        model.addAttribute("answer",answer);
        model.addAttribute("username",user.getUsername());
        model.addAttribute("question",question);
        model.addAttribute("paperAnswer",examPaperAnswer.getId());
        model.addAttribute("examPageId",examPageId);
        return "../static/html/corrector/correctorExam.html";
    }

    @GetMapping("/corrector/getExam")
    public String getExam(@RequestParam Integer examId, Model model){
        ExamPaper exam = examPaperService.getExam(examId);
        model.addAttribute("exam",exam);
        return "../static/html/corrector/exam.html";
    }

    @GetMapping("/answer")
    public String answer(@RequestParam Integer questionId,Integer examPaperId,Model model){
        List<Answer> answerList = examPaperService.answerList(questionId,examPaperId);
        model.addAttribute("answerList",answerList);
        return "../static/html/corrector/answer.html";
    }
}
