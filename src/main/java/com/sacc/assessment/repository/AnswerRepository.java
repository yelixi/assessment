package com.sacc.assessment.repository;

import com.sacc.assessment.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 林夕
 * Date 2021/8/1 20:10
 */
public interface AnswerRepository extends JpaRepository<Answer,Integer> {

}