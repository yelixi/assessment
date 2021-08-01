package com.sacc.assessment.repository;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Paper;

/**
 * @Describe: 试卷接口
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
public interface PaperRepository extends JpaRepository<ExamPaper, Integer> {
}
