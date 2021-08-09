package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.Question;
import com.sacc.assessment.repository.QuestionRepository;
import com.sacc.assessment.service.QuestionService;
import com.sacc.assessment.util.GetNullPropertyNamesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public Question insertQuestion(Question question, String username) {
        log.error("question"+question);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        questionRepository.save(question);
        return question;
    }

    @Override
    @Transactional
    public Boolean updateQuestion(Question question) {

        Optional<Question> q = questionRepository.findById(question.getId());
        Question question1;
        if (q.isPresent()) {
             question1 = q.get();
        }else{
            return Boolean.FALSE;
        }
        question1.setScore(question.getScore());
        question1.setCorrectAnswer(question.getCorrectAnswer());
        question1.setInfoTextContent(question.getInfoTextContent());
        question1.setDeleted(question.isDeleted());
//        log.error(question.getType().name());
        question1.setType(question.getType());
//        question1.setDeleted(question.get);
        question1.setUpdatedAt(LocalDateTime.now());
        questionRepository.saveAndFlush(question1);
        return Boolean.TRUE;
    }


    @Override
    @Transactional
    public Question selectQuestion(Integer id) {
        Optional<Question> questions = questionRepository.findById(id);
        return questions.orElse(null);
    }


    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public void deleteQuesionById(Integer id) {
        questionRepository.deleteById(id);
    }
}
