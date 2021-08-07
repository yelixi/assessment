package com.sacc.assessment.controller;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.ResultEnum;
import com.sacc.assessment.enums.Role;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:10
 */
@PreAuthorize("hasRole('ROOT')")
@Controller
@RequestMapping("/root")
@Slf4j
public class RootController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/register")
    public RestResult<Boolean> register(@RequestBody User user, BindingResult result){
        log.error(user.toString());
        if (result.hasErrors()){
            throw new RuntimeException(result.getObjectName()+result.getTarget());
        }
        return RestResult.success(userService.register(user));
    }

    @ResponseBody
    @DeleteMapping("/deleteUser")
    public RestResult<Boolean> deleteUser(@RequestParam String username){
        return RestResult.success(userService.deleteUser(username));
    }


    @GetMapping("/getAllUser")
    public String getAllUser(Model model){
        model.addAttribute("userList",userService.getAllUser());
        return "../static/html/root/allUser.html";
    }

    @ResponseBody
    @PutMapping("/updateRole")
    public RestResult<Boolean> updateRole(@RequestParam String username, Role role){
        return RestResult.success(userService.updateRole(username,role));
    }

    @GetMapping("/updateRole")
    public String updateRole(){
        return "../static/html/root/updateRole.html";
    }

    @GetMapping("/home")
    public String home(){
        return "../static/html/root/root.html";
    }
}
