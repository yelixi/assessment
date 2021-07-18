package com.sacc.assessment.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

/**
 * Created by 林夕
 * Date 2021/7/18 15:29
 */
@Entity
@Data
public class Apply {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer problemId;

    private Integer userId;

    private Integer examId;

    private String content;

    @OneToOne
    private Score score;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
