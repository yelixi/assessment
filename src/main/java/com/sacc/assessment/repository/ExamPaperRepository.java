package com.sacc.assessment.repository;

import com.sacc.assessment.entity.ExamPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 12:39
 */
public interface ExamPaperRepository extends JpaRepository<ExamPaper,Integer> {

    Page<ExamPaper> findAllByUserId(Integer userId, Pageable pageable);

    ExamPaper findByIdAndUserId(Integer id,Integer userId);
}
