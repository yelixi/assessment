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

    private Integer grade;

    private Integer correctorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String comment;
}
