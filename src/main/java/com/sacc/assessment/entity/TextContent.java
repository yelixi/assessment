package com.sacc.assessment.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @Describe: 试卷文本内容
 * @Author: tyf
 * @CreateTime: 2021/7/19
 **/
@Entity(name = "issue")
@EntityListeners(AuditingEntityListener.class)
@Data
public class TextContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
}

