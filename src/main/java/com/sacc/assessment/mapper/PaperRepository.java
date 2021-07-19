package com.sacc.assessment.mapper;

import com.sacc.assessment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Paper;

/**
 * @Describe: 试卷
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
public interface PaperRepository extends JpaRepository<Paper, Integer> {
}
