package com.sacc.assessment.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 林夕
 * Date 2021/8/31 23:29
 *
 * 选项类
 */
@Entity
@Data
public class Option {

    /**
     * 自增id
     */
    @GeneratedValue
    @Id
    private Integer id;

    /**
     * 选项名称，如：A，B，C，D
     */
    private String name;

    /**
     * 选项内容
     */
    private String content;
}
