package com.sacc.assessment.service;

import com.sacc.assessment.entity.ExamPaperAnswer;
import com.sacc.assessment.model.UserDetail;

/**
 * Created by 林夕
 * Date 2021/8/1 20:26
 */
public interface ExamPaperAnswerService {
    boolean uploadAnswer(ExamPaperAnswer examPaperAnswer, UserDetail userDetail);
}
