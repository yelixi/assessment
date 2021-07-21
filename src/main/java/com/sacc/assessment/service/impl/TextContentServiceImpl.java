package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.TextContent;
import com.sacc.assessment.repository.TextContentRepository;
import com.sacc.assessment.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/7/20
 **/
@Service
public class TextContentServiceImpl implements TextContentService {

    @Autowired
    private TextContentRepository textContentRepository;

    @Override
    public TextContent selectById(Integer id) {
        return null;
    }

    @Override
    public TextContent insert(TextContent textContent) {
        textContentRepository.saveAndFlush(textContent);
        return textContent;
    }

    @Override
    public TextContent upfateById(TextContent textContent) {
        return null;
    }
}
