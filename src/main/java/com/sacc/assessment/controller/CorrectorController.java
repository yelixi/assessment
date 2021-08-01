package com.sacc.assessment.controller;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.entity.Score;
import com.sacc.assessment.form.ScoreForm;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.ScoreService;
import org.apache.xpath.operations.Bool;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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

    @ResponseBody
    @GetMapping("/getAnswer")
    public RestResult<Answer> getAnswer(@RequestParam Integer answerId){
        return RestResult.success(scoreService.getAnswer(answerId));
    }
}
