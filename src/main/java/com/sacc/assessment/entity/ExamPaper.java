package com.sacc.assessment.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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





    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
