package com.sacc.assessment.controller;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.enums.ResultEnum;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.ExamPaperAnswerService;
import com.sacc.assessment.service.ExamPaperService;
import org.springframework.data.util.Pair;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:18
 */
//@PreAuthorize("hasRole('MEMBER')")
@Controller
public class MemberController {

    @Resource
    private ExamPaperAnswerService answerService;

    @Resource
    private ExamPaperService examPaperService;

    @Resource
    private ExamPaperAnswerService examPaperAnswerService;

    @ResponseBody
    @PostMapping("/uploadAnswer")
    public RestResult<Boolean> uploadAnswer(@RequestBody ExamPaperAnswer examPaperAnswer, Authentication authentication){
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return RestResult.success(answerService.uploadAnswer(examPaperAnswer,userDetail));
    }

    @Secured({"ROLE_MEMBER","ROLE_ISSUER"})
    @ResponseBody
    @GetMapping("/getExam")
    public RestResult<ExamPaper> getExam(@RequestParam Integer examId){
        return RestResult.success(examPaperService.getExam(examId));
    }

    @Secured({"ROLE_MEMBER"})
    @GetMapping("/Exam")
    public String exam(@RequestParam Integer examId, Model model){
        ExamPaper examPaper = examPaperService.getExam(examId);
        if(examPaper.getStartTime().isBefore(LocalDateTime.now())&&examPaper.getEndTime().isAfter(LocalDateTime.now())) {
            model.addAttribute("exam", examPaper);
            return "../static/html/member/exam.html";
        }
        else if(examPaper.getStartTime().isAfter(LocalDateTime.now()))
            model.addAttribute("error", ResultEnum.EXAM_IS_NOT_START);
        else model.addAttribute("error", ResultEnum.EXAM_IS_END);
        return "../static/html/error.html";
    }

    @Secured({"ROLE_MEMBER"})
    @GetMapping("/isInExamTime")
    @ResponseBody
    public RestResult<Boolean> isInExamTime(@RequestParam Integer examId){
        return RestResult.success(examPaperService.isInExamTime(examId));
    }
}
