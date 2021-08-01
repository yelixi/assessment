package com.sacc.assessment.controller;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.ExamPaperService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:17
 */
@PreAuthorize("hasRole('ISSUER')")
@Controller
public class IssuerController {

    @Resource
    private ExamPaperService examPaperService;

    @ResponseBody
    @PostMapping("/createExam")
    public RestResult<Boolean> createExam(@RequestBody ExamPaper examPaper, Authentication authentication){
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return RestResult.success(examPaperService.createExam(examPaper,userDetail));
    }

    @ResponseBody
    @PostMapping("/updateExam")
    public RestResult<Boolean> updateExam(@RequestBody ExamPaper examPaper){
        return RestResult.success(examPaperService.updateExam(examPaper));
    }

    @ResponseBody
    @GetMapping("/getMyAllExamPaper")
    public RestResult<List<ExamPaper>> getMyAllExamPaper(Authentication authentication){
        UserDetail userDetail = (UserDetail)authentication.getPrincipal();
        return RestResult.success(examPaperService.getMyAllExamPaper(userDetail));
    }

    @ResponseBody
    @GetMapping("/getExamPaper")
    public RestResult<ExamPaper> getExamPaper(Integer examPaperId,Authentication authentication){
        UserDetail userDetail = (UserDetail)authentication.getPrincipal();
        return RestResult.success(examPaperService.getExamPaper(examPaperId,userDetail));
    }
}
