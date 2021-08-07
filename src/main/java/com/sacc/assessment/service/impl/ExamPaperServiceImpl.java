package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.Question;
import com.sacc.assessment.repository.ExamPaperRepository;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.service.ExamPaperService;
import com.sacc.assessment.util.GetNullPropertyNamesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 12:53
 */
@Service
@Slf4j
public class ExamPaperServiceImpl implements ExamPaperService {

    @Resource
    private ExamPaperRepository examPaperRepository;

    @Override
    public boolean createExam(ExamPaper examPaper, UserDetail userDetail) {
        examPaper.setUserId(userDetail.getId());
        examPaper.setCreatedAt(LocalDateTime.now());
        examPaper.setUpdatedAt(LocalDateTime.now());
        examPaper.getQuestions().forEach(x->x.setCreatedAt(LocalDateTime.now()));
        examPaper.getQuestions().forEach(x->x.setUpdatedAt(LocalDateTime.now()));
        examPaperRepository.save(examPaper);
        return true;
    }

    @Override
    public boolean updateExam(ExamPaper examPaper) {
        ExamPaper exam = examPaperRepository.getOne(examPaper.getId());
        exam.setCreatedAt(examPaper.getCreatedAt());
        exam.setUserId(examPaper.getUserId());
        for(Question question: exam.getQuestions()){
            for(Question q: examPaper.getQuestions()){
                if(question.getId().equals(q.getId())) {
                    BeanUtils.copyProperties(q, question, GetNullPropertyNamesUtil.getNullPropertyNames(q));
                    q.setCreatedAt(LocalDateTime.now());
                }
            }
        }

        for(Question question: examPaper.getQuestions()){
            if(question.getId()==null){
                question.setCreatedAt(LocalDateTime.now());
                question.setUpdatedAt(LocalDateTime.now());
                question.setCreatUser(examPaper.getUserId());
                exam.getQuestions().add(question);
            }
        }

        examPaperRepository.save(exam);
        return true;
    }

    @Override
    public List<ExamPaper> getMyAllExamPaper(UserDetail userDetail) {
        return examPaperRepository.findAllByUserId(userDetail.getId());
    }

    @Override
    public ExamPaper getExamPaper(Integer examPaperId, UserDetail userDetail) {
        return examPaperRepository.findByIdAndUserId(examPaperId, userDetail.getId());
    }

    @Override
    public ExamPaper getExam(Integer examId) {
        return examPaperRepository.getOne(examId);
    }

    @Override
    public List<ExamPaper> getAllExam() {
        return examPaperRepository.findAll();
    }
}
