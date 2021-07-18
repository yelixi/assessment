package com.sacc.assessment.controller;

import com.sacc.assessment.entity.Score;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.ScoreService;
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
@PreAuthorize("hasRole('CORRECTOR')")
@Controller
public class CorrectorController {

    @Resource
    private ScoreService scoreService;

    @ResponseBody
    @PostMapping("/correction")
    public RestResult<Boolean> correction(@RequestBody Score score, Authentication authentication){
        UserDetail userDetail = (UserDetail)authentication.getPrincipal();
        return RestResult.success(scoreService.correction(score,userDetail));
    }
}
