package com.sacc.assessment.controller;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.model.RestResult;
import com.sacc.assessment.service.ExamPaperAnswerService;
import com.sacc.assessment.service.ExamPaperService;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.UserService;
import com.sacc.assessment.vo.ChangePasswordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    /*@ResponseBody
    @PostMapping("/register")
    public RestResult<Boolean> register(@RequestBody User user){
        return RestResult.success(userService.register(user));
    }*/

    @GetMapping("/register")
    public String register(){
        return "../static/html/register.html";
    }

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

    @GetMapping("/img/{filename}")
    public void getImg(@PathVariable String filename, HttpServletResponse resp) throws IOException {


        //InputStream in = new FileInputStream("classes/static/"+filename);
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources("templates/"+filename);
        InputStream in = resources[0].getInputStream();
        byte[] buf = new byte[16384];
        int bytesRead;
        resp.reset();
        //Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
        // Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，
        // 文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
        resp.setContentType("application/x-msdownload");
        resp.setCharacterEncoding("utf-8");
        OutputStream out = new BufferedOutputStream(resp.getOutputStream());
        while ((bytesRead = in.read(buf, 0, buf.length)) >= 0) {
            out.write(buf, 0, bytesRead);//将缓冲区的数据输出到客户端浏览器
        }
        out.close();
        in.close();
    }
}
