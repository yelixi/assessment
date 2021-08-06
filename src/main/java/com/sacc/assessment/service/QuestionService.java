package com.sacc.assessment.service;

import com.sacc.assessment.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Boolean updateQuestion(Question question);

    Question selectQuestion(Integer id);

    Page<Question> findAll(Pageable pageable);

    List<Question> findAll();



}
