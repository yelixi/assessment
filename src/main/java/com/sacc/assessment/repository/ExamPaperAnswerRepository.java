package com.sacc.assessment.repository;

import com.sacc.assessment.entity.ExamPaperAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 林夕
 * Date 2021/8/1 20:24
 */
public interface ExamPaperAnswerRepository extends JpaRepository<ExamPaperAnswer,Integer> {
    ExamPaperAnswer findByUserIdAndExamPaperId(Integer userId,Integer examPaperId);
}
