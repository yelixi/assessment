package com.sacc.assessment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 林夕
 * Date 2021/8/31 23:29
 *
 * 选项类
 */
@Entity
@Data
@Table(name = "`option`")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
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
