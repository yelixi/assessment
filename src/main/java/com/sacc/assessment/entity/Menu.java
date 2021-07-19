package com.sacc.assessment.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @Describe: 用户菜单
 * @Author: tyf
 * @CreateTime: 2021/7/9
 **/
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Menu {
    /**
     * 主键自增ID
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 对象
     */
    private String PermissionRole;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单链接
     */
    private String url;

    /**
     * 父级菜单
     */
    private Long parentId;

    /**
     * 前端图标
     */
    private byte[] icon;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
