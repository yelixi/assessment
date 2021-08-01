package com.sacc.assessment.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Describe: 试卷实体
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class ExamPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * 试卷内的问题列表
     */
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_paper_id")
    private List<Question> questions;

    /**
     * 出卷人id
     */
    private Integer userId;

    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
