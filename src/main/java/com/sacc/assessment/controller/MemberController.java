package com.sacc.assessment.controller;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.AnswerService;
import com.sacc.assessment.service.ExamPaperAnswerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @PostMapping("/uploadAnswer")
    public RestResult<Boolean> uploadAnswer(@RequestBody ExamPaperAnswer examPaperAnswer, Authentication authentication){
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return RestResult.success(answerService.uploadAnswer(examPaperAnswer,userDetail));
    }


}
