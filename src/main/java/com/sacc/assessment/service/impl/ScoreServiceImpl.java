package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.entity.Score;
import com.sacc.assessment.form.ScoreForm;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.AnswerRepository;
import com.sacc.assessment.repository.ExamPaperAnswerRepository;
import com.sacc.assessment.repository.ScoreRepository;
import com.sacc.assessment.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/7/18 17:35
 */
@Service
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private AnswerRepository answerRepository;

    @Resource
    private ExamPaperAnswerRepository examPaperAnswerRepository;

    @Resource
    private ScoreRepository scoreRepository;

    @Override
    public boolean correction(ScoreForm scoreForm, UserDetail userDetail) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerRepository.getOne(scoreForm.getExamPaperAnswerId());
        for (Answer answer : examPaperAnswer.getAnswers()) {
            if(answer.getId().equals(scoreForm.getAnswerId())) {
                Score score;
                if(answer.getScore()==null) {
                    score = new Score();
                    score.setCreatedAt(LocalDateTime.now());
                }else {
                    score = answer.getScore();
                }
                score.setGrade(scoreForm.getGrade());
                score.setComment(scoreForm.getComment());
                score.setUpdatedAt(LocalDateTime.now());
                score.setCorrectorId(userDetail.getId());
                answer.setScore(score);
                scoreRepository.save(score);
                break;
            }
        }
        log.error(examPaperAnswer.toString());
        examPaperAnswerRepository.save(examPaperAnswer);
        return true;
    }

    @Override
    public boolean updateCorrection(ScoreForm scoreForm, UserDetail userDetail) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerRepository.getOne(scoreForm.getExamPaperAnswerId());
        for (Answer answer : examPaperAnswer.getAnswers()) {
            if(answer.getId().equals(scoreForm.getAnswerId())) {
                Score score = answer.getScore();
                if(scoreForm.getComment()!=null)
                    score.setComment(scoreForm.getComment());
                if(scoreForm.getGrade()!=null)
                    score.setGrade(scoreForm.getGrade());
                score.setUpdatedAt(LocalDateTime.now());
            }
        }
        examPaperAnswerRepository.save(examPaperAnswer);
        return true;
    }

    @Override
    public List<Answer> getAllAnswer(Integer examPaperAnswerId) {
        return examPaperAnswerRepository.getOne(examPaperAnswerId).getAnswers();
    }

    @Override
    public Answer getAnswer(Integer answerId) {
        return answerRepository.getOne(answerId);
    }
}
