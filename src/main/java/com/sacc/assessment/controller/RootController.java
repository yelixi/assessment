package com.sacc.assessment.controller;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.ResultEnum;
import com.sacc.assessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 林夕
 * Date 2021/6/15 10:10
 */
@PreAuthorize("hasRole('ROOT')")
@Controller
@RequestMapping("/root")
public class RootController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            throw new RuntimeException(result.getObjectName()+result.getTarget());
        }
        userService.register(user);
    }

    @GetMapping("/home")
    public String home(){
        return "../static/html/root/root.html";
    }
}
