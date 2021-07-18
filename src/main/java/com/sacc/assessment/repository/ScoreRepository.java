package com.sacc.assessment.repository;

import com.sacc.assessment.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 林夕
 * Date 2021/7/18 17:39
 */
public interface ScoreRepository extends JpaRepository<Score,Integer>, JpaSpecificationExecutor<Score> {
}
