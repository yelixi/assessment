package com.sacc.assessment.mapper;

import com.sacc.assessment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/7/8
 **/
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByStudentId(String s);
    List findMenuByStudentId(String s);
}
