package com.sacc.assessment.entity;

import com.sacc.assessment.enums.QuestionType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Describe: 问题实体类
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 题目类型
     */
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
