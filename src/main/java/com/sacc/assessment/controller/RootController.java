package com.sacc.assessment.controller;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.ResultEnum;
import com.sacc.assessment.enums.Role;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:10
 */
//@PreAuthorize("hasRole('ROOT')")
@Controller
@RequestMapping("/root")
@Slf4j
public class RootController {

    @Autowired
    private UserService userService;

    @Secured({"ROLE_ROOT"})
    @ResponseBody
    @PostMapping("/register")
    public RestResult<Boolean> register(@RequestBody User user, BindingResult result){
        log.error(user.toString());
        if (result.hasErrors()){
            throw new RuntimeException(result.getObjectName()+result.getTarget());
        }
        return RestResult.success(userService.register(user));
    }

    @Secured({"ROLE_ROOT"})
    @ResponseBody
    @DeleteMapping("/deleteUser")
    public RestResult<Boolean> deleteUser(@RequestParam String username){
        return RestResult.success(userService.deleteUser(username));
    }

    @Secured({"ROLE_ROOT"})
    @GetMapping("/addUser")
    public String addUser(){
        return "../static/html/root/addUser.html";
    }

    @Secured({"ROLE_ROOT"})
    @GetMapping("/registerAll")
    public String registerAll(){
        return "../static/html/root/registerAll.html";
    }

    @Secured({"ROLE_ROOT"})
    @ResponseBody
    @PostMapping("/registerAll")
    public RestResult<Boolean> registerAll(MultipartFile file) throws IOException {
        return RestResult.success(userService.registerAll(file));
    }

    @Secured({"ROLE_ROOT"})
    @GetMapping("/getAllUser")
    public String getAllUser(Model model){
        model.addAttribute("userList",userService.getAllUser());
        return "../static/html/root/allUser.html";
    }

    @Secured({"ROLE_ROOT"})
    @ResponseBody
    @PutMapping("/updateRole")
    public RestResult<Boolean> updateRole(@RequestParam String username, Role role){
        return RestResult.success(userService.updateRole(username,role));
    }

    @Secured({"ROLE_ROOT"})
    @GetMapping("/updateRole")
    public String updateRole(){
        return "../static/html/root/updateRole.html";
    }

    @Secured({"ROLE_ROOT"})
    @ResponseBody
    @PostMapping("/deleteAll")
    public RestResult<Boolean> deleteAll(MultipartFile file) throws IOException {
        return RestResult.success(userService.deleteAll(file));
    }
}
