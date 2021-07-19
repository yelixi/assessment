package com.sacc.assessment.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @Describe: 单份用户试卷答案实体
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@Entity(name = "issue")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ExamPaperAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
}
