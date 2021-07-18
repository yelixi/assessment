package com.sacc.assessment.service;

import com.sacc.assessment.entity.Score;
import com.sacc.assessment.model.UserDetail;

/**
 * Created by 林夕
 * Date 2021/7/18 17:35
 */
public interface ScoreService {

    boolean correction(Score score, UserDetail userDetail);
}
