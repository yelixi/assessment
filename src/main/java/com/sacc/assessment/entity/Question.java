package com.sacc.assessment.entity;

import com.sacc.assessment.enums.QuestionType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Describe: 问题实体类
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Question {

    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 题目类型
     */
    @Column(nullable = false)
    private QuestionType type;

    /**
     * 题目分数
     */
    private Integer score;

    /**
     * 正确答案
     */
    private String correctAnswer;

    /**
     * 题干信息
     */
    private String infoTextContent;

    /**
     * 创建人
     */
    private Integer creatUser;

    /**
     * 选项
     */
//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "question_id")
//    private List<Option> options;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    /**
     * 是否弃用
     */
    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private String questionType;


    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;


}
