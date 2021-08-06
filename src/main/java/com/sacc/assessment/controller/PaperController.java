package com.sacc.assessment.controller;

import com.sacc.assessment.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Describe: 试卷管理
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@PreAuthorize("hasRole('ISSUER')")
@RestController
@RequestMapping("/papers")
public class PaperController {

    @Autowired
    PaperService paperService;

    /**
     * TODO:试卷管理（增删改查）
     */
    @GetMapping("/")
    public void getPaper(){


    }

    @GetMapping("/{id}")
    public void getPaper(@PathVariable Long id){

    }

    @PostMapping("/")
    public void addPaper(){

    }

    @DeleteMapping("/{id}")
    public void removePaper(@PathVariable Long id){

    }

    @PutMapping("/{id}")
    public void alterPapers(@PathVariable Long id){

    }
}
