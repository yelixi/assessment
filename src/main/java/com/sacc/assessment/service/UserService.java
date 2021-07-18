package com.sacc.assessment.service;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.Role;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:32
 */
public interface UserService {

    boolean register(User user);

    boolean deleteUser(String username);

    List<User> getAllUser();

    boolean updateRole(String username, Role role);
}
