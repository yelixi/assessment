package com.sacc.assessment.service;

import com.sacc.assessment.entity.TextContent;
import com.sacc.assessment.repository.TextContentRepository;

import javax.annotation.Resource;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/7/20
 **/
public interface TextContentService {

    TextContent selectById(Integer id);

    TextContent insert(TextContent textContent);

    TextContent upfateById(TextContent textContent);


}
