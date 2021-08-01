package com.sacc.assessment.service;

import com.sacc.assessment.entity.Answer;
import com.sacc.assessment.entity.Score;
import com.sacc.assessment.form.ScoreForm;
import com.sacc.assessment.model.UserDetail;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/7/18 17:35
 */
public interface ScoreService {

    boolean correction(ScoreForm scoreForm, UserDetail userDetail);

    boolean updateCorrection(ScoreForm scoreForm,UserDetail userDetail);

    List<Answer> getAllAnswer(Integer examPaperAnswerId);

    Answer getAnswer(Integer answerId);
}
