package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.enums.ResultEnum;
import com.sacc.assessment.exception.BusinessException;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.AnswerRepository;
import com.sacc.assessment.repository.ExamPaperAnswerRepository;
import com.sacc.assessment.repository.ExamPaperRepository;
import com.sacc.assessment.service.ExamPaperAnswerService;
import com.sacc.assessment.util.GetNullPropertyNamesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by 林夕
 * Date 2021/8/1 20:26
 */
@Service
@Slf4j
public class ExamPaperAnswerServiceImpl implements ExamPaperAnswerService {

    @Resource
    public ExamPaperAnswerRepository examPaperAnswerRepository;

    @Resource
    public AnswerRepository answerRepository;

    @Resource
    public ExamPaperRepository examPaperRepository;

    @Override
    public boolean uploadAnswer(ExamPaperAnswer examPaperAnswer, UserDetail userDetail) {
        ExamPaper examPaper = examPaperRepository.getOne(examPaperAnswer.getExamPaperId());
        if(examPaper.getEndTime().isBefore(LocalDateTime.now()))
            throw new BusinessException(ResultEnum.TIME_HAS_EXPIRED);
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
                boolean isUpdate = false;
                for (Answer answer1:exitAnswer.getAnswers()){
                    if(answer.getQuestionId().equals(answer1.getQuestionId())) {
                        BeanUtils.copyProperties(answer, answer1, GetNullPropertyNamesUtil.getNullPropertyNames(answer));
                        answer1.setUpdatedAt(LocalDateTime.now());
                        isUpdate=true;
                    }
                }
                if(!isUpdate) {
                    answer.setUserId(userDetail.getId());
                    answer.setExamPageId(examPaperAnswer.getExamPaperId());
                    answer.setCreatedAt(LocalDateTime.now());
                    answer.setUpdatedAt(LocalDateTime.now());
                    exitAnswer.getAnswers().add(answer);
                }
            }
            exitAnswer.setUpdatedAt(LocalDateTime.now());
            examPaperAnswerRepository.save(exitAnswer);
        }
        return true;
    }

    @Override
    public ExamPaperAnswer findOne(Integer examPaperAnswerId) {
        return examPaperAnswerRepository.getOne(examPaperAnswerId);
    }

    @Override
    public ExamPaperAnswer findByUserIdAndExamPaperId(Integer UserId, Integer ExamPaperId) {
        return examPaperAnswerRepository.findByUserIdAndExamPaperId(UserId, ExamPaperId);
    }

    @Override
    public ExamPaperAnswer findExamPaperAnswerByAnswerId(Integer answerId) {
        Integer paperAnswerId = answerRepository.findPaperAnswerIdByAnswerId(answerId);
        Optional<ExamPaperAnswer> examPaperAnswer = examPaperAnswerRepository.findById(paperAnswerId);
        log.error(examPaperAnswer.get().getId().toString());
        if(examPaperAnswer.isPresent()) return examPaperAnswer.get();
        return null;
    }

    @Override
    public List<ExamPaperAnswer> getExamPaperAnswerByExamId(Integer examPaperId) {
        return examPaperAnswerRepository.findAllByExamPaperId(examPaperId);
    }


//    @Override
//    public ExamPaperAnswer findExamPaperAnswerByAnswerId(Integer answerId) {
////        List<Integer> paperAnswerId = answerRepository.findPaperAnswerIdByAnswerId(answerId);
////        System.out.println(paperAnswerId.get(0));
////        List<ExamPaperAnswer> allByExamPaperId = examPaperAnswerRepository.getOne(paperAnswerId.get(0));
////        return allByExamPaperId.get(0);
////        examPaperAnswerRepository.findByAnswer(answer);
//    }

}
