package com.sacc.assessment.controller;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.service.ExamPaperAnswerService;
import com.sacc.assessment.service.ExamPaperService;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.UserService;
import com.sacc.assessment.vo.ChangePasswordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /*@ResponseBody
    @PostMapping("/register")
    public RestResult<Boolean> register(@RequestBody User user){
        return RestResult.success(userService.register(user));
    }*/

    /*@GetMapping("/register")
    public String register(){
        return "../static/html/user/register.html";
    }*/

    @Secured({"ROLE_ROOT"})
    @GetMapping("/home/root")
    public String rootHome(){
        return "../static/html/root/rootHome.html";
    }

    @Secured({"ROLE_ISSUER"})
    @GetMapping("/home/issuer")
    public String issuerHome(){
        return "../static/html/issuer/issuerHome.html";
    }

    @Secured({"ROLE_CORRECTOR"})
    @GetMapping("/home/corrector")
    public String correctorHome(){
        return "../static/html/corrector/correctorHome.html";
    }

    @Secured({"ROLE_MEMBER"})
    @GetMapping("/home/member")
    public String memberHome(Model model, Authentication authentication){
        return "../static/html/member/memberHome.html";
    }
    @GetMapping("/myInfo")
    public String myInfo(Model model, Authentication authentication){
        UserDetail principal = (UserDetail) authentication.getPrincipal();
        model.addAttribute("ROLE", principal.getRole());
        model.addAttribute("userName", principal.getUsername());
        model.addAttribute("userEmail",principal.getEmail());
        model.addAttribute("userId", principal.getStudentId());
        model.addAttribute("page",
                "../static/html/common/fragments.html::" + principal.getRole().toString().toLowerCase()+"_menu(999)");
        return "../static/html/common/userInfo";
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public RestResult<Boolean> changePassword(@RequestBody ChangePasswordVo changePasswordVo,Authentication authentication){
        UserDetail principal = (UserDetail) authentication.getPrincipal();
        return RestResult.success(userService.changePassword(changePasswordVo,principal));
    }

    @GetMapping("/password")
    public String password(Model model, Authentication authentication){
        UserDetail principal = (UserDetail)authentication.getPrincipal();
        model.addAttribute("page",
                "../static/html/common/fragments.html::" + principal.getRole().toString().toLowerCase()+"_menu(999)");
        return "../static/html/common/changePassword.html";
    }
   /* @ResponseBody
    @GetMapping("/js/{url}")
    public String getJs(@PathVariable String url){
        return "../static/js"+url;
    }*/
}
