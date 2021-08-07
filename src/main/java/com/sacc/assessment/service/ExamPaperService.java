package com.sacc.assessment.service;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.model.UserDetail;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 12:44
 */
public interface ExamPaperService {
    boolean createExam(ExamPaper examPaper, UserDetail userDetail);

    boolean updateExam(ExamPaper examPaper);

    List<ExamPaper> getMyAllExamPaper(UserDetail userDetail);

    ExamPaper getExamPaper(Integer examPaperId, UserDetail userDetail);

    ExamPaper getExam(Integer examId);
}
