package com.sacc.assessment.controller;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.User;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.service.ExamPaperService;
import com.sacc.assessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String correctorHome(){
        return "../static/html/corrector/correctorHome.html";
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/home/member")
    public String memberHome(Model model){
        List<ExamPaper> examPaperList = examPaperService.getAllExam();
        model.addAttribute("examList",examPaperList);
        return "../static/html/member/memberHome.html";
    }
}
