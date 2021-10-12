package com.sacc.assessment.service;

import afu.org.checkerframework.checker.igj.qual.I;
import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.ExamPaperAnswerRepository;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 20:26
 */
public interface ExamPaperAnswerService {
    boolean uploadAnswer(ExamPaperAnswer examPaperAnswer, UserDetail userDetail);

    ExamPaperAnswer findOne(Integer examPaperAnswerId);

    ExamPaperAnswer findByUserIdAndExamPaperId(Integer UserId,Integer ExamPaperId);

    ExamPaperAnswer findExamPaperAnswerByAnswerId(Integer answerId);

    List<ExamPaperAnswer> getExamPaperAnswerByExamId(Integer examPaperId);


}
