package com.sacc.assessment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

/*TODO:获取最近的一次考试, 优化表单*/
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

    @Secured({"ROLE_MEMBER"})
    @GetMapping("/getMyExams")
    public String getMyExams(Model model, Authentication authentication){
        List<ExamPaper> examPaperList = examPaperService.getAllExam();
        model.addAttribute("examList",examPaperList);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        List<Integer> list = new ArrayList<>();
        for (ExamPaper examPaper : examPaperList) {
            if(examPaperAnswerService.findByUserIdAndExamPaperId(userDetail.getId(),examPaper.getId())!=null){
                list.add(1);
            }
            else list.add(0);
        }
        model.addAttribute("list",list);
        return "../static/html/member/all-exams.html";
    }

    @Secured({"ROLE_MEMBER"})
    @ResponseBody
    @GetMapping("/myRecentExam")
    public RestResult<ExamPaper> getUnfinishedExam(Authentication authentication){
        UserDetail principal = (UserDetail)authentication.getPrincipal();
        List<ExamPaper> u = examPaperService.getMyUnfinishedExamPaper(principal);
        return RestResult.success(200, u.isEmpty() ? null : u.get(0));
    }
}
