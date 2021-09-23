package com.sacc.assessment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 试卷总分
     */
    private Integer examTotalScore;

    /**
     * 考试开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 考试结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 考试持续时间(单位：分钟)
     */
    private Integer examTime;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
