package com.sacc.assessment.repository;

import com.sacc.assessment.entity.ExamPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 12:39
 */
public interface ExamPaperRepository extends JpaRepository<ExamPaper,Integer> {

    List<ExamPaper> findAllByUserId(Integer userId);

    ExamPaper findByIdAndUserId(Integer id,Integer userId);
}
