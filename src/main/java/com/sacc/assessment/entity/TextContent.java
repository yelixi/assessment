package com.sacc.assessment.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Describe: 试卷文本内容
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@Entity()
@EntityListeners(AuditingEntityListener.class)
@Data
public class TextContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    /**
     * 内容
     */
    private String content;

    /**
     * 创建更新时间
     */
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}

