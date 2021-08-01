package com.sacc.assessment.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by 林夕
 * Date 2021/7/18 16:33
 */
@Data
@Entity
public class Score {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 分数
     */
    private Integer grade;

    /**
     * 批改人id
     */
    private Integer correctorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * 批改人评价
     */
    private String comment;
}
