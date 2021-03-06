package com.sacc.assessment.service;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.model.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 12:44
 */
public interface ExamPaperService {
    ExamPaper createExam(ExamPaper examPaper, UserDetail userDetail);

    boolean updateExam(ExamPaper examPaper);

    Page<ExamPaper> getMyAllExamPaper( Pageable pageable);

    ExamPaper getExamPaper(Integer examPaperId, UserDetail userDetail);

    ExamPaper getExam(Integer examId);

    List<ExamPaper> getAllExam();

    List<Answer> answerList(Integer questionId, Integer examPageId);

    boolean getAllScore(Integer examPaperId, HttpServletResponse resp);

    boolean isInExamTime(Integer examId);

    List<ExamPaper> getMyUnfinishedExamPaper(UserDetail userDetail);
}
