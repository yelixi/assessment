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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/7/18 17:35
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private AnswerRepository answerRepository;

    @Resource
    private ExamPaperAnswerRepository examPaperAnswerRepository;

    @Override
    public boolean correction(ScoreForm scoreForm, UserDetail userDetail) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerRepository.getOne(scoreForm.getExamPaperAnswerId());
        Score score = new Score();
        score.setGrade(scoreForm.getGrade());
        score.setComment(scoreForm.getComment());
        score.setCreatedAt(LocalDateTime.now());
        score.setUpdatedAt(LocalDateTime.now());
        for (Answer answer : examPaperAnswer.getAnswers()) {
            if(answer.getId().equals(scoreForm.getAnswerId()))
                answer.setScore(score);
        }
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
