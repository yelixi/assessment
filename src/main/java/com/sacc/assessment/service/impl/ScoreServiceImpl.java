package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.Score;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.ScoreRepository;
import com.sacc.assessment.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 林夕
 * Date 2021/7/18 17:35
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ScoreRepository scoreRepository;

    @Override
    public boolean correction(Score score, UserDetail userDetail) {
        score.setCorrectorId(userDetail.getId());
        scoreRepository.save(score);
        return true;
    }
}
