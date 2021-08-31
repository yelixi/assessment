package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.ExamPaperAnswerRepository;
import com.sacc.assessment.repository.ExamPaperRepository;
import com.sacc.assessment.service.ExamPaperAnswerService;
import com.sacc.assessment.service.ExamPaperService;
import com.sacc.assessment.util.GetNullPropertyNamesUtil;
import org.springframework.beans.BeanUtils;
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
        ExamPaperAnswer exitAnswer = examPaperAnswerRepository.findByUserIdAndExamPaperId(userDetail.getId(),examPaperAnswer.getExamPaperId());
        if(exitAnswer==null) {
            examPaperAnswer.setUserId(userDetail.getId());
            examPaperAnswer.setCreatedAt(LocalDateTime.now());
            examPaperAnswer.setUpdatedAt(LocalDateTime.now());
            for (Answer answer : examPaperAnswer.getAnswers()) {
                answer.setCreatedAt(LocalDateTime.now());
                answer.setUpdatedAt(LocalDateTime.now());
                answer.setUserId(userDetail.getId());
                answer.setExamPageId(examPaperAnswer.getExamPaperId());
            }
            examPaperAnswerRepository.save(examPaperAnswer);
        }
        else {
            for (Answer answer : examPaperAnswer.getAnswers()) {
                for (Answer answer1:exitAnswer.getAnswers()){
                    if(answer.getQuestionId().equals(answer1.getQuestionId())) {
                        BeanUtils.copyProperties(answer, answer1, GetNullPropertyNamesUtil.getNullPropertyNames(answer));
                        answer1.setUpdatedAt(LocalDateTime.now());
                    }
                }
            }
            examPaperAnswerRepository.save(exitAnswer);
        }
        return true;
    }

    @Override
    public ExamPaperAnswer findOne(Integer examPaperAnswerId) {
        return examPaperAnswerRepository.getOne(examPaperAnswerId);
    }

    @Override
    public boolean findByUserIdAndExamPaperId(Integer UserId, Integer ExamPaperId) {
        return examPaperAnswerRepository.findByUserIdAndExamPaperId(UserId, ExamPaperId) != null;
    }
}
