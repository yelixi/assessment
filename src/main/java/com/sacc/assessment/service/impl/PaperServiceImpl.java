package com.sacc.assessment.service.impl;

import com.sacc.assessment.repository.PaperRepository;
import com.sacc.assessment.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Describe: 试卷CRUD实现层
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    PaperRepository paperRepository;

}
