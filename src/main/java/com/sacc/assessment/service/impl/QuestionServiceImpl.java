package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.Question;
import com.sacc.assessment.entity.TextContent;
import com.sacc.assessment.repository.QuestionRepository;
import com.sacc.assessment.service.QuestionService;
import com.sacc.assessment.service.TextContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/7/8
 **/

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionRepository questionRepository;
    @Resource
    private TextContentService textContentService;

    @Override
    @Transactional
    public Question insertQuestion(Question question, String username) {
        log.error("question"+question);

        //题干等信息
        TextContent infoContent = new TextContent();
        infoContent.setContent("问题测试");
        infoContent.setUpdatedAt(LocalDateTime.now());
        textContentService.insert(infoContent);

        question.setInfoTextContentId(infoContent.getId());
        question.setCreatedAt(LocalDateTime.now());
        questionRepository.save(question);
        return question;
    }

    @Override
    @Transactional
    public Boolean updateQuestion(Question question, TextContent textContent) {

        Optional<Question> q = questionRepository.findById(question.getId());
        Question question1;
        if (q.isPresent()) {
             question1 = q.get();
        }else{
            return Boolean.FALSE;
        }
        question1.setUpdatedAt(LocalDateTime.now());
        question1.setScore(question.getScore());
        question1.setType(question.getType());
        questionRepository.saveAndFlush(question1);

        //更新题干等信息
        //如果内容为null直接返回
        if(textContent == null) return Boolean.TRUE;
        TextContent textContentById = textContentService.selectById(question1.getInfoTextContentId());
        textContent.setId(textContentById.getId());
        textContentService.upfateById(textContent);
        return Boolean.TRUE;
    }


    @Override
    @Transactional
    public Question selectQuestion(Integer id) {
        Optional<Question> questions = questionRepository.findById(id);
        questions.ifPresent(new Consumer<Question>() {
            @Override
            public void accept(Question question) {
                Integer infoTextContentId = question.getInfoTextContentId();
                TextContent textContent = textContentService.selectById(infoTextContentId);

            }
        });
        return questions.orElse(null);
    }

    @Override
    public Page<Question> page() {
        return null;
    }

    @Override
    public List<Question> findAll() {
        List<Question> questions = questionRepository.findAll();
        return questions;
    }
}
