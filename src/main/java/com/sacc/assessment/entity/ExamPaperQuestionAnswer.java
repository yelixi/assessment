package com.sacc.assessment.entity;

import com.sacc.assessment.enums.QuestionType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Describe: 单份用户试卷答案实体
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class ExamPaperQuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * 试卷ID
     */
    private Long examPaperId;

    /**
     * 用户提交试卷ID
     */
    private Long examPaperAnswerId;

    /**
     * 题目类型
     */
    private QuestionType questionType;

    /**
     * 题目满分
     */
    private Double QuestionScore;

    /**
     * 问题内容
     */
    private Long questionTextContentId;

    /**
     * 填空简答做题内容
     */
    private Long answerTextContentId;

    /**
     * 是否正确
     */
    private boolean isRight;

    /**
     * 做题人
     */
    private Long creatStudentId;

    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
