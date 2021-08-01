package com.sacc.assessment.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

/**
 * Created by 林夕
 * Date 2021/8/1 15:14
 * 回答表单
 */

@Entity
@Data
public class Answer {

    @Id
    @GeneratedValue
    private Integer id;

    private String answer;

    private Integer userId;

    private Integer questionId;

    @OneToOne
    private Score score;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}