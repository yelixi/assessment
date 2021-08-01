package com.sacc.assessment.entity;

import com.sacc.assessment.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Describe: 用户类
 * @Author: tyf
 * @CreateTime: 2021/7/8
 **/
@Entity(name = "user")
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "user",indexes = {
                @Index(columnList = "studentId")
}
)
public class User{
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*@Column(nullable = false, unique = true)
    private String email;*/
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String studentId;
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, unique = true)
    private String username;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
