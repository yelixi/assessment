package com.sacc.assessment.service;

import com.sacc.assessment.entity.Question;
import com.sacc.assessment.entity.TextContent;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/7/8
 **/
public interface QuestionService {
    /**
     * 发布试题
     * @return 成功与否
     */
    Question insertQuestion(Question question, String Username);

    Boolean updateQuestion(Question question, TextContent textContent);

    Question selectQuestion(Integer id);

    Page<Question> page();

    List<Question> findAll();

}
