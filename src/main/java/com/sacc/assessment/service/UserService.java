package com.sacc.assessment.service;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.Role;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.vo.ChangePasswordVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    User getUser(Integer userId);

    boolean registerAll(MultipartFile file) throws IOException;

    boolean deleteAll(MultipartFile file) throws IOException;

    boolean changePassword(ChangePasswordVo changePasswordVo, UserDetail userDetail);
}
