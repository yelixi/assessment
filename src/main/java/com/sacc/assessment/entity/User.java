package com.sacc.assessment.entity;

import com.sacc.assessment.enums.Role;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 林夕
 * Date 2021/6/15 9:59
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    //权限
    @NotNull
    private Role role;
}
