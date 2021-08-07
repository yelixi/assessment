package com.sacc.assessment.controller;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.UserService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by 林夕
 * Date 2021/7/8 17:13
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

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

    @GetMapping("/home")
    public String home(Model model, Principal principal){
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail user = (UserDetail) details;
//        System.out.println(user.getRole());
        model.addAttribute("ROLE",user.getRole());
        return "../static/html/home.html";
    }
}
