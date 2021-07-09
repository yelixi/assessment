package com.sacc.assessment.repository;

import com.sacc.assessment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 林夕
 * Date 2021/6/15 10:29
 */
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    User queryUserByUsername(String username);
}
