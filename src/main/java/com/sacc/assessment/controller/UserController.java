package com.sacc.assessment.controller;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.User;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.service.ExamPaperAnswerService;
import com.sacc.assessment.service.ExamPaperService;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/7/8 17:13
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    @GetMapping("/login")
    public String login(){
        return "../static/html/login.html";
    }

    @ResponseBody
    @PostMapping("/register")
    public RestResult<Boolean> register(@RequestBody User user){
        return RestResult.success(userService.register(user));
    }

    @GetMapping("/register")
    public String register(){
        return "../static/html/user/register.html";
    }

    @PreAuthorize("hasRole('ROOT')")
    @GetMapping("/home/root")
    public String rootHome(){
        return "../static/html/root/rootHome.html";
    }

    @PreAuthorize("hasRole('ISSUER')")
    @GetMapping("/home/issuer")
    public String issuerHome(){
        return "../static/html/issuer/issuerHome.html";
    }

    @PreAuthorize("hasRole('CORRECTOR')")
    @GetMapping("/home/corrector")
    public String correctorHome(Model model){
        List<ExamPaper> examPaperList = examPaperService.getAllExam();
        model.addAttribute("examList",examPaperList);
        return "../static/html/corrector/correctorHome.html";
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/home/member")
    public String memberHome(Model model, Authentication authentication){
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
        return "../static/html/member/memberHome.html";
    }
    @GetMapping("/myInfo")
    public String myInfo(Model model, Authentication authentication){
        UserDetail principal = (UserDetail) authentication.getPrincipal();
        model.addAttribute("ROLE", principal.getRole());
        model.addAttribute("userName", principal.getUsername());
        model.addAttribute("page",
                "../static/html/common/fragments.html::" + principal.getRole().toString().toLowerCase()+"_menu(999)");
        return "../static/html/common/userInfo";
    }

   /* @ResponseBody
    @GetMapping("/js/{url}")
    public String getJs(@PathVariable String url){
        return "../static/js"+url;
    }*/
}
