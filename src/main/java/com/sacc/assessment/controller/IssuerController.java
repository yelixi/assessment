package com.sacc.assessment.controller;

import com.alibaba.fastjson.JSON;
import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.ExamPaperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:17
 */
//@PreAuthorize("hasRole('ISSUER')")
@Controller
@RequestMapping("/issuer")
public class IssuerController {

    @Resource
    private ExamPaperService examPaperService;

    @Secured({"ROLE_ISSUER"})
    @ResponseBody
    @PostMapping("/createExam")
    public RestResult<ExamPaper> createExam(@RequestBody ExamPaper examPaper, Authentication authentication){
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return RestResult.success(examPaperService.createExam(examPaper,userDetail));
    }

    @Secured({"ROLE_ISSUER"})
    @GetMapping("/createExam")
    public String createExam(Authentication authentication){
        return "../static/html/issuer/exam-create.html";
    }

    @Secured({"ROLE_ISSUER"})
    @ResponseBody
    @PostMapping("/updateExam")
    public RestResult<Boolean> updateExam(@RequestBody ExamPaper examPaper,Authentication authentication){
        Object details = authentication.getPrincipal();
        UserDetail user = (UserDetail) details;
        examPaper.setUserId(user.getId());
        return RestResult.success(examPaperService.updateExam(examPaper));
    }

    @Secured({"ROLE_ISSUER"})
    @GetMapping("/updateExam/{examId}")
    public String updateExam(@PathVariable Integer examId, Model model, Authentication authentication){
        ExamPaper exam = examPaperService.getExam(examId);
        model.addAttribute("exam", JSON.toJSON(exam));
        return "../static/html/issuer/exam-edit.html";
    }

    @Secured({"ROLE_ISSUER"})
    @GetMapping("/getMyAllExamPaper")
    public String getMyAllExamPaper(Authentication authentication, Model model,
                                    @RequestParam(required = false,defaultValue = "0",name = "page") Integer page ,
                                    @RequestParam(required = false,defaultValue = "10",name = "pagesize") Integer pageSize){
        UserDetail userDetail = (UserDetail)authentication.getPrincipal();

        if (page == null) page = 1;
        if(page <= -1) page++;
        if(pageSize == null) pageSize = 10;
        //默认一页10，按照题目序号升序。
        PageRequest pageRequest = PageRequest.of(page,pageSize, Sort.by(Sort.Direction.ASC,"id"));
        Page<ExamPaper> examPage = examPaperService.getMyAllExamPaper(userDetail,pageRequest);
        int totalPages = examPage.getTotalPages();
        //拿出集合
        List<ExamPaper> exams = examPage.getContent();
        model.addAttribute("examLists",exams);
        model.addAttribute("TotalPages",totalPages);
        model.addAttribute("TotalElements", examPage.getTotalElements());
        model.addAttribute("Number", examPage.getNumber()-1);
        model.addAttribute("NumberOfElements",examPage.getNumberOfElements());
        model.addAttribute("page", page);
        model.addAttribute("examPapers",exams);
        return "../static/html/issuer/exam.html";
    }

    @Secured({"ROLE_ISSUER"})
    @ResponseBody
    @GetMapping("/ExamPaper/{examPaperId}")
    public RestResult<ExamPaper> getExamPaper(@PathVariable Integer examPaperId,Authentication authentication){
        UserDetail userDetail = (UserDetail)authentication.getPrincipal();
        return RestResult.success(examPaperService.getExamPaper(examPaperId,userDetail));
    }

    @Secured({"ROLE_ISSUER"})
    @ResponseBody
    @GetMapping("/getAllScore")
    public RestResult<Boolean> getAllScore(@RequestParam Integer examPaperId, HttpServletResponse resp){
        return RestResult.success(examPaperService.getAllScore(examPaperId,resp));
    }

    @Secured({"ROLE_ISSUER"})
    @GetMapping("/examView")
    public String examView(@RequestParam Integer examId,Model model){
        ExamPaper examPaper = examPaperService.getExam(examId);
        model.addAttribute("exam",examPaper);
        return "../static/html/issuer/examView.html";
    }
}
