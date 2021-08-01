package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.ExamPaperAnswerRepository;
import com.sacc.assessment.repository.ExamPaperRepository;
import com.sacc.assessment.service.ExamPaperAnswerService;
import com.sacc.assessment.service.ExamPaperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * Created by 林夕
 * Date 2021/8/1 20:26
 */
@Service
public class ExamPaperAnswerServiceImpl implements ExamPaperAnswerService {

    @Resource
    public ExamPaperAnswerRepository examPaperAnswerRepository;

    @Override
    public boolean uploadAnswer(ExamPaperAnswer examPaperAnswer, UserDetail userDetail) {
        examPaperAnswer.setUserId(userDetail.getId());
        examPaperAnswer.setCreatedAt(LocalDateTime.now());
        examPaperAnswer.setUpdatedAt(LocalDateTime.now());
        for (Answer answer : examPaperAnswer.getAnswers()) {
            answer.setCreatedAt(LocalDateTime.now());
            answer.setUpdatedAt(LocalDateTime.now());
            answer.setUserId(userDetail.getId());
        }
        examPaperAnswerRepository.save(examPaperAnswer);
        return true;
    }
}
