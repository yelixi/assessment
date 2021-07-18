package com.sacc.assessment.repository;

import com.sacc.assessment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 林夕
 * Date 2021/6/15 10:29
 */
public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    User queryUserByUsername(String username);

    @Modifying
    @Transactional
    void deleteUserByUsername(String username);

}
